package com.bob.redwall.entity.npc;

import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSyncCap;
import com.bob.redwall.common.MessageUIInteract;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.entity.ai.EntityAIAttackMeleeNPC;
import com.bob.redwall.entity.ai.EntityAIAttackMeleeNPC.MobAttackStrategy;
import com.bob.redwall.entity.ai.EntityAIOpenModDoor;
import com.bob.redwall.entity.ai.EntityAITargetNPC;
import com.bob.redwall.entity.ai.pathfind.NPCPathNavigate;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.factions.Faction.FactionStatus;
import com.bob.redwall.init.SpeechHandler;
import com.google.common.base.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class EntityAbstractNPC extends EntityCreature {
	private static final DataParameter<Boolean> MALE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<String> SKIN = EntityDataManager.<String>createKey(EntityAbstractNPC.class, DataSerializers.STRING);
	private static final DataParameter<ITextComponent> TALKING = EntityDataManager.<ITextComponent>createKey(EntityAbstractNPC.class, DataSerializers.TEXT_COMPONENT);
	private static final DataParameter<Boolean> TALKING_ACTIVE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TALKING_TIME = EntityDataManager.<Integer>createKey(EntityAbstractNPC.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ATTACK_COOLDOWN = EntityDataManager.<Integer>createKey(EntityAbstractNPC.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> ATTACKING_ACTIVE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);

	private String customMessage;
	
	public EntityAbstractNPC(World worldIn) {
		this(worldIn, true);
	}
	
	public EntityAbstractNPC(World worldIn, boolean male) {
		super(worldIn);
		this.setTimeSinceLastTalk(0);
		this.setIsMale(male);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
        this.setSize(0.6F, 1.8F);
	}
    
    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new NPCPathNavigate(this, worldIn);
    }
	
	@Override
	public boolean hasCustomName() {
		return true;
	}
	
	public boolean hasCustomMessage() {
		return this.getCustomMessage() != null;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(!this.world.isRemote) {
			if(this.isPotionActive(StatusEffect.POISON) && this.ticksExisted % (80 / (this.getActivePotionEffect(StatusEffect.POISON).getAmplifier() + 1)) == 0) this.attackEntityFrom(new DamageSource("poison").setDamageBypassesArmor().setMagicDamage(), 2.0F);
			this.setTimeSinceLastTalk(this.getTimeSinceLastTalk() + 1);
			if(this.getTimeSinceLastTalk() > 200) {
				this.setTalkingActive(false);
			}
			

			IAttacking attacking = this.getCapability(AttackingProvider.ATTACKING_CAP, null);
			if(this.getCooldown() > 0) this.setCooldown(this.getCooldown() - 1);
			float f = 1.0F - ((float)this.getCooldown() / (float)this.getSwingCooldown());
			
			if(f >= 0.28875F && attacking.get() && !this.isDead) {
				attacking.set(false);
	            float i = (float)this.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
	            
	            label1: {
		            RayTraceResult result = RedwallUtils.raytrace(this, i);
		            if(result == null) break label1;
					Entity entity = result.entityHit;
					if(entity != null && !(entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow)) {
						RedwallUtils.doAttack(this, entity);
					}
	            }
			}
	
			IDefending defending = this.getCapability(DefendingProvider.DEFENDING_CAP, null);
			Ref.NETWORK.sendToAllTracking(new MessageSyncCap(MessageSyncCap.Mode.MOB_ATTACK_MODE, attacking.getMode(), this.getEntityId()), this);
			Ref.NETWORK.sendToAllTracking(new MessageSyncCap(MessageSyncCap.Mode.MOB_DEFENDING, defending.getMode() + (defending.get() ? 8 : 0), this.getEntityId()), this);
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		IAttacking a = this.getCapability(AttackingProvider.ATTACKING_CAP, null);
		if(a.get() || this.getCooldown() > 0) return false;
		a.set(true);
		this.resetCooldown();
		return super.attackEntityAsMob(entity);
	}
	
	public void talk(EntityPlayer player) {
		if(!this.world.isRemote) {
			if(player instanceof EntityPlayerMP) {
				Ref.NETWORK.sendTo(new MessageUIInteract(this.getEntityId()), (EntityPlayerMP)player);
			}
			if(!this.getFaction().playerHasContact(player)) this.getFaction().playerContactFaction(player);
			this.setTimeSinceLastTalk(0);
		} else {
			List<String> speechbank = this.getSpeechbank(this.getOpinionOfPlayer(Minecraft.getMinecraft().player));
			String string = String.format(speechbank.get(this.getRNG().nextInt(speechbank.size())), Minecraft.getMinecraft().player.getDisplayName().getUnformattedText());
			Ref.NETWORK.sendToServer(new MessageUIInteractServer(MessageUIInteractServer.Mode.NPC_TALK, this.getEntityId(), string));
			this.setTimeSinceLastTalk(0);
		}
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(!this.world.isRemote) {
			this.talk(player);
		}
    	return true;
    }
	
	public void setTalking(ITextComponent speech) {
        this.dataManager.set(TALKING, speech);
    }

    public ITextComponent getTalking() {
        return this.dataManager.get(TALKING);
    }
    
    public void setTalkingActive(boolean speech) {
        this.dataManager.set(TALKING_ACTIVE, speech);
    }

    public boolean getTalkingActive() {
        return this.dataManager.get(TALKING_ACTIVE);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(MALE, true);
        this.dataManager.register(SKIN, Ref.MODID + this.getSkinPath() + "1m.png");
        this.dataManager.register(TALKING, new TextComponentString(""));
        this.dataManager.register(TALKING_ACTIVE, false);
        this.dataManager.register(TALKING_TIME, 0);
        this.dataManager.register(ATTACK_COOLDOWN, 0);
        this.dataManager.register(ATTACKING_ACTIVE, false);
        
        this.setSkin(Ref.MODID + this.getSkinPath() + "1m.png");
        this.setCustomNameTag(this.getIsMale() ? this.getNamesBankMale().get(this.getRNG().nextInt(this.getNamesBankMale().size())) : this.getNamesBankFemale().get(this.getRNG().nextInt(this.getNamesBankFemale().size())));
    }
	
    @Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttributeMap().registerAttribute(EntityPlayer.REACH_DISTANCE);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(4.0D);
    }
    
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeAllModifiers();
        
    	this.setIsMale(this.getRNG().nextBoolean());
    	int numSkin = this.getRNG().nextInt(this.numVariants()) + 1;
    	this.setSkin(Ref.MODID + this.getSkinPath() + numSkin + (this.getIsMale() ? "m" : "f") + ".png");
    	this.setCustomNameTag(this.getIsMale() ? this.getNamesBankMale().get(this.getRNG().nextInt(this.getNamesBankMale().size())) : this.getNamesBankFemale().get(this.getRNG().nextInt(this.getNamesBankFemale().size())));
    	this.setCooldown(0);
    	return livingdata;
    }
	
    @Override
	protected boolean canDespawn() {
        return false;
    }

    public void setIsMale(boolean isMale) {
        this.dataManager.set(MALE, Boolean.valueOf(isMale));
    }

    public boolean getIsMale() {
        return this.dataManager.get(MALE);
    }

    public void setSkin(String skin) {
        this.dataManager.set(SKIN, skin);
    }

    public String getSkin() {
        return this.dataManager.get(SKIN);
    }
    
    @Override
    public void setAttackTarget(@Nullable EntityLivingBase target) {
    	super.setAttackTarget(target);
    	this.dataManager.set(ATTACKING_ACTIVE, target != null);
    }
    
    public boolean isTargetLocked() {
    	return this.dataManager.get(ATTACKING_ACTIVE);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }
    
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(1, new EntityAIAttackMeleeNPC(this, 1.0D, true, MobAttackStrategy.HUMAN));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]) {
        	@Override
        	protected boolean isSuitableTarget(EntityLivingBase input, boolean includeInvincibles) {
        		return super.isSuitableTarget(input, includeInvincibles) && EntityAbstractNPC.this.isSuitableTarget(input) && EntityAbstractNPC.this.willFightEntityRevenge(input);
        	}
        });
        this.targetTasks.addTask(2, new EntityAITargetNPC<>(this, EntityLivingBase.class, true));
        this.targetTasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
			@Override
			public boolean apply(EntityLivingBase input) {
				return EntityAbstractNPC.this.isSuitableTarget(input) && !EntityAbstractNPC.this.willFightEntity(input);
			}
        }, 16.0F, 1.0F, 1.2F));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIOpenModDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 10.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest2(this, EntityPlayer.class, 50.0F, 1.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest2(this, EntityLiving.class, 50.0F, 1.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

	@Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("Skin", this.getSkin());
        compound.setBoolean("Male", this.getIsMale());
    	if(this.hasCustomMessage()) {
        	compound.setString("CustomMessage", this.getCustomMessage());
    	}
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("Skin")) this.setSkin(compound.getString("Skin"));
        if(compound.hasKey("Male")) this.setIsMale(compound.getBoolean("Male"));
    	if(compound.hasKey("CustomMessage")) this.setCustomMessage(compound.getString("CustomMessage"));
    }
    
    @Override
	public ITextComponent getDisplayName() {
		ITextComponent itextcomponent = super.getDisplayName();
		itextcomponent.getStyle().setColor(TextFormatting.GOLD);
		itextcomponent.getStyle().setBold(true);
		return itextcomponent;
	}
    
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.GENERIC_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.GENERIC_UNFRIENDLY : SpeechHandler.GENERIC_HOSTILE;
    }
    
    public List<String> getNamesBankMale() {
    	return SpeechHandler.GENERIC_NAMES_M;
    }
    
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.GENERIC_NAMES_F;
    }

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public int getTimeSinceLastTalk() {
		return this.getDataManager().get(TALKING_TIME);
	}

	public void setTimeSinceLastTalk(int timeSinceLastTalk) {
		this.getDataManager().set(TALKING_TIME, timeSinceLastTalk);
	}
	
	public int getSwingCooldown() {
		return (int)(50.0F / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue());
	}
	
	public int getCooldown() {
		return this.dataManager.get(ATTACK_COOLDOWN);
	}
	
	public void setCooldown(int value) {
		this.dataManager.set(ATTACK_COOLDOWN, value);
	}
	
	public void resetCooldown() {
		this.dataManager.set(ATTACK_COOLDOWN, this.getSwingCooldown());
	}
	
	public abstract Faction getFaction();
	
	public abstract String getSkinPath();
	
	public abstract int numVariants();
	
	public abstract EnumOpinion getOpinionOfPlayer(EntityPlayer player);
	
	public abstract EnumNPCType getNPCType();
	
	public abstract boolean willFightEntity(EntityLivingBase entity);

	/**
	 * Will this entity fight the said entity when hit by it?
	 * @param entity The entity that just hit this entity.
	 * @return Whether this entity should target the input.
	 */
    public boolean willFightEntityRevenge(EntityLivingBase entity) {
		return this.willFightEntity(entity);
	}
	
	public boolean isSuitableTarget(EntityLivingBase entity) {
		boolean flag1 = entity instanceof EntityAbstractNPC && this.getFaction().getFactionStatus(((EntityAbstractNPC)entity).getFaction()) == FactionStatus.HOSTILE;
		boolean flag2 = entity instanceof EntityPlayer && this.getOpinionOfPlayer((EntityPlayer)entity) == EnumOpinion.HOSTILE;
		return flag1 || flag2;
	}
	
	public static enum EnumOpinion {
		FRIENDLY,
		UNFRIENDLY,
		HOSTILE;
	}
	
	public static enum EnumNPCType {
		MOUSE("mouse"),
		SQUIRREL("squirrel"),
		MOLE("mole"),
		RAT("rat");
		
		public final String type;
		private EnumNPCType(String type) {
			this.type = type;
		}
	}
}

package com.bob.redwall.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSyncCap;
import com.bob.redwall.common.MessageUIInteract;
import com.bob.redwall.common.MessageUIInteract.Mode;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.bob.redwall.crafting.smithing.EquipmentModifierUtils;
import com.bob.redwall.entity.ai.EntityAIAttackMeleeNPC;
import com.bob.redwall.entity.ai.EntityAIAttackMeleeNPC.MobAttackStrategy;
import com.bob.redwall.entity.ai.EntityAIAttackRangedNPC;
import com.bob.redwall.entity.ai.EntityAINPCLookAtTradingPlayer;
import com.bob.redwall.entity.ai.EntityAIOpenModDoor;
import com.bob.redwall.entity.ai.EntityAITargetNPC;
import com.bob.redwall.entity.ai.pathfind.NPCPathNavigate;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.entity.npc.favors.IFavorCondition;
import com.bob.redwall.entity.npc.favors.IFavorReward;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.factions.Faction.FactionStatus;
import com.bob.redwall.init.ArmorHandler;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.GuiHandler;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.init.SpeechHandler;
import com.bob.redwall.items.brewing.ItemDrinkVessel;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.bob.redwall.items.weapons.ranged.ItemModBow;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityAbstractNPC extends EntityCreature {
	protected static final List<EquipmentChance> EQUIPMENT_LIST_VERMIN_MOSSFLOWER = Lists.newArrayList(new EquipmentChance(50, Items.AIR), new EquipmentChance(15, ItemHandler.bronze_dagger), new EquipmentChance(15, ItemHandler.stone_spear), new EquipmentChance(15, ItemHandler.stone_mace), new EquipmentChance(15, ItemHandler.stone_throwing_axe), new EquipmentChance(10, ItemHandler.bronze_axe), new EquipmentChance(10, ItemHandler.bronze_spear), new EquipmentChance(10, ItemHandler.bronze_mace), new EquipmentChance(10, ItemHandler.bronze_pike), new EquipmentChance(10, ItemHandler.bronze_throwing_axe), new EquipmentChance(7, ItemHandler.iron_dagger), new EquipmentChance(3, ItemHandler.iron_spear), new EquipmentChance(3, ItemHandler.iron_mace), new EquipmentChance(3, ItemHandler.iron_throwing_axe), new EquipmentChance(3, ItemHandler.iron_pike), new EquipmentChance(3, Items.IRON_AXE), new EquipmentChance(3, ItemHandler.bronze_halberd), new EquipmentChance(2, ItemHandler.iron_scimitar), new EquipmentChance(1, ItemHandler.iron_halberd), new EquipmentChance(1, ItemHandler.iron_sword), new EquipmentChance(1, ItemHandler.iron_broadsword), new EquipmentChance(1, ItemHandler.iron_rapier), new EquipmentChance(15, Items.BOW));
	protected static final List<EquipmentChance> EQUIPMENT_LIST_WOODLANDERS = Lists.newArrayList(new EquipmentChance(60, Items.AIR), new EquipmentChance(12, ItemHandler.bronze_dagger), new EquipmentChance(12, ItemHandler.stone_spear), new EquipmentChance(12, ItemHandler.stone_throwing_axe), new EquipmentChance(12, ItemHandler.bronze_axe), new EquipmentChance(12, ItemHandler.bronze_spear), new EquipmentChance(12, ItemHandler.bronze_throwing_axe), new EquipmentChance(10, ItemHandler.iron_dagger), new EquipmentChance(5, ItemHandler.iron_spear), new EquipmentChance(5, ItemHandler.iron_throwing_axe), new EquipmentChance(5, Items.IRON_AXE), new EquipmentChance(4, ItemHandler.iron_scimitar), new EquipmentChance(3, ItemHandler.iron_sword), new EquipmentChance(3, ItemHandler.iron_broadsword), new EquipmentChance(3, ItemHandler.iron_rapier), new EquipmentChance(15, Items.BOW));
	protected static final List<EquipmentChance> EQUIPMENT_LIST_MOSSFLOWER_OTTERS = Lists.newArrayList(new EquipmentChance(20, Items.AIR), new EquipmentChance(8, ItemHandler.bronze_dagger), new EquipmentChance(40, ItemHandler.stone_spear), new EquipmentChance(30, ItemHandler.stone_throwing_axe), new EquipmentChance(7, ItemHandler.bronze_axe), new EquipmentChance(40, ItemHandler.bronze_spear), new EquipmentChance(30, ItemHandler.bronze_throwing_axe), new EquipmentChance(6, ItemHandler.iron_dagger), new EquipmentChance(20, ItemHandler.iron_spear), new EquipmentChance(15, ItemHandler.iron_throwing_axe), new EquipmentChance(15, Items.IRON_AXE), new EquipmentChance(6, ItemHandler.iron_scimitar), new EquipmentChance(5, ItemHandler.iron_sword), new EquipmentChance(5, ItemHandler.iron_broadsword), new EquipmentChance(5, ItemHandler.iron_rapier), new EquipmentChance(20, Items.BOW));
	protected static final List<EquipmentChance> EQUIPMENT_LIST_GUOSIM = Lists.newArrayList(new EquipmentChance(60, ItemHandler.guosim_rapier), new EquipmentChance(10, ItemHandler.guosim_paddle), new EquipmentChance(30, ItemHandler.guosim_bow));
	protected static final List<EquipmentChance> TRADE_LIST_VERMIN_MOSSFLOWER = Lists.newArrayList(new EquipmentChance(10, ItemHandler.bronze_dagger), new EquipmentChance(10, ItemHandler.stone_spear), new EquipmentChance(10, ItemHandler.stone_throwing_axe), new EquipmentChance(10, Items.BOW), new EquipmentChance(15, Items.BREAD, 10), new EquipmentChance(15, Items.WHEAT, 10), new EquipmentChance(8, ItemHandler.beans, 15), new EquipmentChance(8, ItemHandler.bass, 5), new EquipmentChance(8, ItemHandler.perch, 5), new EquipmentChance(5, ItemHandler.grayling, 5), new EquipmentChance(8, ItemHandler.trout, 5), new EquipmentChance(8, ItemHandler.corn, 15), new EquipmentChance(8, ItemHandler.onion, 15), new EquipmentChance(8, ItemHandler.blueberry, 15), new EquipmentChance(8, ItemHandler.blackberry, 15), new EquipmentChance(8, ItemHandler.raspberry, 15), new EquipmentChance(8, Items.POTATO, 15), new EquipmentChance(8, Items.CARROT, 15), new EquipmentChance(8, Items.APPLE, 15), new EquipmentChance(2, Items.BRICK, 20), new EquipmentChance(2, Items.CLAY_BALL, 20), new EquipmentChance(2, Items.BED, 20), new EquipmentChance(8, ItemHandler.turnip, 20));
	protected static final List<EquipmentChance> TRADE_LIST_WOODLANDERS = Lists.newArrayList(new EquipmentChance(10, ItemHandler.bronze_dagger), new EquipmentChance(10, ItemHandler.stone_spear), new EquipmentChance(10, ItemHandler.stone_throwing_axe), new EquipmentChance(10, Items.BOW), new EquipmentChance(15, Items.BREAD, 10), new EquipmentChance(15, Items.WHEAT, 10), new EquipmentChance(8, ItemHandler.beans, 15), new EquipmentChance(8, ItemHandler.bass, 5), new EquipmentChance(8, ItemHandler.perch, 5), new EquipmentChance(5, ItemHandler.grayling, 5), new EquipmentChance(8, ItemHandler.trout, 5), new EquipmentChance(8, ItemHandler.corn, 15), new EquipmentChance(8, ItemHandler.onion, 15), new EquipmentChance(8, ItemHandler.blueberry, 15), new EquipmentChance(8, ItemHandler.blackberry, 15), new EquipmentChance(8, ItemHandler.raspberry, 15), new EquipmentChance(8, Items.POTATO, 15), new EquipmentChance(8, Items.CARROT, 15), new EquipmentChance(8, Items.APPLE, 15), new EquipmentChance(2, Items.BRICK, 20), new EquipmentChance(2, Items.CLAY_BALL, 20), new EquipmentChance(2, Items.BED, 20), new EquipmentChance(8, ItemHandler.turnip, 20));
	protected static final List<EquipmentChance> TRADE_LIST_MOSSFLOWER_OTTERS = Lists.newArrayList(new EquipmentChance(10, ItemHandler.bronze_dagger), new EquipmentChance(10, ItemHandler.stone_spear), new EquipmentChance(10, ItemHandler.stone_throwing_axe), new EquipmentChance(10, Items.BOW), new EquipmentChance(15, Items.BREAD, 10), new EquipmentChance(15, Items.WHEAT, 10), new EquipmentChance(8, ItemHandler.beans, 15), new EquipmentChance(8, ItemHandler.bass, 5), new EquipmentChance(8, ItemHandler.perch, 5), new EquipmentChance(5, ItemHandler.grayling, 5), new EquipmentChance(8, ItemHandler.trout, 5), new EquipmentChance(8, ItemHandler.corn, 15), new EquipmentChance(8, ItemHandler.onion, 15), new EquipmentChance(8, ItemHandler.blueberry, 15), new EquipmentChance(8, ItemHandler.blackberry, 15), new EquipmentChance(8, ItemHandler.raspberry, 15), new EquipmentChance(8, Items.POTATO, 15), new EquipmentChance(8, Items.CARROT, 15), new EquipmentChance(8, Items.APPLE, 15), new EquipmentChance(2, Items.BRICK, 20), new EquipmentChance(2, Items.CLAY_BALL, 20), new EquipmentChance(2, Items.BED, 20), new EquipmentChance(8, ItemHandler.turnip, 20));
	protected static final List<EquipmentChance> TRADE_LIST_GUOSIM = Lists.newArrayList(new EquipmentChance(10, ItemHandler.bronze_dagger), new EquipmentChance(10, ItemHandler.stone_spear), new EquipmentChance(10, ItemHandler.stone_throwing_axe), new EquipmentChance(10, Items.BOW), new EquipmentChance(15, Items.BREAD, 10), new EquipmentChance(15, Items.WHEAT, 10), new EquipmentChance(8, ItemHandler.beans, 15), new EquipmentChance(8, ItemHandler.bass, 5), new EquipmentChance(8, ItemHandler.perch, 5), new EquipmentChance(5, ItemHandler.grayling, 5), new EquipmentChance(8, ItemHandler.trout, 5), new EquipmentChance(8, ItemHandler.corn, 15), new EquipmentChance(8, ItemHandler.onion, 15), new EquipmentChance(8, ItemHandler.blueberry, 15), new EquipmentChance(8, ItemHandler.blackberry, 15), new EquipmentChance(8, ItemHandler.raspberry, 15), new EquipmentChance(8, Items.POTATO, 15), new EquipmentChance(8, Items.CARROT, 15), new EquipmentChance(8, Items.APPLE, 15), new EquipmentChance(2, Items.BRICK, 20), new EquipmentChance(2, Items.CLAY_BALL, 20), new EquipmentChance(2, Items.BED, 20), new EquipmentChance(8, ItemHandler.turnip, 20));
	protected static final List<EquipmentChance> TRADE_LIST_REDWALL = Lists.newArrayList(new EquipmentChance(10, ItemHandler.bronze_dagger), new EquipmentChance(10, ItemHandler.stone_spear), new EquipmentChance(10, ItemHandler.stone_throwing_axe), new EquipmentChance(10, Items.BOW), new EquipmentChance(15, Items.BREAD, 10), new EquipmentChance(15, Items.WHEAT, 10), new EquipmentChance(8, ItemHandler.beans, 15), new EquipmentChance(8, ItemHandler.bass, 5), new EquipmentChance(8, ItemHandler.perch, 5), new EquipmentChance(5, ItemHandler.grayling, 5), new EquipmentChance(8, ItemHandler.trout, 5), new EquipmentChance(8, ItemHandler.corn, 15), new EquipmentChance(8, ItemHandler.onion, 15), new EquipmentChance(8, ItemHandler.blueberry, 15), new EquipmentChance(8, ItemHandler.blackberry, 15), new EquipmentChance(8, ItemHandler.raspberry, 15), new EquipmentChance(8, Items.POTATO, 15), new EquipmentChance(8, Items.CARROT, 15), new EquipmentChance(8, Items.APPLE, 15), new EquipmentChance(2, Items.BRICK, 20), new EquipmentChance(2, Items.CLAY_BALL, 20), new EquipmentChance(2, Items.BED, 20), new EquipmentChance(8, ItemHandler.turnip, 20));

	private static final AttributeModifier ATTACK_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("9998FA56-323B-4433-935B-2FC3FAC87635"), "attack speed modifier", -0.63, 2);

	private static final DataParameter<Boolean> MALE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<String> SKIN = EntityDataManager.<String>createKey(EntityAbstractNPC.class, DataSerializers.STRING);
	private static final DataParameter<ITextComponent> TALKING = EntityDataManager.<ITextComponent>createKey(EntityAbstractNPC.class, DataSerializers.TEXT_COMPONENT);
	private static final DataParameter<Boolean> TALKING_ACTIVE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TALKING_TIME = EntityDataManager.<Integer>createKey(EntityAbstractNPC.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ATTACK_COOLDOWN = EntityDataManager.<Integer>createKey(EntityAbstractNPC.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> ATTACKING_ACTIVE = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CHARGING_BOW = EntityDataManager.<Boolean>createKey(EntityAbstractNPC.class, DataSerializers.BOOLEAN);
	private static final DataParameter<NBTTagCompound> FAVOR = EntityDataManager.<NBTTagCompound>createKey(EntityAbstractNPC.class, DataSerializers.COMPOUND_TAG);

	private String customMessage;
	public int timeToDespawn = 6000;
	private int favorTimer;
	private EntityAIAttackMeleeNPC meleeAi = new EntityAIAttackMeleeNPC(this, 1.0D, true, MobAttackStrategy.HUMAN);
	private EntityAIAttackRangedNPC rangedAi = new EntityAIAttackRangedNPC(this, 1.0D, 20, 24.0F);
	private NBTTagCompound prevFavorTag = new NBTTagCompound();
	private Favor prevFavor = null;
	private ItemStack[] buyingItems;

	private boolean is_trading = false;
	private EntityPlayer customer;

	public EntityAbstractNPC(World worldIn) {
		this(worldIn, true);
	}

	public EntityAbstractNPC(World worldIn, boolean male) {
		super(worldIn);
		this.setTimeSinceLastTalk(0);
		this.setIsMale(male);
		((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
		((PathNavigateGround) this.getNavigator()).setEnterDoors(true);
		this.setSize(0.6F, 1.8F);
		this.experienceValue = 8;
		this.favorTimer = this.getRNG().nextInt(18000) + 6000;
	}

	private void initializeTrades() {
		this.buyingItems = new ItemStack[6];
		for (int i = 0; i < this.buyingItems.length; i++) {
			EquipmentChance e = WeightedRandom.getRandomItem(this.getRNG(), this.getPossibleTrades());
			this.buyingItems[i] = new ItemStack(e.item, this.getRNG().nextInt(e.maxAmount));
		}
	}
	
	protected abstract List<EquipmentChance> getPossibleTrades();

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

		if (!this.world.isRemote) {
			if (this.isPotionActive(StatusEffect.POISON) && this.ticksExisted % (80 / (this.getActivePotionEffect(StatusEffect.POISON).getAmplifier() + 1)) == 0)
				this.attackEntityFrom(new DamageSource("poison").setDamageBypassesArmor().setMagicDamage(), 2.0F);
			this.setTimeSinceLastTalk(this.getTimeSinceLastTalk() + 1);
			if (this.getTimeSinceLastTalk() > 200) {
				this.setTalkingActive(false);
			}

			IAttacking attacking = this.getCapability(AttackingProvider.ATTACKING_CAP, null);
			if (this.getCooldown() > 0)
				this.setCooldown(this.getCooldown() - 1);
			if (attacking.get() && this.getCooldown() == 0)
				attacking.set(false);
			float f = 1.0F - ((float) this.getCooldown() / (float) this.getSwingCooldown());
			boolean flag = rand.nextFloat() - f < 0 && 0.4F < f && f < 0.6F;

			if (flag && attacking.get() && !this.isDead) {
				attacking.set(false);
				float i = (float) this.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

				label1: {
					RayTraceResult result = RedwallUtils.raytrace(this, i);
					if (result == null || this.getRNG().nextFloat() < (this.fumbleAttackChance() * (!(this.getHeldItemMainhand().getItem() instanceof ModCustomWeapon) ? 1.75F : 1.0F)))
						break label1;
					Entity entity = result.entityHit;
					if (entity != null && !(entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow)) {
						RedwallUtils.doAttack(this, entity);
					}
				}
			}

			IDefending defending = this.getCapability(DefendingProvider.DEFENDING_CAP, null);
			Ref.NETWORK.sendToAllTracking(new MessageSyncCap(MessageSyncCap.Mode.MOB_ATTACK_MODE, attacking.getMode(), this.getEntityId()), this);
			Ref.NETWORK.sendToAllTracking(new MessageSyncCap(MessageSyncCap.Mode.MOB_DEFENDING, defending.getMode() + (defending.get() ? 8 : 0), this.getEntityId()), this);

			if (!this.isNoDespawnRequired() && this.world.getClosestPlayerToEntity(this, 25) == null && !this.isTargetLocked()) {
				if (this.timeToDespawn <= 0) {
					this.setDead();
				} else {
					this.timeToDespawn--;
				}
			}

			if (--this.favorTimer <= 0) {
				if (this.getFavor() == null)
					this.createFavor();
				else this.setFavor(null);
				this.favorTimer = this.getRNG().nextInt(18000) + 6000;
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if ((this.getHeldItemMainhand().getItem() instanceof ItemBow || this.getHeldItemMainhand().getItem() instanceof ItemModBow) && this.isHandActive()) {
			this.rangedAi.resetAttackTimer();
			this.resetActiveHand();
			if (this.getAttackTarget() != null)
				this.attackEntityWithRangedAttack(this.getAttackTarget(), 0);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		IAttacking a = this.getCapability(AttackingProvider.ATTACKING_CAP, null);
		if (a.get() || this.getCooldown() > 0)
			return false;
		a.set(true);
		this.resetCooldown();
		return super.attackEntityAsMob(entity);
	}

	public void talk(EntityPlayer player) {
		if (!this.world.isRemote) {
			if (player instanceof EntityPlayerMP) {
				Ref.NETWORK.sendTo(new MessageUIInteract(Mode.NPC_TALK, this.getEntityId()), (EntityPlayerMP) player);
			}
			if (!this.getFaction().playerHasContact(player))
				this.getFaction().playerContactFaction(player);
			this.setTimeSinceLastTalk(0);
		} else {
			List<String> speechbank = this.getSpeechbank(this.getOpinionOfPlayer(Minecraft.getMinecraft().player));
			String string = String.format(speechbank.get(this.getRNG().nextInt(speechbank.size())), Minecraft.getMinecraft().player.getDisplayName().getUnformattedText());
			Ref.NETWORK.sendToServer(new MessageUIInteractServer(MessageUIInteractServer.Mode.NPC_TALK, this.getEntityId(), string));
			this.setTimeSinceLastTalk(0);
		}
	}

	public void updateCombatTasks() {
		if (this.world != null && !this.world.isRemote) {
			this.tasks.removeTask(this.meleeAi);
			this.tasks.removeTask(this.rangedAi);
			ItemStack itemstack = this.getHeldItemMainhand();

			if (itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof ItemModBow) {
				int i = itemstack.getItem() instanceof ItemModBow ? (int) (((ItemModBow) itemstack.getItem()).getChargeTime() * 1.5F) : 30;

				this.rangedAi.setAttackCooldown(i);
				this.tasks.addTask(1, this.rangedAi);
			} else this.tasks.addTask(1, this.meleeAi);
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (player.isSneaking()) {
				this.customer = player;
				this.is_trading = true;
				player.openGui(Ref.MODID, GuiHandler.GUI_TRADING_ID, this.world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
				return true;
			} else {
				boolean gaveFavor = false;
				label1: {
					for (Favor favor : player.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors()) {
						if (this.getUniqueID().equals(favor.getGiverID())) {
							gaveFavor = true;
							for (IFavorCondition c : favor.getConditions()) {
								ItemStack s = player.getHeldItem(hand);
								player.setHeldItem(hand, c.offerItem(player.getHeldItem(hand)));

								if (!player.getHeldItem(hand).equals(s))
									break label1;
							}
						}
					}

					player.getCapability(FactionCapProvider.FACTION_CAP, null).updateFavors();
					Ref.NETWORK.sendTo(new MessageSyncCap(player.getCapability(FactionCapProvider.FACTION_CAP, null).writeToNBT(), MessageSyncCap.Mode.FACTION_STATS), (EntityPlayerMP) player);

					if (this.getFavor() != null && !gaveFavor)
						Ref.NETWORK.sendTo(new MessageUIInteract(Mode.OPEN_GUI, GuiHandler.GUI_FAVOR_ACCEPT_REJECT_ID, this.posX, this.posY, this.posZ), (EntityPlayerMP) player);
					else this.talk(player);
				}
			}
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
		this.dataManager.register(CHARGING_BOW, false);
		this.dataManager.register(FAVOR, new NBTTagCompound());

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
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(EntityAbstractNPC.ATTACK_SPEED_MODIFIER);
		this.updateCombatTasks();

		this.setIsMale(this.getRNG().nextBoolean());
		this.resetSkinNameData();
		this.setCooldown(0);
		this.initializeTrades();
		return livingdata;
	}

	public void resetSkinNameData() {
		int numSkin = this.getRNG().nextInt(this.numVariants()) + 1;
		this.setSkin(Ref.MODID + this.getSkinPath() + numSkin + (this.getIsMale() ? "m" : "f") + ".png");
		this.setCustomNameTag(this.getIsMale() ? this.getNamesBankMale().get(this.getRNG().nextInt(this.getNamesBankMale().size())) : this.getNamesBankFemale().get(this.getRNG().nextInt(this.getNamesBankFemale().size())));
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

	public void setFavor(Favor favor) {
		NBTTagCompound tag = favor == null ? new NBTTagCompound() : favor.writeToNBT();
		this.dataManager.set(FAVOR, tag);
		this.prevFavor = favor;
		this.prevFavorTag = tag;
	}

	public Favor getFavor() {
		if (this.dataManager.get(FAVOR).hasNoTags())
			return null;
		if (this.dataManager.get(FAVOR).equals(this.prevFavorTag))
			return this.prevFavor;
		Favor f = new Favor(null, this, "", new ArrayList<IFavorCondition>(), new ArrayList<IFavorReward>(), new ArrayList<IFavorReward>(), 0);
		f.readFromNBT(null, this.dataManager.get(FAVOR));
		return f;
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
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]) {
			@Override
			protected boolean isSuitableTarget(EntityLivingBase input, boolean includeInvincibles) {
				return super.isSuitableTarget(input, includeInvincibles) && EntityAbstractNPC.this.willFightEntityRevenge(input);
			}
		});
		this.targetTasks.addTask(2, new EntityAITargetNPC<>(this, EntityLivingBase.class, true));
		this.targetTasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
			@Override
			public boolean apply(EntityLivingBase input) {
				return EntityAbstractNPC.this.isSuitableTarget(input) && !EntityAbstractNPC.this.willFightEntity(input);
			}
		}, 16.0F, 1.0F, 1.2F));
		this.tasks.addTask(3, new EntityAINPCLookAtTradingPlayer(this));
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
		compound.setInteger("TimeToDespawn", this.timeToDespawn);
		compound.setInteger("FavorTimer", this.favorTimer);
		compound.setTag("Favor", this.getFavor() == null ? new NBTTagCompound() : this.getFavor().writeToNBT());
		if (this.hasCustomMessage())
			compound.setString("CustomMessage", this.getCustomMessage());
		NBTTagList buyingItems = new NBTTagList();
		for (int i = 0; i < this.buyingItems.length; i++)
			buyingItems.appendTag(this.buyingItems[i].writeToNBT(new NBTTagCompound()));
		compound.setTag("BuyingItems", buyingItems);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("Skin"))
			this.setSkin(compound.getString("Skin"));
		if (compound.hasKey("Male"))
			this.setIsMale(compound.getBoolean("Male"));
		if (compound.hasKey("TimeToDespawn"))
			this.timeToDespawn = compound.getInteger("TimeToDespawn");
		if (compound.hasKey("FavorTimer"))
			this.favorTimer = compound.getInteger("FavorTimer");
		if (compound.hasKey("Favor")) {
			if (compound.getTag("Favor").hasNoTags())
				this.setFavor(null);
			else {
				Favor f = new Favor(null, this, "", new ArrayList<IFavorCondition>(), new ArrayList<IFavorReward>(), new ArrayList<IFavorReward>(), 0);
				f.readFromNBT(null, (NBTTagCompound) compound.getTag("Favor"));
				this.setFavor(f);
			}
		}
		if (compound.hasKey("CustomMessage"))
			this.setCustomMessage(compound.getString("CustomMessage"));
		if (compound.hasKey("BuyingItems"))
			for (int i = 0; i < this.buyingItems.length; i++)
				this.buyingItems[i] = new ItemStack(compound.getTagList("BuyingItems", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i));
		this.updateCombatTasks();
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
		return (int) (50.0F / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue());
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
	 * Creates a new favor for this entity and sets this entity's current offered
	 * favor.
	 */
	public abstract void createFavor();

	/**
	 * Will this entity fight the said entity when hit by it?
	 * 
	 * @param entity
	 *            The entity that just hit this entity.
	 * @return Whether this entity should target the input.
	 */
	public boolean willFightEntityRevenge(EntityLivingBase entity) {
		return this.willFightEntity(entity);
	}

	public boolean isSuitableTarget(EntityLivingBase entity) {
		boolean flag1 = entity instanceof EntityAbstractNPC && this.getFaction().getFactionStatus(((EntityAbstractNPC) entity).getFaction()) == FactionStatus.HOSTILE && this.getFaction() != ((EntityAbstractNPC) entity).getFaction();
		boolean flag2 = entity instanceof EntityPlayer && this.getOpinionOfPlayer((EntityPlayer) entity) == EnumOpinion.HOSTILE;
		return flag1 || flag2;
	}

	public float fumbleAttackChance() {
		return 0.3F;
	}

	public float fumbleBlockingChance() {
		return 0.6F;
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		if (source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getTrueSource();
			if (entity instanceof EntityPlayer) {
				if (this.getOpinionOfPlayer((EntityPlayer) entity) == EnumOpinion.FRIENDLY)
					return true;
			} else if (entity instanceof EntityAbstractNPC) {
				if (this.getFaction().getFactionStatus(((EntityAbstractNPC) entity).getFaction()) == FactionStatus.ALLIED)
					return true;
				else if (this.getFaction().getFactionStatus(((EntityAbstractNPC) entity).getFaction()) == FactionStatus.FRIENDLY)
					return true;
			}
		}
		return super.isEntityInvulnerable(source);
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		super.setItemStackToSlot(slotIn, stack);

		if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND) {
			this.updateCombatTasks();
		}
	}

	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityArrow entityarrow = this.getArrow(distanceFactor);
		double d0 = target.posX - this.posX;
		double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - entityarrow.posY;
		double d2 = target.posZ - this.posZ;
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		Item bow = this.getHeldItemMainhand().getItem();
		float power = distanceFactor == 0 ? 0 : (bow instanceof ItemModBow ? ((ItemModBow) bow).getPower() * 0.75F : 2.25F);
		entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, power, (float) (14 - this.world.getDifficulty().getDifficultyId() * 4));
		this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(entityarrow);
	}

	protected EntityArrow getArrow(float distanceFactor) {
		EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
		entitytippedarrow.setEnchantmentEffectsFromEntity(this, distanceFactor);
		return entitytippedarrow;
	}

	@SideOnly(Side.CLIENT)
	public boolean isChargingBow() {
		return ((Boolean) this.dataManager.get(CHARGING_BOW)).booleanValue();
	}

	public void setChargingBow(boolean chargingBow) {
		this.dataManager.set(CHARGING_BOW, Boolean.valueOf(chargingBow));
	}

	public ItemStack[] getBuyingItems() {
		return this.buyingItems;
	}

	public EntityPlayer getCustomer() {
		return this.customer;
	}

	public boolean isTrading() {
		return this.is_trading;
	}

	public void setIsTrading(boolean bool) {
		this.is_trading = bool;
	}

	public float getStackValue(ItemStack stack) {
		float f = 0.0F;

		if (stack.getItem() == Items.GOLD_NUGGET)
			f = 1.0F;
		else if (stack.getItem() == Items.GOLD_INGOT)
			f = 9.0F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK))
			f = 81.0F;
		else if (stack.getItem() == Items.IRON_NUGGET)
			f = 0.75F;
		else if (stack.getItem() == Items.IRON_INGOT)
			f = 6.75F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.IRON_BLOCK))
			f = 60.75F;
		else if (stack.getItem() == ItemHandler.copper_nugget)
			f = 0.2F;
		else if (stack.getItem() == ItemHandler.copper_ingot)
			f = 1.8F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.copper_block))
			f = 16.2F;
		else if (stack.getItem() == ItemHandler.tin_nugget)
			f = 0.5F;
		else if (stack.getItem() == ItemHandler.tin_ingot)
			f = 4.5F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.tin_block))
			f = 40.5F;
		else if (stack.getItem() == ItemHandler.bronze_nugget)
			f = 0.65F;
		else if (stack.getItem() == ItemHandler.bronze_ingot)
			f = 5.85F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.bronze_block))
			f = 52.65F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.ANVIL))
			f = 50.0F;
		else if (stack.getItem() == Items.WHEAT)
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.HAY_BLOCK))
			f = 0.5F;
		else if (stack.getItem() == Items.BREAD)
			f = 0.17F;
		else if (stack.getItem() == Items.WHEAT_SEEDS)
			f = 0.01F;
		else if (stack.getItem() == Items.BEETROOT)
			f = 0.06F;
		else if (stack.getItem() == Items.BEETROOT_SEEDS)
			f = 0.01F;
		else if (stack.getItem() == Items.POTATO)
			f = 0.03F;
		else if (stack.getItem() == Items.BAKED_POTATO)
			f = 0.05F;
		else if (stack.getItem() == Items.POISONOUS_POTATO)
			f = -0.05F;
		else if (stack.getItem() == Items.APPLE)
			f = 0.07F;
		else if (stack.getItem() == Items.BEETROOT_SOUP)
			f = 0.1F;
		else if (stack.getItem() == Items.MUSHROOM_STEW)
			f = 0.15F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.BROWN_MUSHROOM))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.RED_MUSHROOM))
			f = 0.05F;
		else if (stack.getItem() == Items.BOWL)
			f = 0.02F;
		else if (stack.getItem() == Items.REEDS || stack.getItem() == ItemHandler.cornstalk || stack.getItem() == ItemHandler.water_reeds || stack.getItem() == ItemHandler.water_reeds_dried)
			f = 0.01F;
		else if (stack.getItem() == Items.FISH || stack.getItem() == ItemHandler.trout || stack.getItem() == ItemHandler.bass || stack.getItem() == ItemHandler.perch || stack.getItem() == ItemHandler.grayling)
			f = 0.1F;
		else if (stack.getItem() == Items.COOKED_FISH || stack.getItem() == ItemHandler.trout_cooked || stack.getItem() == ItemHandler.bass_cooked || stack.getItem() == ItemHandler.perch_cooked || stack.getItem() == ItemHandler.grayling_cooked)
			f = 0.2F;
		else if (stack.getItem() == Items.SUGAR)
			f = 0.02F;
		else if (stack.getItem() == Items.COOKIE)
			f = 0.07F;
		else if (stack.getItem() == Items.MELON)
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.MELON_BLOCK))
			f = 0.1F;
		else if (stack.getItem() == Items.MELON_SEEDS)
			f = 0.01F;
		else if (stack.getItem() == Items.PUMPKIN_SEEDS)
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN) || stack.getItem() == Item.getItemFromBlock(Blocks.LIT_PUMPKIN))
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.CAKE))
			f = 0.5F;
		else if (stack.getItem() == Items.ROTTEN_FLESH)
			f = -0.1F;
		else if (stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == ItemHandler.mug_empty)
			f = 0.05F;
		else if (stack.getItem() == Items.CARROT)
			f = 0.06F;
		else if (stack.getItem() == Items.PUMPKIN_PIE)
			f = 0.2F;
		else if (stack.getItem() == ItemHandler.strawberry || stack.getItem() == ItemHandler.blueberry || stack.getItem() == ItemHandler.blackberry || stack.getItem() == ItemHandler.raspberry || stack.getItem() == ItemHandler.elderberry || stack.getItem() == ItemHandler.wildberry)
			f = 0.03F;
		else if (stack.getItem() == ItemHandler.strawberry_seeds || stack.getItem() == ItemHandler.blueberry_seeds || stack.getItem() == ItemHandler.blackberry_seeds || stack.getItem() == ItemHandler.raspberry_seeds || stack.getItem() == ItemHandler.elderberry_seeds || stack.getItem() == ItemHandler.wildberry_seeds)
			f = 0.01F;
		else if (stack.getItem() == ItemHandler.plum || stack.getItem() == ItemHandler.damson)
			f = 0.05F;
		else if (stack.getItem() == ItemHandler.corn)
			f = 0.05F;
		else if (stack.getItem() == ItemHandler.turnip)
			f = 0.01F;
		else if (stack.getItem() == ItemHandler.onion)
			f = 0.02F;
		else if (stack.getItem() == ItemHandler.peas)
			f = 0.02F;
		else if (stack.getItem() == ItemHandler.rice)
			f = 0.03F;
		else if (stack.getItem() == ItemHandler.rice_bowl)
			f = 0.05F;
		else if (stack.getItem() == ItemHandler.yam)
			f = 0.05F;
		else if (stack.getItem() == ItemHandler.beans)
			f = 0.04F;
		else if (stack.getItem() == ItemHandler.grapes)
			f = 0.04F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.grape_vine_trellis))
			f = 0.04F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STONE) || stack.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE) || stack.getItem() == Item.getItemFromBlock(Blocks.MOSSY_COBBLESTONE) || stack.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE_WALL))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.PLANKS) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_planks) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_planks))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.LOG) || stack.getItem() == Item.getItemFromBlock(Blocks.LOG2) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_log) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_log))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.LEAVES) || stack.getItem() == Item.getItemFromBlock(Blocks.LEAVES2) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_leaves) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_leaves))
			f = 0.005F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.OAK_FENCE) || stack.getItem() == Item.getItemFromBlock(Blocks.ACACIA_FENCE) || stack.getItem() == Item.getItemFromBlock(Blocks.BIRCH_FENCE) || stack.getItem() == Item.getItemFromBlock(Blocks.DARK_OAK_FENCE) || stack.getItem() == Item.getItemFromBlock(Blocks.JUNGLE_FENCE) || stack.getItem() == Item.getItemFromBlock(Blocks.SPRUCE_FENCE) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_fence) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_fence))
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.OAK_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(Blocks.ACACIA_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(Blocks.BIRCH_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(Blocks.DARK_OAK_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(Blocks.JUNGLE_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(Blocks.SPRUCE_FENCE_GATE) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_fence_gate) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_fence_gate))
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.SAPLING) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_sapling) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_sapling))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.OAK_STAIRS) || stack.getItem() == Item.getItemFromBlock(Blocks.ACACIA_STAIRS) || stack.getItem() == Item.getItemFromBlock(Blocks.BIRCH_STAIRS) || stack.getItem() == Item.getItemFromBlock(Blocks.DARK_OAK_STAIRS) || stack.getItem() == Item.getItemFromBlock(Blocks.JUNGLE_STAIRS) || stack.getItem() == Item.getItemFromBlock(Blocks.SPRUCE_STAIRS) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_stairs) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_stairs))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.WOODEN_SLAB) || stack.getItem() == Item.getItemFromBlock(BlockHandler.alder_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.apple_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.ash_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.aspen_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.beech_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.elm_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.fir_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.hornbeam_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.larch_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.maple_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.pine_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.plum_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.willow_slab) || stack.getItem() == Item.getItemFromBlock(BlockHandler.yew_slab))
			f = 0.01F;
		else if (stack.getItem() == Items.ACACIA_DOOR || stack.getItem() == Items.BIRCH_DOOR || stack.getItem() == Items.DARK_OAK_DOOR || stack.getItem() == Items.JUNGLE_DOOR || stack.getItem() == Items.OAK_DOOR || stack.getItem() == Items.SPRUCE_DOOR || stack.getItem() == ItemHandler.alder_door || stack.getItem() == ItemHandler.apple_door || stack.getItem() == ItemHandler.ash_door || stack.getItem() == ItemHandler.aspen_door || stack.getItem() == ItemHandler.beech_door || stack.getItem() == ItemHandler.elm_door || stack.getItem() == ItemHandler.fir_door || stack.getItem() == ItemHandler.hornbeam_door || stack.getItem() == ItemHandler.larch_door || stack.getItem() == ItemHandler.maple_door || stack.getItem() == ItemHandler.pine_door || stack.getItem() == ItemHandler.plum_door || stack.getItem() == ItemHandler.willow_door || stack.getItem() == ItemHandler.yew_door)
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.thatch))
			f = 0.005F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.thatch_stairs))
			f = 0.005F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.thatch_slab))
			f = 0.002F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.SANDSTONE))
			f = 0.015F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.GLASS))
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.GLASS_PANE))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS))
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE))
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.GRAVEL))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.SAND))
			f = 0.01F;
		else if (stack.getItem() == Items.BED)
			f = 0.5F;
		else if (stack.getItem() == Items.BRICK)
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.BRICK_STAIRS))
			f = 0.06F;
		else if (stack.getItem() == Items.CLAY_BALL)
			f = 0.015F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STONE_SLAB) || stack.getItem() == Item.getItemFromBlock(Blocks.STONE_SLAB2))
			f = 0.005F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.BRICK_BLOCK))
			f = 0.06F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF))
			f = 1.0F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH))
			f = 0.1F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))
			f = 0.08F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
			f = 0.04F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.FURNACE))
			f = 0.1F;
		else if (stack.getItem() == Items.SIGN)
			f = 0.06F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.LADDER))
			f = 0.04F;
		else if (stack.getItem() == Items.STICK)
			f = 0.005F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.CACTUS))
			f = 0.01F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.CLAY))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STONEBRICK))
			f = 0.04F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STONE_BRICK_STAIRS))
			f = 0.04F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.EMERALD_BLOCK))
			f = 72.0F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))
			f = 72.0F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.HARDENED_CLAY))
			f = 0.07F;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY))
			f = 0.08F;
		else if (stack.getItem() == Items.BANNER)
			f = 0.2F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.southsward_brick) || stack.getItem() == Item.getItemFromBlock(BlockHandler.southsward_pillar) || stack.getItem() == Item.getItemFromBlock(BlockHandler.southsward_stairs))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.southsward_slab))
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.redwall_brick) || stack.getItem() == Item.getItemFromBlock(BlockHandler.redwall_pillar) || stack.getItem() == Item.getItemFromBlock(BlockHandler.redwall_stairs))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.redwall_slab))
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.kotir_brick) || stack.getItem() == Item.getItemFromBlock(BlockHandler.kotir_pillar) || stack.getItem() == Item.getItemFromBlock(BlockHandler.kotir_stairs))
			f = 0.05F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.kotir_slab))
			f = 0.03F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.candle))
			f = 0.2F;
		else if (stack.getItem() == ItemHandler.weapon_rack)
			f = 0.02F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.smithing_generic))
			f = 55.0F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.smithing_redwall))
			f = 55.0F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.smeltery))
			f = 40.0F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.cooking_generic))
			f = 10.0F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.brewing_redwall))
			f = 5.0F;
		else if (stack.getItem() == Item.getItemFromBlock(BlockHandler.brewing_guosim))
			f = 5.0F;
		else if (stack.getItem() == Items.ARROW)
			f = 0.1F;
		else if (stack.getItem() == Items.STRING)
			f = 0.05F;
		else if (stack.getItem() == Items.FEATHER)
			f = 0.03F;
		else if (stack.getItem() == Items.FLINT)
			f = 0.02F;
		else if (stack.getItem() == Items.BUCKET)
			f = 6.0F;
		else if (stack.getItem() == Items.BOOK)
			f = 0.5F;
		else if (stack.getItem() == Items.PAPER)
			f = 0.04F;
		else if (stack.getItem() == Items.MAP)
			f = 0.4F;
		else if (stack.getItem() == Items.COAL)
			f = 0.05F;
		else if (stack.getItem() == Items.EMERALD)
			f = 8.0F;
		else if (stack.getItem() == Items.DIAMOND)
			f = 8.0F;
		else if (stack.getItem() == Items.FLINT_AND_STEEL)
			f = 8.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage());
		else if (stack.getItem() == Items.SHIELD)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage());
		else if (stack.getItem() == Items.BOAT || stack.getItem() == Items.SPRUCE_BOAT || stack.getItem() == Items.BIRCH_BOAT || stack.getItem() == Items.JUNGLE_BOAT || stack.getItem() == Items.ACACIA_BOAT || stack.getItem() == Items.DARK_OAK_BOAT)
			f = 1.2F;
		else if (stack.getItem() == ItemHandler.mug_drink || stack.getItem() == ItemHandler.bowl_drink || stack.getItem() == ItemHandler.drink_bottle)
			f = (ItemDrinkVessel.getDrink(stack).getCarbs() + ItemDrinkVessel.getDrink(stack).getProtein() + ItemDrinkVessel.getDrink(stack).getFruits() + ItemDrinkVessel.getDrink(stack).getVeggies()) / 40.0F * FoodModifierUtils.getQualityMultiplier(stack) + this.getStackValue(new ItemStack(((ItemDrinkVessel) stack.getItem()).getContainerItem()));
		else if (stack.getItem() == Items.IRON_SHOVEL)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_PICKAXE)
			f = 30.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_AXE)
			f = 30.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_HOE)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_scythe)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.BOW)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.WOODEN_SHOVEL)
			f = 0.1F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.WOODEN_PICKAXE)
			f = 0.3F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.WOODEN_AXE)
			f = 0.3F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.WOODEN_HOE)
			f = 0.2F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.STONE_SHOVEL)
			f = 0.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.STONE_PICKAXE)
			f = 1.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.STONE_AXE)
			f = 1.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.STONE_HOE)
			f = 1.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_SHOVEL)
			f = 5.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_PICKAXE)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_AXE)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_HOE)
			f = 7.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_scythe)
			f = 15.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_spade)
			f = 5.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_pickaxe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_axe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_hoe)
			f = 7.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_scythe)
			f = 15.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.redwall_spade)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.redwall_pickaxe)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.redwall_axe)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.redwall_hoe)
			f = 24.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.redwall_scythe)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_longbow)
			f = 15.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.rogue_crew_longbow)
			f = 13.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_longbow)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.guosim_bow)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_sword)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_broadsword)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_rapier)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_scimitar)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_halberd)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_spear)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_pike)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_mace)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_dagger)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_battleaxe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.golden_throwing_axe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.stone_spear)
			f = 1.2F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.stone_mace)
			f = 1.2F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.stone_throwing_axe)
			f = 1.2F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_halberd)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_spear)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_pike)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_mace)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_dagger)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_battleaxe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.bronze_throwing_axe)
			f = 10.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_sword)
			f = 24.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_broadsword)
			f = 24.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_rapier)
			f = 24.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_scimitar)
			f = 24.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_halberd)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_spear)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_pike)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_mace)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_dagger)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_battleaxe)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.iron_throwing_axe)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_sword)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_broadsword)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_rapier)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_claymore)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_sabre)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_spear)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_pike)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_lance)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_dagger)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_battleaxe)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.salamandastron_dirk)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_sword)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_hammer)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_pike)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_dagger)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_mace)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.southsward_spear)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_sword)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_broadsword)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_rapier)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_dagger)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_sabre)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.riftgard_spear)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.northlands_claymore)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.northlands_dirk)
			f = 32.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.northlands_sgian_dhu)
			f = 32.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.guosim_rapier)
			f = 36.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.guosim_paddle)
			f = 12.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.rogue_crew_sword)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.rogue_crew_battleaxe)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.rogue_crew_throwing_axe)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_sword)
			f = 44.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_battleaxe)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_dagger)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_halberd)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_spear)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ItemHandler.kotir_pike)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.LEATHER_HELMET)
			f = 5.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.LEATHER_CHESTPLATE)
			f = 8.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.LEATHER_LEGGINGS)
			f = 7.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.LEATHER_BOOTS)
			f = 4.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.CHAINMAIL_HELMET)
			f = 50.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.CHAINMAIL_CHESTPLATE)
			f = 80.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.CHAINMAIL_LEGGINGS)
			f = 70.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.CHAINMAIL_BOOTS)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_HELMET)
			f = 50.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_CHESTPLATE)
			f = 80.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_LEGGINGS)
			f = 70.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.IRON_BOOTS)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_HELMET)
			f = 25.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_CHESTPLATE)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_LEGGINGS)
			f = 35.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == Items.GOLDEN_BOOTS)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.bronze_helmet)
			f = 25.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.bronze_chest)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.bronze_legs)
			f = 35.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.bronze_boots)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mail_helmet)
			f = 25.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mail_chest)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mail_legs)
			f = 35.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mail_boots)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mixed_helmet)
			f = 25.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mixed_chest)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mixed_legs)
			f = 35.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.golden_mixed_boots)
			f = 20.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.iron_mixed_helmet)
			f = 50.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.iron_mixed_chest)
			f = 80.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.iron_mixed_legs)
			f = 70.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.iron_mixed_boots)
			f = 40.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.salamandastron_helmet)
			f = 60.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.salamandastron_chest)
			f = 95.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.salamandastron_legs)
			f = 82.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.salamandastron_boots)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.kotir_helmet)
			f = 60.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.kotir_chest)
			f = 95.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.kotir_legs)
			f = 82.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.kotir_boots)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.riftgard_helmet)
			f = 60.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.riftgard_chest)
			f = 95.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.riftgard_legs)
			f = 82.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.riftgard_boots)
			f = 48.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.southsward_helmet)
			f = 55.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.southsward_chest)
			f = 87.5F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.southsward_legs)
			f = 80.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);
		else if (stack.getItem() == ArmorHandler.southsward_boots)
			f = 46.0F * ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * EquipmentModifierUtils.getQualityMultiplier(stack);

		return f * stack.getCount();
	}

	public static enum EnumOpinion {
		FRIENDLY, UNFRIENDLY, HOSTILE;
	}

	public static enum EnumNPCType {
		MOUSE("mouse", "Mouse"), SQUIRREL("squirrel", "Squirrel"), MOLE("mole", "Mole"), RAT("rat", "Rat"), FERRET("rat", "Ferret"), STOAT("rat", "Stoat"), WEASEL("rat", "Weasel"), OTTER("otter", "Otter"), SHREW("shrew", "Shrew");

		public final String armorSlug;
		public final String name;

		private EnumNPCType(String armorSlug, String name) {
			this.armorSlug = armorSlug;
			this.name = name;
		}
	}

	public static class EquipmentChance extends WeightedRandom.Item {
		private Item item;
		private int maxAmount = 1;

		public EquipmentChance(int weight, Item item) {
			super(weight);
			this.item = item;
		}

		public EquipmentChance(int weight, Item item, int maxAmount) {
			this(weight, item);
			this.maxAmount = maxAmount;
		}

		public Item getItem() {
			return this.item;
		}
		
		public int getMaxAmount() {
			return this.maxAmount;
		}
	}
}

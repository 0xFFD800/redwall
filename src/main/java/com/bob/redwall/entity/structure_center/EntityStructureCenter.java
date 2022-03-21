package com.bob.redwall.entity.structure_center;

import java.util.ArrayList;
import java.util.List;

import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.entity.npc.favors.IFavorCondition;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.factions.Faction.FactionStatus;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class EntityStructureCenter extends EntityLivingBase {
	public static final List<EntityStructureCenter> ACTIVE_STRUCTURES = new ArrayList<>();

	private AxisAlignedBB aoe;

	public EntityStructureCenter(World worldIn, AxisAlignedBB aoe) {
		super(worldIn);
		this.aoe = aoe;
		this.width = 1;
		this.height = 1;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return new ArrayList<>();
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT;
	}

	public AxisAlignedBB getAOE() {
		return this.aoe;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		EntityStructureCenter.ACTIVE_STRUCTURES.add(this);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source instanceof EntityDamageSource && ((EntityDamageSource) source).getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) ((EntityDamageSource) source).getTrueSource();
			if (player.getCapability(FactionCapProvider.FACTION_CAP, null).getPlayerContacted(this.getFaction()) && player.getCapability(FactionCapProvider.FACTION_CAP, null).get(this.getFaction(), FacStatType.LOYALTY) >= 0)
				return false;
		}

		boolean b = super.attackEntityFrom(source, amount);
		if (this.getHealth() <= 0.0F && source instanceof EntityDamageSource && ((EntityDamageSource) source).getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) ((EntityDamageSource) source).getTrueSource();
			player.addExperience(this.getXPReward());
			FactionCap cap = (FactionCap) player.getCapability(FactionCapProvider.FACTION_CAP, null);
			for (Faction f : Faction.getAllFactions()) {
				if (f.playerHasContact(player) && f.getFactionStatus(this.getFaction()) == FactionStatus.HOSTILE) {
					cap.set(f, FacStatType.LOYALTY, cap.get(f, FacStatType.LOYALTY) + this.getLoyaltyReward(), true);
					cap.set(f, FacStatType.FIGHT, cap.get(f, FacStatType.FIGHT) + this.getFightSkillReward(), true);
				}
			}

			for (Favor favor : cap.getFavors())
				for (IFavorCondition c : favor.getConditions())
					c.destroyStructure(this);

			player.sendMessage(new TextComponentString(I18n.format("message.defeatStructureHostile", this.getXPReward(), this.getLoyaltyReward(), this.getFightSkillReward())));
		}

		return b;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
	}

	@Override
	public void setDead() {
		EntityStructureCenter.ACTIVE_STRUCTURES.remove(this);
		super.setDead();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int[] a = new int[] { (int) aoe.minX, (int) aoe.minY, (int) aoe.minZ, (int) aoe.maxX, (int) aoe.maxY, (int) aoe.maxZ };
		compound.setIntArray("AOE", a);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("AOE")) {
			int[] a = compound.getIntArray("AOE");
			this.aoe = new AxisAlignedBB(a[0], a[1], a[2], a[3], a[4], a[5]);
		}

		super.readFromNBT(compound);
	}

	public abstract Faction getFaction();

	public abstract int getXPReward();

	public abstract int getLoyaltyReward();

	public abstract int getFightSkillReward();
}

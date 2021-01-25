package com.bob.redwall.entity.ai;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackMeleeNPC extends EntityAIBase {
	World world;
	protected EntityAbstractNPC attacker;
	/**
	 * An amount of decrementing ticks that allows the entity to attack once the
	 * tick reaches 0.
	 */
	protected int attackTick;
	/** The speed with which the mob will approach the target */
	double speedTowardsTarget;
	double actualSpeed;
	/**
	 * When true, the mob will continue chasing its target, even if it can't find a
	 * path to them right now.
	 */
	boolean longMemory;
	/** The PathEntity of our entity. */
	Path path;
	private int delayCounter;
	private double targetX;
	private double targetY;
	private double targetZ;
	private int failedPathFindingPenalty = 0;
	private boolean canPenalize = false;
	private final MobAttackStrategy strategy;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	private int blockingTime = -1;

	public EntityAIAttackMeleeNPC(EntityAbstractNPC creature, double speedIn, boolean useLongMemory, MobAttackStrategy strategy) {
		this.attacker = creature;
		this.world = creature.world;
		this.speedTowardsTarget = speedIn;
		this.actualSpeed = speedIn;
		this.longMemory = useLongMemory;
		this.strategy = strategy;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else if (!entitylivingbase.isEntityAlive()) {
			return false;
		} else {
			if (canPenalize) {
				if (--this.delayCounter <= 0) {
					this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
					this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
					return this.path != null;
				} else {
					return true;
				}
			}
			this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);

			if (this.path != null) {
				return true;
			} else {
				return this.getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else if (!entitylivingbase.isEntityAlive()) {
			return false;
		} else if (!this.longMemory) {
			return !this.attacker.getNavigator().noPath();
		} else if (!this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))) {
			return false;
		} else {
			return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).isSpectator() && !((EntityPlayer) entitylivingbase).isCreative();
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.attacker.getNavigator().setPath(this.path, this.actualSpeed);
		this.delayCounter = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	@Override
	public void resetTask() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer) entitylivingbase).isSpectator() || ((EntityPlayer) entitylivingbase).isCreative())) {
			this.attacker.setAttackTarget((EntityLivingBase) null);
		}

		this.attacker.getNavigator().clearPath();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
		--this.delayCounter;

		if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
			this.targetX = entitylivingbase.posX;
			this.targetY = entitylivingbase.getEntityBoundingBox().minY;
			this.targetZ = entitylivingbase.posZ;
			this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);

			if (this.canPenalize) {
				this.delayCounter += failedPathFindingPenalty;
				if (this.attacker.getNavigator().getPath() != null) {
					net.minecraft.pathfinding.PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
					if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1) failedPathFindingPenalty = 0;
					else failedPathFindingPenalty += 10;
				} else {
					failedPathFindingPenalty += 10;
				}
			}

			if (d0 > 1024.0D) {
				this.delayCounter += 10;
			} else if (d0 > 256.0D) {
				this.delayCounter += 5;
			}

			if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.actualSpeed)) {
				this.delayCounter += 15;
			}
		}

		if (d0 <= this.getAttackReachSqr(entitylivingbase) * 0.5625 && this.strategy.reachResponse == 1) this.attacker.getNavigator().tryMoveToXYZ(this.attacker.posX, this.attacker.posY, this.attacker.posZ, 0.0D);
		else if (this.strategy.reachResponse == 2 && d0 <= this.getAttackReachSqr(entitylivingbase) * 0.75F) this.updateStrafing(entitylivingbase);

		if (this.strategy.block) this.updateBlocking(entitylivingbase);

		this.attackTick = Math.max(this.attackTick - 1, 0);
		this.checkAndPerformAttack(entitylivingbase, d0);
	}

	protected void checkAndPerformAttack(EntityLivingBase target, double distance) {
		double d0 = this.getAttackReachSqr(target);

		if (distance <= d0 && this.attackTick <= 0 && this.blockingTime == -1) {
			this.attackTick = Math.max(this.attacker.getSwingCooldown(), 20);
			this.attacker.swingArm(EnumHand.MAIN_HAND);
			this.attacker.attackEntityAsMob(target);
			IAttacking a = this.attacker.getCapability(AttackingProvider.ATTACKING_CAP, null);
			a.setMode(this.strategy.attackType == 0 ? 0 : this.strategy.attackType == 1 ? this.attacker.getRNG().nextInt(3) : this.selectAttackBasedOnWeapons(target));
			if (this.strategy.attemptCrit) this.performCritBehavior();
		}
	}

	protected double getAttackReachSqr(EntityLivingBase attackTarget) {
		return Math.pow(this.attacker.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() * 0.75F, 2);
	}

	protected int selectAttackBasedOnWeapons(EntityLivingBase target) {
		ItemStack attackWeapon = this.attacker.getHeldItemMainhand();
		ItemStack defenseWeapon = target.getHeldItemMainhand();
		ItemStack defenseOffhand = target.getHeldItemOffhand();

		if (!(attackWeapon.getItem() instanceof ModCustomWeapon)) return 0;
		ModCustomWeapon aW = (ModCustomWeapon) attackWeapon.getItem();

		boolean canStab = aW.isStab(attackWeapon, this.attacker);
		boolean canSweep = aW.isSweep(attackWeapon, this.attacker) || aW.isBludgeon(attackWeapon, this.attacker);

		if (!canSweep) return 2;

		if(this.attacker.getRNG().nextFloat() < this.attacker.fumbleAttackChance()) return this.attacker.getRNG().nextInt(3);
		boolean flag = defenseWeapon.getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) defenseWeapon.getItem()).canBlock(defenseWeapon, target);
		if (target.getCapability(DefendingProvider.DEFENDING_CAP, null).get() && (defenseOffhand.getItem() instanceof ItemShield || flag)) {
			switch (target.getCapability(DefendingProvider.DEFENDING_CAP, null).getMode()) {
			case 0:
				return 1;
			case 1:
				return 0;
			case 2:
				return 0;
			}
		} else if (flag && !(defenseOffhand.getItem() instanceof ItemShield)) {
			if (canStab && this.attacker.getRNG().nextFloat() < 0.75F) return 2;
			else return this.attacker.getRNG().nextInt(2);
		} else if (RedwallUtils.getReach(target) > Math.sqrt(this.getAttackReachSqr(target))) {
			return this.attacker.getRNG().nextFloat() < 0.8F || !canStab ? this.attacker.getRNG().nextInt(2) : 2;
		}

		return !canStab ? this.attacker.getRNG().nextInt(2) : this.attacker.getRNG().nextInt(3);
	}

	protected void updateStrafing(EntityLivingBase target) {
		double d0 = this.attacker.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
		if (d0 <= this.getAttackReachSqr(target)) {
			this.attacker.getNavigator().clearPath();
			++this.strafingTime;
		} else {
			this.attacker.getNavigator().tryMoveToEntityLiving(target, this.actualSpeed);
			this.strafingTime = -1;
		}

		if (this.strafingTime >= 20) {
			if ((double) this.attacker.getRNG().nextFloat() < 0.3D) {
				this.strafingClockwise = !this.strafingClockwise;
			}

			if ((double) this.attacker.getRNG().nextFloat() < 0.3D) {
				this.strafingBackwards = !this.strafingBackwards;
			}

			this.strafingTime = 0;
		}

		if (this.strafingTime > -1) {
			if (d0 > this.getAttackReachSqr(target) * 0.5625) {
				this.strafingBackwards = false;
			} else if (d0 < this.getAttackReachSqr(target) * 0.0625) {
				this.strafingBackwards = true;
			}

			this.attacker.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
			this.attacker.faceEntity(target, 30.0F, 30.0F);
		} else {
			this.attacker.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
		}
	}

	protected void updateBlocking(EntityLivingBase target) {
		IAttacking targetAttacking = target.getCapability(AttackingProvider.ATTACKING_CAP, null);
		IAttacking a = this.attacker.getCapability(AttackingProvider.ATTACKING_CAP, null);
		IDefending d = this.attacker.getCapability(DefendingProvider.DEFENDING_CAP, null);

		ItemStack main = this.attacker.getHeldItemMainhand();
		ItemStack off = this.attacker.getHeldItemOffhand();

		if (this.blockingTime > -1) {
			this.blockingTime--;
			if (this.blockingTime == -1) {
				this.attacker.setSneaking(false);
				this.actualSpeed = this.speedTowardsTarget;
				this.attacker.resetCooldown();
				this.attackTick = this.attacker.getSwingCooldown() + this.attacker.getRNG().nextInt((int)(this.attacker.fumbleAttackChance() * 10.0F) + 10);
			}
		}

		boolean flag = main.getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) main.getItem()).canBlock(main, this.attacker);
		if (targetAttacking.get() && !a.get() && this.attacker.getCooldown() == 0 && (flag || off.getItem() instanceof ItemShield)) {
			int i = targetAttacking.getMode();
			if(this.attacker.getRNG().nextFloat() < this.attacker.fumbleBlockingChance()) i = this.attacker.getRNG().nextInt(3);
			switch (i) {
			case 0: {
				this.attacker.setSneaking(true);
				this.actualSpeed = 0.6D;
				this.blockingTime = 20;
				d.set(true);
				d.setMode(1);
				break;
			}
			case 1: {
				this.attacker.setSneaking(true);
				this.actualSpeed = 0.6D;
				this.blockingTime = 20;
				d.set(true);
				d.setMode(0);
				break;
			}
			case 2: {
				if (!(off.getItem() instanceof ItemShield)) break;
				this.attacker.setSneaking(true);
				this.actualSpeed = 0.6D;
				this.blockingTime = 20;
				d.set(true);
				d.setMode(2);
				break;
			}
			}
		}
	}

	protected void performCritBehavior() {

	}

	public static enum MobAttackStrategy {
		HUMAN(2, 2, true, true), // "Smart" behavior; selects attack based on weapons, strafes when in range,
									// tries to block attacks if applicable, attempts criticals.
		ZOMBIE(0, 0, false, false), // Never stops due to reach, and always attacks type 0. Never blocks.
		SKELETON(1, 1, true, false), // Randomly selects which attack type to do; chases and then just stops when in
										// range. Attempts blocks.
		ENDERMAN(0, 2, false, false), // Always attacks type 0, strafes when in range.
		ILLAGER(2, 2, false, true); // Same as human, but doesn't block.

		private final int attackType;
		private final int reachResponse;
		private final boolean block;
		private final boolean attemptCrit;

		private MobAttackStrategy(int attackType, int reachResponse, boolean block, boolean attemptCrit) {
			this.attackType = attackType;
			this.reachResponse = reachResponse;
			this.block = block;
			this.attemptCrit = attemptCrit;
		}
	}
}
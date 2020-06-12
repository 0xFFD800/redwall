package com.bob.redwall.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAITargetNPC<T extends EntityLivingBase> extends EntityAITarget {
	protected final Class<T> targetClass;
	protected final EntityAITargetNPC.Sorter theNearestAttackableTargetSorter;
	protected final Predicate<? super Entity> targetEntitySelector;
	protected T targetEntity;

	public EntityAITargetNPC(final EntityAbstractNPC creature, Class<T> classTarget, boolean checkSight) {
		super(creature, checkSight);
		this.targetClass = classTarget;
		this.theNearestAttackableTargetSorter = new EntityAITargetNPC.Sorter(creature);
		this.targetEntitySelector = new Predicate<Entity>() {
			@Override
			public boolean apply(@Nullable Entity p_apply_1_) {
				return p_apply_1_ == null || !(p_apply_1_ instanceof EntityLivingBase) ? false : (!EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) ? false : EntityAITargetNPC.isSuitableTarget(EntityAITargetNPC.this.taskOwner, p_apply_1_, false) && EntityAITargetNPC.this.targetClass.isAssignableFrom(p_apply_1_.getClass()) && EntityAITargetNPC.this.shouldBeAgainstMob(EntityAITargetNPC.this.taskOwner, (EntityLivingBase) p_apply_1_));
			}
		};
	}

	public Class<T> getTargetClass() {
		return this.targetClass;
	}

	public boolean getChecksSight() {
		return this.shouldCheckSight;
	}

	protected static boolean isSuitableTarget(EntityLiving attacker, @Nullable Entity entity, boolean includeInvincibles) {
		if (entity instanceof EntityLivingBase) {
			return EntityAITarget.isSuitableTarget(attacker, (EntityLivingBase) entity, includeInvincibles, true);
		}
		return false;
	}

	protected boolean shouldBeAgainstMob(EntityLiving attacker, @Nullable EntityLivingBase defender) {
		return ((EntityAbstractNPC) attacker).willFightEntity(defender) && (defender == null ? false : this.targetClass.isAssignableFrom(defender.getClass()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean shouldExecute() {
		if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class) {
			List<Entity> list = this.taskOwner.world.getEntitiesInAABBexcluding(this.taskOwner, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

			if (list.isEmpty()) {
				return false;
			} else {
				Collections.sort(list, this.theNearestAttackableTargetSorter);
				float brightness = EntityAITargetNPC.this.taskOwner.world.getLightBrightness(list.get(0).getPosition());
				if (brightness < 1.0D && this.shouldCheckSight) {
					list = this.taskOwner.world.getEntitiesInAABBexcluding(this.taskOwner, this.getTargetableArea(this.getTargetDistance() * (brightness)), this.targetEntitySelector);
					if (list.isEmpty()) {
						return false;
					} else {
						Collections.sort(list, this.theNearestAttackableTargetSorter);
						if (list.get(0) instanceof EntityLivingBase && this.shouldBeAgainstMob(this.taskOwner, (EntityLivingBase) list.get(0))) {
							this.targetEntity = (T) list.get(0);
							return true;
						} else {
							return false;
						}
					}
				}

				if (list.get(0) instanceof EntityLivingBase && this.shouldBeAgainstMob(this.taskOwner, (EntityLivingBase) list.get(0))) {
					this.targetEntity = (T) list.get(0);
					return true;
				} else {
					return false;
				}
			}
		} else {
			EntityLivingBase entity = (T) this.taskOwner.world.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + (double) this.taskOwner.getEyeHeight(), this.taskOwner.posZ, this.getTargetDistance(), this.getTargetDistance(), new Function<EntityPlayer, Double>() {
				@Override
				public Double apply(@Nullable EntityPlayer p_apply_1_) {
					if (p_apply_1_ != null && EntityAITargetNPC.this.shouldCheckSight) {
						float brightness = EntityAITargetNPC.this.taskOwner.world.getLightBrightness(p_apply_1_.getPosition());
						return Double.valueOf(brightness);
					}
					return 1.0D;
				}
			}, new Predicate<EntityPlayer>() {
				@Override
				public boolean apply(@Nullable EntityPlayer p_apply_1_) {
					return p_apply_1_ == null || (p_apply_1_ instanceof EntityLivingBase == false) ? false : (!EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) ? false : EntityAITargetNPC.isSuitableTarget(EntityAITargetNPC.this.taskOwner, p_apply_1_, false));
				}
			});
			if (entity != null && this.shouldBeAgainstMob(this.taskOwner, entity)) {
				this.targetEntity = (T) entity;
			} else {
				this.targetEntity = null;
			}
			return this.targetEntity != null;
		}
	}

	protected AxisAlignedBB getTargetableArea(double targetDistance) {
		return this.taskOwner.getEntityBoundingBox().expand(targetDistance, targetDistance, targetDistance);
	}

	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator<Entity> {
		private final Entity entity;

		public Sorter(Entity entity) {
			this.entity = entity;
		}

		@Override
		public int compare(Entity p_compare_1_, Entity p_compare_2_) {
			double d0 = this.entity.getDistanceSq(p_compare_1_);
			double d1 = this.entity.getDistanceSq(p_compare_2_);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}
	}
}
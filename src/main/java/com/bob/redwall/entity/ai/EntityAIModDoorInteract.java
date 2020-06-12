package com.bob.redwall.entity.ai;

import com.bob.redwall.blocks.multiuse.BlockModDoor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public class EntityAIModDoorInteract extends EntityAIBase {
	protected EntityLiving entity;
	protected BlockPos doorPosition = BlockPos.ORIGIN;
	protected BlockModDoor doorBlock;
	boolean hasStoppedDoorInteraction;
	float entityPositionX;
	float entityPositionZ;

	public EntityAIModDoorInteract(EntityLiving entityIn) {
		this.entity = entityIn;

		if (!(entityIn.getNavigator() instanceof PathNavigateGround)) {
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	@Override
	public boolean shouldExecute() {
		if (!this.entity.collidedHorizontally) {
			return false;
		} else {
			PathNavigateGround pathnavigateground = (PathNavigateGround) this.entity.getNavigator();
			Path path = pathnavigateground.getPath();

			if (path != null && !path.isFinished() && pathnavigateground.getEnterDoors()) {
				for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i) {
					PathPoint pathpoint = path.getPathPointFromIndex(i);
					this.doorPosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z);

					if (this.entity.getDistanceSq((double) this.doorPosition.getX(), this.entity.posY, (double) this.doorPosition.getZ()) <= 2.25D) {
						this.doorBlock = this.getBlockModDoor(this.doorPosition);

						if (this.doorBlock != null) {
							return true;
						}
					}
				}

				this.doorPosition = (new BlockPos(this.entity)).up();
				this.doorBlock = this.getBlockModDoor(this.doorPosition);
				return this.doorBlock != null;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !this.hasStoppedDoorInteraction;
	}

	@Override
	public void startExecuting() {
		this.hasStoppedDoorInteraction = false;
		this.entityPositionX = (float) ((double) ((float) this.doorPosition.getX() + 0.5F) - this.entity.posX);
		this.entityPositionZ = (float) ((double) ((float) this.doorPosition.getZ() + 0.5F) - this.entity.posZ);
	}

	@Override
	public void updateTask() {
		float f = (float) ((double) ((float) this.doorPosition.getX() + 0.5F) - this.entity.posX);
		float f1 = (float) ((double) ((float) this.doorPosition.getZ() + 0.5F) - this.entity.posZ);
		float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

		if (f2 < 0.0F) {
			this.hasStoppedDoorInteraction = true;
		}
	}

	private BlockModDoor getBlockModDoor(BlockPos pos) {
		IBlockState iblockstate = this.entity.world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return block instanceof BlockModDoor && iblockstate.getMaterial() == Material.WOOD ? (BlockModDoor) block : null;
	}
}

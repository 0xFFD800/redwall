package com.bob.redwall.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenModDoor extends EntityAIModDoorInteract {
	boolean closeDoor;
	int closeDoorTemporisation;

	public EntityAIOpenModDoor(EntityLiving entitylivingIn, boolean shouldClose) {
		super(entitylivingIn);
		this.entity = entitylivingIn;
		this.closeDoor = shouldClose;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
	}

	@Override
	public void startExecuting() {
		this.closeDoorTemporisation = 20;
		this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, true);
	}

	@Override
	public void resetTask() {
		if (this.closeDoor) {
			this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, false);
		}
	}

	@Override
	public void updateTask() {
		--this.closeDoorTemporisation;
		super.updateTask();
	}
}
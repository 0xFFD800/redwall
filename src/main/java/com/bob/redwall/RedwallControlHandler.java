package com.bob.redwall;

import com.bob.redwall.common.MessageSetCap;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.common.MessageSetCap.Mode;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class RedwallControlHandler {
	public static boolean handleAttack(int mode) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		IAttacking attackCap = player.getCapability(AttackingProvider.ATTACKING_CAP, null);
		float reach = (float) player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

		if (player.getCooledAttackStrength(mc.getRenderPartialTicks()) != 1.0F) {
			if (attackCap.get()) {
				Ref.NETWORK.sendToServer(new MessageUIInteractServer(MessageUIInteractServer.Mode.PERFORM_ATTACK));
				attackCap.set(false);
			}
			return true;
		}

		switch (mode) {
		case 0: {
			ItemStack itemstack = player.getHeldItemMainhand();

			RayTraceResult result = RedwallUtils.raytrace(player, reach);
			if (result == null)
				return false;
			if (result.typeOfHit != RayTraceResult.Type.BLOCK) {
				if (itemstack.getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) itemstack.getItem()).isTwoHanded(itemstack, player) && !player.getHeldItemOffhand().isEmpty())
					return true;

				attackCap.set(true);
				attackCap.setMode(0);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 0, Mode.ATTACKING));
				player.resetCooldown();
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		}
		case 1: {
			ItemStack itemstack = player.getHeldItemMainhand();
			RayTraceResult result = RedwallUtils.raytrace(player, reach);

			if (result == null)
				return false;
			if (result.typeOfHit != RayTraceResult.Type.BLOCK) {
				if (itemstack.getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) itemstack.getItem()).isTwoHanded(itemstack, player) && !player.getHeldItemOffhand().isEmpty())
					return true;

				player.getCapability(AttackingProvider.ATTACKING_CAP, null).set(true);
				player.getCapability(AttackingProvider.ATTACKING_CAP, null).setMode(1);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 1, Mode.ATTACKING));
				player.resetCooldown();
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		}
		case 2: {
			ItemStack itemstack = player.getHeldItemMainhand();
			RayTraceResult result = RedwallUtils.raytrace(player, reach);

			if (result == null)
				return false;
			if (result.typeOfHit != RayTraceResult.Type.BLOCK) {
				if (itemstack.getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) itemstack.getItem()).isTwoHanded(itemstack, player) && !player.getHeldItemOffhand().isEmpty())
					return true;

				player.getCapability(AttackingProvider.ATTACKING_CAP, null).set(true);
				player.getCapability(AttackingProvider.ATTACKING_CAP, null).setMode(2);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 2, Mode.ATTACKING));
				player.resetCooldown();
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		}
		}

		return false;
	}

	public static boolean handleDefenseStart(int mode) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		if (player == null)
			return false;
		float reach = (float) player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

		switch (mode) {
		case 0: {
			ItemStack itemstack = player.getHeldItemMainhand();

			RayTraceResult result = RedwallUtils.raytrace(player, reach);
			if (result == null)
				return false;
			if (result.typeOfHit != RayTraceResult.Type.BLOCK && itemstack.getItem() instanceof ModCustomWeapon) {
				if (player.getCooledAttackStrength(mc.getRenderPartialTicks()) != 1.0F)
					return true;

				if (itemstack != null)
					if (((ModCustomWeapon) itemstack.getItem()).isTwoHanded(itemstack, player) && !player.getHeldItemOffhand().isEmpty())
						return true;

				player.getCapability(DefendingProvider.DEFENDING_CAP, null).set(true);
				player.getCapability(DefendingProvider.DEFENDING_CAP, null).setMode(0);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 0, Mode.DEFENDING));
				return true;
			}
		}
		case 1: {
			ItemStack itemstack = player.getHeldItemMainhand();
			RayTraceResult result = RedwallUtils.raytrace(player, reach);

			if (result == null)
				return false;
			if (result.typeOfHit != RayTraceResult.Type.BLOCK && itemstack.getItem() instanceof ModCustomWeapon) {
				if (player.getCooledAttackStrength(mc.getRenderPartialTicks()) != 1.0F)
					return true;

				if (itemstack != null)
					if (((ModCustomWeapon) itemstack.getItem()).isTwoHanded(itemstack, player) && !player.getHeldItemOffhand().isEmpty())
						return true;

				player.getCapability(DefendingProvider.DEFENDING_CAP, null).set(true);
				player.getCapability(DefendingProvider.DEFENDING_CAP, null).setMode(1);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 1, Mode.DEFENDING));
				return true;
			}
		}
		case 2: {
			ItemStack itemstack = player.getHeldItemOffhand();
			if (itemstack.getItemUseAction() == EnumAction.BLOCK) {
				player.setActiveHand(EnumHand.OFF_HAND);
				Ref.NETWORK.sendToServer(new MessageSetCap(true, 2, Mode.DEFENDING));
				player.getCapability(DefendingProvider.DEFENDING_CAP, null).set(true);
				player.getCapability(DefendingProvider.DEFENDING_CAP, null).setMode(2);
				return true;
			}
		}
		}

		return false;
	}

	public static boolean handleDefenseEnd(int mode) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		if (player == null)
			return false;
		if (mode == 1 || mode == 0) {
			boolean returnb = player.getCapability(DefendingProvider.DEFENDING_CAP, null).get();
			player.getCapability(DefendingProvider.DEFENDING_CAP, null).set(false);
			player.getCapability(DefendingProvider.DEFENDING_CAP, null).setMode(0);
			Ref.NETWORK.sendToServer(new MessageSetCap(false, 0, Mode.DEFENDING));
			return returnb;
		} else if (mode == 2) {
			player.resetActiveHand();
			Ref.NETWORK.sendToServer(new MessageSetCap(false, 0, Mode.DEFENDING));
			player.getCapability(DefendingProvider.DEFENDING_CAP, null).set(false);
			player.getCapability(DefendingProvider.DEFENDING_CAP, null).setMode(0);
		}
		return false;
	}
}

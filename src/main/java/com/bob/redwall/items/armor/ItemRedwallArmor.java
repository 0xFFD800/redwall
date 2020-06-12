package com.bob.redwall.items.armor;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemRedwallArmor extends ItemArmor {
	private final float weight;
	public ItemRedwallArmor(String name, CreativeTabs tab, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, float f) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.weight = f;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
	}
	
	public float getArmorWeight() {
		return this.weight;
	}
	
	@Override
	public String getArmorTexture(ItemStack armor, Entity entity, EntityEquipmentSlot slot, String type) {
        ItemArmor item = (ItemArmor)armor.getItem();
        String texture = item.getArmorMaterial().getName();
        String domain = "redwall";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s = entity instanceof EntityAbstractNPC ? "/" + ((EntityAbstractNPC)entity).getNPCType().type : "";
		String s1 = String.format("%s:textures/models/armor%s/%s_layer_%d%s.png", domain, s, texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

        return s1;
	}

    private boolean isLegSlot(EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.LEGS;
    }
}

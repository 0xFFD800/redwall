package com.bob.redwall.init;

import com.bob.redwall.items.brewing.ItemDrinkVessel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;

public class ItemColorHandler {
	public static void init() {
		ItemColorHandler.registerItemColorHandler(new IItemColor() {
            @Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
                return ColorizerFoliage.getFoliageColorBasic();
            }
        }, new Item[] { Item.getItemFromBlock(BlockHandler.maple_leaves), Item.getItemFromBlock(BlockHandler.elm_leaves), Item.getItemFromBlock(BlockHandler.ash_leaves), Item.getItemFromBlock(BlockHandler.larch_leaves), Item.getItemFromBlock(BlockHandler.hornbeam_leaves), Item.getItemFromBlock(BlockHandler.alder_leaves), Item.getItemFromBlock(BlockHandler.aspen_leaves), Item.getItemFromBlock(BlockHandler.beech_leaves), Item.getItemFromBlock(BlockHandler.willow_leaves) });
		ItemColorHandler.registerItemColorHandler(new IItemColor() {
            @Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
                return tintIndex == 0 ? ColorizerFoliage.getFoliageColorBasic() : -1;
            }
        }, new Item[] { Item.getItemFromBlock(BlockHandler.apple_leaves), Item.getItemFromBlock(BlockHandler.plum_leaves), Item.getItemFromBlock(BlockHandler.pear_leaves), Item.getItemFromBlock(BlockHandler.quince_leaves), Item.getItemFromBlock(BlockHandler.chestnut_leaves), Item.getItemFromBlock(BlockHandler.wheatgrass), Item.getItemFromBlock(BlockHandler.shortgrass) });
		ItemColorHandler.registerItemColorHandler(new IItemColor() {
            @Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
                return ColorizerFoliage.getFoliageColorPine();
            }
        }, new Item[] { Item.getItemFromBlock(BlockHandler.pine_leaves), Item.getItemFromBlock(BlockHandler.fir_leaves), Item.getItemFromBlock(BlockHandler.yew_leaves) });
		ItemColorHandler.registerItemColorHandler(new IItemColor() {
            @Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
                return tintIndex == 1 ? (ItemDrinkVessel.getDrink(stack) == null ? -1 : ItemDrinkVessel.getDrink(stack).getTint()) : -1;
            }
		}, new Item[] { ItemHandler.drink_bottle, ItemHandler.mug_drink, ItemHandler.bowl_drink });
		/*ItemColorHandler.registerItemColorHandler(new IItemColor() {
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return tintIndex == 1 || tintIndex == 0 ? ((ItemSwordHilt)stack.getItem()).getHandleType(stack).getColor() : tintIndex == 2 ? ((ItemSwordHilt)stack.getItem()).getPommelType(stack).getColor() : tintIndex == 3 ? ((ItemSwordHilt)stack.getItem()).getPommelStoneType(stack).getColor() : -1;
            }
        }, new Item[] {ItemHandler.sword_hilt});
		ItemColorHandler.registerItemColorHandler(new IItemColor() {
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return tintIndex == 1 ? ((ItemCustomSword)stack.getItem()).getHandleType(stack).getColor() : tintIndex == 2 ? ((ItemCustomSword)stack.getItem()).getPommelType(stack).getColor() : tintIndex == 3 ? ((ItemCustomSword)stack.getItem()).getPommelStoneType(stack).getColor() : -1;
            }
        }, new Item[] { ItemHandler.golden_sword, ItemHandler.golden_rapier, ItemHandler.golden_broadsword, ItemHandler.golden_scimitar, ItemHandler.iron_sword, ItemHandler.iron_rapier, ItemHandler.iron_broadsword, ItemHandler.iron_scimitar, ItemHandler.steel_sword, ItemHandler.steel_rapier, ItemHandler.steel_broadsword, ItemHandler.steel_scimitar, ItemHandler.eastern_broadsword, ItemHandler.eastern_sword });*/
	}
	
	public static void registerItemColorHandler(IItemColor color, Item[] items) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, items);
	}
}

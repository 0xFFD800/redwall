package com.bob.redwall.init;

import com.bob.redwall.tileentity.TileEntityDrinkVessel;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockColorHandler {
	public static void init() {
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
            }
        }, new Block[] {BlockHandler.maple_leaves, BlockHandler.elm_leaves, BlockHandler.ash_leaves, BlockHandler.larch_leaves, BlockHandler.hornbeam_leaves, BlockHandler.alder_leaves, BlockHandler.aspen_leaves, BlockHandler.beech_leaves, BlockHandler.willow_leaves});
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return tintIndex == 0 ? (worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic()) : -1;
            }
        }, new Block[] {BlockHandler.apple_leaves, BlockHandler.plum_leaves, BlockHandler.pear_leaves, BlockHandler.quince_leaves, BlockHandler.chestnut_leaves});
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return ColorizerFoliage.getFoliageColorPine();
            }
        }, new Block[] {BlockHandler.pine_leaves, BlockHandler.fir_leaves, BlockHandler.yew_leaves});
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
            	return tintIndex == 0 ? (worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic()) : -1;
            }
        }, new Block[] {BlockHandler.wheatgrass, BlockHandler.shortgrass});
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return tintIndex == 0 ? (worldIn != null && pos != null ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 3031284) : -1;
            }
        }, new Block[] {BlockHandler.water_reeds});
		BlockColorHandler.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return tintIndex == 1 ? (worldIn != null && pos != null ? (worldIn.getTileEntity(pos) instanceof TileEntityDrinkVessel && ((TileEntityDrinkVessel)worldIn.getTileEntity(pos)).getDrink() != null ? ((TileEntityDrinkVessel)worldIn.getTileEntity(pos)).getDrink().getTint() : 0) : -1) : -1;
            }
        }, new Block[] {BlockHandler.mug, BlockHandler.bowl, BlockHandler.bottle});
	}
	
	public static void registerBlockColorHandler(IBlockColor color, Block[] blocks) {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(color, blocks);
	}
}

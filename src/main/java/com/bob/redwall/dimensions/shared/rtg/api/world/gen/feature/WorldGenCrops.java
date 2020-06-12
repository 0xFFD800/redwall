package com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature;

import java.util.Random;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.bob.redwall.blocks.plants.BlockModBush;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrops extends WorldGenerator {

    private Block farmType;
    private int farmSize;
    private int farmDensity;
    private int farmHeight;
    private boolean farmWater;
    private boolean shouldPlaceFarmland;


    /*
     * 0 = potatoes, 1 = carrots, 2 = beetroot, 3 = wheat
     */
    public WorldGenCrops(int type, int size, int density, int height, Boolean water, @Nullable Block block, boolean shouldPlaceFarmland) {

        farmType = type == 0 ? Blocks.POTATOES : type == 1 ? Blocks.CARROTS : type == 2 ? Blocks.BEETROOTS : BlockHandler.wheatgrass;
        if(block != null) farmType = block;
        farmSize = size;
        farmDensity = density;
        farmHeight = height;
        farmWater = water;
        this.shouldPlaceFarmland = shouldPlaceFarmland;
    }

    @ParametersAreNonnullByDefault
    public boolean generate(World world, Random rand, BlockPos blockPos) {

        return this.generate(world, rand, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {

        IBlockState b;
        while (y > 0) {
            b = world.getBlockState(new BlockPos(x, y, z));
            if (!world.isAirBlock(new BlockPos(x, y, z)) || b.getBlock().isLeaves(b, world, new BlockPos(x, y, z))) {
                break;
            }
            y--;
        }

        b = world.getBlockState(new BlockPos(x, y, z));
        if (b.getBlock() != Blocks.GRASS && b.getBlock() != Blocks.DIRT) {
            return false;
        }

        for (int j = 0; j < 4; j++) {
            b = world.getBlockState(new BlockPos(j == 0 ? x - 1 : j == 1 ? x + 1 : x, y, j == 2 ? z - 1 : j == 3 ? z + 1 : z));
            if (b.getMaterial() != Material.GROUND && b.getMaterial() != Material.GRASS) {
                return false;
            }
        }

        int maxGrowth = this.farmType instanceof BlockCrops ? ((BlockCrops) farmType).getMaxAge() + 1 : 0;
        if(this.farmType instanceof BlockModBush) maxGrowth = ((BlockModBush)farmType).getMaxAge() + 1;

        int rx, ry, rz;
        for (int i = 0; i < farmDensity; i++) {
            rx = rand.nextInt(farmSize) - 2;
            ry = rand.nextInt(farmHeight) - 1;
            rz = rand.nextInt(farmSize) - 2;
            b = world.getBlockState(new BlockPos(x + rx, y + ry, z + rz));

            if ((b.getBlock() == Blocks.GRASS || b.getBlock() == Blocks.DIRT) && world.isAirBlock(new BlockPos(x + rx, y + ry + 1, z + rz))) {
                if(this.farmType != BlockHandler.wheatgrass && this.shouldPlaceFarmland) world.setBlockState(new BlockPos(x + rx, y + ry, z + rz), Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE,rand.nextInt(8)));
                else world.setBlockState(new BlockPos(x + rx, y + ry, z + rz), Blocks.GRASS.getDefaultState());
                if(this.farmType instanceof BlockCrops) world.setBlockState(new BlockPos(x + rx, y +ry + 1, z + rz), ((BlockCrops) farmType).withAge(rand.nextInt(maxGrowth)));
                else if(this.farmType instanceof BlockModBush) world.setBlockState(new BlockPos(x + rx, y +ry + 1, z + rz), ((BlockModBush) farmType).withAge(maxGrowth - 1).withProperty(BlockModBush.BERRIES, rand.nextBoolean()));
                else world.setBlockState(new BlockPos(x + rx, y +ry + 1, z + rz), this.farmType.getDefaultState());
            }
        }
        if(farmWater == true) {
            world.setBlockState(new BlockPos(x, y, z), Blocks.WATER.getDefaultState());
        }
        return true;
    }
}

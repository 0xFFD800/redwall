package com.bob.redwall.blocks.plants;

import java.util.Random;

import javax.annotation.Nullable;

import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWaterReeds extends ModBlock implements net.minecraftforge.common.IPlantable {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    public static final PropertyBool WATERY = PropertyBool.create("watery");
    public static final PropertyBool TOP = PropertyBool.create("top");
    protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    public BlockWaterReeds(String name) {
        super(new MaterialLogic(MapColor.GRASS){
        	@Override
        	public boolean blocksMovement() {
        		return true;
        	}
        }, name, null, 0.0F, 0.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return REED_AABB;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.getBlockState(pos.down()).getBlock() == BlockHandler.water_reeds || this.checkForDrop(worldIn, pos, state)) {
            if (worldIn.isAirBlock(pos.up())) {
                int i;

                for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
                    ;
                }

                if (i < 6) {
                    int j = ((Integer)state.getValue(AGE)).intValue();

                    if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                    if (j == 7) {
                        worldIn.setBlockState(pos.up(), this.getStateForPlacement(worldIn, pos, EnumFacing.UP, 0, 0, 0, 0, null, null));
                        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 4);
                    } else {
                        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                    }
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        Block block = state.getBlock();

        if (block == this) {
            return true;
        } else if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SAND && block != Blocks.CLAY) {
            return false;
        } else {
            if(worldIn.getBlockState(pos).getBlock() == Blocks.WATER || (worldIn.getBlockState(pos).getBlock() == this && worldIn.getBlockState(pos).getValue(WATERY).booleanValue())) {
            	return true;
            }
            
            BlockPos pos0 = pos.down();
            while(!worldIn.getBlockState(pos0).isSideSolid(worldIn, pos0, EnumFacing.DOWN) && !worldIn.getBlockState(pos0).getBlock().isAir(worldIn.getBlockState(pos0), worldIn, pos0)) {
            	if(worldIn.getBlockState(pos0).getBlock() == Blocks.WATER || (worldIn.getBlockState(pos0).getBlock() == this && worldIn.getBlockState(pos0).getValue(WATERY).booleanValue())) return true;
            	pos0 = pos0.down();
            }
            
            BlockPos pos1 = pos.up();
            while(!worldIn.getBlockState(pos1).isSideSolid(worldIn, pos1, EnumFacing.DOWN) && !worldIn.getBlockState(pos1).getBlock().isAir(worldIn.getBlockState(pos1), worldIn, pos1)) {
            	if(worldIn.getBlockState(pos1).getBlock() == Blocks.WATER || (worldIn.getBlockState(pos1).getBlock() == this && worldIn.getBlockState(pos1).getValue(WATERY).booleanValue())) return true;
            	pos1 = pos1.up();
            }

            return false;
        }
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkForDrop(worldIn, pos, state);
    }

    protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (this.canBlockStay(worldIn, pos)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        return this.canPlaceBlockAt(worldIn, pos);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemHandler.water_reeds;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ItemHandler.water_reeds);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta < 8 ? this.getDefaultState().withProperty(AGE, Integer.valueOf(meta)).withProperty(WATERY, false) : this.getDefaultState().withProperty(AGE, Integer.valueOf(meta - 8)).withProperty(WATERY, true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Boolean)state.getValue(WATERY)).booleanValue() ? ((Integer)state.getValue(AGE)).intValue() + 8 : ((Integer)state.getValue(AGE)).intValue();
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return world.getBlockState(pos).getBlock() == Blocks.WATER && world.getBlockState(pos.up()).getBlock() != Blocks.WATER ? getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(WATERY, true) : getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return net.minecraftforge.common.EnumPlantType.Water;
    }
    
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {AGE, WATERY, TOP});
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).getBlock() == this ? state.withProperty(TOP, false) : state.withProperty(TOP, true);
    }
}
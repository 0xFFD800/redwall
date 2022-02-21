package com.bob.redwall.blocks.decor;

import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;
import com.bob.redwall.tileentity.TileEntityPlate;
import com.bob.redwall.tileentity.tesr.TESRPlate;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlate extends ModBlock implements ITileEntityProvider {
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.2D, 0.9D);
	
	public BlockPlate(Material mat, String name, CreativeTabs tab) {
		super(mat, name, tab, 0, 0);
        this.initModel();
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TESRPlate());
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPlate();
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileEntityPlate))
			return false;
		
		TileEntityPlate ted = (TileEntityPlate)te;

        if (player instanceof EntityPlayerMP)
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, new ItemStack(this));

        if (!world.isRemote) {
        	if (ted.getStack() != ItemStack.EMPTY) {
        		if (player.isSneaking()) {
        			player.addItemStackToInventory(ted.getStack());
            		ted.setStack(ItemStack.EMPTY);
        		} else if (player.getHeldItem(hand).getItem() instanceof ItemFood && ItemStack.areItemsEqual(player.getHeldItem(hand), ted.getStack())) {
        			ted.growStack(1);
        			player.getHeldItem(hand).shrink(1);
        			if (player.getHeldItem(hand).isEmpty())
        				player.setHeldItem(hand, ItemStack.EMPTY);
        		} else if (player.canEat(false) && ted.getStack().getItem() instanceof ItemFood) {
	        		((ItemFood)ted.getStack().getItem()).onItemUseFinish(ted.getStack(), world, player);
        			ted.growStack(-1);
        		}
        	} else {
        		if (player.getHeldItem(hand).getItem() instanceof ItemFood) {
        			ted.setStack(new ItemStack(player.getHeldItem(hand).getItem(), 1, player.getHeldItem(hand).getItemDamage()));
        			player.getHeldItem(hand).shrink(1);
        			if (player.getHeldItem(hand).isEmpty())
        				player.setHeldItem(hand, ItemStack.EMPTY);
        		}
        	}
        }

        if (player != null)
            player.addStat(StatList.getObjectUseStats(Item.getItemFromBlock(this)));
        
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityPlate)
			drops.add(((TileEntityPlate)te).getStack());
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return STANDING_AABB;
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return this.canBePlacedOn(worldIn, pos.down());
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBePlacedOn(worldIn, pos.down())) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBePlacedOn(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isSideSolid(worldIn, pos, EnumFacing.UP) || worldIn.getBlockState(pos).getBlock() instanceof BlockFence;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityDrinkVessel && (entityIn instanceof EntityLivingBase || entityIn instanceof EntityArrow)) {
        	TileEntityPlate te = (TileEntityPlate)worldIn.getTileEntity(pos);
            if(te.getStack() != ItemStack.EMPTY)
	            for(int i = 0; i < 30; i++)
	            	worldIn.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + RANDOM.nextFloat(), pos.getY() + RANDOM.nextFloat(), pos.getZ() + RANDOM.nextFloat(), (RANDOM.nextFloat() - 0.5F) * 10, (RANDOM.nextFloat() - 0.5F) * 10 + 5, (RANDOM.nextFloat() - 0.5F) * 10, Item.getIdFromItem(te.getStack().getItem()));
            
            if(!worldIn.isRemote) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
	            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ARMORSTAND_BREAK, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat(), false);
            }
        }
    }
}

package com.bob.redwall.blocks.decor;

import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.crafting.cooking.FoodModifier;
import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.bob.redwall.items.brewing.ItemDrinkVessel;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDrinkVessel extends ModBlock implements ITileEntityProvider {
    public static final PropertyBool FULL = PropertyBool.create("full");
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.5D, 0.7D);
	public ResourceLocation itemIdFull;
	public ResourceLocation itemIdEmpty;
	
	public BlockDrinkVessel(Material mat, String name, ResourceLocation itemFull, ResourceLocation itemEmpty) {
		super(mat, name, null, 0, 0);
		this.itemIdFull = itemFull;
		this.itemIdEmpty = itemEmpty;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDrinkVessel();
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
		// Only execute on the server
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileEntityDrinkVessel) || ((TileEntityDrinkVessel)te).getDrink() == null) {
			return false;
		}
		
		TileEntityDrinkVessel ted = (TileEntityDrinkVessel)te;

        if (player instanceof EntityPlayerMP) {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, new ItemStack(this));
        }

        if (!world.isRemote) {
            ted.getDrink().onConsumed(ted, world, player);
        }

        ted.setDrink(null);
        ted.getModifiers().clear();
        ted.getLevels().clear();

        if (player != null) {
            player.addStat(StatList.getObjectUseStats(Item.getItemFromBlock(this)));
        }
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return this.getItem((IBlockAccess)world, pos, state);
	}
	
	public ItemStack getItem(IBlockAccess world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityDrinkVessel && ((TileEntityDrinkVessel)te).getDrink() != null) {
			TileEntityDrinkVessel ted = (TileEntityDrinkVessel)te;
	        ItemStack stack = new ItemStack(Item.getByNameOrId(this.itemIdFull.toString()), 1);
	        ItemDrinkVessel.setDrink(stack, ted.getDrink());

			NBTTagList mlist = new NBTTagList();
			for(FoodModifier mod : ted.getModifiers()) {
				NBTTagCompound mnbt = new NBTTagCompound();
				mnbt.setInteger("id", FoodModifier.getModifierID(mod));
				mnbt.setInteger("lvl", ted.getLevels().get(ted.getModifiers().indexOf(mod)));
				mlist.appendTag(mnbt);
			}
			stack.getTagCompound().setTag(FoodModifierUtils.MODIFIER_LIST_KEY, mlist);
			
	        return stack;
		} else {
	        return new ItemStack(Item.getByNameOrId(this.itemIdEmpty.toString()), 1);
		}
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		spawnAsEntity(worldIn, pos, this.getItem(worldIn, pos, state));
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {}
	
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
            TileEntityDrinkVessel te = (TileEntityDrinkVessel)worldIn.getTileEntity(pos);
            if(te.getDrink() != null) {
	            for(int i = 0; i < 30; i++) {
	            	worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + RANDOM.nextFloat(), pos.getY() + RANDOM.nextFloat(), pos.getZ() + RANDOM.nextFloat(), (RANDOM.nextFloat() - 0.5F) * 10, (RANDOM.nextFloat() - 0.5F) * 10 + 5, (RANDOM.nextFloat() - 0.5F) * 10, 0);
	            	//Particle particle = new ParticleSplash.Factory().createParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), worldIn, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, RANDOM.nextFloat(), RANDOM.nextFloat() * 4 + 1, RANDOM.nextFloat(), 0);
	            	//particle.setRBGColorF((float)((te.getDrink().getTint() >>> 16) & 0xFF) / 255.0F, (float)((te.getDrink().getTint() >>> 8) & 0xFF) / 255.0F, (float)(te.getDrink().getTint() & 0xFF) / 255.0F);
	            }
            }
            
            if(!worldIn.isRemote) {
	            te.setDrink(null);
	            this.onBlockHarvested(worldIn, pos, state, null);
	            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ARMORSTAND_BREAK, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat(), false);
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FULL});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
    	return 0;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	if(!(worldIn.getTileEntity(pos) instanceof TileEntityDrinkVessel)) return state;
        return state.withProperty(FULL, ((TileEntityDrinkVessel)worldIn.getTileEntity(pos)).getDrink() != null);
    }
}

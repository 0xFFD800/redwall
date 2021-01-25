package com.bob.redwall.items.brewing;

import javax.annotation.Nullable;

import com.bob.redwall.items.ModItem;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrinkVessel extends ModItem {
	private ResourceLocation blockId;
	
	public ItemDrinkVessel(String name, CreativeTabs tab, ResourceLocation blockId) {
		super(name, tab);
		this.blockId = blockId;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		ItemDrinkVessel.setDrink(stack, Drink.DrinkList.WATER);
        return stack;
    }
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for(Drink drink : Drink.BY_ID.values()) {
            	items.add(ItemDrinkVessel.setDrink(this.getDefaultInstance().copy(), drink));
            }
        }
    }
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
        }

        if (!worldIn.isRemote) {
            if(ItemDrinkVessel.getDrink(stack) != null) ItemDrinkVessel.getDrink(stack).onConsumed(stack, worldIn, entityLiving);
        }

        if (entityplayer != null) {
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(this.getContainerItem());
            }

            if (entityplayer != null) {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(this.getContainerItem()));
            }
        }

        return stack;
    }
	
	public static ItemStack setDrink(ItemStack stack, Drink drink) {
		ItemDrinkVessel.getTagCompoundSafe(stack).setString("drink", drink.getID().toString());
		return stack;
	}
	
	@Nullable
	public static Drink getDrink(ItemStack stack) {
		return Drink.BY_ID.get(new ResourceLocation(ItemDrinkVessel.getTagCompoundSafe(stack).getString("drink")));
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

		if (raytraceresult != null) {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = raytraceresult.getBlockPos();
	            Vec3d vec = raytraceresult.hitVec;
	            EnumFacing facing = raytraceresult.sideHit;

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
				}

				if(worldIn.getTileEntity(blockpos) instanceof TileEntityDrinkVessel) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					TileEntityDrinkVessel te = (TileEntityDrinkVessel)worldIn.getTileEntity(blockpos);
					if(te.getDrink() == null) {
						te.setDrink(ItemDrinkVessel.getDrink(itemstack));
						itemstack.shrink(1);
						ItemStack newStack = new ItemStack(this.getContainerItem());
						playerIn.addItemStackToInventory(newStack);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, newStack);
						
					} else {
						return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
					}
				} else {
					if (!worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos)) {
						blockpos = blockpos.offset(facing);
			        }
					if (!itemstack.isEmpty() && playerIn.canPlayerEdit(blockpos, facing, itemstack) && worldIn.mayPlace(this.makeBlock(), blockpos, false, facing, (Entity)null)) {
			            int i = this.getMetadata(itemstack.getMetadata());
			            IBlockState iblockstate1 = this.makeBlock().getStateForPlacement(worldIn, blockpos, facing, (float)vec.x, (float)vec.y, (float)vec.z, i, playerIn, handIn);

			            if (this.placeBlockAt(itemstack, playerIn, worldIn, blockpos, facing, (float)vec.x, (float)vec.y, (float)vec.z, iblockstate1)) {
			                iblockstate1 = worldIn.getBlockState(blockpos);
			                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, blockpos, playerIn);
			                worldIn.playSound(playerIn, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			                itemstack.shrink(1);
			            }

			            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
			        }
				}
			}
		}
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
	
	protected Block makeBlock() {
		return Block.getBlockFromName(this.blockId.toString());
	}

	@Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(this.getUnlocalizedName(stack) + ".name").trim() + " " + I18n.format("drink.".concat(ItemDrinkVessel.getDrink(stack) == null ? "empty" : ItemDrinkVessel.getDrink(stack).getID().getResourcePath()));
    }

    private static NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }
	
	public boolean hasStatusEffects(ItemStack stack) {
		return ItemDrinkVessel.getDrink(stack) != null && ItemDrinkVessel.getDrink(stack).hasStatusEffects();
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.makeBlock()) {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            if(world.getTileEntity(pos) instanceof TileEntityDrinkVessel) ((TileEntityDrinkVessel)world.getTileEntity(pos)).setDrink(ItemDrinkVessel.getDrink(stack));
            this.makeBlock().onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }
}

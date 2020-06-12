package com.bob.redwall.items.crafting;

import com.bob.redwall.items.ModItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSwordHilt extends ModItem {
	public ItemSwordHilt(String name, CreativeTabs tab) {
		super(name, tab);
	}
	
	public EnumHandleType getHandleType(ItemStack stack) {
		return EnumHandleType.getById((int) (this.getTagCompoundSafe(stack).hasKey("handle_type") ? this.getTagCompoundSafe(stack).getFloat("handle_type") : EnumHandleType.WOOD.getId()));
	}
	
	public EnumPommelType getPommelType(ItemStack stack) {
		return EnumPommelType.getById((int) (this.getTagCompoundSafe(stack).hasKey("pommel_type") ? this.getTagCompoundSafe(stack).getFloat("pommel_type") : EnumPommelType.WOOD.getId()));
	}
	
	public EnumPommelStoneType getPommelStoneType(ItemStack stack) {
		return EnumPommelStoneType.getById((int) (this.getTagCompoundSafe(stack).hasKey("pommel_stone_type") ? this.getTagCompoundSafe(stack).getFloat("pommel_stone_type") : EnumPommelStoneType.NONE.getId()));
	}
	
	public void setHandleType(ItemStack stack, EnumHandleType handleType) {
		this.getTagCompoundSafe(stack).setFloat("handle_type", handleType.getId());
	}
	
	public void setPommelType(ItemStack stack, EnumPommelType pommelType) {
		this.getTagCompoundSafe(stack).setFloat("pommel_type", pommelType.getId());
	}
	
	public void setPommelStoneType(ItemStack stack, EnumPommelStoneType pommelStoneType) {
		this.getTagCompoundSafe(stack).setFloat("pommel_stone_type", pommelStoneType.getId());
	}

    private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }
    
    public static enum EnumHandleType {
    	WOOD(0, 0x886626),
    	LEATHERBOUND(1, 0x333333),
    	METAL(2, 0xDCDCDC),
    	BONE(3, 0xEDEBCA);
    	
    	private final int id;
    	private final int color;
    	private EnumHandleType(int id, int color) {
    		this.id = id;
    		this.color = color;
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public int getColor() {
    		return this.color;
    	}
    	
    	public static EnumHandleType getById(int id) {
    		for(EnumHandleType type : EnumHandleType.values()) {
    			if(type.id == id) {
    				return type;
    			}
    		}
    		return WOOD;
    	}
    }
    
    public static enum EnumPommelType {
    	WOOD(0, 0x886626),
    	STONE(1, 0x9A9A9A),
    	GOLD(2, 0xFFD800),
    	BRONZE(3, 0xDB8F25),
    	IRON(4, 0xDCDCDC),
    	STEEL(5, 0xEBEBEB);
    	
    	private final int id;
    	private final int color;
    	private EnumPommelType(int id, int color) {
    		this.id = id;
    		this.color = color;
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public int getColor() {
    		return this.color;
    	}
    	
    	public static EnumPommelType getById(int id) {
    		for(EnumPommelType type : EnumPommelType.values()) {
    			if(type.id == id) {
    				return type;
    			}
    		}
    		return WOOD;
    	}
    }
    
    public static enum EnumPommelStoneType {
    	NONE(0, 0xEBEBEB),
    	DIAMOND(1, 0x8CF4E2),
    	EMERALD(2, 0x17DD62),
    	RUBY(3, 0xC7031F),
    	SAPPHIRE(4, 0x032CC7),
    	TOPAZ(5, 0x975519),
    	AMETHYST(6, 0x03C78E),
    	TURQUOISE(7, 0x03C7AB);
    	
    	private final int id;
    	private final int color;
    	private EnumPommelStoneType(int id, int color) {
    		this.id = id;
    		this.color = color;
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public int getColor() {
    		return this.color;
    	}
    	
    	public static EnumPommelStoneType getById(int id) {
    		for(EnumPommelStoneType type : EnumPommelStoneType.values()) {
    			if(type.id == id) {
    				return type;
    			}
    		}
    		return NONE;
    	}
    }
}

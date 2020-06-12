package com.bob.redwall.blocks.multiuse;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockModBrick extends Block {
    public static final PropertyEnum<BlockModBrick.EnumType> VARIANT = PropertyEnum.<BlockModBrick.EnumType>create("variant", BlockModBrick.EnumType.class);
    public static final int DEFAULT_META = BlockModBrick.EnumType.DEFAULT.getMetadata();
    public static final int MOSSY_META = BlockModBrick.EnumType.MOSSY.getMetadata();
    public static final int CRACKED_META = BlockModBrick.EnumType.CRACKED.getMetadata();
    public static final int CHISELED_META = BlockModBrick.EnumType.CHISELED.getMetadata();

    public BlockModBrick(String name, CreativeTabs tab, float hardness, float resistance, int harvest, String tool) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(tab);
        this.setHardness(hardness);
        this.setResistance(resistance);
		this.setHarvestLevel(tool, harvest);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockModBrick.EnumType.DEFAULT));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((BlockModBrick.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (BlockModBrick.EnumType blockstonebrick$enumtype : BlockModBrick.EnumType.values()) {
            items.add(new ItemStack(this, 1, blockstonebrick$enumtype.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockModBrick.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((BlockModBrick.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    public static enum EnumType implements IStringSerializable {
        DEFAULT(0, "_brick", "default"),
        MOSSY(1, "_brick_mossy", "mossy"),
        CRACKED(2, "_brick_cracked", "cracked"),
        CHISELED(3, "_brick_chiseled", "chiseled");

        private static final BlockModBrick.EnumType[] META_LOOKUP = new BlockModBrick.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static BlockModBrick.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        static {
            for (BlockModBrick.EnumType blockstonebrick$enumtype : values()) {
                META_LOOKUP[blockstonebrick$enumtype.getMetadata()] = blockstonebrick$enumtype;
            }
        }
    }
}
package com.bob.redwall.biomes.cool;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeRedwallForest extends Biome {
    private final BiomeRedwallForest.Type type;

    public BiomeRedwallForest(BiomeRedwallForest.Type typeIn, Biome.BiomeProperties properties) {
        super(properties);
        this.type = typeIn;
        this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;

        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityBird.class, 10, 3, 4));

        if (this.type == BiomeRedwallForest.Type.FLOWER) {
            this.decorator.treesPerChunk = 6;
            this.decorator.flowersPerChunk = 100;
            this.decorator.grassPerChunk = 1;
        }
    }

    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.type == BiomeRedwallForest.Type.FLOWER)
        {
            double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
        }
        else
        {
            return super.pickRandomFlower(rand, pos);
        }
    }

    @SuppressWarnings("deprecation")
	public void decorate(World worldIn, Random rand, BlockPos pos) {
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, pos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS)) { 
	        int i = rand.nextInt(5) - 3;
	
	        if (this.type == BiomeRedwallForest.Type.FLOWER) {
	            i += 2;
	        }
	
	        this.addDoublePlants(worldIn, rand, pos, i);
        }
        
        super.decorate(worldIn, rand, pos);
    }

    public void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_) {
        for (int i = 0; i < p_185378_4_; ++i) {
            int j = p_185378_2_.nextInt(3);

            if (j == 0) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            } else if (j == 1) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            } else if (j == 2) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int k = 0; k < 5; ++k) {
                int l = p_185378_2_.nextInt(16) + 8;
                int i1 = p_185378_2_.nextInt(16) + 8;
                int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1))) {
                    break;
                }
            }
        }
    }

    @Override
    public void addDefaultFlowers() {
        if (type != BiomeRedwallForest.Type.FLOWER) {
            super.addDefaultFlowers();
            return;
        }
        
        for (BlockFlower.EnumFlowerType type : BlockFlower.EnumFlowerType.values()) {
            if (type.getBlockType() == BlockFlower.EnumFlowerColor.YELLOW) continue;
            if (type == BlockFlower.EnumFlowerType.BLUE_ORCHID) type = BlockFlower.EnumFlowerType.POPPY;
            addFlower(net.minecraft.init.Blocks.RED_FLOWER.getDefaultState().withProperty(net.minecraft.init.Blocks.RED_FLOWER.getTypeProperty(), type), 10);
        }
    }

    public Class <? extends Biome > getBiomeClass() {
        return BiomeRedwallForest.class;
    }

    public static enum Type {
        MIXED,
        DECIDUOUS,
        CONIFEROUS,
        FLOWER,
        OAK,
        BIRCH,
        MAPLE,
        ELM,
        ASH,
        SPRUCE,
        PINE,
        FIR,
        LARCH,
        SCRUB,
        SCRUB_PLATEAU,
        PINE_PLATEAU, 
        HEATHLAND, 
        SOUTHSWARD,
        NOONVALE;
    }
}

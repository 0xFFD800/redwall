package com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature;

import java.util.Random;

import com.bob.redwall.Ref;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenSandstoneSlab extends WorldGenerator {
	public static final ResourceLocation SLAB_1 = new ResourceLocation(Ref.MODID, "mossflower/sandstone_slabs_1");
	public static final ResourceLocation SLAB_2 = new ResourceLocation(Ref.MODID, "mossflower/sandstone_slabs_2");
	public static final ResourceLocation SLAB_3 = new ResourceLocation(Ref.MODID, "mossflower/sandstone_slabs_3");
	public static final ResourceLocation[] STRUCTURE_CHANCES = new ResourceLocation[] { SLAB_1, SLAB_2, SLAB_3 };

	public WorldGenSandstoneSlab() {}

	@Override
	public boolean generate(World world, Random rand, BlockPos blockpos) {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		int r = rand.nextInt(3);
		ResourceLocation structureLoc = STRUCTURE_CHANCES[r];
		Template template = templatemanager.getTemplate(minecraftserver, structureLoc);

		if (template == null) {
			System.out.println("Template " + structureLoc + " is missing!");
			return false;
		}

		BlockPos blockpos2 = template.getSize();
		BlockPos blockpos3 = new BlockPos(blockpos.getX() + blockpos2.getX(), blockpos.getY(), blockpos.getZ());
		BlockPos blockpos4 = new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ() + blockpos2.getZ());
		BlockPos blockpos5 = new BlockPos(blockpos.getX() + blockpos2.getX(), blockpos.getY(), blockpos.getZ() + blockpos2.getZ());
		BlockPos blockpos6 = new BlockPos(blockpos.getX() + blockpos2.getX() / 2, blockpos.getY(), blockpos.getZ() + blockpos2.getZ() / 2);
		int i = Math.min(world.getHeight(blockpos.getX(), blockpos.getZ()), world.getHeight(blockpos3.getX(), blockpos3.getZ()));
		int i2 = Math.min(i, world.getHeight(blockpos4.getX(), blockpos4.getZ()));
		int i3 = Math.min(i2, world.getHeight(blockpos5.getX(), blockpos5.getZ()));
		int i4 = Math.min(i3, world.getHeight(blockpos6.getX(), blockpos6.getZ()));
		int i5 = blockpos.getY() - i4;

		if (world.getBlockState(blockpos).getBlock() == Blocks.WATER || world.getBlockState(blockpos3).getBlock() == Blocks.WATER || world.getBlockState(blockpos4).getBlock() == Blocks.WATER || world.getBlockState(blockpos5).getBlock() == Blocks.WATER) return false;

		PlacementSettings placementsettings = (new PlacementSettings()).setChunk((ChunkPos) null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

		template.addBlocksToWorldChunk(world, blockpos.down(i5), placementsettings);

		return true;
	}
}
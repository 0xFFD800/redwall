package com.bob.redwall.dimensions.redwall.structures.mossflower;

import java.util.Random;

import com.bob.redwall.Ref;
import com.bob.redwall.dimensions.shared.rtg.api.util.Logger;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.good.EntityOtterMossflower;
import com.bob.redwall.entity.structure_center.EntityMossflowerOtterHearth;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenOtterHolt extends StructureComponent {
	public static final ResourceLocation OTTER_HOLT_1 = new ResourceLocation(Ref.MODID, "mossflower/otter_holt_1");
    /** The size of the bounding box for this feature in the X axis */
    protected int width;
    /** The size of the bounding box for this feature in the Y axis */
    protected int height;
    /** The size of the bounding box for this feature in the Z axis */
    protected int depth;
    protected int horizontalPos = -1;
    
    public WorldGenOtterHolt() {}

	public WorldGenOtterHolt(StructureBoundingBox position) {
		super(0);
		this.boundingBox = position;
		this.width = position.getXSize();
		this.height = position.getYSize();
		this.depth = position.getZSize();
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox position) {
		BlockPos blockpos = new BlockPos(position.minX, position.minY, position.minZ);
        WorldServer worldserver = (WorldServer)world;
        MinecraftServer minecraftserver = world.getMinecraftServer();
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        ResourceLocation structureLoc = OTTER_HOLT_1;
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
        int i6 = 7;

		if (world.getBlockState(blockpos).getBlock() == Blocks.WATER || world.getBlockState(blockpos3).getBlock() == Blocks.WATER || world.getBlockState(blockpos4).getBlock() == Blocks.WATER || world.getBlockState(blockpos5).getBlock() == Blocks.WATER) return false;

        PlacementSettings placementsettings = (new PlacementSettings()).setChunk((ChunkPos)null).setReplacedBlock((Block)null).setIgnoreStructureBlock(false);

        template.addBlocksToWorldChunk(world, blockpos.down(i5 + i6), placementsettings);
        
        //Otters in rooms
        
        for(int j = 0; j < 5; j++) {
        	boolean m = j == 0 ? true : j == 1 ? false : rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 14, blockpos.down(i5 + i6).getY() + 2, blockpos.getZ() + 25, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        for(int j = 0; j < 6; j++) {
        	boolean m = j == 0 ? true : j == 1 ? false : rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 23, blockpos.down(i5 + i6).getY() + 2, blockpos.getZ() + 26, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
    	boolean m1 = rand.nextBoolean();
        EntityAbstractNPC dweller1 = new EntityOtterMossflower(world, m1);
        dweller1.setLocationAndAngles(blockpos.getX() + 28, blockpos.down(i5 + i6).getY() + 2, blockpos.getZ() + 18, 0, 0);
        dweller1.enablePersistence();
        dweller1.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller1.posX, dweller1.posY, dweller1.posZ)), (IEntityLivingData)null);
        dweller1.setIsMale(m1);
    	dweller1.resetSkinNameData();
        world.spawnEntity(dweller1);
        Logger.info(dweller1.toString());
        
        for(int j = 0; j < 3; j++) {
        	boolean m = j == 0 ? true : j == 1 ? false : rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 26, blockpos.down(i5 + i6).getY() + 1, blockpos.getZ() + 10, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        for(int j = 0; j < 3; j++) {
        	boolean m = j == 0 ? true : j == 1 ? false : rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 21, blockpos.down(i5 + i6).getY() + 1, blockpos.getZ() + 4, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        //Forge
        
        for(int j = 0; j < rand.nextInt(3) + 1; j++) {
        	boolean m = rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 15, blockpos.down(i5 + i6).getY() + 4, blockpos.getZ() + 16, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        //Armory, other side
        
        for(int j = 0; j < rand.nextInt(3) + 1; j++) {
        	boolean m = rand.nextBoolean();
	        EntityAbstractNPC dweller = new EntityOtterMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 15, blockpos.down(i5 + i6).getY() + 6, blockpos.getZ() + 19, 0, 0);
	        dweller.enablePersistence();
            dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        EntityMossflowerOtterHearth center = new EntityMossflowerOtterHearth(world, new AxisAlignedBB(blockpos.getX(), blockpos.down(i5 + i6 + 2).getY(), blockpos.getZ(), blockpos.getX() + template.getSize().getX(), blockpos.down(i5 + 4).getY() + template.getSize().getY(), blockpos.getZ() + template.getSize().getZ()));
        
    	center.setLocationAndAngles(blockpos.getX() + 20, blockpos.down(i5 + i6).getY() + 3, blockpos.getZ() + 9, 0, 0);

        return true;
	}
	
	@Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("Width", this.width);
        tagCompound.setInteger("Height", this.height);
        tagCompound.setInteger("Depth", this.depth);
        tagCompound.setInteger("HPos", this.horizontalPos);
    }

	@Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        this.width = tagCompound.getInteger("Width");
        this.height = tagCompound.getInteger("Height");
        this.depth = tagCompound.getInteger("Depth");
        this.horizontalPos = tagCompound.getInteger("HPos");
    }
	
	public static BlockPos getSize() {
		return new BlockPos(32, 23, 32);
	}
}

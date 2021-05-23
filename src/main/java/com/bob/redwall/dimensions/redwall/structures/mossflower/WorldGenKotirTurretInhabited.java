package com.bob.redwall.dimensions.redwall.structures.mossflower;

import java.util.Random;

import com.bob.redwall.Ref;
import com.bob.redwall.dimensions.shared.rtg.api.util.Logger;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.evil.EntityFerretMossflower;
import com.bob.redwall.entity.npc.evil.EntityRatMossflower;
import com.bob.redwall.entity.npc.evil.EntityWeaselMossflower;
import com.bob.redwall.entity.structure_center.EntityMossflowerVerminCampfire;

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

public class WorldGenKotirTurretInhabited extends StructureComponent {
	public static final ResourceLocation KOTIR_TURRET_INHABITED_1 = new ResourceLocation(Ref.MODID, "mossflower/kotir_turret_inhabited_1");
    /** The size of the bounding box for this feature in the X axis */
    protected int width;
    /** The size of the bounding box for this feature in the Y axis */
    protected int height;
    /** The size of the bounding box for this feature in the Z axis */
    protected int depth;
    protected int horizontalPos = -1;
    
    public WorldGenKotirTurretInhabited() {}

	public WorldGenKotirTurretInhabited(StructureBoundingBox position) {
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
        ResourceLocation structureLoc = KOTIR_TURRET_INHABITED_1;
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

        PlacementSettings placementsettings = (new PlacementSettings()).setChunk((ChunkPos)null).setReplacedBlock((Block)null).setIgnoreStructureBlock(false);

        template.addBlocksToWorldChunk(world, blockpos.down(i5), placementsettings);
        
        for(int j = 0; j < position.getXSize(); j++) {
        	for(int k = 0; k < position.getZSize(); k++) {
        		world.setBlockState(blockpos.down(i5 + 1).south(k).east(j), Blocks.GRASS.getDefaultState());
        	}
        }
        
        for(int j = 0; j < rand.nextInt(6) + 1; j++) {
        	boolean m = rand.nextBoolean();
            int c = rand.nextInt(3);
	        EntityAbstractNPC dweller = c == 2 ? new EntityRatMossflower(world, m) : c == 1 ? new EntityWeaselMossflower(world, m) : new EntityFerretMossflower(world, m);
	        dweller.setLocationAndAngles(blockpos.getX() + 6, blockpos.down(i5).getY() + 0, blockpos.getZ() + 5, 0, 0);
	        dweller.enablePersistence();
	        dweller.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        world.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
        EntityMossflowerVerminCampfire center = new EntityMossflowerVerminCampfire(world, new AxisAlignedBB(this.getBoundingBox().minX, this.getBoundingBox().minY, this.getBoundingBox().minZ, this.getBoundingBox().maxX, this.getBoundingBox().maxY, this.getBoundingBox().maxZ));
        
        center.setLocationAndAngles(blockpos.getX() + 6, blockpos.down(i5).getY() + 0, blockpos.getZ() + 5, 0, 0);
        
        world.spawnEntity(center);
        
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
		return new BlockPos(11, 12, 11);
	}
}

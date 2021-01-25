package com.bob.redwall.dimensions.redwall.structures.mossflower;

import java.util.Random;

import com.bob.redwall.Ref;
import com.bob.redwall.dimensions.shared.rtg.api.util.Logger;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.good.EntityMoleWoodlander;
import com.bob.redwall.entity.npc.good.EntityMouseWoodlander;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenGroundDwelling extends StructureComponent {
	public static final ResourceLocation GROUND_DWELLING_1 = new ResourceLocation(Ref.MODID, "mossflower/ground_dwelling_1");
	public static final ResourceLocation GROUND_DWELLING_2 = new ResourceLocation(Ref.MODID, "mossflower/ground_dwelling_2");
	public static final ResourceLocation GROUND_DWELLING_3 = new ResourceLocation(Ref.MODID, "mossflower/ground_dwelling_3");
	public static final ResourceLocation[] STRUCTURE_CHANCES = new ResourceLocation[] {GROUND_DWELLING_1, GROUND_DWELLING_2, GROUND_DWELLING_3};
	
    /** The size of the bounding box for this feature in the X axis */
    protected int width;
    /** The size of the bounding box for this feature in the Y axis */
    protected int height;
    /** The size of the bounding box for this feature in the Z axis */
    protected int depth;
    protected int horizontalPos = -1;
    
    public WorldGenGroundDwelling() {}

	public WorldGenGroundDwelling(StructureBoundingBox position) {
		super(0);
		this.boundingBox = position;
		this.width = position.getXSize();
		this.height = position.getYSize();
		this.depth = position.getZSize();
	}

	@Override
	public boolean addComponentParts(World worldIn, Random rand, StructureBoundingBox position) {
		BlockPos blockpos = new BlockPos(position.minX, position.minY, position.minZ);
        WorldServer worldserver = (WorldServer)worldIn;
        MinecraftServer minecraftserver = worldIn.getMinecraftServer();
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
        int i = Math.min(worldIn.getHeight(blockpos.getX(), blockpos.getZ()), worldIn.getHeight(blockpos3.getX(), blockpos3.getZ()));
        int i2 = Math.min(i, worldIn.getHeight(blockpos4.getX(), blockpos4.getZ()));
        int i3 = Math.min(i2, worldIn.getHeight(blockpos5.getX(), blockpos5.getZ()));
        int i4 = Math.min(i3, worldIn.getHeight(blockpos6.getX(), blockpos6.getZ()));
        int i5 = blockpos.getY() - i4;
        int i6 = r == 2 ? 1 : r == 1 ? 3 : 3;

        PlacementSettings placementsettings = (new PlacementSettings()).setChunk((ChunkPos)null).setReplacedBlock((Block)null).setIgnoreStructureBlock(false);

        template.addBlocksToWorldChunk(worldIn, blockpos.down(i5 + i6), placementsettings);
        
        int c = rand.nextInt(2);
        for(int j = 0; j < rand.nextInt(4) + 1; j++) {
        	boolean m = j == 0 ? true : j == 1 ? false : rand.nextBoolean();
	        EntityAbstractNPC dweller = c == 1 ? new EntityMouseWoodlander(worldIn, m) : new EntityMoleWoodlander(worldIn, m);
	        if(structureLoc == GROUND_DWELLING_1) {
		        dweller.setLocationAndAngles(blockpos.getX() + 7, blockpos.down(i5 + i6).getY() + 1, blockpos.getZ() + 8, 0, 0);
	        } else if(structureLoc == GROUND_DWELLING_2) {
		        dweller.setLocationAndAngles(blockpos.getX() + 5, blockpos.down(i5 + i6).getY() + 0, blockpos.getZ() + 6, 0, 0);
	        } else {
		        dweller.setLocationAndAngles(blockpos.getX() + 4, blockpos.down(i5 + i6).getY() + 0, blockpos.getZ() + 4, 0, 0);
	        }
	        dweller.enablePersistence();
            dweller.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(dweller.posX, dweller.posY, dweller.posZ)), (IEntityLivingData)null);
	        dweller.setIsMale(m);
	    	dweller.resetSkinNameData();
            worldIn.spawnEntity(dweller);
	        Logger.info(dweller.toString());
        }
        
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
		return new BlockPos(15, 9, 19);
	}
}

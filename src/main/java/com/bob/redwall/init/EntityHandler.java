package com.bob.redwall.init;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.client.render.RenderDagger;
import com.bob.redwall.entity.client.render.RenderMole;
import com.bob.redwall.entity.client.render.RenderMouse;
import com.bob.redwall.entity.client.render.RenderSpear;
import com.bob.redwall.entity.client.render.RenderSquirrel;
import com.bob.redwall.entity.client.render.RenderThrowingAxe;
import com.bob.redwall.entity.npc.EntityMoleRedwall;
import com.bob.redwall.entity.npc.EntityMoleWoodlander;
import com.bob.redwall.entity.npc.EntityMouseRedwall;
import com.bob.redwall.entity.npc.EntityMouseWoodlander;
import com.bob.redwall.entity.npc.EntitySquirrelRedwall;
import com.bob.redwall.entity.npc.EntitySquirrelWoodlander;
import com.bob.redwall.entity.projectile.dagger.EntityDagger;
import com.bob.redwall.entity.projectile.spear.EntitySpear;
import com.bob.redwall.entity.projectile.throwing_axe.EntityThrowingAxe;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHandler {
	public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "spear"), EntitySpear.class, Ref.MODID + ":spear", id++, Ref.MODID, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "dagger"), EntityDagger.class, Ref.MODID + ":dagger", id++, Ref.MODID, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "throwing_axe"), EntityThrowingAxe.class, Ref.MODID + ":throwing_axe", id++, Ref.MODID, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "mouse_redwall"), EntityMouseRedwall.class, Ref.MODID + ":mouse_redwall", id++, Ref.MODID, 64, 3, true, 0x005108, 0x603213);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "mouse_woodlander"), EntityMouseWoodlander.class, Ref.MODID + ":mouse_woodlander", id++, Ref.MODID, 64, 3, true, 0x302104, 0x603213);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "squirrel_redwall"), EntitySquirrelRedwall.class, Ref.MODID + ":squirrel_redwall", id++, Ref.MODID, 64, 3, true, 0x004505, 0x804523);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "squirrel_woodlander"), EntitySquirrelWoodlander.class, Ref.MODID + ":squirrel_woodlander", id++, Ref.MODID, 64, 3, true, 0x282003, 0x804523);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "mole_redwall"), EntityMoleRedwall.class, Ref.MODID + ":mole_redwall", id++, Ref.MODID, 64, 3, true, 0x001104, 0x100602);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "mole_woodlander"), EntityMoleWoodlander.class, Ref.MODID + ":mole_woodlander", id++, Ref.MODID, 64, 3, true, 0x222124, 0x101612);
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, RenderSpear.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDagger.class, RenderDagger.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowingAxe.class, RenderThrowingAxe.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMouseRedwall.class, RenderMouse.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMouseWoodlander.class, RenderMouse.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySquirrelRedwall.class, RenderSquirrel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySquirrelWoodlander.class, RenderSquirrel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMoleRedwall.class, RenderMole.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMoleWoodlander.class, RenderMole.FACTORY);
	}
}

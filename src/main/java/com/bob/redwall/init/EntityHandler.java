package com.bob.redwall.init;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.client.render.RenderDagger;
import com.bob.redwall.entity.client.render.RenderFerret;
import com.bob.redwall.entity.client.render.RenderMole;
import com.bob.redwall.entity.client.render.RenderMouse;
import com.bob.redwall.entity.client.render.RenderOtter;
import com.bob.redwall.entity.client.render.RenderRat;
import com.bob.redwall.entity.client.render.RenderShrew;
import com.bob.redwall.entity.client.render.RenderSpear;
import com.bob.redwall.entity.client.render.RenderSquirrel;
import com.bob.redwall.entity.client.render.RenderStoat;
import com.bob.redwall.entity.client.render.RenderThrowingAxe;
import com.bob.redwall.entity.client.render.RenderWeasel;
import com.bob.redwall.entity.npc.evil.EntityFerretMossflower;
import com.bob.redwall.entity.npc.evil.EntityRatMossflower;
import com.bob.redwall.entity.npc.evil.EntityStoatMossflower;
import com.bob.redwall.entity.npc.evil.EntityWeaselMossflower;
import com.bob.redwall.entity.npc.good.EntityOtterMossflower;
import com.bob.redwall.entity.npc.good.EntityShrewGuosim;
import com.bob.redwall.entity.npc.good.redwall.EntityMoleRedwall;
import com.bob.redwall.entity.npc.good.redwall.EntityMouseRedwall;
import com.bob.redwall.entity.npc.good.redwall.EntityShrewRedwall;
import com.bob.redwall.entity.npc.good.redwall.EntitySquirrelRedwall;
import com.bob.redwall.entity.npc.good.woodlander.EntityMoleWoodlander;
import com.bob.redwall.entity.npc.good.woodlander.EntityMouseWoodlander;
import com.bob.redwall.entity.npc.good.woodlander.EntitySquirrelWoodlander;
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
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "otter_mossflower"), EntityOtterMossflower.class, Ref.MODID + ":otter_mossflower", id++, Ref.MODID, 64, 3, true, 0x004208, 0x504513);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "shrew_redwall"), EntityShrewRedwall.class, Ref.MODID + ":shrew_redwall", id++, Ref.MODID, 64, 3, true, 0x00603F, 0x158F49);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "shrew_guosim"), EntityShrewGuosim.class, Ref.MODID + ":shrew_guosim", id++, Ref.MODID, 64, 3, true, 0x00403F, 0x154F89);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "rat_mossflower"), EntityRatMossflower.class, Ref.MODID + ":rat_mossflower", id++, Ref.MODID, 64, 3, true, 0x302224, 0x403435);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "ferret_mossflower"), EntityFerretMossflower.class, Ref.MODID + ":ferret_mossflower", id++, Ref.MODID, 64, 3, true, 0x302224, 0x403435);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "weasel_mossflower"), EntityWeaselMossflower.class, Ref.MODID + ":weasel_mossflower", id++, Ref.MODID, 64, 3, true, 0x502224, 0x603435);
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID, "stoat_mossflower"), EntityStoatMossflower.class, Ref.MODID + ":stoat_mossflower", id++, Ref.MODID, 64, 3, true, 0x401519, 0x103435);
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
        RenderingRegistry.registerEntityRenderingHandler(EntityOtterMossflower.class, RenderOtter.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityShrewRedwall.class, RenderShrew.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityShrewGuosim.class, RenderShrew.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRatMossflower.class, RenderRat.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFerretMossflower.class, RenderFerret.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityWeaselMossflower.class, RenderWeasel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityStoatMossflower.class, RenderStoat.FACTORY);
	}
}

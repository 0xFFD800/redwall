package com.bob.redwall.init;

import java.util.concurrent.Callable;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeight;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeightProvider;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeightStorage;
import com.bob.redwall.entity.capabilities.armor_weight.IArmorWeight;
import com.bob.redwall.entity.capabilities.booleancap.attacking.Attacking;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingStorage;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.Defending;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingStorage;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.FactionCapStorage;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.entity.capabilities.nutrition.INutrition;
import com.bob.redwall.entity.capabilities.nutrition.Nutrition;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.bob.redwall.entity.capabilities.nutrition.NutritionStorage;
import com.bob.redwall.entity.capabilities.season.ISeasonCap;
import com.bob.redwall.entity.capabilities.season.SeasonCap;
import com.bob.redwall.entity.capabilities.season.SeasonCapProvider;
import com.bob.redwall.entity.capabilities.season.SeasonCapStorage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	public static final ResourceLocation SEASON_CAP = new ResourceLocation(Ref.MODID, "season");
	public static final ResourceLocation ATTACKING_CAP = new ResourceLocation(Ref.MODID, "attacking");
	public static final ResourceLocation DEFENDING_CAP = new ResourceLocation(Ref.MODID, "defending");
	public static final ResourceLocation ARMOR_WEIGHT_CAP = new ResourceLocation(Ref.MODID, "armor_weight");
	public static final ResourceLocation FACTION_CAP = new ResourceLocation(Ref.MODID, "factions");
	public static final ResourceLocation NUTRITION_CAP = new ResourceLocation(Ref.MODID, "nutrition");

	@SubscribeEvent
	public void onAttachToEntity(AttachCapabilitiesEvent<Entity> event) {
		event.addCapability(ATTACKING_CAP, new AttackingProvider());
		event.addCapability(DEFENDING_CAP, new DefendingProvider());
		event.addCapability(ARMOR_WEIGHT_CAP, new ArmorWeightProvider());
		if (!(event.getObject() instanceof EntityPlayer)) return;
		// EntityPlayer entityplayer = (EntityPlayer)event.getObject();
		event.addCapability(FACTION_CAP, new FactionCapProvider());
		event.addCapability(NUTRITION_CAP, new NutritionProvider());
	}

	@SubscribeEvent
	public void onAttachToWorld(AttachCapabilitiesEvent<World> event) {
		event.addCapability(SEASON_CAP, new SeasonCapProvider());
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(ISeasonCap.class, new SeasonCapStorage(), new Callable<ISeasonCap>() {
			@Override
			public ISeasonCap call() throws Exception {
				return new SeasonCap();
			}
		});

		CapabilityManager.INSTANCE.register(IAttacking.class, new AttackingStorage(), new Callable<IAttacking>() {
			@Override
			public IAttacking call() throws Exception {
				return new Attacking();
			}
		});

		CapabilityManager.INSTANCE.register(IDefending.class, new DefendingStorage(), new Callable<IDefending>() {
			@Override
			public IDefending call() throws Exception {
				return new Defending();
			}
		});

		CapabilityManager.INSTANCE.register(IArmorWeight.class, new ArmorWeightStorage(), new Callable<IArmorWeight>() {
			@Override
			public IArmorWeight call() throws Exception {
				return new ArmorWeight();
			}
		});

		CapabilityManager.INSTANCE.register(IFactionCap.class, new FactionCapStorage(), new Callable<IFactionCap>() {
			@Override
			public IFactionCap call() throws Exception {
				return new FactionCap();
			}
		});

		CapabilityManager.INSTANCE.register(INutrition.class, new NutritionStorage(), new Callable<INutrition>() {
			@Override
			public INutrition call() throws Exception {
				return new Nutrition();
			}
		});
	}
}

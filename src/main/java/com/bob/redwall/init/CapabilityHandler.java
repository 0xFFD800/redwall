package com.bob.redwall.init;

import java.util.concurrent.Callable;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.agility.Agility;
import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.agility.AgilityStats;
import com.bob.redwall.entity.capabilities.agility.IAgility;
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
import com.bob.redwall.entity.capabilities.species.ISpeciesCap;
import com.bob.redwall.entity.capabilities.species.SpeciesCap;
import com.bob.redwall.entity.capabilities.species.SpeciesCapProvider;
import com.bob.redwall.entity.capabilities.species.SpeciesCapStorage;
import com.bob.redwall.entity.capabilities.speed.ISpeed;
import com.bob.redwall.entity.capabilities.speed.Speed;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.speed.SpeedStats;
import com.bob.redwall.entity.capabilities.strength.IStrength;
import com.bob.redwall.entity.capabilities.strength.Strength;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.strength.StrengthStats;
import com.bob.redwall.entity.capabilities.vitality.IVitality;
import com.bob.redwall.entity.capabilities.vitality.Vitality;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;
import com.bob.redwall.entity.capabilities.vitality.VitalityStats;

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
	public static final ResourceLocation STRENGTH_CAP = new ResourceLocation(Ref.MODID, "strength");
	public static final ResourceLocation SPEED_CAP = new ResourceLocation(Ref.MODID, "speed");
	public static final ResourceLocation VITALITY_CAP = new ResourceLocation(Ref.MODID, "vitality");
	public static final ResourceLocation AGILITY_CAP = new ResourceLocation(Ref.MODID, "agility");
	public static final ResourceLocation SPECIES_CAP = new ResourceLocation(Ref.MODID, "species");

	@SubscribeEvent
	public void onAttachToEntity(AttachCapabilitiesEvent<Entity> event) {
		event.addCapability(ATTACKING_CAP, new AttackingProvider());
		event.addCapability(DEFENDING_CAP, new DefendingProvider());
		event.addCapability(ARMOR_WEIGHT_CAP, new ArmorWeightProvider());
		event.addCapability(STRENGTH_CAP, new StrengthProvider());
		event.addCapability(SPEED_CAP, new SpeedProvider());
		event.addCapability(VITALITY_CAP, new VitalityProvider());
		event.addCapability(AGILITY_CAP, new AgilityProvider());
		if (!(event.getObject() instanceof EntityPlayer)) return;
		// EntityPlayer entityplayer = (EntityPlayer)event.getObject();
		event.addCapability(FACTION_CAP, new FactionCapProvider());
		event.addCapability(NUTRITION_CAP, new NutritionProvider());
		event.addCapability(SPECIES_CAP, new SpeciesCapProvider());
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

		CapabilityManager.INSTANCE.register(IStrength.class, new StrengthStats(), new Callable<IStrength>() {
			@Override
			public IStrength call() throws Exception {
				return new Strength();
			}
		});

		CapabilityManager.INSTANCE.register(ISpeed.class, new SpeedStats(), new Callable<ISpeed>() {
			@Override
			public ISpeed call() throws Exception {
				return new Speed();
			}
		});

		CapabilityManager.INSTANCE.register(IVitality.class, new VitalityStats(), new Callable<IVitality>() {
			@Override
			public IVitality call() throws Exception {
				return new Vitality();
			}
		});

		CapabilityManager.INSTANCE.register(IAgility.class, new AgilityStats(), new Callable<IAgility>() {
			@Override
			public IAgility call() throws Exception {
				return new Agility();
			}
		});

		CapabilityManager.INSTANCE.register(ISpeciesCap.class, new SpeciesCapStorage(), new Callable<ISpeciesCap>() {
			@Override
			public ISpeciesCap call() throws Exception {
				return new SpeciesCap();
			}
		});
	}
}

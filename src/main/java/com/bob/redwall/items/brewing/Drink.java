package com.bob.redwall.items.brewing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.bob.redwall.Ref;
import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Drink {
	public static final HashMap<ResourceLocation, Drink> BY_ID = new HashMap<ResourceLocation, Drink>();
	private final Map<String, PotionEffect> effects = new HashMap<String, PotionEffect>();
	private final Map<String, Float> probabilities = new HashMap<String, Float>();
	private final List<String> ids = Lists.newArrayList();
	private ResourceLocation id;
	private int tint;
	private float alcohol;
	private float protein;
	private float carbs;
	private float veggies;
	private float fruits;

	public Drink(ResourceLocation id, int tint, float alcohol) {
		this(id, tint, alcohol, 0, 0, 0, 0);
	}

	public Drink(ResourceLocation id, int tint, float alcohol, float prot, float carb, float veg, float frut) {
		this.id = id;
		this.tint = tint;
		this.alcohol = alcohol;
		this.protein = prot;
		this.carbs = carb;
		this.veggies = veg;
		this.fruits = frut;
		Drink.BY_ID.put(id, this);
	}

	public int getTint() {
		return tint;
	}

	public Drink addPotionEffect(PotionEffect effect, float probability) {
		String string = String.valueOf(new UUID(new Random().nextLong(), new Random().nextLong()).hashCode());
		this.effects.put(string, effect);
		this.probabilities.put(string, probability);
		this.ids.add(string);
		return this;
	}

	public void onConsumed(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote) {
			for (String id : this.ids) {
				PotionEffect potionId = this.effects.get(id);
				float potionEffectProbability = this.probabilities.get(id);
				if (potionId != null && entityLiving.getRNG().nextFloat() < potionEffectProbability) {
					if (potionId.getPotion().isInstant()) {
						new PotionEffect(potionId).getPotion().affectEntity(entityLiving, entityLiving, entityLiving, potionId.getAmplifier(), 1.0D);
					} else {
						entityLiving.addPotionEffect(new PotionEffect(potionId.getPotion(), (int) ((float) potionId.getDuration() * FoodModifierUtils.getEffectDurationMultiplier(entityLiving, stack)), potionId.getAmplifier(), potionId.getIsAmbient(), potionId.doesShowParticles()));
					}
				}
			}

			entityLiving.getCapability(NutritionProvider.NUTRITION_CAP, null).addBAC(this.alcohol / 3.75F * FoodModifierUtils.getAlcoholMultiplier(entityLiving, stack));
			FoodModifierUtils.onConsumed(entityLiving, stack);
		}
	}

	public void onConsumed(TileEntityDrinkVessel ted, World world, EntityLivingBase entityLiving) {
		if (!world.isRemote) {
			for (String id : this.ids) {
				PotionEffect potionId = this.effects.get(id);
				float potionEffectProbability = this.probabilities.get(id);
				if (potionId != null && entityLiving.getRNG().nextFloat() < potionEffectProbability) {
					if (potionId.getPotion().isInstant()) {
						new PotionEffect(potionId).getPotion().affectEntity(entityLiving, entityLiving, entityLiving, potionId.getAmplifier(), 1.0D);
					} else {
						entityLiving.addPotionEffect(new PotionEffect(potionId.getPotion(), (int) ((float) potionId.getDuration() * FoodModifierUtils.getEffectDurationMultiplier(entityLiving, ted)), potionId.getAmplifier(), potionId.getIsAmbient(), potionId.doesShowParticles()));
					}
				}
			}

			entityLiving.getCapability(NutritionProvider.NUTRITION_CAP, null).addBAC(this.alcohol / 3.75F * FoodModifierUtils.getAlcoholMultiplier(entityLiving, ted));
			FoodModifierUtils.onConsumed(entityLiving, ted);
		}
	}

	public float getAlcohol() {
		return this.alcohol;
	}

	public float getProtein() {
		return this.protein;
	}

	public float getCarbs() {
		return this.carbs;
	}

	public float getVeggies() {
		return this.veggies;
	}

	public float getFruits() {
		return this.fruits;
	}

	public boolean hasStatusEffects() {
		return !this.ids.isEmpty();
	}

	public ResourceLocation getID() {
		return this.id;
	}

	public static void init() {
		Ref.LOGGER.info("Registering Drinks...");
		Ref.LOGGER.info("Using water tint " + Integer.toHexString(DrinkList.WATER.tint));
	}

	public static class DrinkList {
		public static final Drink WATER = new Drink(new ResourceLocation(Ref.MODID, "water"), 0x385DC6, 0.0F);
		public static final Drink OCTOBER_ALE = new Drink(new ResourceLocation(Ref.MODID, "october_ale"), 0x633A1E, 0.1F, 10, 10, 10, 10).addPotionEffect(new PotionEffect(MobEffects.SATURATION, 0, 9), 1.0F);
		public static final Drink ELDERBERRY_WINE = new Drink(new ResourceLocation(Ref.MODID, "elderberry_wine"), 0x480A56, 0.3F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1), 1.0F).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300), 0.2F);
		public static final Drink DAMSON_WINE = new Drink(new ResourceLocation(Ref.MODID, "damson_wine"), 0x600B58, 0.25F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 1), 1.0F);
		public static final Drink MINT_TEA = new Drink(new ResourceLocation(Ref.MODID, "mint_tea"), 0x94D877, 0.0F, 0, 4, 4, 0).addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 0, 1), 0.5F);
		public static final Drink STRAWBERRY_FIZZ = new Drink(new ResourceLocation(Ref.MODID, "strawberry_fizz"), 0xFF9E9E, 0.0F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 1), 0.4F);
		public static final Drink BUTTERCUP_HONEY_CORDIAL = new Drink(new ResourceLocation(Ref.MODID, "buttercup_honey_cordial"), 0xFFE875, 0.0F, 0, 4, 2, 0).addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 600, 0), 0.3F);
		public static final Drink CUP_O_CHEER = new Drink(new ResourceLocation(Ref.MODID, "cup_o_cheer"), 0xFF8C75, 0.2F, 0, 4, 2, 0).addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 0), 0.2F);
		public static final Drink NUTBROWN_BEER = new Drink(new ResourceLocation(Ref.MODID, "nutbrown_beer"), 0x9E6845, 0.15F, 2, 4, 0, 0).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 0), 0.8F);
		public static final Drink APPLE_CIDER = new Drink(new ResourceLocation(Ref.MODID, "apple_cider"), 0xFFC32B, 0.05F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1), 0.7F);
		public static final Drink BEETROOT_PORT = new Drink(new ResourceLocation(Ref.MODID, "beetroot_port"), 0x7F0000, 0.25F, 0, 2, 2, 0).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0), 0.5F);
		public static final Drink DANDELION_BURDOCK_CORDIAL = new Drink(new ResourceLocation(Ref.MODID, "dandelion_burdock_cordial"), 0xCAE23F, 0.15F, 0, 4, 2, 0).addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 0, 1), 0.8F);
		public static final Drink MEAD = new Drink(new ResourceLocation(Ref.MODID, "mead"), 0xFFE500, 0.075F, 1, 5, 1, 1).addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 0), 0.1F);
		public static final Drink SHREWBEER = new Drink(new ResourceLocation(Ref.MODID, "shrewbeer"), 0x744E45, 0.15F, 4, 8, 4, 4).addPotionEffect(new PotionEffect(MobEffects.SATURATION, 0, 7), 1.0F);
		public static final Drink WATERPORTER = new Drink(new ResourceLocation(Ref.MODID, "waterporter"), 0x6B222B, 0.2F, 0, 2, 2, 0).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0), 0.4F);
		public static final Drink GULLYPLUG_PUNCH = new Drink(new ResourceLocation(Ref.MODID, "gullyplug_punch"), 0xEA757F, 0.35F, 0, 4, 4, 0).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0), 0.8F);
		public static final Drink PALE_CIDER = new Drink(new ResourceLocation(Ref.MODID, "pale_cider"), 0xFFE7A8, 0.05F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 0), 0.2F);
		public static final Drink MOUNTAIN_ALE = new Drink(new ResourceLocation(Ref.MODID, "mountain_ale"), 0xCCA372, 0.15F, 2, 4, 0, 0).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 1), 0.5F);
		public static final Drink NETTLEBEER = new Drink(new ResourceLocation(Ref.MODID, "nettlebeer"), 0x35230D, 0.2F, 2, 4, 0, 0).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 0), 1.0F);
		public static final Drink BLACKBERRY_GROG = new Drink(new ResourceLocation(Ref.MODID, "blackberry_grog"), 0x170635, 0.35F, 0, 4, 0, 4).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1), 1.0F);
	}
}

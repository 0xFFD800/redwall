package com.bob.redwall.crafting.cooking;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import com.bob.redwall.Ref;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryBuilder;

@SuppressWarnings("unchecked")
public abstract class FoodModifier extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<FoodModifier> {
    public static RegistryNamespaced<ResourceLocation, FoodModifier> REGISTRY;
    static {
    	try {
			((RegistryBuilder<FoodModifier>) ReflectionHelper.findMethod(GameData.class, "makeRegistry", "makeRegistry", ResourceLocation.class, FoodModifier.class.getClass(), int.class).invoke(null, new ResourceLocation(Ref.MODID, "foodmodifiers"), FoodModifier.class, Short.MAX_VALUE - 1)).create();
			REGISTRY = GameData.getWrapper(FoodModifier.class);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
    
    private final FoodModifier.Rarity rarity;
    protected String name;

    @Nullable
    public static FoodModifier getModifierByID(int id) {
        return (FoodModifier)REGISTRY.getObjectById(id);
    }

    public static int getModifierID(FoodModifier enchantmentIn) {
        return REGISTRY.getIDForObject(enchantmentIn);
    }

    @Nullable
    public static FoodModifier getModifierByLocation(String location) {
        return (FoodModifier)REGISTRY.getObject(new ResourceLocation(Ref.MODID, location));
    }

    protected FoodModifier(FoodModifier.Rarity rarityIn) {
        this.rarity = rarityIn;
    }

    public FoodModifier.Rarity getRarity(int level) {
        return this.rarity;
    }

    public int getMinLevel() {
        return 1;
    }

    public int getMaxLevel() {
        return 1;
    }

    /**
     * Calculates the food change of the modifier. Negative amounts may cause the food to remove food, rather than add it.
     * @param level The level of the modifier.
     * @return The change in the amount of food the food gives.
     */
    public int calcModifierFood(int level) {
        return 0;
    }

    /**
     * Calculates the saturation change of the modifier. Negative amounts may cause the food to remove saturation, rather than add it.
     * @param level The level of the modifier.
     * @return The change in the amount of saturation the food gives.
     */
    public float calcModifierSaturation(int level) {
        return 0;
    }

    /**
     * The change in the protein the food gives.
     * @param level The level of the modifier.
     * @return The change in the amount of protein the food gives.
     */
    public float calcModifierProtein(int level) {
        return 0;
    }

    /**
     * The change in the carbs the food gives.
     * @param level The level of the modifier.
     * @return The change in the amount of carbs the food gives.
     */
    public float calcModifierCarbs(int level) {
        return 0;
    }

    /**
     * The change in the fruit the food gives.
     * @param level The level of the modifier.
     * @return The change in the amount of fruit the food gives.
     */
    public float calcModifierFruits(int level) {
        return 0;
    }

    /**
     * The change in the veggies the food gives.
     * @param level The level of the modifier.
     * @return The change in the amount of veggies the food gives.
     */
    public float calcModifierVeggies(int level) {
        return 0;
    }

    /**
     * <b>DRINKS ONLY.</b> <br>
     * The alcohol the player receives is multiplied by this number.
     * @param level The level of the modifier.
     * @return The number to multiply the drink's alcohol content by.
     */
    public float calcModifierAlcohol(int level) {
        return 1.0F;
    }

    /**
     * The duration of the effects the player receives is multiplied by this number.
     * @param level The level of the modifier.
     * @return The number to multiply the effect duration by.
     */
	public float calcModifierEffectDuration(int level) {
		return 1.0F;
	}

    public boolean canApplyTogether(FoodModifier ench) {
        return this != ench;
    }

    public FoodModifier setName(String enchName) {
        this.name = enchName;
        return this;
    }

    public String getName() {
        return "modifier." + this.name;
    }

    public String getTranslatedName(int level) {
        String s = I18n.format(this.getName() + level);

        if(this.isNegative()) {
        	s = TextFormatting.RED + s;
        } else if (this.getRarity(level) == Rarity.UNCOMMON) {
            s = TextFormatting.YELLOW + s;
        } else if (this.getRarity(level) == Rarity.RARE) {
            s = TextFormatting.AQUA + s;
        } else if (this.getRarity(level) == Rarity.EPIC) {
            s = TextFormatting.DARK_PURPLE + s;
        } else if (this.getRarity(level) == Rarity.LEGENDARY) {
            s = TextFormatting.GOLD + s;
        }

        return s;
    }
    
    public boolean isNegative() {
    	return false;
    }

    /**
     * Called when an entity eats this food.
     * @param user The eating entity.
     * @param food The food being eaten.
     * @param level The level of this modifier.
     */
    public void onFoodEaten(EntityLivingBase user, ItemStack food, int level) { }

    public boolean canApplyOnCrafting(ItemStack stack) {
        return false;
    }
    
    public int getQuality(int level) {
		return 111 - this.getRarity(level).weight;
    }

    public static void registerModifiers() {
        REGISTRY.register(0, new ResourceLocation(Ref.MODID, "addfood"), new FoodModifierAddFood(Rarity.COMMON));
        REGISTRY.register(1, new ResourceLocation(Ref.MODID, "subtractfood"), new FoodModifierSubtractFood(Rarity.COMMON));
        REGISTRY.register(2, new ResourceLocation(Ref.MODID, "addsaturation"), new FoodModifierAddSaturation(Rarity.COMMON));
        REGISTRY.register(3, new ResourceLocation(Ref.MODID, "subtractsaturation"), new FoodModifierSubtractSaturation(Rarity.COMMON));
        REGISTRY.register(4, new ResourceLocation(Ref.MODID, "addprotein"), new FoodModifierAddNutrient(Rarity.UNCOMMON, "protein"));
        REGISTRY.register(5, new ResourceLocation(Ref.MODID, "addcarbs"), new FoodModifierAddNutrient(Rarity.UNCOMMON, "carbs"));
        REGISTRY.register(6, new ResourceLocation(Ref.MODID, "addfruits"), new FoodModifierAddNutrient(Rarity.UNCOMMON, "fruits"));
        REGISTRY.register(7, new ResourceLocation(Ref.MODID, "addveggies"), new FoodModifierAddNutrient(Rarity.UNCOMMON, "veggies"));
    }

    public static enum Rarity {
        COMMON(100),
        UNCOMMON(50),
        RARE(20),
        EPIC(10),
        LEGENDARY(1);

        private final int weight;

        private Rarity(int rarityWeight) {
            this.weight = rarityWeight;
        }

        public int getWeight() {
            return this.weight;
        }
    }
}
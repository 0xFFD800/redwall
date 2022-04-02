package com.bob.redwall.crafting.smithing;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import com.bob.redwall.Ref;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryBuilder;

@SuppressWarnings("unchecked")
public abstract class EquipmentModifier extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<EquipmentModifier> {
	public static RegistryNamespaced<ResourceLocation, EquipmentModifier> REGISTRY;
	static {
		try {
			((RegistryBuilder<EquipmentModifier>) ReflectionHelper.findMethod(GameData.class, "makeRegistry", "makeRegistry", ResourceLocation.class, EquipmentModifier.class.getClass(), int.class).invoke(null, new ResourceLocation(Ref.MODID, "modifiers"), EquipmentModifier.class, Short.MAX_VALUE - 1)).create();
			REGISTRY = GameData.getWrapper(EquipmentModifier.class);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private final EquipmentModifier.Rarity rarity;
	public EnumEnchantmentType type;
	protected String name;

	@Nullable
	public static EquipmentModifier getModifierByID(int id) {
		return (EquipmentModifier) REGISTRY.getObjectById(id);
	}

	public static int getModifierID(EquipmentModifier enchantmentIn) {
		return REGISTRY.getIDForObject(enchantmentIn);
	}

	@Nullable
	public static EquipmentModifier getModifierByLocation(String location) {
		return (EquipmentModifier) REGISTRY.getObject(new ResourceLocation(Ref.MODID, location));
	}

	protected EquipmentModifier(EquipmentModifier.Rarity rarityIn, EnumEnchantmentType typeIn) {
		this.rarity = rarityIn;
		this.type = typeIn;
	}

	public EquipmentModifier.Rarity getRarity(int level) {
		return this.rarity;
	}

	public int getMinLevel() {
		return 1;
	}

	public int getMaxLevel() {
		return 1;
	}

	/**
	 * Calculates the damage reduced by this enchantment. Typically used on armor.
	 * 
	 * @param level
	 *            The level of the modifier.
	 * @param source
	 *            The {@link DamageSource} being defended against.
	 * @return The damage protection of the modifier based on level and damage
	 *         source passed.
	 */
	public int calcModifierDamage(int level, DamageSource source) {
		return 0;
	}

	/**
	 * Calculates the additional damage that will be dealt by an item with this
	 * modifier.
	 * 
	 * @param level
	 *            The level of the modifier.
	 * @param creatureType
	 *            The {@link EnumCreatureAttribute} of the creature being hit.
	 * @return The extra damage that the modified weapon should deal.
	 */
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return 0.0F;
	}

	/**
	 * Calculates the additional digging speed that will be used by an item with
	 * this modifier.
	 * 
	 * @param level
	 *            The level of the modifier.
	 * @param block
	 *            The block being broken.
	 * @return The extra digging speed that the modified tool should use.
	 */
	public float calcDiggingSpeedModifier(int level, Block block) {
		return 0.0F;
	}

	/**
	 * Calculates the durability multiplier that will be used by an item with this
	 * modifier.
	 * 
	 * @param level
	 *            The level of the modifier.
	 * @return The multiplier to the durability that the item should have
	 */
	public float calcDurabilityMultiplier(int level) {
		return 0.0F;
	}

	public boolean canApplyTogether(EquipmentModifier ench) {
		return this != ench;
	}

	public EquipmentModifier setName(String enchName) {
		this.name = enchName;
		return this;
	}

	public String getName() {
		return "modifier." + this.name;
	}

	public String getTranslatedName(int level) {
		String s = I18n.format(this.getName() + level);

		if (this.isNegative())
			s = TextFormatting.RED + s;
		else if (this.getRarity(level) == Rarity.UNCOMMON)
			s = TextFormatting.YELLOW + s;
		else if (this.getRarity(level) == Rarity.RARE)
			s = TextFormatting.AQUA + s;
		else if (this.getRarity(level) == Rarity.EPIC)
			s = TextFormatting.DARK_PURPLE + s;
		else if (this.getRarity(level) == Rarity.LEGENDARY)
			s = TextFormatting.GOLD + s;

		return s;
	}

	public boolean isNegative() {
		return false;
	}

	/**
	 * Called when an entity with a modified piece of equipment attacks another
	 * entity.
	 * 
	 * @param user
	 *            The attacking entity.
	 * @param target
	 *            The entity being attacked.
	 * @param level
	 *            The level of this modifier.
	 */
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {}

	/**
	 * Called when an entity with a modified piece of equipment is hurt.
	 * 
	 * @param user
	 *            The defending entity.
	 * @param attacker
	 *            The attacking entity.
	 * @param level
	 *            The level of this modifier.
	 */
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {}

	public boolean canApplyOnCrafting(ItemStack stack) {
		return false;
	}

	public int getQuality(int level) {
		return 111 - this.getRarity(level).weight;
	}

	public static void registerModifiers() {
		REGISTRY.register(0, new ResourceLocation(Ref.MODID, "adddamage"), new EquipmentModifierAddDamage(Rarity.COMMON));
		REGISTRY.register(1, new ResourceLocation(Ref.MODID, "subtractdamage"), new EquipmentModifierSubtractDamage(Rarity.COMMON));
		REGISTRY.register(2, new ResourceLocation(Ref.MODID, "addprotection"), new EquipmentModifierAddProtection(Rarity.COMMON));
		REGISTRY.register(3, new ResourceLocation(Ref.MODID, "subtractprotection"), new EquipmentModifierSubtractProtection(Rarity.COMMON));
		REGISTRY.register(4, new ResourceLocation(Ref.MODID, "projectileprotection"), new EquipmentModifierProjectileProtection(Rarity.COMMON));
		REGISTRY.register(5, new ResourceLocation(Ref.MODID, "adddigspeed"), new EquipmentModifierAddDigSpeed(Rarity.COMMON));
		REGISTRY.register(6, new ResourceLocation(Ref.MODID, "subtractdigspeed"), new EquipmentModifierSubtractDigSpeed(Rarity.COMMON));
		REGISTRY.register(7, new ResourceLocation(Ref.MODID, "adddurability"), new EquipmentModifierAddDurability(Rarity.COMMON));
		REGISTRY.register(8, new ResourceLocation(Ref.MODID, "subtractdurability"), new EquipmentModifierSubtractDurability(Rarity.COMMON));
	}

	public static enum Rarity {
		COMMON(100), UNCOMMON(50), RARE(20), EPIC(10), LEGENDARY(1);

		private final int weight;

		private Rarity(int rarityWeight) {
			this.weight = rarityWeight;
		}

		public int getWeight() {
			return this.weight;
		}
	}
}
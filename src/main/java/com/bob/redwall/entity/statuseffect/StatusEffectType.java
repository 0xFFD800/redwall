package com.bob.redwall.entity.statuseffect;

import com.bob.redwall.Ref;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent.Register;

public class StatusEffectType extends PotionType {
	public static PotionType POISON;
	
	public StatusEffectType(PotionEffect[] potionEffects) {
		super(potionEffects);
	}

	public static void init() {
		POISON = new StatusEffectType(new PotionEffect[] {new PotionEffect(StatusEffect.POISON, 900, 0, false, false)}).setRegistryName(Ref.MODID, "fatal_poison");
	}
	
	public static void register(Register<PotionType> event) {
		event.getRegistry().register(POISON);
	}
}

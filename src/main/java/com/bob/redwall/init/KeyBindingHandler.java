package com.bob.redwall.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {
	
	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[3];
	
	public static void init(){
		KEY_BINDINGS[0] = new KeyBinding("key.open_factions.desc", Keyboard.KEY_K, "key.redwall.category");
		KEY_BINDINGS[1] = new KeyBinding("key.open_skills.desc", Keyboard.KEY_L, "key.redwall.category");
		KEY_BINDINGS[2] = new KeyBinding("key.open_favors.desc", Keyboard.KEY_O, "key.redwall.category");
		
		for(int i = 0; i < KEY_BINDINGS.length; ++i){
			ClientRegistry.registerKeyBinding(KEY_BINDINGS[i]);
		}
	}
}

package com.bob.redwall.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {
	
	public static KeyBinding[] KEY_BINDINGS;
	
	public static void init(){
		KEY_BINDINGS = new KeyBinding[1];
		
		KEY_BINDINGS[0] = new KeyBinding("key.open_factions.desc", Keyboard.KEY_K, "key.redwall.category");
		
		for(int i = 0; i < KEY_BINDINGS.length; ++i){
			ClientRegistry.registerKeyBinding(KEY_BINDINGS[i]);
		}
	}
}

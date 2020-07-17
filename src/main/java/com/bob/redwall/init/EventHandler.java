package com.bob.redwall.init;

import com.bob.redwall.RedwallControlHandler;
import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSetCap;
import com.bob.redwall.common.MessageSyncSeason;
import com.bob.redwall.crafting.smithing.EquipmentModifier;
import com.bob.redwall.crafting.smithing.EquipmentModifierUtils;
import com.bob.redwall.dimensions.ModDimensions;
import com.bob.redwall.dimensions.redwall.EnumSeasons;
import com.bob.redwall.dimensions.redwall.RedwallTeleporter;
import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;
import com.bob.redwall.dimensions.redwall.WorldTypeRedwall;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeightProvider;
import com.bob.redwall.entity.capabilities.armor_weight.IArmorWeight;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.bob.redwall.entity.capabilities.season.SeasonCapProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.factions.Faction.FactionStatus;
import com.bob.redwall.gui.smithing.ContainerSmithingGeneric;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(MouseEvent event) { 
	    Minecraft mc = Minecraft.getMinecraft();
	    EntityPlayer player = mc.player;
	    ItemStack itemstack = player.getHeldItemMainhand();
	    
	    if (event.isButtonstate() && itemstack.getItem() instanceof ModCustomWeapon) {
    		if(event.getButton() == 1 && ((ModCustomWeapon)itemstack.getItem()).hasRightClickAbility(itemstack, player)) return; 
	    	if(player.isSneaking()) event.setCanceled(RedwallControlHandler.handleDefenseStart(event.getButton()));
	    	else event.setCanceled(RedwallControlHandler.handleAttack(event.getButton()));
	    } else if(itemstack.getItem() instanceof ModCustomWeapon) {
	    	event.setCanceled(RedwallControlHandler.handleDefenseEnd(event.getButton()));
	    }

	    if (event.getButton() == 1 && event.isButtonstate()) {
	        if (player != null) {
		    	/*if(itemstack != null && (itemstack.getItem() instanceof ItemBowAndArrow || itemstack.getItem() instanceof ItemCrossbow)) {
		    		if(player.getHeldItemOffhand() != null && !(player.getHeldItemOffhand().getItem() instanceof ItemValArrow) && !player.getHeldItemOffhand().isEmpty()) {
		    			event.setCanceled(true);
		    			return;
		    		}
		    	}*/
	        }
	    }
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInput(KeyInputEvent event) {
	    KeyBinding[] keyBindings = KeyBindingHandler.KEY_BINDINGS;
		EntityPlayerSP player = Minecraft.getMinecraft().player;
	   
	    if(keyBindings[0].isPressed()) {
	    	if (Minecraft.getMinecraft().inGameHasFocus) {
	    		Ref.NETWORK.sendToServer(new MessageSetCap(MessageSetCap.Mode.REQUEST_FACTION_UPDATE));
	    		player.openGui(Ref.MODID, GuiHandler.GUI_FACTIONS_ID, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
	    	} else {
				if (player.openContainer instanceof ContainerPlayer) {
					player.closeScreen();
				}
			}
	    }
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LootTableLoadEvent event) {//Replace Fishing Loot Tables
		if(event.getName().toString().equals("minecraft:gameplay/fishing/fish") || event.getName().toString().equals("minecraft:gameplay/fishing/junk") || event.getName().toString().equals("minecraft:gameplay/fishing/treasure")){
			LootEntry entry = new LootEntryTable(new ResourceLocation(Ref.MODID, "inject/fish"), 1000000, 0, new LootCondition[0], "fishes");
			
			event.getTable().getPool("main").addEntry(entry);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EntityViewRenderEvent.CameraSetup event){    
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)event.getEntity();
			if(!entityplayer.isCreative()) {
				Minecraft.getMinecraft().gameSettings.gammaSetting = RedwallUtils.getMoonBrightnessFactor(entityplayer.world) / 100F;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(BiomeEvent.GetGrassColor event) {
		if(event.getBiome().canRain() && !event.getBiome().isHighHumidity()) {
			int k = event.getOriginalColor();

            float ored = (float)(k >> 16 & 255);
            float ogreen = (float)(k >> 8 & 255);
            float oblue = (float)(k & 255);
            
            float nred = ored * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierRed();
            float ngreen = ogreen * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierGreen();
            float nblue = oblue * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierBlue();
            
            int finalColor = ((int)nred << 8) + (int)ngreen;
            finalColor = (finalColor << 8) + (int)nblue;
            
            event.setNewColor(finalColor);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(BiomeEvent.GetFoliageColor event) {
		if(event.getBiome().canRain() && !event.getBiome().isHighHumidity()) {
			int k = event.getOriginalColor();

            float ored = (float)(k >> 16 & 255);
            float ogreen = (float)(k >> 8 & 255);
            float oblue = (float)(k & 255);
            
            float nred = ored * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierRed();
            float ngreen = ogreen * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierGreen();
            float nblue = oblue * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierBlue();
            
            int finalColor = ((int)nred << 8) + (int)ngreen;
            finalColor = (finalColor << 8) + (int)nblue;
            
            event.setNewColor(finalColor);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)event.getEntity();
			entityplayer.getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null).init(entityplayer);
			entityplayer.getCapability(FactionCapProvider.FACTION_CAP, null).init(entityplayer);
			if(entityplayer.world.getWorldType() instanceof WorldTypeRedwall && entityplayer.world.provider.getDimension() != ModDimensions.redwallDimId) {
				RedwallTeleporter.tele(entityplayer, ModDimensions.redwallDimId);
				entityplayer.setSpawnPoint(RedwallWorldProvider.VALOUR_SPAWN_POINT, true);
			}
			if(event.getWorld().getGameRules().getBoolean("visibleNametags")) entityplayer.setAlwaysRenderNameTag(true);
			else entityplayer.setAlwaysRenderNameTag(false);
			
			if(entityplayer instanceof EntityPlayerMP) {
				EnumSeasons season = event.getWorld().getCapability(SeasonCapProvider.SEASON_CAP, null).getSeason();
				if(season == null) season = EnumSeasons.SUMMER;
				Ref.NETWORK.sendTo(new MessageSyncSeason(season.name()), (EntityPlayerMP)entityplayer);
				RedwallUtils.updatePlayerFactionStats((EntityPlayerMP)entityplayer);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingEquipmentChangeEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			if(event.getSlot().getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
				float value = RedwallUtils.getArmorWeight(player);
				IArmorWeight weight = player.getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null);
				if(value != weight.get()) {
					weight.set(value);
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingKnockBackEvent event) {
		event.setCanceled(true);
		RedwallUtils.doEntityKnockback(event.getEntityLiving(), event.getAttacker(), event.getStrength(), event.getRatioX(), event.getRatioZ());
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(WorldEvent.Load event) {
		//if(!event.getWorld().getGameRules().hasRule("doNormalSaving")) event.getWorld().getGameRules().addGameRule("doNormalSaving", "true", GameRules.ValueType.BOOLEAN_VALUE);
		if(!event.getWorld().getGameRules().hasRule("visibleNametags")) event.getWorld().getGameRules().addGameRule("visibleNametags", "false", GameRules.ValueType.BOOLEAN_VALUE);
		/*if(event.getWorld() instanceof WorldServer && !event.getWorld().getGameRules().getBoolean("doNormalSaving")) {
			WorldServer worldserver = (WorldServer) event.getWorld();
			worldserver.disableLevelSaving = true;
		}*/
		
		if(event.getWorld().provider.isSurfaceWorld()) {
			//event.getWorld().provider.setCloudRenderer(new RenderClouds());
			//event.getWorld().provider.setWeatherRenderer(new RenderWeather());
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(PlayerTickEvent event) {
	    if (event.phase == TickEvent.Phase.START && !event.player.world.isRemote) {
	    	//Attacking
	    	IAttacking attacking = event.player.getCapability(AttackingProvider.ATTACKING_CAP, null);
	    	
			if(event.player.getCooledAttackStrength(0.5F) >= 0.28875F && attacking.get()) {
				attacking.set(false);
	            float i = (float)event.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
	            
	            label1: {
		            RayTraceResult result = RedwallUtils.raytrace(event.player, i);
		            if(result == null) break label1;
					Entity entity = result.entityHit;
					if(entity != null && !(entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow)) {
						RedwallUtils.doPlayerAttack(event.player, entity);
					}
	            }
			}
			
			//Fatal Poison Effect
			if(event.player.isPotionActive(StatusEffect.POISON) && event.player.ticksExisted % (int)(20.0F / (float)(event.player.getActivePotionEffect(StatusEffect.POISON).getAmplifier() + 1)) == 0) {
				event.player.attackEntityFrom(new DamageSource("poison").setDamageBypassesArmor(), 1.0F);
			}
			
			event.player.getCapability(NutritionProvider.NUTRITION_CAP, null).update(event.player);
	    } else if(event.phase == TickEvent.Phase.START && event.player.world.isRemote) {
	    	IDefending defending = event.player.getCapability(DefendingProvider.DEFENDING_CAP, null);
			if(defending.get() && !event.player.isSneaking()) RedwallControlHandler.handleDefenseEnd(defending.getMode());
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingHurtEvent event) {
	    float armorPoints = EquipmentModifierUtils.getDamageModifierDefense(event.getEntityLiving(), event.getSource());
	    if(!event.getSource().isUnblockable()) event.setAmount(CombatRules.getDamageAfterAbsorb(event.getAmount(), armorPoints, 0));
	    if(event.getSource() instanceof EntityDamageSource && ((EntityDamageSource)event.getSource()).getImmediateSource() instanceof EntityLivingBase) EquipmentModifierUtils.doDefense(event.getEntityLiving(), (EntityLivingBase)((EntityDamageSource)event.getSource()).getImmediateSource());
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(PlayerEvent.BreakSpeed event) {
		float mult = EquipmentModifierUtils.getDiggingSpeedMultiplier(event.getEntityLiving(), event.getState().getBlock());
		event.setNewSpeed(event.getOriginalSpeed() * mult);
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(PlayerContainerEvent.Open event) {
		if(event.getContainer() instanceof ContainerSmithingGeneric) {
			((ContainerSmithingGeneric)event.getContainer()).openInventory(event.getEntityPlayer());
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(ItemTooltipEvent event) {
		if(event.getItemStack().hasTagCompound()) {
			NBTTagCompound rootTag = event.getItemStack().getTagCompound();
			if(rootTag.hasKey(EquipmentModifierUtils.MODIFIER_LIST_KEY)) {
				NBTTagList list = rootTag.getTagList(EquipmentModifierUtils.MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound tag = list.getCompoundTagAt(i);
					int id = tag.getInteger("id");
					int lvl = tag.getInteger("lvl");
					EquipmentModifier mod = EquipmentModifier.getModifierByID(id);
					String name = mod.getTranslatedName(lvl);
					event.getToolTip().add(name);
				}
			}
		}
	}

	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingEntityUseItemEvent.Finish event) {
		if(event.getEntity() instanceof EntityPlayer && event.getItem().getItem().getItemUseAction(event.getItem()) == EnumAction.EAT) {
			((EntityPlayer)event.getEntity()).getCapability(NutritionProvider.NUTRITION_CAP, null).eatFood(event.getItem());
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityAbstractNPC) {
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			EntityAbstractNPC npc = (EntityAbstractNPC)event.getEntityLiving();
			IFactionCap facCap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
			Faction fac = npc.getFaction();
			Faction factionGreatestLoyalty = fac;
			
			if(!fac.playerHasContact(player)) fac.playerContactFaction(player);
			
			for(Faction f : Faction.getAllFactions()) {
				if(facCap.get(f, FacStatType.LOYALTY) > facCap.get(factionGreatestLoyalty, FacStatType.LOYALTY)) factionGreatestLoyalty = f;
				if(fac == f) {
					facCap.set(fac, FacStatType.LOYALTY, facCap.get(fac, FacStatType.LOYALTY) - 100.0F, true);
				}

				if(fac.getFactionStatus(f) == FactionStatus.ALLIED) facCap.set(f, FacStatType.LOYALTY, facCap.get(fac, FacStatType.LOYALTY) - 100.0F, true);
				if(fac.getFactionStatus(f) == FactionStatus.FRIENDLY) facCap.set(f, FacStatType.LOYALTY, facCap.get(fac, FacStatType.LOYALTY) - 40.0F, true);
				if(fac.getFactionStatus(f) == FactionStatus.HOSTILE) facCap.set(f, FacStatType.LOYALTY, facCap.get(fac, FacStatType.LOYALTY) + 20.0F, true);
			}

			facCap.set(factionGreatestLoyalty, FacStatType.FIGHT, facCap.get(factionGreatestLoyalty, FacStatType.FIGHT) + 10.0F, true);
			if(player.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && ((ModCustomWeapon)player.getHeldItemMainhand().getItem()).getFaction() != null) {
				Faction f = ((ModCustomWeapon)player.getHeldItemMainhand().getItem()).getFaction();
				facCap.set(f, FacStatType.FIGHT, facCap.get(f, FacStatType.FIGHT) + 5.0F, true);
			}
		}
	}
}

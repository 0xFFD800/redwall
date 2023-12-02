package com.bob.redwall.init;

import com.bob.redwall.RedwallControlHandler;
import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.biomes.icy.BiomeRedwallArctic;
import com.bob.redwall.biomes.warm.BiomeRedwallMarsh;
import com.bob.redwall.common.MessageSetCap;
import com.bob.redwall.common.MessageSyncSeason;
import com.bob.redwall.common.MessageSyncSeason.Mode;
import com.bob.redwall.crafting.cooking.FoodModifier;
import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.bob.redwall.crafting.smithing.EquipmentModifier;
import com.bob.redwall.crafting.smithing.EquipmentModifierUtils;
import com.bob.redwall.dimensions.ModDimensions;
import com.bob.redwall.dimensions.redwall.EnumSeasons;
import com.bob.redwall.dimensions.redwall.RedwallTeleporter;
import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;
import com.bob.redwall.dimensions.redwall.WorldTypeRedwall;
import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeightProvider;
import com.bob.redwall.entity.capabilities.armor_weight.IArmorWeight;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.Faction.FactionStatus;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.entity.capabilities.nutrition.Nutrition;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.bob.redwall.entity.capabilities.season.SeasonCapProvider;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.entity.npc.favors.IFavorCondition;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.gui.brewing.ContainerBrewingGuosim;
import com.bob.redwall.gui.brewing.ContainerBrewingRedwall;
import com.bob.redwall.gui.brewing.ContainerBrewingVerminMossflower;
import com.bob.redwall.gui.cooking.ContainerCookingGeneric;
import com.bob.redwall.gui.smelting.ContainerSmeltery;
import com.bob.redwall.gui.smithing.ContainerSmithingGeneric;
import com.bob.redwall.gui.smithing.redwall.ContainerSmithingRedwall;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.bob.redwall.items.weapons.ranged.ItemModBow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(MouseEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		ItemStack itemstack = player.getHeldItemMainhand();

		if (event.isButtonstate()) {
			if (itemstack.getItem() instanceof ModCustomWeapon) {
				if (event.getButton() == 1 && ((ModCustomWeapon) itemstack.getItem()).hasRightClickAbility(itemstack, player))
					return;
				if (player.isSneaking())
					event.setCanceled(RedwallControlHandler.handleDefenseStart(event.getButton()));
				else event.setCanceled(RedwallControlHandler.handleAttack(event.getButton()));
			} else if (event.getButton() == 0) {
				event.setCanceled(RedwallControlHandler.handleAttack(event.getButton()));
			}
		} else if (itemstack.getItem() instanceof ModCustomWeapon) {
			event.setCanceled(RedwallControlHandler.handleDefenseEnd(event.getButton()));
		}

		if (event.getButton() == 1 && event.isButtonstate()) {
			if (player != null) {
				/*
				 * if(itemstack != null && (itemstack.getItem() instanceof ItemBowAndArrow ||
				 * itemstack.getItem() instanceof ItemCrossbow)) {
				 * if(player.getHeldItemOffhand() != null &&
				 * !(player.getHeldItemOffhand().getItem() instanceof ItemValArrow) &&
				 * !player.getHeldItemOffhand().isEmpty()) { event.setCanceled(true); return; }
				 * }
				 */
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onKeyInput(KeyInputEvent event) {
		KeyBinding[] keyBindings = KeyBindingHandler.KEY_BINDINGS;
		EntityPlayerSP player = Minecraft.getMinecraft().player;

		if (keyBindings[0].isPressed()) {
			if (Minecraft.getMinecraft().inGameHasFocus) {
				Ref.NETWORK.sendToServer(new MessageSetCap(MessageSetCap.Mode.REQUEST_FACTION_UPDATE));
				player.openGui(Ref.MODID, GuiHandler.GUI_FACTIONS_ID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
				if (!player.getCapability(FactionCapProvider.FACTION_CAP, null).isInitialized()) {
					player.closeScreen();
					player.getCapability(FactionCapProvider.FACTION_CAP, null).setInit();
				}
			} else if (player.openContainer instanceof ContainerPlayer)
				player.closeScreen();
		}

		if (keyBindings[1].isPressed()) {
			if (Minecraft.getMinecraft().inGameHasFocus) {
				Ref.NETWORK.sendToServer(new MessageSetCap(MessageSetCap.Mode.REQUEST_SKILLS_UPDATE));
				player.openGui(Ref.MODID, GuiHandler.GUI_SKILLS_ID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
				if (!player.getCapability(AgilityProvider.AGILITY_CAP, null).isInitialized()) {
					player.closeScreen();
					player.getCapability(AgilityProvider.AGILITY_CAP, null).setInit();
				}
			} else if (player.openContainer instanceof ContainerPlayer)
				player.closeScreen();
		}

		if (keyBindings[2].isPressed()) {
			if (Minecraft.getMinecraft().inGameHasFocus) {
				player.openGui(Ref.MODID, GuiHandler.GUI_FAVOR_ID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
				if (!player.getCapability(FactionCapProvider.FACTION_CAP, null).isInitialized()) {
					player.closeScreen();
					player.getCapability(FactionCapProvider.FACTION_CAP, null).setInit();
				}
			} else if (player.openContainer instanceof ContainerPlayer)
				player.closeScreen();
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LootTableLoadEvent event) {// Replace Fishing Loot Tables
		if (event.getName().toString().equals("minecraft:gameplay/fishing/fish") || event.getName().toString().equals("minecraft:gameplay/fishing/junk") || event.getName().toString().equals("minecraft:gameplay/fishing/treasure")) {
			LootEntry entry = new LootEntryTable(new ResourceLocation(Ref.MODID, "inject/fish"), 1000000, 0, new LootCondition[0], "fishes");

			event.getTable().getPool("main").addEntry(entry);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(EntityViewRenderEvent.CameraSetup event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) event.getEntity();
			if (!entityplayer.isCreative())
				Minecraft.getMinecraft().gameSettings.gammaSetting = RedwallUtils.getMoonBrightnessFactor(entityplayer.world) / 100F;
			Minecraft.getMinecraft().gameSettings.attackIndicator = 0;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(BiomeEvent.GetGrassColor event) {
		if (event.getBiome().canRain() && !event.getBiome().isHighHumidity()) {
			int k = event.getOriginalColor();

			float ored = (float) (k >> 16 & 255);
			float ogreen = (float) (k >> 8 & 255);
			float oblue = (float) (k & 255);

			float nred = ored * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierRed();
			float ngreen = ogreen * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierGreen();
			float nblue = oblue * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getGrassColorMultiplierBlue();

			int finalColor = ((int) nred << 8) + (int) ngreen;
			finalColor = (finalColor << 8) + (int) nblue;

			event.setNewColor(finalColor);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(BiomeEvent.GetFoliageColor event) {
		if (event.getBiome().canRain() && !event.getBiome().isHighHumidity()) {
			int k = event.getOriginalColor();

			float ored = (float) (k >> 16 & 255);
			float ogreen = (float) (k >> 8 & 255);
			float oblue = (float) (k & 255);

			float nred = ored * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierRed();
			float ngreen = ogreen * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierGreen();
			float nblue = oblue * RedwallUtils.getSeason(Minecraft.getMinecraft().world).getFoliageColorMultiplierBlue();

			int finalColor = ((int) nred << 8) + (int) ngreen;
			finalColor = (finalColor << 8) + (int) nblue;

			event.setNewColor(finalColor);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			((EntityLivingBase) event.getEntity()).getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null).init((EntityLivingBase) event.getEntity());
			((EntityLivingBase) event.getEntity()).getCapability(SpeedProvider.SPEED_CAP, null).init((EntityLivingBase) event.getEntity());
			((EntityLivingBase) event.getEntity()).getCapability(StrengthProvider.STRENGTH_CAP, null).init((EntityLivingBase) event.getEntity());
			((EntityLivingBase) event.getEntity()).getCapability(VitalityProvider.VITALITY_CAP, null).init((EntityLivingBase) event.getEntity());
			((EntityLivingBase) event.getEntity()).getCapability(AgilityProvider.AGILITY_CAP, null).init((EntityLivingBase) event.getEntity());
			if (event.getEntity() instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) event.getEntity();
				entityplayer.getCapability(FactionCapProvider.FACTION_CAP, null).init(entityplayer);
				if (entityplayer.world.getWorldType() instanceof WorldTypeRedwall && entityplayer.world.provider.getDimension() != ModDimensions.DIM_REDWALL_ID) {
					RedwallTeleporter.tele(entityplayer, ModDimensions.DIM_REDWALL_ID);
					entityplayer.setSpawnPoint(RedwallWorldProvider.REDWALL_SPAWN_POINT, true);
				}
				if (event.getWorld().getGameRules().getBoolean("visibleNametags"))
					entityplayer.setAlwaysRenderNameTag(true);
				else entityplayer.setAlwaysRenderNameTag(false);

				if (entityplayer instanceof EntityPlayerMP) {
					EnumSeasons season = event.getWorld().getCapability(SeasonCapProvider.SEASON_CAP, null).getSeason();
					if (season == null)
						season = EnumSeasons.SUMMER;
					Ref.NETWORK.sendTo(new MessageSyncSeason(Mode.SEASON, season.name()), (EntityPlayerMP) entityplayer);
					RedwallUtils.updatePlayerFactionStats((EntityPlayerMP) entityplayer);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingEquipmentChangeEvent event) {
		if (event.getSlot().getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
			float value = RedwallUtils.getArmorWeight(event.getEntityLiving());
			IArmorWeight weight = event.getEntityLiving().getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null);
			if (value != weight.get()) {
				weight.set(value);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingKnockBackEvent event) {
		event.setCanceled(true);
		RedwallUtils.doEntityKnockback(event.getEntityLiving(), event.getAttacker(), event.getStrength(), event.getRatioX(), event.getRatioZ());
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(WorldEvent.Load event) {
		// if(!event.getWorld().getGameRules().hasRule("doNormalSaving"))
		// event.getWorld().getGameRules().addGameRule("doNormalSaving", "true",
		// GameRules.ValueType.BOOLEAN_VALUE);

		if (!event.getWorld().getGameRules().hasRule("visibleNametags")) {
			event.getWorld().getGameRules().addGameRule("visibleNametags", "false", GameRules.ValueType.BOOLEAN_VALUE);
			// If the world doesn't have this custom game rule yet, we assume it's the first
			// time loaded and turn off natural regen.
			event.getWorld().getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
		}
		/*
		 * if(event.getWorld() instanceof WorldServer &&
		 * !event.getWorld().getGameRules().getBoolean("doNormalSaving")) { WorldServer
		 * worldserver = (WorldServer) event.getWorld(); worldserver.disableLevelSaving
		 * = true; }
		 */

		if (event.getWorld().provider.isSurfaceWorld()) {
			// event.getWorld().provider.setCloudRenderer(new RenderClouds());
			// event.getWorld().provider.setWeatherRenderer(new RenderWeather());
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && !event.player.world.isRemote) {
			if (event.player.getCapability(AttackingProvider.ATTACKING_CAP, null).get() && event.player.getCooledAttackStrength(0.5F) == 1.0F)
				event.player.getCapability(AttackingProvider.ATTACKING_CAP, null).set(false);

			// Terrain Speed Modifier
			event.player.getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null).updateTick();

			// Fatal Poison Effect
			if (event.player.isPotionActive(StatusEffect.POISON) && event.player.ticksExisted % (int) (20.0F / (float) (event.player.getActivePotionEffect(StatusEffect.POISON).getAmplifier() + 1)) == 0)
				event.player.attackEntityFrom(new DamageSource("poison").setDamageBypassesArmor(), 1.0F);

			// Nutrition
			event.player.getCapability(NutritionProvider.NUTRITION_CAP, null).update(event.player);

			// Loyalty timer
			if (event.player.ticksExisted % Faction.LOYALTY_CHANGE_TIMER == 0) {
				for (Faction f : Faction.getAllFactions()) {
					float points = event.player.getCapability(FactionCapProvider.FACTION_CAP, null).get(f, FacStatType.LOYALTY);
					if (RedwallUtils.getFacStatLevel(event.player, f, FacStatType.LOYALTY) > 0)
						if (f.isVermin())
							event.player.getCapability(FactionCapProvider.FACTION_CAP, null).set(f, FacStatType.LOYALTY, points - 1, true);
						else if (f.isPeaceful())
							event.player.getCapability(FactionCapProvider.FACTION_CAP, null).set(f, FacStatType.LOYALTY, points + 1, true);
				}
			}

			event.player.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors().forEach((u1) -> u1.update());
		} else if (event.phase == TickEvent.Phase.START && event.player.world.isRemote) {
			IDefending defending = event.player.getCapability(DefendingProvider.DEFENDING_CAP, null);
			if (defending.get() && !event.player.isSneaking())
				RedwallControlHandler.handleDefenseEnd(defending.getMode());
			
			if (event.player.getActivePotionEffect(MobEffects.NAUSEA) != null) {
				Nutrition.drunkMoveAngle = (float) Nutrition.drunkMove.getValue(event.player.ticksExisted, event.player.moveForward) % 360.0F;
				event.player.rotationYaw += Math.cos((Math.PI / 180.0) * Nutrition.drunkMoveAngle) * 3.0;
				event.player.rotationPitch += Math.sin((Math.PI / 180.0) * Nutrition.drunkMoveAngle) * 3.0;
		        event.player.prevRotationYaw = event.player.rotationYaw;
		        event.player.prevRotationPitch = event.player.rotationPitch;
				System.out.println(event.player.rotationYaw);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingHurtEvent event) {
		float armorPoints = EquipmentModifierUtils.getDamageModifierDefense(event.getEntityLiving(), event.getSource());
		if (!event.getSource().isUnblockable())
			event.setAmount(CombatRules.getDamageAfterAbsorb(event.getAmount(), armorPoints, 0));
		if (event.getSource() instanceof EntityDamageSource && ((EntityDamageSource) event.getSource()).getImmediateSource() instanceof EntityLivingBase)
			EquipmentModifierUtils.doDefense(event.getEntityLiving(), (EntityLivingBase) ((EntityDamageSource) event.getSource()).getImmediateSource());

		if (event.getEntityLiving() instanceof EntityPlayer && (event.getEntityLiving().getHeldItemMainhand().getItem() instanceof ItemBow || event.getEntityLiving().getHeldItemMainhand().getItem() instanceof ItemModBow) && event.getEntityLiving().isHandActive()) {
			ItemStack arrow = RedwallUtils.findAmmo((EntityPlayer) event.getEntityLiving());
			ItemArrow itemarrow = (ItemArrow) arrow.getItem();
			EntityArrow entityarrow = itemarrow.createArrow(event.getEntity().world, arrow, event.getEntityLiving());
			entityarrow.shoot(event.getEntityLiving(), event.getEntityLiving().rotationPitch, event.getEntityLiving().rotationYaw, 0.0F, 0, 1.0F);
			event.getEntityLiving().getHeldItemMainhand().damageItem(1, event.getEntityLiving());
			event.getEntity().world.spawnEntity(entityarrow);
			if (!((EntityPlayer) event.getEntity()).isCreative())
				arrow.shrink(1);
			event.getEntityLiving().resetActiveHand();
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerEvent.BreakSpeed event) {
		float mult = EquipmentModifierUtils.getDiggingSpeedMultiplier(event.getEntityLiving(), event.getState().getBlock());
		float mult2 = RedwallUtils.getSpecies(event.getEntityPlayer()).getDigSpeedMod();
		event.setNewSpeed(event.getOriginalSpeed() * mult * mult2);
		if (RedwallUtils.getStructureAtPos(event.getPos()) != null) {
			if (!event.getEntity().world.isRemote && (!RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getEntityPlayer()) || event.getEntityPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) >= 0) && !event.getEntityPlayer().isCreative()) {
				event.setNewSpeed(0.0F);
				event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.breakStructureFriendly"));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(BlockEvent.BreakEvent event) {
		if (RedwallUtils.getStructureAtPos(event.getPos()) != null && RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getPlayer()) && !event.getPlayer().isCreative()) {
			event.getPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).set(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY, event.getPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) - 1, true);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(BlockEvent.PlaceEvent event) {
		if (RedwallUtils.getStructureAtPos(event.getPos()) != null && (!RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getPlayer()) || event.getPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) >= 0) && !event.getPlayer().isCreative()) {
			event.setCanceled(true);
			event.getPlayer().sendMessage(new TextComponentTranslation("message.placeStructureFriendly"));
		} else if (RedwallUtils.getStructureAtPos(event.getPos()) != null && RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getPlayer()) && !event.getPlayer().isCreative()) {
			event.getPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).set(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY, event.getPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) - 1, true);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerInteractEvent.RightClickBlock event) {
		Block block = event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock();
		TileEntity te = event.getEntityPlayer().world.getTileEntity(event.getPos());
		if (((te != null && te instanceof TileEntityLockable) || block instanceof BlockDoor) && !event.getEntityPlayer().isCreative()) {
			if (RedwallUtils.getStructureAtPos(event.getPos()) != null && (!RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getEntityPlayer()) || event.getEntityPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) >= 0)) {
				event.setCanceled(true);
				event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.interactStructureFriendly"));
			} else if (RedwallUtils.getStructureAtPos(event.getPos()) != null && RedwallUtils.getStructureAtPos(event.getPos()).getFaction().playerHasContact(event.getEntityPlayer())) {
				event.getEntityPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).set(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY, event.getEntityPlayer().getCapability(FactionCapProvider.FACTION_CAP, null).get(RedwallUtils.getStructureAtPos(event.getPos()).getFaction(), FacStatType.LOYALTY) - 1, true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerContainerEvent.Open event) {
		if (event.getContainer() instanceof ContainerSmithingGeneric) {
			((ContainerSmithingGeneric) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerSmithingRedwall) {
			((ContainerSmithingRedwall) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerCookingGeneric) {
			((ContainerCookingGeneric) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerBrewingRedwall) {
			((ContainerBrewingRedwall) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerBrewingGuosim) {
			((ContainerBrewingGuosim) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerBrewingVerminMossflower) {
			((ContainerBrewingVerminMossflower) event.getContainer()).openInventory(event.getEntityPlayer());
		} else if (event.getContainer() instanceof ContainerSmeltery) {
			((ContainerSmeltery) event.getContainer()).openInventory(event.getEntityPlayer());
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(ItemTooltipEvent event) {
		if (event.getItemStack().hasTagCompound()) {
			NBTTagCompound rootTag = event.getItemStack().getTagCompound();
			if (rootTag.hasKey(EquipmentModifierUtils.MODIFIER_LIST_KEY)) {
				NBTTagList list = rootTag.getTagList(EquipmentModifierUtils.MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound tag = list.getCompoundTagAt(i);
					int id = tag.getInteger("id");
					int lvl = tag.getInteger("lvl");
					EquipmentModifier mod = EquipmentModifier.getModifierByID(id);
					String name = mod.getTranslatedName(lvl);
					event.getToolTip().add(name);
				}
			}

			if (rootTag.hasKey(FoodModifierUtils.MODIFIER_LIST_KEY)) {
				NBTTagList list = rootTag.getTagList(FoodModifierUtils.MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound tag = list.getCompoundTagAt(i);
					int id = tag.getInteger("id");
					int lvl = tag.getInteger("lvl");
					FoodModifier mod = FoodModifier.getModifierByID(id);
					String name = mod.getTranslatedName(lvl);
					event.getToolTip().add(name);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntity() instanceof EntityPlayer && event.getItem().getItem().getItemUseAction(event.getItem()) == EnumAction.EAT) {
			((EntityPlayer) event.getEntity()).getCapability(NutritionProvider.NUTRITION_CAP, null).eatFood(event.getItem());
			FoodModifierUtils.onConsumed(event.getEntityLiving(), event.getItem());
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingDeathEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityAbstractNPC) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityAbstractNPC npc = (EntityAbstractNPC) event.getEntityLiving();
			IFactionCap facCap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
			Faction fac = npc.getFaction();
			Faction factionGreatestLoyalty = fac;
			for (Favor favor : facCap.getFavors())
				for (IFavorCondition c : favor.getConditions())
					c.killNPC(npc);

			if (!fac.playerHasContact(player))
				fac.playerContactFaction(player);

			for (Faction f : Faction.getAllFactions()) {
				if (facCap.get(f, FacStatType.LOYALTY) > facCap.get(factionGreatestLoyalty, FacStatType.LOYALTY))
					factionGreatestLoyalty = f;
				if (fac == f)
					facCap.set(fac, FacStatType.LOYALTY, facCap.get(fac, FacStatType.LOYALTY) - 100.0F, true);

				if (f.getFactionStatus(fac) == FactionStatus.ALLIED)
					facCap.set(f, FacStatType.LOYALTY, facCap.get(f, FacStatType.LOYALTY) - 100.0F, true);
				if (f.getFactionStatus(fac) == FactionStatus.FRIENDLY)
					facCap.set(f, FacStatType.LOYALTY, facCap.get(f, FacStatType.LOYALTY) - 40.0F, true);
				if (f.getFactionStatus(fac) == FactionStatus.HOSTILE)
					facCap.set(f, FacStatType.LOYALTY, facCap.get(f, FacStatType.LOYALTY) + 20.0F, true);
			}

			facCap.set(factionGreatestLoyalty, FacStatType.FIGHT, facCap.get(factionGreatestLoyalty, FacStatType.FIGHT) + 10.0F, true);
			if (player.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && ((ModCustomWeapon) player.getHeldItemMainhand().getItem()).getFaction() != null) {
				Faction f = ((ModCustomWeapon) player.getHeldItemMainhand().getItem()).getFaction();
				facCap.set(f, FacStatType.FIGHT, facCap.get(f, FacStatType.FIGHT) + 5.0F, true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingJumpEvent event) {
		float f = event.getEntityLiving().rotationYaw * 0.017453292F;
		if (event.getEntityLiving().isSprinting() && !event.getEntityLiving().collidedHorizontally) { // Sprint Jump
			event.getEntityLiving().motionY += 0.05D;
			event.getEntityLiving().motionX += (double) (MathHelper.sin(f) * 0.3F);
			event.getEntityLiving().motionZ -= (double) (MathHelper.cos(f) * 0.3F);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerWakeUpEvent event) {
		if (event.shouldSetSpawn()) {
			if (event.getEntityPlayer().shouldHeal() && event.getEntityPlayer().getHealth() >= RedwallUtils.getSpecies(event.getEntityPlayer()).getHPReqForRegen())
				event.getEntityPlayer().heal(event.getEntityPlayer().getMaxHealth() - event.getEntityPlayer().getHealth());
			if (event.getEntity().world.getGameRules().getBoolean("doDaylightCycle") && !event.getEntity().world.isRemote) {
				long l = (long) (event.getEntity().world.getWorldTime() + RedwallWorldProvider.REDWALL_DAY_LENGTH);
				MinecraftServer server = ((WorldServer) event.getEntity().world).getMinecraftServer();
				for (int i = 0; i < server.worlds.length; ++i) {
					WorldServer worldserver = server.worlds[i];
					worldserver.setWorldTime(l - l % (long) RedwallWorldProvider.REDWALL_DAY_LENGTH);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(LivingSpawnEvent.CheckSpawn event) {
		if (event.getEntity() instanceof EntityAbstractNPC && event.getEntityLiving().getRNG().nextFloat() > RedwallWorldProvider.NPC_SPAWN_CHANCE)
			event.setResult(Result.DENY);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == ElementType.CROSSHAIRS) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(RedwallUtils.REDWALL_INGAME);
			GlStateManager.enableAlpha();
			float f = Minecraft.getMinecraft().player.getCooledAttackStrength(0.0F);
			boolean flag = false;

			if (Minecraft.getMinecraft().pointedEntity != null && Minecraft.getMinecraft().pointedEntity instanceof EntityLivingBase && f >= 1.0F) {
				flag = Minecraft.getMinecraft().player.getCooldownPeriod() > 5.0F;
				flag = flag & ((EntityLivingBase) Minecraft.getMinecraft().pointedEntity).isEntityAlive();
			}

			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

			int i = sr.getScaledHeight() / 2 - 7 + 16;
			int j = sr.getScaledWidth() / 2 - 8;

			if (flag) {
				Gui.drawModalRectWithCustomSizedTexture(j, i, 30, 0, 16, 16, 45, 15);
			} else if (f < 1.0F) {
				int k = (int) ((1 - (Math.abs(f - 0.5F) * 2.0F)) * 16.0F);
				Gui.drawModalRectWithCustomSizedTexture(j, i, 0, 0, 15, 15, 45, 15);
				Gui.drawModalRectWithCustomSizedTexture(j, i + 15 - k, 15, 15 - k, k, k, 45, 15);
			}
		}
	}

	private static float fog_density = 0.0F;
	private static float prev_fog_density = 0.0F;

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(RenderFogEvent event) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		Biome biome = player.world.getBiome(player.getPosition());

		if (biome == BiomeHandler.redwall_forest_ash || biome == BiomeHandler.redwall_forest_southsward || biome == BiomeHandler.redwall_abyss || biome instanceof BiomeRedwallMarsh)
			GlStateManager.setFog(GlStateManager.FogMode.EXP);
		else if (biome instanceof BiomeRedwallArctic)
			GlStateManager.setFog(GlStateManager.FogMode.LINEAR);

		if (player.world.isRaining())
			GlStateManager.setFog(GlStateManager.FogMode.EXP);

		GlStateManager.setFogDensity(MathHelper.clamp(prev_fog_density + ((fog_density - prev_fog_density) * (float) event.getRenderPartialTicks()), 0.0F, 1.0F));
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(TickEvent.ClientTickEvent event) {
		if (event.phase == Phase.END) {
			prev_fog_density = fog_density;

			EntityPlayerSP player = Minecraft.getMinecraft().player;
			if (player == null || player.world == null)
				return;

			Biome biome = player.world.getBiome(player.getPosition());
			float f = 0.0F;
			if (biome == BiomeHandler.redwall_forest_ash || biome == BiomeHandler.redwall_forest_southsward)
				f = 0.02F;
			else if (biome == BiomeHandler.redwall_abyss)
				f = 0.03F;
			else if (biome instanceof BiomeRedwallMarsh)
				f = 0.05F;
			else if (biome instanceof BiomeRedwallArctic)
				f = 0.02F;

			if (player.world.isRaining())
				fog_density = f + (player.world.getRainStrength((float) Minecraft.getMinecraft().getRenderPartialTicks()) - 0.2F) * 0.05F;
			else fog_density = f;
		}
	}
}

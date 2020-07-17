package com.bob.redwall;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.bob.redwall.common.MessageSyncCap;
import com.bob.redwall.common.MessageSyncSeason;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.crafting.smithing.EquipmentModifier;
import com.bob.redwall.crafting.smithing.EquipmentModifierUtils;
import com.bob.redwall.dimensions.redwall.EnumSeasons;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.entity.capabilities.season.ISeasonCap;
import com.bob.redwall.entity.capabilities.season.SeasonCapProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.items.armor.ItemRedwallArmor;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedwallUtils {
	public static EnumSeasons getSeason(IBlockAccess world) {
		if (!(world instanceof World)) return EnumSeasons.SUMMER;
		return ((World) world).getCapability(SeasonCapProvider.SEASON_CAP, null).getSeason() != null ? ((World) world).getCapability(SeasonCapProvider.SEASON_CAP, null).getSeason() : EnumSeasons.SUMMER;
	}

	public static void updateSeason(WorldServer world, EnumSeasons season) {
		ISeasonCap cap = world.getCapability(SeasonCapProvider.SEASON_CAP, null);
		cap.setSeason(season);
		Ref.NETWORK.sendToAll(new MessageSyncSeason(season.name()));
		for (Chunk chunk : world.getChunkProvider().getLoadedChunks()) {
			for (int i = 0; i < 255; i += 16) {
				BlockPos pos = chunk.getPos().getBlock(0, 0 + i, 0);
				world.markAndNotifyBlock(pos, chunk, Blocks.AIR.getDefaultState(), chunk.getBlockState(pos), 11);
			}
		}
	}

	public static int getModdedBiomeFoliageColorBirch(IBlockAccess worldIn, BlockPos pos) {
		int original = ColorizerFoliage.getFoliageColorBirch();
		if (worldIn == null || pos == null) return original;
		net.minecraftforge.event.terraingen.BiomeEvent.GetFoliageColor event = new net.minecraftforge.event.terraingen.BiomeEvent.GetFoliageColor(worldIn.getBiome(pos), original);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		return event.getNewColor();
	}

	public static void updateSnowBlock(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		Biome biome = worldIn.getBiome(pos);
		float f = biome.getTemperature(pos) > 0.8F ? biome.getTemperature(pos) : biome.getTemperature(pos) * RedwallUtils.getSeason(worldIn).getTemperatureMultiplier();
		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 || f > 0.15F) {
			worldIn.setBlockToAir(pos);
		}
	}

	/**
	 * Returns the current moon brightness. Used by the CameraEvent.Setup hook in
	 * {@link com.bob.valour.init.EventHandler}.
	 * 
	 * @return the brightness setting used in survival mode.
	 */
	@SideOnly(Side.CLIENT)
	public static float getMoonBrightnessFactor(World world) {
		int i = 0;
		switch (world.getMoonPhase()) {
		case 0: // Full moon
			i = 0;
			break;
		case 1: // Waning gibbous
			i = 12;
			break;
		case 2: // Waning half
			i = 25;
			break;
		case 3: // Waning crescent
			i = 38;
			break;
		case 4: // New moon
			i = 50;
			break;
		case 5: // Waxing crescent
			i = 38;
			break;
		case 6: // Waxing half
			i = 25;
			break;
		case 7: // Waxing gibbous
			i = 12;
			break;
		}

		float f = world.getSunBrightnessBody(Minecraft.getMinecraft().getRenderPartialTicks());
		f *= 50F;
		i -= ((int) f - 10);

		if (i > 50) i = 50;
		if (i < 0) i = 0;
		return 50 - i;
	}

	public static void doEntityKnockback(EntityLivingBase entity, Entity cause, float strength, double xRatio, double zRatio) {
		entity.motionX /= 2.0D;
		entity.motionZ /= 2.0D;
	}

	public static void doEntityKnockbackTrue(EntityLivingBase entity, Entity cause, float strength, double xRatio, double zRatio) {
		entity.isAirBorne = true;
		float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
		entity.motionX /= 2.0D;
		entity.motionZ /= 2.0D;
		entity.motionX -= xRatio / (double) f * (double) strength;
		entity.motionZ -= zRatio / (double) f * (double) strength;

		if (entity.onGround) {
			entity.motionY /= 2.0D;
			entity.motionY += (double) strength;

			if (entity.motionY > 0.4D) {
				entity.motionY = 0.4D;
			}
		}
	}

	public static void doPlayerAttack(EntityPlayer player, Entity targetEntity) {
		if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(player, targetEntity)) return;
		RedwallUtils.doAttack(player, targetEntity);
	}

	public static void doAttack(EntityLivingBase attacker, Entity targetEntity) {
		DamageSource source = attacker instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) attacker) : DamageSource.causeMobDamage(attacker);
		if (targetEntity.canBeAttackedWithItem()) {
			if (!targetEntity.hitByEntity(attacker)) {
				float f = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				float f1 = 0.0F;
				float knockback = 0.0F;
				float stun = 0.0F;
				float health = 0.0F;
				float aoe = 0.0F;
				ItemStack weapon = attacker.getHeldItemMainhand();

				if (targetEntity instanceof EntityLivingBase) {
					f1 = EquipmentModifierUtils.getAdditionalAttack(attacker, (EntityLivingBase) targetEntity);
					health = ((EntityLivingBase) targetEntity).getHealth();
				}

				f += f1;

				boolean isWeapon = weapon.getItem() instanceof ModCustomWeapon;
				boolean isBludgeon = isWeapon && ((ModCustomWeapon) weapon.getItem()).isBludgeon(weapon, attacker);
				boolean isSweep = isWeapon && !isBludgeon && ((ModCustomWeapon) weapon.getItem()).isSweep(weapon, attacker);
				boolean isStab = isWeapon && !isBludgeon && ((ModCustomWeapon) weapon.getItem()).isStab(weapon, attacker);
				if (!isBludgeon && !isSweep && !isStab) isBludgeon = true;

				boolean doCriticalNonStab = attacker.fallDistance > 0.0F && !attacker.onGround && !attacker.isOnLadder() && !attacker.isInWater() && !attacker.isPotionActive(MobEffects.BLINDNESS) && !attacker.isRiding() && targetEntity instanceof EntityLivingBase;
				doCriticalNonStab = doCriticalNonStab && !attacker.isSprinting();
				double mx = targetEntity.motionX;
				double my = targetEntity.motionY;
				double mz = targetEntity.motionZ;
				boolean doCriticalStabSweep2 = attacker.isSprinting();

				boolean didDamage = false;

				if (f > 0.0F || f1 > 0.0F) {
					if (isBludgeon || !isWeapon) {
						if (!isWeapon) {
							mx = targetEntity.motionX;
							my = targetEntity.motionY;
							mz = targetEntity.motionZ;
							didDamage = targetEntity.attackEntityFrom(source, f);
						} else {
							label1: {
								IAttacking attacking = attacker.getCapability(AttackingProvider.ATTACKING_CAP, null);
								if (doCriticalNonStab && attacking.getMode() == 0) {
									stun = f / 4.0F;
									f *= 1.5F;
								}
								if (targetEntity instanceof EntityLivingBase) {
									EntityLivingBase living = ((EntityLivingBase) targetEntity);
									if (attacking.getMode() == 2) attacking.setMode(0);
									IDefending defending = living.getCapability(DefendingProvider.DEFENDING_CAP, null);
									if (living.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && defending.get() && ((defending.getMode() == 0 && attacking.getMode() == 1) || (defending.getMode() == 1 && attacking.getMode() == 0)) && RedwallUtils.canEntityBlockDamage(living, source)) {
										attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, attacker.getSoundCategory(), 1.0F, 0.75F);
										break label1;
									}
								}

								mx = targetEntity.motionX;
								my = targetEntity.motionY;
								mz = targetEntity.motionZ;
								didDamage = targetEntity.attackEntityFrom(source, f);
							}
						}
					} else {
						IAttacking attacking = attacker.getCapability(AttackingProvider.ATTACKING_CAP, null);
						boolean hasAttacked = false;
						
						if(isWeapon && ((ModCustomWeapon)weapon.getItem()).getFaction() != null && attacker instanceof EntityPlayer) {
							float fightSkill = attacker.getCapability(FactionCapProvider.FACTION_CAP, null).get(((ModCustomWeapon)weapon.getItem()).getFaction(), FacStatType.FIGHT);
							if(fightSkill > 0) {
								f *= ((float)RedwallUtils.getFacStatLevel((EntityPlayer)attacker, ((ModCustomWeapon)weapon.getItem()).getFaction(), FacStatType.FIGHT) / 4.0F) + 1.0F;
							}
						}

						switch (attacking.getMode()) {
						case 0:
							if (isSweep) {
								label1: {
									if (doCriticalNonStab) {
										stun = f / 4.0F;
										f *= 1.5F;
									}
									if (targetEntity instanceof EntityLivingBase) {
										EntityLivingBase living = ((EntityLivingBase) targetEntity);
										IDefending defending = living.getCapability(DefendingProvider.DEFENDING_CAP, null);
										if (living.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && defending.get() && defending.getMode() == 1 && RedwallUtils.canEntityBlockDamage(living, source)) {
											attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, attacker.getSoundCategory(), 1.0F, 0.75F);
											break label1;
										}
									}

									mx = targetEntity.motionX;
									my = targetEntity.motionY;
									mz = targetEntity.motionZ;
									didDamage = targetEntity.attackEntityFrom(source, f);
								}
								hasAttacked = true;
							}
							break;
						case 1:
							if (isSweep) {
								if (doCriticalStabSweep2) {
									aoe = (float) (attacker.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() / 4.0F);
									f *= 1.25F;
									attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1.0F, 1.0F);
								}

								label1: {
									if (targetEntity instanceof EntityLivingBase) {
										EntityLivingBase living = ((EntityLivingBase) targetEntity);
										IDefending defending = living.getCapability(DefendingProvider.DEFENDING_CAP, null);
										if (living.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && defending.get() && defending.getMode() == 0 && RedwallUtils.canEntityBlockDamage(living, source)) {
											attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, attacker.getSoundCategory(), 1.0F, 0.75F);
											break label1;
										}
									}

									mx = targetEntity.motionX;
									my = targetEntity.motionY;
									mz = targetEntity.motionZ;
									didDamage = targetEntity.attackEntityFrom(source, f);
								}
								hasAttacked = true;
							}
							break;
						case 2:
							if (isStab) {
								if (doCriticalStabSweep2) {
									knockback = 0.5F;
									attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, attacker.getSoundCategory(), 1.0F, 1.0F);
								} else f /= 1.5F;

								mx = targetEntity.motionX;
								my = targetEntity.motionY;
								mz = targetEntity.motionZ;
								didDamage = targetEntity.attackEntityFrom(source, f);
								hasAttacked = true;
							}
							break;
						}

						if (!hasAttacked) {
							if (isSweep) {
								label1: {
									if (targetEntity instanceof EntityLivingBase) {
										EntityLivingBase living = ((EntityLivingBase) targetEntity);
										IDefending defending = living.getCapability(DefendingProvider.DEFENDING_CAP, null);
										if (living.getHeldItemMainhand().getItem() instanceof ModCustomWeapon && defending.get() && defending.getMode() == 0 && RedwallUtils.canEntityBlockDamage(living, source)) {
											attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, attacker.getSoundCategory(), 1.0F, 0.75F);
											break label1;
										}
									}

									mx = targetEntity.motionX;
									my = targetEntity.motionY;
									mz = targetEntity.motionZ;
									didDamage = targetEntity.attackEntityFrom(source, f);
								}
								hasAttacked = true;
							} else {
								if (doCriticalStabSweep2) {
									knockback = 0.5F;
									attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, attacker.getSoundCategory(), 1.0F, 1.0F);
								} else f /= 1.5F;

								mx = targetEntity.motionX;
								my = targetEntity.motionY;
								mz = targetEntity.motionZ;
								didDamage = targetEntity.attackEntityFrom(source, f);
								hasAttacked = true;
							}
						}

						if (didDamage) {
							if (targetEntity instanceof EntityLivingBase) {
								if (knockback > 0.0F) {
									RedwallUtils.doEntityKnockbackTrue(((EntityLivingBase) targetEntity), attacker, knockback, (double) MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
								} else {
									((EntityLivingBase) targetEntity).knockBack(attacker, 0.5F, (double) MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
								}

								if (stun > 0.0F) {
									((EntityLivingBase) targetEntity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (stun * 10.0F), 128, false, false));
									((EntityLivingBase) targetEntity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (int) (stun * 10.0F), 128, false, false));
								}

								if (aoe > 0.0F) {
									for (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().grow(aoe, 0.25D, aoe))) {
										if (entitylivingbase != attacker && entitylivingbase != targetEntity && !attacker.isOnSameTeam(entitylivingbase) && attacker.getDistanceSq(entitylivingbase) < 16.0D) {
											entitylivingbase.knockBack(attacker, 0.4F, (double) MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
											entitylivingbase.attackEntityFrom(source, f / 2.0F);
										}
									}
								}

								attacker.motionX *= 0.6D;
								attacker.motionZ *= 0.6D;
								attacker.setSprinting(false);

								if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
									((EntityPlayerMP) targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
									targetEntity.velocityChanged = false;
									targetEntity.motionX = mx;
									targetEntity.motionY = my;
									targetEntity.motionZ = mz;
								}

								if (doCriticalNonStab || doCriticalStabSweep2) {
									attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0F, 1.0F);
									if (attacker instanceof EntityPlayer) ((EntityPlayer) attacker).onCriticalHit(targetEntity);
								} else {
									if (isWeapon) {
										attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, attacker.getSoundCategory(), 1.0F, 1.0F);
									} else {
										attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, attacker.getSoundCategory(), 1.0F, 1.0F);
									}
								}

								attacker.setLastAttackedEntity(targetEntity);

								if (targetEntity instanceof EntityLivingBase) {
									EquipmentModifierUtils.doAttack(attacker, (EntityLivingBase) targetEntity);
								}

								Entity entity = targetEntity;

								if (targetEntity instanceof MultiPartEntityPart) {
									IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

									if (ientitymultipart instanceof EntityLivingBase) {
										entity = (EntityLivingBase) ientitymultipart;
									}
								}

								if (!weapon.isEmpty() && entity instanceof EntityLivingBase) {
									ItemStack beforeHitCopy = weapon.copy();
									if (attacker instanceof EntityPlayer) weapon.hitEntity((EntityLivingBase) entity, (EntityPlayer) attacker);

									if (weapon.isEmpty()) {
										attacker.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
										if (attacker instanceof EntityPlayer) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) attacker, beforeHitCopy, EnumHand.MAIN_HAND);
									}
								}

								if (targetEntity instanceof EntityLivingBase) {
									float f4 = health - ((EntityLivingBase) targetEntity).getHealth();
									if (attacker instanceof EntityPlayer) ((EntityPlayer) attacker).addStat(StatList.DAMAGE_DEALT, Math.round(f4 * 10.0F));

									if (attacker.world instanceof WorldServer && f4 > 2.0F) {
										int k = (int) ((double) f4 * 0.5D);
										((WorldServer) attacker.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + (double) (targetEntity.height * 0.5F), targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D, new int[0]);
									}
								}

								if (attacker instanceof EntityPlayer) ((EntityPlayer) attacker).addExhaustion(0.1F);
							}
						} else {
							attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, attacker.getSoundCategory(), 1.0F, 1.0F);
						}
					}
				}
			}
		}
	}

	public static float doArmorCalc(float damage, float totalArmor, float toughnessAttribute) {
		float f = 2.0F + toughnessAttribute / 4.0F;
		float f1 = MathHelper.clamp(totalArmor - damage / f, totalArmor * 0.2F, 20.0F);
		return damage * (1.0F - f1 / 25.0F);
	}

	public static float getArmorWeight(EntityPlayer player) {
		float armor_weight = 0;
		for (ItemStack stack : player.getArmorInventoryList()) {
			Item item = stack.getItem();
			if (item instanceof ItemRedwallArmor) {
				armor_weight += ((ItemRedwallArmor) item).getArmorWeight();
			} else {
				if (item == Items.IRON_HELMET) armor_weight += 0.2F;
				else if (item == Items.IRON_CHESTPLATE) armor_weight += 0.4F;
				else if (item == Items.IRON_LEGGINGS) armor_weight += 0.3F;
				else if (item == Items.IRON_BOOTS) armor_weight += 0.1F;
				else if (item == Items.CHAINMAIL_HELMET) armor_weight += 0.1F;
				else if (item == Items.CHAINMAIL_CHESTPLATE) armor_weight += 0.2F;
				else if (item == Items.CHAINMAIL_LEGGINGS) armor_weight += 0.2F;
				else if (item == Items.CHAINMAIL_BOOTS) armor_weight += 0.1F;
				else if (item == Items.GOLDEN_HELMET) armor_weight += 0.5F;
				else if (item == Items.GOLDEN_CHESTPLATE) armor_weight += 0.8F;
				else if (item == Items.GOLDEN_LEGGINGS) armor_weight += 0.7F;
				else if (item == Items.GOLDEN_BOOTS) armor_weight += 0.4F;
				else if (item == Items.LEATHER_HELMET) armor_weight += 0.05F;
				else if (item == Items.LEATHER_CHESTPLATE) armor_weight += 0.1F;
				else if (item == Items.LEATHER_LEGGINGS) armor_weight += 0.1F;
				else if (item == Items.LEATHER_BOOTS) armor_weight += 0.05F;
			}
		}
		return -armor_weight * 2;
	}

	public static void applyEquipmentModifiers(EntityPlayer player, ItemStack stack, float skill) {
		if (!player.world.isRemote) {
			int rand = (int) ((skill / 2.0F + player.getRNG().nextInt(Math.abs((int) skill) + 1) - 20.0F) / 5.0F);
			List<Entry<Integer, EquipmentModifier>> possibilities = Lists.newArrayList();

			for (ResourceLocation loc : EquipmentModifier.REGISTRY.getKeys()) {
				EquipmentModifier mod = EquipmentModifier.REGISTRY.getObject(loc);
				if (mod.canApplyOnCrafting(stack)) {
					for (int i = mod.getMinLevel(); i < mod.getMaxLevel(); i++) {
						if ((mod.getQuality(i) < 1 && rand < mod.getQuality(i)) || (mod.getQuality(i) > -1 && rand > mod.getQuality(i))) {
							HashMap<Integer, EquipmentModifier> tempMap = Maps.newHashMap();
							tempMap.put(i, mod);
							possibilities.add(tempMap.entrySet().iterator().next());
						}
					}
				}
			}

			if (!possibilities.isEmpty()) {
				int i = player.getRNG().nextInt(possibilities.size());
				Entry<Integer, EquipmentModifier> finalMod = possibilities.get(i);
				possibilities.remove(i);
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				NBTTagList list = new NBTTagList();
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("id", EquipmentModifier.getModifierID(finalMod.getValue()));
				nbt.setInteger("lvl", finalMod.getKey());
				list.appendTag(nbt);

				if (player.getRNG().nextDouble() < 0.2) {
					Entry<Integer, EquipmentModifier> secondMod = possibilities.get(player.getRNG().nextInt(possibilities.size()));
					NBTTagCompound nbt2 = new NBTTagCompound();
					nbt2.setInteger("id", EquipmentModifier.getModifierID(secondMod.getValue()));
					nbt2.setInteger("lvl", finalMod.getKey());
					list.appendTag(nbt2);
				}

				stack.getTagCompound().setTag(EquipmentModifierUtils.MODIFIER_LIST_KEY, list);
			}
		}
	}

	public static RayTraceResult raytrace(EntityLivingBase player, float dist) {
		AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(player.posX - 0.5D, player.posY - 0.0D, player.posZ - 0.5D, player.posX + 0.5D, player.posY + 1.5D, player.posZ + 0.5D);
		RayTraceResult returnMOP = null;

		if (player.world != null) {
			double var2 = dist;
			returnMOP = player.rayTrace(var2, 0);
			double calcdist = var2;
			Vec3d pos = player.getPositionEyes(0);
			var2 = calcdist;
			if (returnMOP != null) {
				calcdist = returnMOP.hitVec.distanceTo(pos);
			}

			Vec3d lookvec = player.getLook(0);

			Vec3d var8 = pos.addVector(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
			Entity pointedEntity = null;
			float var9 = 1.0F;
			java.util.List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, theViewBoundingBox.expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2).grow(var9, var9, var9));
			double d = calcdist;

			for (Entity entity : list) {
				if (entity.canBeCollidedWith()) {
					float bordersize = entity.getCollisionBorderSize();
					AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2, entity.posY, entity.posZ - entity.width / 2, entity.posX + entity.width / 2, entity.posY + entity.height, entity.posZ + entity.width / 2);
					aabb.grow(bordersize, bordersize, bordersize);
					RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

					if (aabb.contains(pos)) {
						if (0.0D < d || d == 0.0D) {
							pointedEntity = entity;
							d = 0.0D;
						}
					} else if (mop0 != null) {
						double d1 = pos.distanceTo(mop0.hitVec);

						if (d1 < d || d == 0.0D) {
							pointedEntity = entity;
							d = d1;
						}
					}
				}
			}

			if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
				returnMOP = new RayTraceResult(pointedEntity);
			} else if (d < calcdist) {
				returnMOP = player.world.rayTraceBlocks(pos, lookvec);
			}
		}
		return returnMOP;
	}

	public static boolean canEntityBlockDamage(EntityLivingBase entity, DamageSource damageSourceIn) {
		if (!damageSourceIn.isUnblockable() && entity.isActiveItemStackBlocking()) {
			Vec3d vec3d = damageSourceIn.getDamageLocation();

			if (vec3d != null) {
				Vec3d vec3d1 = entity.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

				if (vec3d2.dotProduct(vec3d1) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}

	@SuppressWarnings("incomplete-switch")
	public static void doAnimationBiped(ModelBiped model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getTicksElytraFlying() > 4;
		boolean flag1 = false;
		model.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
		EnumHandSide enumhandside = RedwallUtils.getMainHand(entityIn);
		ModelRenderer modelrenderer = RedwallUtils.getArmForSide(model, enumhandside);

		if (flag) {
			model.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		} else {
			model.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		}

		model.bipedBody.rotateAngleY = 0.0F;
		model.bipedRightArm.rotationPointZ = 0.0F;
		model.bipedRightArm.rotationPointX = -5.0F;
		model.bipedLeftArm.rotationPointZ = 0.0F;
		model.bipedLeftArm.rotationPointX = 5.0F;
		float f = 1.0F;

		if (flag) {
			f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		model.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		model.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		model.bipedRightArm.rotateAngleZ = 0.0F;
		model.bipedLeftArm.rotateAngleZ = 0.0F;
		model.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		model.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
		model.bipedRightLeg.rotateAngleY = 0.0F;
		model.bipedLeftLeg.rotateAngleY = 0.0F;
		model.bipedRightLeg.rotateAngleZ = 0.0F;
		model.bipedLeftLeg.rotateAngleZ = 0.0F;

		if (model.isRiding) {
			model.bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
			model.bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
			model.bipedRightLeg.rotateAngleX = -1.4137167F;
			model.bipedRightLeg.rotateAngleY = ((float) Math.PI / 10F);
			model.bipedRightLeg.rotateAngleZ = 0.07853982F;
			model.bipedLeftLeg.rotateAngleX = -1.4137167F;
			model.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
			model.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		}

		model.bipedRightArm.rotateAngleY = 0.0F;
		model.bipedRightArm.rotateAngleZ = 0.0F;

		switch (model.leftArmPose) {
		case EMPTY:
			model.bipedLeftArm.rotateAngleY = 0.0F;
			break;
		case BLOCK:
			model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
			model.bipedLeftArm.rotateAngleY = 0.5235988F;
			break;
		case ITEM:
			model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
			model.bipedLeftArm.rotateAngleY = 0.0F;
		}

		switch (model.rightArmPose) {
		case EMPTY:
			model.bipedRightArm.rotateAngleY = 0.0F;
			break;
		case BLOCK:
			model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
			model.bipedRightArm.rotateAngleY = -0.5235988F;
			break;
		case ITEM:
			model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
			model.bipedRightArm.rotateAngleY = 0.0F;
		}

		if (model.swingProgress > 0.0F || (entityIn instanceof EntityPlayer && (1.0F - ((EntityPlayer) entityIn).getCooledAttackStrength(Minecraft.getMinecraft().getRenderPartialTicks())) > 0.0F) || (entityIn instanceof EntityAbstractNPC && ((EntityAbstractNPC) entityIn).getCooldown() > 0)) {
			float f1 = model.swingProgress;
			if (entityIn instanceof EntityPlayer) f1 = 1.0F - ((EntityPlayer) entityIn).getCooledAttackStrength(Minecraft.getMinecraft().getRenderPartialTicks());
			else if (entityIn instanceof EntityAbstractNPC) f1 = 1.0F - (((float) ((EntityAbstractNPC) entityIn).getCooldown()) / ((float) ((EntityAbstractNPC) entityIn).getSwingCooldown()));
			model.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT) {
				model.bipedBody.rotateAngleY *= -1.0F;
			}

			model.bipedRightArm.rotationPointZ = MathHelper.sin(model.bipedBody.rotateAngleY) * 5.0F;
			model.bipedRightArm.rotationPointX = -MathHelper.cos(model.bipedBody.rotateAngleY) * 5.0F;
			model.bipedLeftArm.rotationPointZ = -MathHelper.sin(model.bipedBody.rotateAngleY) * 5.0F;
			model.bipedLeftArm.rotationPointX = MathHelper.cos(model.bipedBody.rotateAngleY) * 5.0F;
			model.bipedRightArm.rotateAngleY += model.bipedBody.rotateAngleY;
			model.bipedLeftArm.rotateAngleY += model.bipedBody.rotateAngleY;
			model.bipedLeftArm.rotateAngleX += model.bipedBody.rotateAngleY;
			f1 = 1.0F - model.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			if (entityIn instanceof EntityPlayer) f1 = 1.0F - ((EntityPlayer) entityIn).getCooledAttackStrength(Minecraft.getMinecraft().getRenderPartialTicks());
			else if (entityIn instanceof EntityAbstractNPC) f1 = 1.0F - (((float) ((EntityAbstractNPC) entityIn).getCooldown()) / ((float) ((EntityAbstractNPC) entityIn).getSwingCooldown()));
			IAttacking attacking = entityIn instanceof EntityLivingBase ? ((EntityLivingBase) entityIn).getCapability(AttackingProvider.ATTACKING_CAP, null) : null;
			if (attacking == null) {
				float f2 = MathHelper.sin(f1 * (float) Math.PI);
				float f3 = MathHelper.sin(model.swingProgress * (float) Math.PI) * -(model.bipedHead.rotateAngleX - 0.7F) * 0.75F;
				modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
				modelrenderer.rotateAngleY += model.bipedBody.rotateAngleY * 2.0F;
				modelrenderer.rotateAngleZ += MathHelper.sin(model.swingProgress * (float) Math.PI) * -0.4F;
			} else {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entityIn;
				ItemStack weapon = entitylivingbase.getHeldItemMainhand();

				boolean isWeapon = weapon.getItem() instanceof ModCustomWeapon;
				boolean isBludgeon = isWeapon && ((ModCustomWeapon) weapon.getItem()).isBludgeon(weapon, entitylivingbase);
				boolean isSweep = isWeapon && !isBludgeon && ((ModCustomWeapon) weapon.getItem()).isSweep(weapon, entitylivingbase);
				boolean isStab = isWeapon && !isBludgeon && ((ModCustomWeapon) weapon.getItem()).isStab(weapon, entitylivingbase);
				if (!isBludgeon && !isSweep && !isStab) isBludgeon = true;

				if ((isSweep || isBludgeon) && attacking.getMode() == 0) {
					// Vertical Sweep
					float f2 = MathHelper.sin(f1 * (float) Math.PI);
					float f3 = MathHelper.sin(model.swingProgress * (float) Math.PI) * -(model.bipedHead.rotateAngleX - 0.7F) * 0.75F;
					modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
					modelrenderer.rotateAngleY += model.bipedBody.rotateAngleY * 2.0F;
					modelrenderer.rotateAngleZ += MathHelper.sin(model.swingProgress * (float) Math.PI) * -0.4F;
				} else if ((isSweep || isBludgeon) && attacking.getMode() == 1) {
					// Horizontal Sweep
					float f2 = MathHelper.sin(f1 * (float) Math.PI);
					float f3 = MathHelper.sin(model.swingProgress * (float) Math.PI) * -(model.bipedHead.rotateAngleX - 0.7F) * 0.75F;
					modelrenderer.rotateAngleZ = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
					modelrenderer.rotateAngleY += model.bipedBody.rotateAngleY * 2.0F;
					modelrenderer.rotateAngleX += MathHelper.sin(model.swingProgress * (float) Math.PI) * -0.4F;
				} else if (isStab && attacking.getMode() == 2) {
					model.bipedBody.rotateAngleX = 0.25F;
					modelrenderer.rotateAngleX -= 0.8F;
					RedwallUtils.getArmForSide(model, enumhandside.opposite()).rotateAngleX += 0.4F;
					model.bipedRightLeg.rotationPointZ = 4.0F;
					model.bipedLeftLeg.rotationPointZ = 4.0F;
					model.bipedRightLeg.rotationPointY = 9.0F;
					model.bipedLeftLeg.rotationPointY = 9.0F;
					model.bipedHead.rotationPointY = 1.0F;
					modelrenderer.rotateAngleX = (MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F) * 2.5F;
					flag1 = true;
				} else {
					if (isSweep || isBludgeon) {
						// Go back, do vertical sweep
						float f2 = MathHelper.sin(f1 * (float) Math.PI);
						float f3 = MathHelper.sin(model.swingProgress * (float) Math.PI) * -(model.bipedHead.rotateAngleX - 0.7F) * 0.75F;
						modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
						modelrenderer.rotateAngleY += model.bipedBody.rotateAngleY * 2.0F;
						modelrenderer.rotateAngleZ += MathHelper.sin(model.swingProgress * (float) Math.PI) * -0.4F;
					} else {
						model.bipedBody.rotateAngleX = 0.25F;
						modelrenderer.rotateAngleX -= 0.8F;
						RedwallUtils.getArmForSide(model, enumhandside.opposite()).rotateAngleX += 0.4F;
						model.bipedRightLeg.rotationPointZ = 4.0F;
						model.bipedLeftLeg.rotationPointZ = 4.0F;
						model.bipedRightLeg.rotationPointY = 9.0F;
						model.bipedLeftLeg.rotationPointY = 9.0F;
						model.bipedHead.rotationPointY = 1.0F;
						modelrenderer.rotateAngleX = (MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F) * 2.5F;
						flag1 = true;
					}
				}
			}
		}

		if (model.isSneak) {
			model.bipedBody.rotateAngleX = 0.25F;
			model.bipedRightArm.rotateAngleX += 0.4F;
			model.bipedLeftArm.rotateAngleX += 0.4F;
			model.bipedRightLeg.rotationPointZ = 4.0F;
			model.bipedLeftLeg.rotationPointZ = 4.0F;
			model.bipedRightLeg.rotationPointY = 9.0F;
			model.bipedLeftLeg.rotationPointY = 9.0F;
			model.bipedRightLeg.rotateAngleX += 0.5F;
			model.bipedLeftLeg.rotateAngleX -= 0.5F;
			model.bipedHead.rotationPointY = 1.0F;

			IDefending defending = entityIn instanceof EntityLivingBase ? ((EntityLivingBase) entityIn).getCapability(DefendingProvider.DEFENDING_CAP, null) : null;
			if (defending != null) {
				if (defending.get()) {
					switch (defending.getMode()) {
					case 0:
						modelrenderer.rotateAngleX = -(float) Math.PI / 2.0F;
						modelrenderer.rotateAngleY = -(float) Math.PI / 3.0F;
						break;
					case 1:
						modelrenderer.rotateAngleX = -(float) Math.PI / 2.5F;
						modelrenderer.rotateAngleZ = (float) Math.PI / 2.5F;
						break;
					case 2:
						break;
					}
				}
			}
		} else if (!flag1) {
			model.bipedBody.rotateAngleX = 0.0F;
			model.bipedRightLeg.rotationPointZ = 0.1F;
			model.bipedLeftLeg.rotationPointZ = 0.1F;
			model.bipedRightLeg.rotationPointY = 12.0F;
			model.bipedLeftLeg.rotationPointY = 12.0F;
			model.bipedHead.rotationPointY = 0.0F;
		}

		model.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		model.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		model.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		model.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		if (model.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			model.bipedRightArm.rotateAngleY = -0.1F + model.bipedHead.rotateAngleY;
			model.bipedLeftArm.rotateAngleY = 0.1F + model.bipedHead.rotateAngleY + 0.4F;
			model.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + model.bipedHead.rotateAngleX;
			model.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + model.bipedHead.rotateAngleX;
		} else if (model.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			model.bipedRightArm.rotateAngleY = -0.1F + model.bipedHead.rotateAngleY - 0.4F;
			model.bipedLeftArm.rotateAngleY = 0.1F + model.bipedHead.rotateAngleY;
			model.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + model.bipedHead.rotateAngleX;
			model.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + model.bipedHead.rotateAngleX;
		}

		ItemStack itemstack = ((EntityLivingBase) entityIn).getHeldItemMainhand();
		EnumAction enumaction = itemstack.getItemUseAction();
		if (enumaction == ItemHandler.SPEAR_ACTION && ((EntityLivingBase) entityIn).isHandActive()) {
			RedwallUtils.getArmForSide(model, RedwallUtils.getMainHand(entityIn)).rotateAngleX = (float) -Math.PI;
			RedwallUtils.getArmForSide(model, RedwallUtils.getMainHand(entityIn)).rotateAngleY = 0;
		}

		ModelBiped.copyModelAngles(model.bipedHead, model.bipedHeadwear);
	}

	public static ModelRenderer getArmForSide(ModelBiped biped, EnumHandSide side) {
		return side == EnumHandSide.LEFT ? biped.bipedLeftArm : biped.bipedRightArm;
	}

	public static EnumHandSide getMainHand(Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) entityIn;
			EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
			return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
		} else {
			return EnumHandSide.RIGHT;
		}
	}

	public static boolean shouldNotReleaseMouse() {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		IDefending defending = player.getCapability(DefendingProvider.DEFENDING_CAP, null);
		return player.isSneaking() && defending.get() && defending.getMode() == 2;
	}

	public static void updatePlayerFactionStats(EntityPlayerMP player) {
		Ref.NETWORK.sendTo(new MessageSyncCap(player.getCapability(FactionCapProvider.FACTION_CAP, null).writeToNBT(), MessageSyncCap.Mode.FACTION_STATS), player);
	}

	public static int getFacStatLevel(EntityPlayer player, Faction fac, FactionCap.FacStatType type) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		float amount = cap.get(fac, type);
		boolean flag = amount < 0;
		return (int) Math.floor(Math.sqrt(((flag ? -amount : amount) + 20) / 40.0F)) * (flag ? -1 : 1);
	}

	public static int getFacStatLevelRaw(EntityPlayer player, Faction fac, FactionCap.FacStatType type) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		float amount = cap.get(fac, type);
		boolean flag = amount < 0;
		return (int) Math.floor(Math.sqrt((flag ? -amount : amount) / 40.0F)) * (flag ? -1 : 1);
	}

	public static void checkAndHandleLevelChange(float amount, EntityPlayer player, Faction fac, FactionCap.FacStatType type) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		float amountToLevelUp = Math.signum(amount) * 40.0F * (float) Math.pow(((float) RedwallUtils.getFacStatLevelRaw(player, fac, type) + 1.0F), 2) - 20.0F;
		float amountToLevelDown = Math.signum(amount) * 40.0F * (float) Math.pow(((float) RedwallUtils.getFacStatLevelRaw(player, fac, type) - 1.0F), 2) - 20.0F;

		if (amount > amountToLevelUp) {
			if (player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageUIInteractServer(MessageUIInteractServer.Mode.SEND_LEVEL_TOAST, type.getId(), "facstats.toast.title1", "facstats.toast.subtitle1", fac.getID()), (EntityPlayerMP) player);
			cap.set(fac, type, Math.signum(amount) * 40.0F * (float) Math.pow(((float) RedwallUtils.getFacStatLevelRaw(player, fac, type) + 1.0F), 2), false);
		} else if (amount < amountToLevelDown) {
			if (player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageUIInteractServer(MessageUIInteractServer.Mode.SEND_LEVEL_TOAST, type.getId(), "facstats.toast.title2", "facstats.toast.subtitle2", fac.getID()), (EntityPlayerMP) player);
			cap.set(fac, type, Math.signum(amount) * 40.0F * (float) Math.pow(((float) RedwallUtils.getFacStatLevelRaw(player, fac, type) - 1.0F), 2), false);
		}
	}

	/**
	 * A generic getReach method to get the reach from entities.
	 * 
	 * @param entity
	 *            The entity to get the reach from.
	 * @return The reach distance of the entity. For non-players, it automatically
	 *         multiplies the answer by 0.75.
	 */
	public static float getReach(EntityLivingBase entity) {
		if (entity instanceof EntityAbstractNPC || entity instanceof EntityPlayer) {
			return (float) entity.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() * (entity instanceof EntityPlayer ? 1 : 0.75F);
		} else if (entity.getHeldItemMainhand().getItem() instanceof ModCustomWeapon) {
			return 3.375F + (((ModCustomWeapon) entity.getHeldItemMainhand().getItem()).getReach() * 0.75F);
		} else {
			return 3.375F;
		}
	}
}

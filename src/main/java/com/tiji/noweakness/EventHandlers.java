package com.tiji.noweakness;

import net.minecraft.component.ComponentChanges;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.UUID;

import static net.minecraft.component.DataComponentTypes.*;

public class EventHandlers {
    public static boolean onDeath(LivingEntity livingEntity, DamageSource damageSource, float v) {
        if (livingEntity instanceof ServerPlayerEntity) {
            EntityAttributeInstance attribute = livingEntity.getAttributeInstance(EntityAttributes.MAX_HEALTH);

            if (attribute.getBaseValue() >= 10) {
                ItemStack health = new ItemStack(Items.NETHER_STAR);

                ComponentChanges.Builder builder = ComponentChanges.builder();

                NbtCompound compound = new NbtCompound();
                compound.putBoolean("isHealth", true);
                builder.add(CUSTOM_DATA, NbtComponent.of(compound));

                builder.add(CUSTOM_NAME, Translations.HEALTH_ITEM_NAME);
                builder.add(LORE, new LoreComponent(List.of(Translations.HEALTH_ITEM_LORE)));
                health.applyChanges(builder.build());

                ServerWorld world = (ServerWorld) livingEntity.getWorld();
                double xM = MathHelper.nextDouble(world.random, -0.1, 0.1);
                double yM = MathHelper.nextDouble(world.random, 0.0, 0.1);
                double zM = MathHelper.nextDouble(world.random, -0.1, 0.1);
                ItemEntity healthEntity = new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), health, xM, yM, zM);
                healthEntity.setNeverDespawn();
                world.spawnEntity(healthEntity);

                attribute.setBaseValue(attribute.getBaseValue() - 2);
            }
        }
        return true;
    }

    public static void onPlayerDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        ServerPlayerEntity player = handler.getPlayer();
        if (No.pvpTime.containsKey(player.getUuid())) {
            if (No.isPvp(player)) player.kill((ServerWorld) player.getWorld());

            No.pvpTime.remove(player.getUuid());
        }
    }

    public static void onTick(MinecraftServer server) {
        if (No.SERVERWORLD == null) {
            No.SERVERWORLD = server.getWorld(ServerWorld.OVERWORLD);
            RegisterCommands.setupEnchantments();
        }
        for (UUID uuid : No.pvpTime.keySet()) {
            if (No.isPvp(uuid)) {
                if (server.getPlayerManager().getPlayer(uuid) == null) {
                    continue;
                }

                Text pvpTimeMessage = Translations.getPvpTimeWarning((int) (Constants.PVP_TIME_MS - (System.currentTimeMillis() - No.pvpTime.get(uuid)))/1000);

                server.getPlayerManager().getPlayer(uuid).sendMessage(pvpTimeMessage, true);
            }
            else if(No.isPvpIncludingLastTick(uuid)) {
                server.getPlayerManager().getPlayer(uuid).sendMessage(Translations.PVP_TIME_END, true);
            }
        }
        for (UUID uuid : No.totemTime.keySet()) {
            if (No.shouldWarnTotem(uuid)) {
                if (server.getPlayerManager().getPlayer(uuid) == null) {
                    continue;
                }

                server.getPlayerManager().getPlayer(uuid).sendMessage(Translations.TOO_MUCH_TOTEMS, true);

                server.getPlayerManager().getPlayer(uuid).sendMessage(Text.literal(String.valueOf((System.currentTimeMillis() / 50))));

                if (System.currentTimeMillis() / 50 % 2 == 0) {
                    server.getPlayerManager().getPlayer(uuid).playSoundToPlayer(SoundEvents.BLOCK_NOTE_BLOCK_BIT.value(),
                            SoundCategory.MASTER, 0.6f, getAlertSound.getSoundPitch(Math.toIntExact(System.currentTimeMillis() / 50 % 64)));
                }
            } else if (No.shouldKillTotemOwner(uuid)) {
                if (server.getPlayerManager().getPlayer(uuid) == null) {
                    continue;
                }

                server.getPlayerManager().getPlayer(uuid).kill((ServerWorld) server.getPlayerManager().getPlayer(uuid).getWorld());
                No.totemTime.remove(uuid);
            }
        }
    }
}

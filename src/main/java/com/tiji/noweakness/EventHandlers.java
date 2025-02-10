package com.tiji.noweakness;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.UUID;

public class EventHandlers {
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
                if (server.getPlayerManager().getPlayer(uuid) != null) {
                    server.getPlayerManager().getPlayer(uuid).sendMessage(Translations.PVP_TIME_END, true);
                }
            }
        }
        for (UUID uuid : No.totemTime.keySet()) {
            if (No.shouldWarnTotem(uuid)) {
                if (server.getPlayerManager().getPlayer(uuid) == null) {
                    continue;
                }

                server.getPlayerManager().getPlayer(uuid).sendMessage(Translations.TOO_MUCH_TOTEMS, true);

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

package com.tiji.noweakness;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class No implements ModInitializer {
	public static final String MOD_ID = "no_weakness";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static HashMap<UUID, Long> pvpTime = new HashMap<>();
	public static HashMap<UUID, Long> totemTime = new HashMap<>();
	public static ServerWorld SERVERWORLD = null;

	@Override
	public void onInitialize() {
		ServerTickEvents.END_SERVER_TICK.register(EventHandlers::onTick);
		ServerPlayConnectionEvents.DISCONNECT.register(EventHandlers::onPlayerDisconnect);
		ServerLivingEntityEvents.ALLOW_DEATH.register(EventHandlers::onDeath);

        try {
            HomeLocationContainer.createOrLoad();
        } catch (IOException e) {
            throw new RuntimeException("홈 세이브 로드 실패: ", e);
        }

        RegisterCommands.register();

		LOGGER.info("No약자 모드 초기화 완료");
	}

	public static boolean isPvp(UUID player) {
		if (No.pvpTime.containsKey(player)) {
			return Constants.PVP_TIME_MS - (System.currentTimeMillis() - No.pvpTime.get(player)) > 0;
		}
		return false;
	}

	public static boolean isPvp(ServerPlayerEntity player) {
		return isPvp(player.getUuid());
	}

	public static boolean isPvpIncludingLastTick(UUID player) {
		if (No.pvpTime.containsKey(player)) {
            return Constants.PVP_TIME_MS - (System.currentTimeMillis() - No.pvpTime.get(player)) > -100;
        }
        return false;
	}

	public static boolean shouldWarnTotem(UUID player) {
		if (No.totemTime.containsKey(player)) {
            return (System.currentTimeMillis() - No.totemTime.get(player)) < Constants.TOO_MUCH_TOTEM_WAIT_TIME;
        }
        return false;
	}

	public static boolean shouldKillTotemOwner(UUID player) {
		if (No.totemTime.containsKey(player)) {
            return (System.currentTimeMillis() - No.totemTime.get(player)) > Constants.TOO_MUCH_TOTEM_WAIT_TIME;
        }
        return false;
	}
}
package com.tiji.noweakness.mixin;

import com.tiji.noweakness.Constants;
import net.minecraft.SharedConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class InitTaskMixin {
	@Inject(at = @At("HEAD"), method = "onSpawn")
	private void inject(CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;

		Scoreboard scoreboard = Objects.requireNonNull(player.getServer()).getScoreboard();

		if (scoreboard.getTeam("main") == null) {
			// 초기화가 아직 되지 않음
			Team team = scoreboard.addTeam("main");
			team.setNameTagVisibilityRule(AbstractTeam.VisibilityRule.NEVER);
			team.setShowFriendlyInvisibles(false);

			// 월드보더 설정
			player.getServer().getOverworld().getWorldBorder().setSize(Constants.WORLD_BORDER_SIZE);
			Objects.requireNonNull(player.getServer().getWorld(World.NETHER)).getWorldBorder().setSize(Constants.WORLD_BORDER_SIZE);

			// 렌덤 스폰
			player.getServer().getGameRules().get(GameRules.SPAWN_RADIUS).set(Constants.WORLD_BORDER_SIZE, null);
		}
		if (player.getScoreboardTeam() == null) {
			// 처음 들어온 플레이어
			scoreboard.addScoreHolderToTeam(player.getNameForScoreboard(), scoreboard.getTeam("main"));
		}
	}
}
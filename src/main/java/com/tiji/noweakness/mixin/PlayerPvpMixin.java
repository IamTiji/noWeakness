package com.tiji.noweakness.mixin;

import com.mojang.authlib.GameProfile;
import com.tiji.noweakness.No;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerPvpMixin {
    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "attack", at = @At("HEAD"))
    private void attack(Entity target, CallbackInfo info) {
        // 플레이어 공격시 PVP 모드 활성화
        if (target instanceof PlayerEntity) {
            No.pvpTime.put(this.getGameProfile().getId(), System.currentTimeMillis());
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void attacked(ServerWorld world, DamageSource source, float amount, CallbackInfo info) {
        // 플레이어한테 공격 받을시 PVP 모드 활성화
        if (source.getAttacker() instanceof PlayerEntity) {
            No.pvpTime.put(this.getGameProfile().getId(), System.currentTimeMillis());
        }
    }
}

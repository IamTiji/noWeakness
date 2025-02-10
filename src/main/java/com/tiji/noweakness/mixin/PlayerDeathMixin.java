package com.tiji.noweakness.mixin;

import com.tiji.noweakness.No;
import com.tiji.noweakness.Translations;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.component.DataComponentTypes.*;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeathMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void inject(DamageSource damageSource, CallbackInfo ci) {
        EntityAttributeInstance attribute = ((LivingEntityInvoker) this).getAttributeInstanceInvoker(EntityAttributes.MAX_HEALTH);

        if (attribute.getBaseValue() >= 10) {
            ItemStack health = new ItemStack(Items.NETHER_STAR);

            ComponentChanges.Builder builder = ComponentChanges.builder();

            NbtCompound compound = new NbtCompound();
            compound.putBoolean("isHealth", true);
            builder.add(CUSTOM_DATA, NbtComponent.of(compound));

            builder.add(CUSTOM_NAME, Translations.HEALTH_ITEM_NAME);
            builder.add(LORE, new LoreComponent(List.of(Translations.HEALTH_ITEM_LORE)));
            health.applyChanges(builder.build());

            BaseEntityInvoker entity = (BaseEntityInvoker) this;

            ServerWorld world = (ServerWorld) entity.getWorldInvoker();
            double xM = MathHelper.nextDouble(world.random, -0.1, 0.1);
            double yM = MathHelper.nextDouble(world.random, 0.0, 0.1);
            double zM = MathHelper.nextDouble(world.random, -0.1, 0.1);
            ItemEntity healthEntity = new ItemEntity(world, entity.getXInvoker(), entity.getYInvoker(), entity.getZInvoker(), health, xM, yM, zM);
            healthEntity.setNeverDespawn();
            world.spawnEntity(healthEntity);

            attribute.setBaseValue(attribute.getBaseValue() - 2);
        }
    }

}

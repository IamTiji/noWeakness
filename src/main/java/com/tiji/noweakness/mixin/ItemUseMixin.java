package com.tiji.noweakness.mixin;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

import static net.minecraft.component.DataComponentTypes.CUSTOM_DATA;

@Mixin(Item.class)
public class ItemUseMixin {
    @Inject(method = "use", at = @At("HEAD"))
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack item = user.getStackInHand(hand);

        if (item.getComponents().get(CUSTOM_DATA) != null) {
            if (item.isOf(Items.NETHER_STAR)) {
                Predicate<ItemStack> nether_star = (item_) -> {
                    if (item_.getComponents().get(CUSTOM_DATA) == null) return false;
                    return item_.isOf(Items.NETHER_STAR);
                };

                user.getInventory().remove(nether_star, 1, user.playerScreenHandler.getCraftingInput());

                EntityAttributeInstance attribute = user.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                attribute.setBaseValue(attribute.getBaseValue() + 2.0f);

                user.playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.MASTER, 2.0f, 1.8f);
            }
        }
    }
}

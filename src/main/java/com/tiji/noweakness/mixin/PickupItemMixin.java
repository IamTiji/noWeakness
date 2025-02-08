package com.tiji.noweakness.mixin;

import com.tiji.noweakness.Constants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.tiji.noweakness.No;

@Mixin(PlayerInventory.class)
public class PickupItemMixin {
    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow @Final public PlayerEntity player;

    @Inject(at = @At("HEAD"), method = "insertStack(ILnet/minecraft/item/ItemStack;)Z")
    private void inject(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        int count = this.main.stream().filter(itemStack -> itemStack.isOf(Items.TOTEM_OF_UNDYING))
                .mapToInt(ItemStack::getCount).sum();

        if (count > Constants.MAX_TOTEMS_ALLOWED) {
            No.totemTime.put(this.player.getGameProfile().getId(), System.currentTimeMillis());
        } else No.totemTime.remove(this.player.getGameProfile().getId());
    }
}

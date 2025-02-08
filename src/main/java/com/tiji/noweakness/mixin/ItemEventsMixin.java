package com.tiji.noweakness.mixin;

import com.tiji.noweakness.Constants;
import com.tiji.noweakness.No;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(PlayerInventory.class)
public class ItemEventsMixin {
    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow @Final public PlayerEntity player;

    @Inject(at = @At("RETURN"), method = {
            "addStack(ILnet/minecraft/item/ItemStack;)I"
    })
    private void inject1(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("addStack(ILnet/minecraft/item/ItemStack;)I"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
    "swapSlotWithHotbar"
    })
    private void inject2(int slot, CallbackInfo ci) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("swapSlotWithHotbar(II)V"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
    "swapStackWithHotbar"
    })
    private void inject3(ItemStack stack, CallbackInfo ci) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("swapStackWithHotbar(Lnet/minecraft/item/ItemStack;)V"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
    "remove"
    })
    private void inject4(Predicate<ItemStack> shouldRemove, int maxCount, Inventory craftingInventory, CallbackInfoReturnable<Integer> cir) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("remove(Ljava/util/function/Predicate;ILnet/minecraft/inventory/Inventory;)I"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
            "insertStack(ILnet/minecraft/item/ItemStack;)Z"
    })
    private void inject5(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("insertStack(ILnet/minecraft/item/ItemStack;)Z"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
            "removeOne"
    })
    private void inject6(ItemStack stack, CallbackInfo ci) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("removeOne(Lnet/minecraft/item/ItemStack;)V"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
            "removeStack(II)Lnet/minecraft/item/ItemStack;"
    })
    private void inject7(int index, int count, CallbackInfoReturnable<ItemStack> cir) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("removeStack(II)Lnet/minecraft/item/ItemStack;"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
            "removeStack(I)Lnet/minecraft/item/ItemStack;"
    })
    private void inject8(int index, CallbackInfoReturnable<ItemStack> cir) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("removeStack(I)Lnet/minecraft/item/ItemStack;"), false);
        CommonInject();
    }
    @Inject(at = @At("RETURN"), method = {
            "setStack(ILnet/minecraft/item/ItemStack;)V"
    })
    private void inject9(int index, ItemStack stack, CallbackInfo ci) {
        if (Constants.DEBUG_ITEM_EVENTS) this.player.sendMessage(Text.literal("setStack(ILnet/minecraft/item/ItemStack;)V"), false);
        CommonInject();
    }

    @Unique
    private void CommonInject() {
        int count = this.main.stream().filter(itemStack -> itemStack.isOf(Items.TOTEM_OF_UNDYING))
                .mapToInt(ItemStack::getCount).sum();

        if (count > Constants.MAX_TOTEMS_ALLOWED) {
            No.totemTime.put(this.player.getGameProfile().getId(), System.currentTimeMillis());
        } else No.totemTime.remove(this.player.getGameProfile().getId());
    }
}

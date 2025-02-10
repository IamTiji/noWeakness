package com.tiji.noweakness.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface BaseEntityInvoker {
    @Invoker("getX")
    double getXInvoker();

    @Invoker("getY")
    double getYInvoker();

    @Invoker("getZ")
    double getZInvoker();

    @Invoker("getWorld")
    World getWorldInvoker();
}

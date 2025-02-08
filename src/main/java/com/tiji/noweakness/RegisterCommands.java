package com.tiji.noweakness;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.component.DataComponentTypes.*;

public class RegisterCommands {

    private static RegistryEntry<Enchantment> UNBREAKING = null;
    private static RegistryEntry<Enchantment> MENDING    = null;
    private static RegistryEntry<Enchantment> EFFICIENCY = null;
    private static RegistryEntry<Enchantment> FORTUNE    = null;

    public static void setupEnchantments() {
        RegistryEntryLookup<Enchantment> EnchantmentLookup = No.SERVERWORLD.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

        UNBREAKING = EnchantmentLookup.getOrThrow(Enchantments.UNBREAKING);
        MENDING    = EnchantmentLookup.getOrThrow(Enchantments.MENDING);
        EFFICIENCY = EnchantmentLookup.getOrThrow(Enchantments.EFFICIENCY);
        FORTUNE    = EnchantmentLookup.getOrThrow(Enchantments.FORTUNE);
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, access, executor) -> {
            LiteralCommandNode<ServerCommandSource> convert = dispatcher.register(CommandManager.literal("교환")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .executes(RegisterCommands::executeExchangeCommandDescription)
                    .then(CommandManager.literal("겉날게")
                            .executes(RegisterCommands::executeExchangeCommandElytra))
                    .then(CommandManager.literal("네더라이트")
                            .executes(RegisterCommands::executeExchangeCommandNetherite))
                    .then(CommandManager.literal("도구")
                            .executes(RegisterCommands::executeExchangeCommandTool)));
            LiteralCommandNode<ServerCommandSource> rule = dispatcher.register(CommandManager.literal("규칙")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .executes(RegisterCommands::executeRuleCommand));
            LiteralCommandNode<ServerCommandSource> extract = dispatcher.register(CommandManager.literal("생체전환")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .executes(RegisterCommands::executeExtractHealth)
                    .then(
                            CommandManager.argument("량", IntegerArgumentType.integer(1))
                                    .executes(RegisterCommands::executeExtractHealthAmount)
                    ));
            LiteralCommandNode<ServerCommandSource> home = dispatcher.register(CommandManager.literal("홈")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .then(CommandManager.literal("지정")
                            .executes(RegisterCommands::executeHomeSetCommand))
                    .then(CommandManager.literal("이동")
                            .executes(RegisterCommands::executeHomeTeleportCommand))
                    .then(CommandManager.literal("정보")
                            .executes(RegisterCommands::executeHomeInfoCommand)));
            LiteralCommandNode<ServerCommandSource> tpa = dispatcher.register(CommandManager.literal("티피요청")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .then(
                            CommandManager.literal("요청")
                                    .then(CommandManager.argument("대상", EntityArgumentType.player())
                                            .executes(RegisterCommands::executeTpaRequestCommand)))
                    .then(
                            CommandManager.literal("수락")
                                    .then(CommandManager.argument("대상", EntityArgumentType.player())
                                        .executes(RegisterCommands::executeTpaAcceptCommand)))
                    .then(
                            CommandManager.literal("거절")
                                    .then(CommandManager.argument("대상", EntityArgumentType.player())
                                        .executes(RegisterCommands::executeTpaDeclineCommand)))
                    .then(
                            CommandManager.literal("취소")
                                    .then(CommandManager.argument("대상", EntityArgumentType.player())
                                        .executes(RegisterCommands::executeTpaCancelCommand))
                    ));
            dispatcher.register(CommandManager.literal("convert").requires(ServerCommandSource::isExecutedByPlayer).redirect(convert));
            dispatcher.register(CommandManager.literal("rule").requires(ServerCommandSource::isExecutedByPlayer).redirect(rule));
            dispatcher.register(CommandManager.literal("extract").requires(ServerCommandSource::isExecutedByPlayer).redirect(extract));
            dispatcher.register(CommandManager.literal("home").requires(ServerCommandSource::isExecutedByPlayer).redirect(home));
            dispatcher.register(CommandManager.literal("tpa").requires(ServerCommandSource::isExecutedByPlayer).redirect(tpa));
        });
    }

    private static int executeExchangeCommandDescription(CommandContext<ServerCommandSource> source) {
        source.getSource().getPlayer().sendMessage(Translations.CONVERT_INFO, false);
        return 0;
    }

    private static int executeExchangeCommandElytra(CommandContext<ServerCommandSource> source) {
        if (source.getSource().getPlayer().isHolding(Items.DRAGON_EGG)) {
            ItemStack elytra = new ItemStack(Items.ELYTRA, 1);

            elytra.addEnchantment(UNBREAKING, 3);
            elytra.addEnchantment(MENDING, 1);

            Predicate<ItemStack> dragonEggPredicate = itemStack -> itemStack.getItem() == Items.DRAGON_EGG;

            source.getSource().getPlayer().getInventory().remove(dragonEggPredicate, 1, source.getSource().getPlayer().playerScreenHandler.getCraftingInput());

            source.getSource().getPlayer().giveOrDropStack(elytra);

            source.getSource().getPlayer().sendMessage(Translations.CONVERT_ELYTRA_SUCCESS, false);
            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.NO_DRAGON_EGG_FAIL, false);
        return 1;
    }

    private static int executeExchangeCommandNetherite(CommandContext<ServerCommandSource> source) {
        if (source.getSource().getPlayer().isHolding(Items.DRAGON_EGG)) {
            ItemStack netheriteIngot = new ItemStack(Items.NETHERITE_INGOT, 5);

            Predicate<ItemStack> dragonEggPredicate = itemStack -> itemStack.getItem() == Items.DRAGON_EGG;

            source.getSource().getPlayer().getInventory().remove(dragonEggPredicate, 1, source.getSource().getPlayer().playerScreenHandler.getCraftingInput());

            source.getSource().getPlayer().giveOrDropStack(netheriteIngot);
            source.getSource().getPlayer().sendMessage(Translations.CONVERT_NETHERITE_SUCCESS, false);
            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.NO_DRAGON_EGG_FAIL, false);
        return 1;
    }

    private static int executeExchangeCommandTool(CommandContext<ServerCommandSource> source) {
        if (source.getSource().getPlayer().isHolding(Items.DRAGON_EGG)) {
            ItemStack axe = new ItemStack(Items.DIAMOND_AXE, 1);
            axe.addEnchantment(EFFICIENCY, 5);
            axe.addEnchantment(FORTUNE, 3);
            axe.addEnchantment(UNBREAKING, 3);
            axe.addEnchantment(MENDING, 1);

            ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE, 1);
            pickaxe.addEnchantment(EFFICIENCY, 5);
            pickaxe.addEnchantment(FORTUNE, 3);
            pickaxe.addEnchantment(UNBREAKING, 3);
            pickaxe.addEnchantment(MENDING, 1);

            ItemStack shovel = new ItemStack(Items.DIAMOND_SHOVEL, 1);
            shovel.addEnchantment(EFFICIENCY, 5);
            shovel.addEnchantment(FORTUNE, 3);
            shovel.addEnchantment(UNBREAKING, 3);
            shovel.addEnchantment(MENDING, 1);

            Predicate<ItemStack> dragonEggPredicate = itemStack -> itemStack.getItem() == Items.DRAGON_EGG;

            source.getSource().getPlayer().getInventory().remove(dragonEggPredicate, 1, source.getSource().getPlayer().playerScreenHandler.getCraftingInput());

            source.getSource().getPlayer().giveOrDropStack(axe);
            source.getSource().getPlayer().giveOrDropStack(pickaxe);
            source.getSource().getPlayer().giveOrDropStack(shovel);

            source.getSource().getPlayer().sendMessage(Translations.CONVERT_TOOL_SUCCESS, false);
            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.NO_DRAGON_EGG_FAIL, false);
        return 1;
    }

    private static int executeRuleCommand(CommandContext<ServerCommandSource> source) {
        source.getSource().getPlayer().sendMessage(Translations.RULE_TEXT);
        return 0;
    }

    private static int executeExtractHealth(CommandContext<ServerCommandSource> source) {
        LivingEntity livingEntity = source.getSource().getPlayer();
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

            livingEntity.giveOrDropStack(health);

            attribute.setBaseValue(attribute.getBaseValue() - 2);

            source.getSource().getPlayer().playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.MASTER, 2.0f, 0.1f);

            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.EXTRACT_HEALTH_NOT_ENOUGH_FAIL, false);
        return 1;
    }
    private static int executeExtractHealthAmount(CommandContext<ServerCommandSource> source) {
        for (int i = 0; i < source.getArgument("량", Integer.class); i++) {
            if (executeExtractHealth(source) == 1) break;
        }
        return 0;
    }
    private static int executeHomeSetCommand(CommandContext<ServerCommandSource> source) {
        if (source.getSource().getWorld().getRegistryKey() != World.OVERWORLD) {
            source.getSource().getPlayer().sendMessage(Translations.SET_HOME_NOT_OVERWORLD_FAIL, false);
            return 1;
        }
        Vec3d position = source.getSource().getPosition();
        HomeLocationContainer.setHomeLocation(source.getSource().getPlayer().getUuid(), position);
        source.getSource().getPlayer().sendMessage(Translations.getHomeCommandSuccess(
                Math.floor(position.x * 100)/100, Math.floor(position.y * 100)/100, Math.floor(position.z * 100)/100
        ), false);
        return 0;
    }
    private static int executeHomeTeleportCommand(CommandContext<ServerCommandSource> source) {
        if (No.isPvp(source.getSource().getPlayer())) {
            source.getSource().getPlayer().sendMessage(Translations.TELEPORT_HOME_IN_PVP_FAIL, false);
            return 1;
        }
        if (HomeLocationContainer.hasHomeLocation(source.getSource().getPlayer().getUuid())) {
            Vec3d position = HomeLocationContainer.getHomeLocation(source.getSource().getPlayer().getUuid());
            source.getSource().getPlayer().teleport(position.x, position.y, position.z, false);
            // 렉벡 방지
            source.getSource().getPlayer().networkHandler.syncWithPlayerPosition();
            source.getSource().getPlayer().sendMessage(Translations.TELEPORT_HOME_SUCCESS, false);
            source.getSource().getPlayer().playSoundToPlayer(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 2.0f, 1f);
            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.TELEPORT_HOME_NO_HOME_FAIL, false);
        return 1;
    }
    private static int executeHomeInfoCommand(CommandContext<ServerCommandSource> source) {
        if (HomeLocationContainer.hasHomeLocation(source.getSource().getPlayer().getUuid())) {
            Vec3d position = HomeLocationContainer.getHomeLocation(source.getSource().getPlayer().getUuid());
            source.getSource().getPlayer().sendMessage(Translations.getHomeInfoCommandResult(
                    Math.floor(position.x * 100)/100, Math.floor(position.y * 100)/100, Math.floor(position.y * 100)/100
            ), false);
            return 0;
        }
        source.getSource().getPlayer().sendMessage(Translations.INFO_HOME_NO_HOME_FAIL, false);
        return 1;
    }
    private static int executeTpaRequestCommand(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ServerPlayerEntity from = source.getSource().getPlayer();
        ServerPlayerEntity to = source.getArgument("대상", EntitySelector.class).getPlayer(source.getSource());

        if (TpaRequestsContainer.isThereRequest(from.getUuid(), to.getUuid())) {
            source.getSource().getPlayer().sendMessage(Translations.TPA_REQUEST_ALREADY_SENT_FAIL, false);
            return 1;
        }
        TpaRequestsContainer.addRequest(from.getUuid(), to.getUuid());
        source.getSource().getPlayer().sendMessage(Translations.getTpaRequestResponse(to.getNameForScoreboard()), false);
        to.sendMessage(Translations.getTpaRequestedText(from.getNameForScoreboard()), false);
        return 0;
    }
    private static int executeTpaDeclineCommand(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ServerPlayerEntity from = source.getSource().getPlayer();
        ServerPlayerEntity to = source.getArgument("대상", EntitySelector.class).getPlayer(source.getSource());

        if (!TpaRequestsContainer.isThereRequest(to.getUuid(), from.getUuid())) {
            from.sendMessage(Translations.TPA_REQUEST_NOT_FOUND_FAIL, false);
            return 1;
        }
        TpaRequestsContainer.removeRequest(to.getUuid(), from.getUuid());
        from.sendMessage(Translations.getTpaRequestDeclinedTargetText(to.getNameForScoreboard()), false);
        to.sendMessage(Translations.getTpaRequestDeclinedSenderText(from.getNameForScoreboard()), false);
        return 0;
    }
    private static int executeTpaAcceptCommand(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ServerPlayerEntity from = source.getSource().getPlayer();
        ServerPlayerEntity to = source.getArgument("대상", EntitySelector.class).getPlayer(source.getSource());

        if (!TpaRequestsContainer.isThereRequest(to.getUuid(), from.getUuid())) {
            from.sendMessage(Translations.TPA_REQUEST_NOT_FOUND_FAIL, false);
            return 1;
        } else if (No.isPvp(from)) {
            from.sendMessage(Translations.TPA_ACCEPT_IN_PVP_FAIL, false);
            return 1;
        } else if (No.isPvp(to)) {
            from.sendMessage(Translations.TPA_ACCEPT_TARGET_IN_PVP_FAIL, false);
            return 1;
        }
        TpaRequestsContainer.removeRequest(to.getUuid(), from.getUuid());
        from.sendMessage(Translations.getTpaRequestAcceptedTargetText(to.getNameForScoreboard()), false);
        to.sendMessage(Translations.getTpaRequestAcceptedSenderText(from.getNameForScoreboard()), false);
        to.teleport(from.getX(), from.getY(), from.getZ(), false);
        to.networkHandler.syncWithPlayerPosition();
        return 0;
    }
    private static int executeTpaCancelCommand(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ServerPlayerEntity from = source.getSource().getPlayer();
        ServerPlayerEntity to = source.getArgument("대상", EntitySelector.class).getPlayer(source.getSource());

        if (!TpaRequestsContainer.isThereRequest(to.getUuid(), from.getUuid())) {
            from.sendMessage(Translations.TPA_REQUEST_NOT_FOUND_FAIL, false);
            return 1;
        }
        TpaRequestsContainer.removeRequest(to.getUuid(), from.getUuid());
        from.sendMessage(Translations.getTpaRequestCancelledTargetText(to.getNameForScoreboard()), false);
        to.sendMessage(Translations.getTpaRequestCancelledSenderText(from.getNameForScoreboard()), false);
        return 0;
    }
}

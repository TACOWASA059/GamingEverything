package com.github.tacowasa059.gamingeverything.fabric;

import com.github.tacowasa059.gamingeverything.client.GamingClient;
import com.github.tacowasa059.gamingeverything.client.GamingCommands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class GamingEverythingFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMapping key = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.gamingeverything.settings", GLFW.GLFW_KEY_G, "key.categories.gamingeverything"));
        GamingClient.initialize(key);
        ClientTickEvents.END_CLIENT_TICK.register(GamingClient::tick);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("gaming")
                        .executes(context -> GamingCommands.open())
                        .then(ClientCommandManager.literal("on").executes(context -> GamingCommands.set("master", true)))
                        .then(ClientCommandManager.literal("off").executes(context -> GamingCommands.set("master", false)))
                        .then(ClientCommandManager.literal("toggle").executes(context -> GamingCommands.toggle("master")))
                        .then(category("master"))
                        .then(category("blocks"))
                        .then(category("sky"))
                        .then(category("entities"))
                        .then(category("hands"))
                        .then(category("items"))
                        .then(category("gui"))
                        .then(category("particles"))
                        .then(category("hud"))
                        .then(category("special"))));
    }

    private static LiteralArgumentBuilder<FabricClientCommandSource> category(String name) {
        return ClientCommandManager.literal(name)
                .executes(context -> GamingCommands.toggle(name))
                .then(ClientCommandManager.literal("on").executes(context -> GamingCommands.set(name, true)))
                .then(ClientCommandManager.literal("off").executes(context -> GamingCommands.set(name, false)))
                .then(ClientCommandManager.literal("toggle").executes(context -> GamingCommands.toggle(name)));
    }
}

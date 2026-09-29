package com.github.tacowasa059.gamingeverything.fabric;

import com.github.tacowasa059.gamingeverything.client.GamingClient;
import com.github.tacowasa059.gamingeverything.client.GamingCommands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class GamingEverythingFabric implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("gamingeverything", "main"));

    @Override
    public void onInitializeClient() {
        KeyMapping key = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.gamingeverything.settings", GLFW.GLFW_KEY_G, CATEGORY));
        GamingClient.initialize(key);
        ClientTickEvents.END_CLIENT_TICK.register(GamingClient::tick);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("gaming")
                        .executes(context -> GamingCommands.open())
                        .then(ClientCommands.literal("on").executes(context -> GamingCommands.set("master", true)))
                        .then(ClientCommands.literal("off").executes(context -> GamingCommands.set("master", false)))
                        .then(ClientCommands.literal("toggle").executes(context -> GamingCommands.toggle("master")))
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
        return ClientCommands.literal(name)
                .executes(context -> GamingCommands.toggle(name))
                .then(ClientCommands.literal("on").executes(context -> GamingCommands.set(name, true)))
                .then(ClientCommands.literal("off").executes(context -> GamingCommands.set(name, false)))
                .then(ClientCommands.literal("toggle").executes(context -> GamingCommands.toggle(name)));
    }
}

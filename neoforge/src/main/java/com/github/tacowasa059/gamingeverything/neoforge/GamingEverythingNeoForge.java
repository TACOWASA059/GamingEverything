package com.github.tacowasa059.gamingeverything.neoforge;

import com.github.tacowasa059.gamingeverything.GamingEverything;
import com.github.tacowasa059.gamingeverything.client.GamingClient;
import com.github.tacowasa059.gamingeverything.client.GamingCommands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@Mod(value = GamingEverything.MOD_ID, dist = Dist.CLIENT)
public final class GamingEverythingNeoForge {
    private KeyMapping settingsKey;

    public GamingEverythingNeoForge(IEventBus modBus) {
        modBus.addListener(this::registerKeys);
        NeoForge.EVENT_BUS.addListener(this::clientTick);
        NeoForge.EVENT_BUS.addListener(this::registerClientCommands);
    }

    private void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("gaming")
                .executes(context -> GamingCommands.open())
                .then(Commands.literal("on").executes(context -> GamingCommands.set("master", true)))
                .then(Commands.literal("off").executes(context -> GamingCommands.set("master", false)))
                .then(Commands.literal("toggle").executes(context -> GamingCommands.toggle("master")))
                .then(category("master")).then(category("blocks")).then(category("sky"))
                .then(category("entities")).then(category("hands")).then(category("items"))
                .then(category("gui")).then(category("particles")).then(category("hud"))
                .then(category("special")));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> category(String name) {
        return Commands.literal(name)
                .executes(context -> GamingCommands.toggle(name))
                .then(Commands.literal("on").executes(context -> GamingCommands.set(name, true)))
                .then(Commands.literal("off").executes(context -> GamingCommands.set(name, false)))
                .then(Commands.literal("toggle").executes(context -> GamingCommands.toggle(name)));
    }

    private void registerKeys(RegisterKeyMappingsEvent event) {
        settingsKey = new KeyMapping("key.gamingeverything.settings", GLFW.GLFW_KEY_G,
                "key.categories.gamingeverything");
        event.register(settingsKey);
        GamingClient.initialize(settingsKey);
    }

    private void clientTick(ClientTickEvent.Post event) {
        if (settingsKey != null) GamingClient.tick(Minecraft.getInstance());
    }
}


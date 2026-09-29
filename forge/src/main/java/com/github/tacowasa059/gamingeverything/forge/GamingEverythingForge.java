package com.github.tacowasa059.gamingeverything.forge;

import com.github.tacowasa059.gamingeverything.GamingEverything;
import com.github.tacowasa059.gamingeverything.client.GamingClient;
import com.github.tacowasa059.gamingeverything.client.GamingCommands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(GamingEverything.MOD_ID)
public final class GamingEverythingForge {
    private KeyMapping settingsKey;

    public GamingEverythingForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::registerKeys);
        MinecraftForge.EVENT_BUS.addListener(this::clientTick);
        MinecraftForge.EVENT_BUS.addListener(this::registerClientCommands);
    }

    private void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("gaming")
                .executes(context -> GamingCommands.open())
                .then(Commands.literal("on").executes(context -> GamingCommands.set("master", true)))
                .then(Commands.literal("off").executes(context -> GamingCommands.set("master", false)))
                .then(Commands.literal("toggle").executes(context -> GamingCommands.toggle("master")))
                .then(category("master"))
                .then(category("blocks"))
                .then(category("sky"))
                .then(category("entities"))
                .then(category("hands"))
                .then(category("items"))
                .then(category("gui"))
                .then(category("particles"))
                .then(category("hud"))
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

    private void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && settingsKey != null) {
            GamingClient.tick(Minecraft.getInstance());
        }
    }
}

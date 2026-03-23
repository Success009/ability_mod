package com.ability.mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbilityModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ability-mod-client");
    
    // v82: Connect to the LOCAL GHOST IP
    // The Bridge will forward this to 100.89.137.102
    public static final String SERVER_IP = "100.127.127.127";
    public static final int SERVER_PORT = 25565;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Ability SMP v82 Split-Brain Client Initializing...");
    }

    public static void joinServer(Screen parent) {
        LOGGER.info("Connecting to Ability SMP at {}:{}", SERVER_IP, SERVER_PORT);
        connect(parent, SERVER_IP, SERVER_PORT);
    }

    private static void connect(Screen parent, String ip, int port) {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerAddress serverAddress = new ServerAddress(ip, port);
        ServerInfo serverInfo = new ServerInfo("Ability SMP", ip + ":" + port, ServerInfo.ServerType.OTHER);
        ConnectScreen.connect(parent, client, serverAddress, serverInfo, false, null);
    }
}

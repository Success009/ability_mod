package com.ability.bridge.commands;

import com.ability.bridge.AbilityBridge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeCommand implements CommandExecutor, TabCompleter {

    private final AbilityBridge plugin;
    private final LegacyComponentSerializer legacySerializer;

    public BridgeCommand(AbilityBridge plugin) {
        this.plugin = plugin;
        this.legacySerializer = LegacyComponentSerializer.legacyAmpersand();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("bridge.admin")) {
            sender.sendMessage(legacySerializer.deserialize("&cYou do not have permission to use this command."));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reloadConfig();
            sender.sendMessage(legacySerializer.deserialize(plugin.getConfigManager().getPluginPrefix() + "&aConfiguration reloaded!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("restart")) {
            sender.sendMessage(legacySerializer.deserialize(plugin.getConfigManager().getPluginPrefix() + "&eRestarting plugin systems..."));
            plugin.reloadPlugin();
            sender.sendMessage(legacySerializer.deserialize(plugin.getConfigManager().getPluginPrefix() + "&aPlugin systems restarted!"));
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(legacySerializer.deserialize("&b&lAbility Plugin Commands:"));
        sender.sendMessage(legacySerializer.deserialize("&f/bridge reload &7- Reload the configuration"));
        sender.sendMessage(legacySerializer.deserialize("&f/bridge restart &7- Full restart of all plugin systems"));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("reload");
            completions.add("restart");
            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}

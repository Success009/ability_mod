package com.ability.bridge.commands;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.managers.LinkManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LinkCommand implements CommandExecutor {

    private final AbilityBridge plugin;
    private final LinkManager linkManager;
    private final LegacyComponentSerializer legacySerializer;

    public LinkCommand(AbilityBridge plugin, LinkManager linkManager) {
        this.plugin = plugin;
        this.linkManager = linkManager;
        this.legacySerializer = LegacyComponentSerializer.legacyAmpersand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players!", NamedTextColor.RED));
            return true;
        }

        // Generate 6-digit code
        String code = linkManager.generateLinkCode(player);

        List<String> linkMessage = plugin.getConfigManager().getLinkMessage();

        if (linkMessage == null || linkMessage.isEmpty()) {
            // Send default formatted message to player if config is empty
            player.sendMessage(Component.empty());
            player.sendMessage(Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.GRAY));
            player.sendMessage(Component.text("  Account Linking", NamedTextColor.GOLD, TextDecoration.BOLD));
            player.sendMessage(Component.empty());
            player.sendMessage(Component.text("  Your 6-digit code: ", NamedTextColor.GRAY)
                    .append(Component.text(code, NamedTextColor.GREEN, TextDecoration.BOLD)));
            player.sendMessage(Component.empty());
            player.sendMessage(Component.text("  → Enter this code on the website", NamedTextColor.YELLOW));
            player.sendMessage(Component.text("  → Code expires in 5 minutes", NamedTextColor.YELLOW));
            player.sendMessage(Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.GRAY));
            player.sendMessage(Component.empty());
        } else {
            for (String line : linkMessage) {
                player.sendMessage(legacySerializer.deserialize(line.replace("{code}", code)));
            }
        }

        return true;
    }
}

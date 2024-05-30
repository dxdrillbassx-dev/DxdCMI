package com.example.dxdcmi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    private final Main plugin;

    public DelHomeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(plugin.getMessage("specify_home_name"));
            return true;
        }

        String homeName = args[0];
        if (!plugin.getPluginConfig().contains("homes." + player.getName() + "." + homeName)) {
            player.sendMessage(plugin.getMessage("home_not_set").replace("{home}", homeName));
            return true;
        }

        plugin.getPluginConfig().set("homes." + player.getName() + "." + homeName, null);
        plugin.saveConfig();
        player.sendMessage(plugin.getMessage("home_removed").replace("{home}", homeName));
        return true;
    }
}

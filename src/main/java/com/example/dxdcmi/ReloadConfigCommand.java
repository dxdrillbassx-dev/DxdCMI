package com.example.dxdcmi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand implements CommandExecutor {

    private final Main plugin;

    public ReloadConfigCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("cmilike.reloadconfig")) {
            sender.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage(plugin.getMessage("config_reloaded"));
        return true;
    }
}

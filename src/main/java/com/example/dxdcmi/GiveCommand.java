package com.example.dxdcmi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements CommandExecutor {

    private final Main plugin;

    public GiveCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("cmilike.give")) {
            sender.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(plugin.getMessage("give_usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(plugin.getMessage("player_not_found").replace("{player}", args[0]));
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getMessage("invalid_amount"));
            return true;
        }

        if (amount <= 0) {
            sender.sendMessage(plugin.getMessage("positive_amount"));
            return true;
        }

        plugin.getPluginConfig().set("economy." + target.getName(), plugin.getPluginConfig().getDouble("economy." + target.getName()) + amount);
        plugin.saveConfig();

        sender.sendMessage(plugin.getMessage("money_given").replace("{amount}", String.valueOf(amount)).replace("{player}", target.getName()));
        target.sendMessage(plugin.getMessage("money_received").replace("{amount}", String.valueOf(amount)).replace("{player}", sender.getName()));

        return true;
    }
}

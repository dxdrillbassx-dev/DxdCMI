package com.example.dxdcmi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBalanceCommand implements CommandExecutor {

    private final Main plugin;

    public SetBalanceCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("cmilike.setbalance")) {
            sender.sendMessage(plugin.getMessage("no_permission"));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(plugin.getMessage("setbalance_usage"));
            return true;
        }

        String playerName = args[0];
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getMessage("invalid_amount"));
            return true;
        }

        if (amount < 0) {
            sender.sendMessage(plugin.getMessage("positive_amount"));
            return true;
        }

        plugin.getPluginConfig().set("economy." + playerName, amount);
        plugin.saveConfig();

        sender.sendMessage(plugin.getMessage("balance_set").replace("{player}", playerName).replace("{amount}", String.valueOf(amount)));
        return true;
    }
}

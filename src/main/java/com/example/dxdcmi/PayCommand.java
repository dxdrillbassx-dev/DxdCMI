package com.example.dxdcmi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final Main plugin;

    public PayCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 2) {
            player.sendMessage(plugin.getMessage("pay_usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(plugin.getMessage("player_not_found").replace("{player}", args[0]));
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getMessage("invalid_amount"));
            return true;
        }

        if (amount <= 0) {
            player.sendMessage(plugin.getMessage("positive_amount"));
            return true;
        }

        double playerBalance = plugin.getPluginConfig().getDouble("economy." + player.getName());
        if (playerBalance < amount) {
            player.sendMessage(plugin.getMessage("insufficient_funds_pay"));
            return true;
        }

        plugin.getPluginConfig().set("economy." + player.getName(), playerBalance - amount);
        plugin.getPluginConfig().set("economy." + target.getName(), plugin.getPluginConfig().getDouble("economy." + target.getName()) + amount);
        plugin.saveConfig();

        player.sendMessage(plugin.getMessage("payment_sent").replace("{amount}", String.valueOf(amount)).replace("{player}", target.getName()));
        target.sendMessage(plugin.getMessage("payment_received").replace("{amount}", String.valueOf(amount)).replace("{player}", player.getName()));

        return true;
    }
}

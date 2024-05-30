package com.example.dxdcmi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private final Main plugin;

    public BalanceCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;
        double balance = plugin.getPluginConfig().getDouble("economy." + player.getName());
        player.sendMessage(plugin.getMessage("balance").replace("{balance}", String.valueOf(balance)));
        return true;
    }
}

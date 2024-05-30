package com.example.dxdcmi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ListHomesCommand implements CommandExecutor {

    private final Main plugin;

    public ListHomesCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;
        List<Location> playerHomes = plugin.getHomeLocations(player);
        if (playerHomes == null || playerHomes.isEmpty()) {
            player.sendMessage(plugin.getMessage("no_homes_set"));
            return true;
        }

        player.sendMessage(plugin.getMessage("homes_list_prefix"));
        for (Location homeLocation : playerHomes) {
            player.sendMessage(ChatColor.GREEN + "- " + homeLocation.getWorld().getName() + ": " +
                    homeLocation.getBlockX() + ", " + homeLocation.getBlockY() + ", " + homeLocation.getBlockZ());
        }

        return true;
    }
}

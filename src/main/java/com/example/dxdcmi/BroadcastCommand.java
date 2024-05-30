package com.example.dxdcmi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
            return true;
        }

        String message = String.join(" ", args);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        return true;
    }
}

package com.example.dxdcmi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddHomeCommand implements CommandExecutor {

    private final Main plugin;

    public AddHomeCommand(Main plugin) {
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
        if (plugin.getPluginConfig().contains("homes." + player.getName() + "." + homeName)) {
            player.sendMessage(plugin.getMessage("home_already_set").replace("{home}", homeName));
            return true;
        }

        // Проверяем, существует ли раздел "homes" для игрока
        if (plugin.getPluginConfig().contains("homes." + player.getName())) {
            // Проверяем, достигнуто ли максимальное количество домов
            if (plugin.getPluginConfig().getConfigurationSection("homes." + player.getName()).getKeys(false).size() >= plugin.getMaxHomes()) {
                player.sendMessage(plugin.getMessage("max_homes_reached"));
                return true;
            }
        } else {
            // Если раздел не существует, создаем его
            plugin.getPluginConfig().createSection("homes." + player.getName());
        }

        // Устанавливаем дом для игрока и сохраняем конфигурацию
        plugin.getPluginConfig().set("homes." + player.getName() + "." + homeName, player.getLocation());
        plugin.saveConfig();
        player.sendMessage(plugin.getMessage("home_set").replace("{home}", homeName));
        return true;
    }
}

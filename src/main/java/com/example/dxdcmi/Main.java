package com.example.dxdcmi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        getLogger().info("CMILike Plugin Enabled");
        saveDefaultConfig();
        config = getConfig();
        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("CMILike Plugin Disabled");
    }

    private void registerCommands() {
        getCommand("addhome").setExecutor(new AddHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("delhome").setExecutor(new DelHomeCommand(this));
        getCommand("listhomes").setExecutor(new ListHomesCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("reloadconfig").setExecutor(new ReloadConfigCommand(this));
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("give").setExecutor(new GiveCommand(this));
        getCommand("setbalance").setExecutor(new SetBalanceCommand(this));
        getCommand("shop").setExecutor(new ShopCommand());
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }

    public String getMessage(String key) {
        if (config.contains(key)) {
            return config.getString(key);
        } else {
            return "Message not found!";
        }
    }

    public int getMaxHomes() {
        return config.getInt("settings.max_homes", 5); // Возвращаем значение из конфига, если нету, возвращаем 5 по умолчанию
    }

    public List<Location> getHomeLocations(Player player) {
        List<Location> homeLocations = new ArrayList<>();
        String playerName = player.getName(); // Получаем имя игрока
        var section = config.getConfigurationSection("homes." + playerName);

        if (section != null)
            for (String key : section.getKeys(false)) {
                var homeSection = section.getConfigurationSection(key);
                if (homeSection != null)
                    homeLocations.add(new Location(
                            Bukkit.getWorld(homeSection.getString("world", "world")),
                            homeSection.getDouble("x", 0D),
                            homeSection.getDouble("y", 0D),
                            homeSection.getDouble("z", 0D),
                            (float) homeSection.getDouble("yaw"),
                            (float) homeSection.getDouble("pitch")
                    ));
            }
        return homeLocations;
    }
}

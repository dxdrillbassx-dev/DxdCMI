package com.example.dxdcmi;

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
        Set<String> keys = config.getConfigurationSection("homes." + playerName).getKeys(false);

        for (String key : keys) {
            double x = config.getDouble("homes." + playerName + "." + key + ".x");
            double y = config.getDouble("homes." + playerName + "." + key + ".y");
            double z = config.getDouble("homes." + playerName + "." + key + ".z");
            float yaw = (float) config.getDouble("homes." + playerName + "." + key + ".yaw");
            float pitch = (float) config.getDouble("homes." + playerName + "." + key + ".pitch");
            String worldName = config.getString("homes." + playerName + "." + key + ".world");

            Location location = new Location(getServer().getWorld(worldName), x, y, z, yaw, pitch);
            homeLocations.add(location);
        }

        return homeLocations;
    }
}

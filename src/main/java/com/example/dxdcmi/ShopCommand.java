package com.example.dxdcmi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        Inventory shopInventory = Bukkit.createInventory(player, 27, "Shop");

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
        diamondSwordMeta.setDisplayName("§bDiamond Sword");
        List<String> diamondSwordLore = new ArrayList<>();
        diamondSwordLore.add("§7Price: §e10 coins");
        diamondSwordMeta.setLore(diamondSwordLore);
        diamondSword.setItemMeta(diamondSwordMeta);

        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta goldenAppleMeta = goldenApple.getItemMeta();
        goldenAppleMeta.setDisplayName("§6Golden Apple");
        List<String> goldenAppleLore = new ArrayList<>();
        goldenAppleLore.add("§7Price: §e5 coins");
        goldenAppleMeta.setLore(goldenAppleLore);
        goldenApple.setItemMeta(goldenAppleMeta);

        shopInventory.setItem(11, diamondSword);
        shopInventory.setItem(15, goldenApple);

        player.openInventory(shopInventory);

        return true;
    }
}

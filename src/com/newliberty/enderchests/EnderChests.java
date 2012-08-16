package com.newliberty.enderchests;

import java.io.*;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderChests extends JavaPlugin {
	public static EnderChests plugin;
	
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		plugin = this;
		
		PluginDescriptionFile pdfFile = getDescription();
		this.getLogger().info(pdfFile.getName() + ", Version " + pdfFile.getVersion() + ", Has Been Enabled!");
	}
	
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.getLogger().info(pdfFile.getName() + ", Version "
				+ pdfFile.getVersion() + ", Has Been Disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		//TODO: Create command "/ecp list <player>" with permission enderchests.admin
		return false;
	
	}
	
	/**
	 * @author OstlerDev
	 * 
	 * This method saves the enderchest to a yml file called x,y,z.yml
	 * it should be called when a player exits an ender chest.
	 * @throws IOException 
	 */
	public void saveEnderChest(Location loc, Inventory inventory, Player p){
		File packFile = getPackFile(loc);
		FileConfiguration packConfig = YamlConfiguration.loadConfiguration(packFile);

        packConfig.set("inventory", inventory.getContents());
        packConfig.set("owner", p.getDisplayName());
        try {
            packConfig.save(packFile);
        } catch (IOException e) {
            this.getLogger().info("Failed to save inventory...");
        }
    }

	/**
	 * @author OstlerDev
	 * 
	 * @This method loads the enderchest from a yml file called x,y,z.yml
	 * it should be called when a player opens an ender chest.
	 * 
	 * @throws ClassNotFoundException 
	 */
	public Inventory loadEnderChest(Location loc, Inventory inventory, Player p){
		Inventory inv = inventory;
		File packFile = getPackFile(loc);
		if (packFile.exists()){
        FileConfiguration packConfig = YamlConfiguration.loadConfiguration(packFile);
        List<?> list = packConfig.getList("inventory");
        if (list != null) {
            for (int i = 0; i < Math.min(list.size(), inv.getSize()); i += 1) {
                inv.setItem(i, (ItemStack)list.get(i));
            }
        }
    }
		return inv;
	}

	public static File getPackFile(Location loc) {
        return new File(plugin.getDataFolder(), loc.toString() + ".yml"); // TODO: formatting, 0000
    }
}

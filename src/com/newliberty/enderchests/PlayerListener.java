package com.newliberty.enderchests;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class PlayerListener implements Listener{
	static EnderChests plugin;
	PlayerListener(EnderChests instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) throws IOException{
		if (e.getBlock().getType() == Material.ENDER_CHEST){
			Inventory inventory = Bukkit.createInventory(e.getPlayer(), 27, "ProtectedEnderChest");
			plugin.saveEnderChest(e.getBlock().getLocation(), inventory, e.getPlayer());
			InventoryView view = e.getPlayer().openInventory(inventory);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void playerInteractEvent(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		if (block.getType() == Material.ENDER_CHEST && Action.RIGHT_CLICK_BLOCK == event.getAction()){
			openPack(event.getPlayer(), 27, block);
			event.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
		plugin.getLogger().info("Event Happened");
        String title = event.getView().getTitle();
        plugin.getLogger().info(title);
        if (title.startsWith("Protected")) {
            Inventory inventory = event.getInventory();
            plugin.getLogger().info("Title does start with that!");
            Block b = event.getPlayer().getTargetBlock(null, 5);
            Location loc = b.getLocation();

            // Note: broken on 1.2.3-R0.2. Works on 1.2.4 snapshots: craftbukkit-1.2.4-R0.1-20120325.235512-21.jar
            plugin.saveEnderChest(loc, inventory, (Player)event.getPlayer());
        } else {
        	plugin.getLogger().info(title);
        }
    }

    public static void openPack(Player player, int numSlots, Block b) {
        Inventory inventory = Bukkit.createInventory(player, numSlots, "ProtectedEnderChest");
 
        inventory = plugin.loadEnderChest(b.getLocation(), inventory, player);
        
        plugin.getLogger().info(player.getDisplayName() + inventory.getTitle());

        InventoryView view = player.openInventory(inventory);
        // will be saved on close
    }
}

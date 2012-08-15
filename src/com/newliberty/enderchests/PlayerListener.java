package com.newliberty.enderchests;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{
	static EnderChests plugin;
	PlayerListener(EnderChests instance){
		instance = plugin;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) throws IOException{
		if (e.getBlock().getType() == Material.ENDER_CHEST){
			Inventory inventory = Bukkit.createInventory(e.getPlayer(), 27, "ProtectedEnderChest");
			//plugin.saveEnderChest(e.getBlock().getLocation(), inventory, e.getPlayer());
			InventoryView view = e.getPlayer().openInventory(inventory);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		if (block.getType() == Material.ENDER_CHEST){
			openPack(event.getPlayer(), 27);
		}
	}
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
    public void onInventoryClose(InventoryCloseEvent event) throws IOException {
        if (event.getViewers() == null) {
            return;
        }

        // Anytime rearrange inventory, have to check if moved to/from chestplate/hand/other
        for (HumanEntity viewer: event.getViewers()) {
            if (!(viewer instanceof Player)) {
                continue;
            }
        }

        // Save pack on close
        if (event.getView() == null || event.getView().getTitle() == null) {
            return;
        }
        String title = event.getView().getTitle();
        if (title.startsWith("ProtectedEnderChest")) {
            Inventory inventory = event.getInventory();

            // Note: broken on 1.2.3-R0.2. Works on 1.2.4 snapshots: craftbukkit-1.2.4-R0.1-20120325.235512-21.jar
            plugin.saveEnderChest(event.getPlayer().getEyeLocation(), inventory, (Player)event.getPlayer());
        }
    }
	private void openPack(Player player, ItemStack item) {
        int numSlots = 27;

        openPack(player, numSlots);
    }

    public static void openPack(Player player, int numSlots) {
        Inventory inventory = Bukkit.createInventory(player, numSlots, "ProtectedEnderChest");

        inventory = plugin.loadEnderChest(player.getEyeLocation(), inventory, player);

        InventoryView view = player.openInventory(inventory);
        // will be saved on close
    }
}

package com.newliberty.enderchests;

import java.io.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.EnderChest;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderChests extends JavaPlugin {
	
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.getLogger().info(pdfFile.getName() + ", Version "
				+ pdfFile.getVersion() + ", Has Been Enabled!");
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
	 */
	public void saveEnderChest(EnderChest chest, Player p){
		File f = new File(this.getDataFolder() + "/" + p.getEyeLocation().getBlockX() + "," + p.getEyeLocation().getBlockY() + "," + p.getEyeLocation().getBlockZ());
		try{
			//TODO: Test this method!
			  // Create file 
			  ObjectOutput out = new ObjectOutputStream(new FileOutputStream(f));
		      out.writeObject(chest);
		      out.close();
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
				  this.getLogger().info("[EnderProtect] Failed to save chest at" + p.getEyeLocation().getBlockX() + "," + p.getEyeLocation().getBlockY() + "," + p.getEyeLocation().getBlockZ());
			  }
	}
	
	/**
	 * @author OstlerDev
	 * 
	 * This method loads the enderchest from a yml file called x,y,z.yml
	 * it should be called when a player opens an ender chest.
	 * 
	 * @throws ClassNotFoundException 
	 */
	public void loadEnderChest(EnderChest chest, Player p) throws ClassNotFoundException{
		// Deserialize from a file
		File f = new File(this.getDataFolder() + "/" + p.getEyeLocation().getBlockX() + "," + p.getEyeLocation().getBlockY() + "," + p.getEyeLocation().getBlockZ());
	    ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(f));
	    // Deserialize the object
	    chest = (EnderChest) in.readObject();
	    in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

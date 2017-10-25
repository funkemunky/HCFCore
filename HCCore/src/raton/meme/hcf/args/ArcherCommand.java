package raton.meme.hcf.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import raton.meme.hcf.HCF;

public class ArcherCommand implements CommandExecutor{

	private HCF hcf;
	public ArcherCommand(HCF hcf) {
		this.hcf = hcf;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission(cmd.getPermission())) {
			sender.sendMessage(cmd.getPermissionMessage());
			return true;
		}
		
		if(args.length == 0) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(!hcf.getConfig().contains("Archer")) {
					p.sendMessage(ChatColor.RED + "Archer kit was not set yet!");
					return true;
				}
				ItemStack[] content = ((List<ItemStack>) hcf.getConfig().getList("Archer.Contents")).toArray(new ItemStack[0]);
				ItemStack[] armor = ((List<ItemStack>) hcf.getConfig().getList("Archer.Armor")).toArray(new ItemStack[0]);
				p.getInventory().setContents(content);
				p.getInventory().setArmorContents(armor);
				p.setHealth(20D);
				p.setFoodLevel(20);
				return true;
			}
			sender.sendMessage(ChatColor.RED.toString() + "Only players may use this feature!");
		} else {
			if(args[0].equalsIgnoreCase("set")) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					hcf.getConfig().set("Archer.Contents", p.getInventory().getContents());
					hcf.getConfig().set("Archer.Armor", p.getInventory().getArmorContents());
					hcf.saveConfig();
					p.sendMessage(ChatColor.GREEN + "Set the archer class for /archer!");
				} else {
					sender.sendMessage(ChatColor.RED.toString() + "Only players may use this feature!");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("give")) {
				if(args.length != 2) {
					sender.sendMessage(ChatColor.RED + "Invalid arguments! Usage: /archer give <player>");
					return true;
				}
				Player player = Bukkit.getPlayer(args[1]);
				if(player != null) {
					ItemStack[] content = ((List<ItemStack>) hcf.getConfig().getList("Archer.Contents")).toArray(new ItemStack[0]);
					ItemStack[] armor = ((List<ItemStack>) hcf.getConfig().getList("Archer.Armor")).toArray(new ItemStack[0]);
					player.getInventory().setContents(content);
					player.getInventory().setArmorContents(armor);
					player.setHealth(20D);
					player.setFoodLevel(20);
					sender.sendMessage(ChatColor.GREEN.toString() + "Gave " + player.getName() + " the archer class!");
					player.sendMessage(ChatColor.YELLOW + "You were given the archer class.");
				} else {
					sender.sendMessage(ChatColor.RED.toString() + "The player '" + args[1] + "' is currently not online!");
				}
				return true;
			}
			sender.sendMessage(ChatColor.RED + "Invalid arguments! Do /archer <give/set> <args>");
		}
		return true;
	}

}

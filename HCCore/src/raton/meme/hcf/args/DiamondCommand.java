package raton.meme.hcf.args;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import raton.meme.hcf.HCF;

public class DiamondCommand implements CommandExecutor {

	private HCF hcf;
	public DiamondCommand(HCF hcf) {
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
				if(!hcf.getConfig().contains("Diamond")) {
					p.sendMessage(ChatColor.RED + "Diamond kit was not set yet!");
					return true;
				}
				ItemStack[] content = ((List<ItemStack>) hcf.getConfig().getList("Diamond.Contents")).toArray(new ItemStack[0]);
				ItemStack[] armor = ((List<ItemStack>) hcf.getConfig().getList("Diamond.Armor")).toArray(new ItemStack[0]);
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
					hcf.getConfig().set("Diamond.Contents", p.getInventory().getContents());
					hcf.getConfig().set("Diamond.Armor", p.getInventory().getArmorContents());
					hcf.saveConfig();
					p.sendMessage(ChatColor.GREEN + "Set the Diamond class for /diamond!");
				} else {
					sender.sendMessage(ChatColor.RED.toString() + "Only players may use this feature!");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("give")) {
				if(args.length != 2) {
					sender.sendMessage(ChatColor.RED + "Invalid arguments! Usage: /diamond give <player>");
					return true;
				}
				Player player = Bukkit.getPlayer(args[1]);
				if(player != null) {
					ItemStack[] content = ((List<ItemStack>) hcf.getConfig().getList("Diamond.Contents")).toArray(new ItemStack[0]);
					ItemStack[] armor = ((List<ItemStack>) hcf.getConfig().getList("Diamond.Armor")).toArray(new ItemStack[0]);
					player.getInventory().setContents(content);
					player.getInventory().setArmorContents(armor);
					player.setHealth(20D);
					player.setFoodLevel(20);
					sender.sendMessage(ChatColor.GREEN.toString() + "Gave " + player.getName() + " the Diamond class!");
					player.sendMessage(ChatColor.YELLOW + "You were given the Diamond class.");
				} else {
					sender.sendMessage(ChatColor.RED.toString() + "The player '" + args[1] + "' is currently not online!");
				}
				return true;
			}
			sender.sendMessage(ChatColor.RED + "Invalid arguments! Do /diamond <give/set> <args>");
		}
		return true;
	}

}

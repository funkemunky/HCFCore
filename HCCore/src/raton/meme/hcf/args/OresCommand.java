package raton.meme.hcf.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import raton.meme.hcf.HCF;

public class OresCommand implements CommandExecutor {

	private HCF hcf;
	public OresCommand(HCF hcf) {
		this.hcf = hcf;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");
			return true;
		}
		if(args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <player>");
			return true;
		}
		Player p = (Player) sender;
		if(args.length == 0) {
			String uuid = p.getUniqueId().toString();
			if(hcf.getConfig().contains(uuid)) {
				p.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
				p.sendMessage(ChatColor.YELLOW + "Your Ores:");
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " * " + ChatColor.GRAY + ChatColor.BOLD + "Stone: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Stone"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " * " + ChatColor.DARK_GRAY + "Coal: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Coal"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " * " + ChatColor.BLUE + "Lapis: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Lapis"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " * " + ChatColor.RED + "Redstone: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Redstone"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " * " + ChatColor.GRAY + "Iron: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Iron"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GOLD + "Gold: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Gold"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.AQUA + "Diamond: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Diamond"));
				p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GREEN + "Emeraid: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Emerald"));
				p.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
			} else {
				p.sendMessage(ChatColor.RED + "You haven't mined anything yet!");
			}
		} else {
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null) {
					target = (Player) Bukkit.getOfflinePlayer(args[0]);
				}
				String uuid = target.getUniqueId().toString();
				if(hcf.getConfig().contains(uuid)) {
					p.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
					p.sendMessage(ChatColor.YELLOW + target.getName() + "'s Ores:");
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GRAY + ChatColor.BOLD + "Stone: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Stone"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.DARK_GRAY + "Coal: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Coal"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.BLUE + "Lapis: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Lapis"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.RED + "Redstone: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Redstone"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GRAY + "Iron: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Iron"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GOLD + "Gold: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Gold"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.AQUA + "Diamond: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Diamond"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.GREEN + "Emeraid: " + ChatColor.WHITE + hcf.getConfig().getInt(uuid + ".Mined.Emerald"));
					p.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD +  " * " + ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
				} else {
					p.sendMessage(ChatColor.RED + target.getName() + " hasn't mined anything yet!");
				}
			}
		}
		return true;
	}

}

package raton.meme.hcf.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import raton.meme.hcf.HCF;

public class GlowstoneMountain implements CommandExecutor {

	private HCF hcf;
	public GlowstoneMountain(HCF hcf) {
		this.hcf = hcf;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("command.glowstonemountain")) {
			sender.sendMessage(ChatColor.RED + "You don not have permission for this command.");
			return true;
		}
		if(args.length == 0) {
			sender.sendMessage(ChatColor.YELLOW + "You have reset Glowstone Mountain!");
			hcf.resetGlowstoneMountain();
		}
		return true;
	}

}

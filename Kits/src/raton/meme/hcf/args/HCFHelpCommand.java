package raton.meme.hcf.args;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.doctordark.utils.BukkitUtils;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class HCFHelpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m------------------------------------------- "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lHCRiots &8» &cFactions Help"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUseful Commands"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &c/Report {name} {reason}&7: &fReport a Player"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &c/Helpop {request}&7: &fRequest Help"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &c/Mapkit&7: &fView the Kit for this Map"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &c/Coords&7: &fView important locations"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUseful Links"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cTeamSpeak&7: &fts.hcriots.net"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cStore&7: &fstore.hcriots.net"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cWebsite&7: &fwww.hcriots.com"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m------------------------------------------- "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
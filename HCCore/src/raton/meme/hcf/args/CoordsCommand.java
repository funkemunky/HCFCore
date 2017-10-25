package raton.meme.hcf.args;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.doctordark.utils.BukkitUtils;

public class CoordsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
		p.sendMessage("§8§m----------------------------------");
		p.sendMessage("§4§lImportant Coordinates");
		p.sendMessage(" ");
		p.sendMessage("§7* §d§lCandy §7(KoTH) §8§ §f500, 500 §7(Overworld)");
		p.sendMessage("§7* §e§lTower §7(KoTH) §8§ §f-500, 500 §7(Overworld)");
		p.sendMessage("§7* §b§lShip §7(KoTH ) §8§ §f500, -500 §7(Overworld)");
		p.sendMessage("§7* §c§lMountain §7(KoTH) §8§ §f-500, -500 §7(Overworld)"); 
		p.sendMessage("§7* §6§lGlowstone Mountain §8§ §f-500, 500 §7(Nether)");
		p.sendMessage("§7* §9§lEnd §7(KoTH) §8§ §f11, 8 §7(End)");
		p.sendMessage(" ");
		p.sendMessage("§7* §3§lEnd Portals §8§ §f750 750 §7(All Quadrants)");
		p.sendMessage("§8§m----------------------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
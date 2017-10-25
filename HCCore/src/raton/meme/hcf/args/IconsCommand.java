package raton.meme.hcf.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.doctordark.utils.other.chat.ClickAction;
import com.doctordark.utils.other.chat.Text;

public class IconsCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        Player p = (Player) sender;
        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        if (c.getName().equalsIgnoreCase("icons")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "/icons pick");
                new Text("[Reset Icon]").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/pex user " + p.getName() + " prefix \"\"").send(p);
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("pick")) {
                    sender.sendMessage(ChatColor.GRAY + "Please select an icon:");
                    new Text("✪").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor").send(p);
                    new Text("✞").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor").send(p);
                    new Text("✘").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick xcolor").send(p);
                    new Text("♫").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick musiccolor").send(p);
                    new Text("✈").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick planecolor").send(p);
                    new Text("✿").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick flowercolor").send(p);
                    new Text("♀").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick egirlcolor").send(p);
                    new Text("♂").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick eboycolor").send(p);
                    return true;
                }
            }
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("starcolor")) {
                    sender.sendMessage(ChatColor.GRAY + "Selected Tag (✪) Choose Color:");
                    new Text("█").setColor(ChatColor.RED).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc1").send(p);
                    new Text("█").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc2").send(p);
                    new Text("█").setColor(ChatColor.WHITE).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc3").send(p);
                    new Text("█").setColor(ChatColor.AQUA).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc4").send(p);
                    new Text("█").setColor(ChatColor.YELLOW).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc5").send(p);
                    new Text("█").setColor(ChatColor.LIGHT_PURPLE).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc6").send(p);
                    new Text("█").setColor(ChatColor.GOLD).setClick(ClickAction.RUN_COMMAND, "/icons pick starcolor starc7").send(p);
                    return true;
                }
                if (args[1].equalsIgnoreCase("crosscolor")) {
                    sender.sendMessage(ChatColor.GRAY + "Selected Tag (✞) Choose Color:");
                    new Text("█").setColor(ChatColor.RED).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor1").send(p);
                    new Text("█").setColor(ChatColor.GREEN).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor2").send(p);
                    new Text("█").setColor(ChatColor.WHITE).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor3").send(p);
                    new Text("█").setColor(ChatColor.AQUA).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor4").send(p);
                    new Text("█").setColor(ChatColor.YELLOW).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor5").send(p);
                    new Text("█").setColor(ChatColor.LIGHT_PURPLE).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor6").send(p);
                    new Text("█").setColor(ChatColor.GOLD).setClick(ClickAction.RUN_COMMAND, "/icons pick crosscolor crosscolor7").send(p);
                    return true;
                }
            }

            if (args[2].equalsIgnoreCase("starc1")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.RED + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&c✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc2")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.GREEN + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&a✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc3")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.WHITE + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&f✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc4")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.AQUA + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&b✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc5")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.YELLOW + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&e✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc6")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.LIGHT_PURPLE + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&d✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("starc7")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.GOLD + "(✪)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&6✪");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor1")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.RED + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&c✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor2")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.GREEN + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&a✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor3")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.WHITE + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&f✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor4")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.AQUA + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&b✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor5")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.YELLOW + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&e✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor6")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.LIGHT_PURPLE + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&d✞");
                return true;
            }

            if (args[2].equalsIgnoreCase("crosscolor7")) {
                sender.sendMessage(ChatColor.GRAY + "Completed Icon: " + ChatColor.GOLD + "(✞)");
                Bukkit.getServer().dispatchCommand(cs, "pex user " + p.getName() + " prefix " +  "&6✞");
                return true;
            }
        }

        return false;
    }
}

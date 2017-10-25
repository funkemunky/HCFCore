package raton.meme.hcf.startoftheworld;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.doctordark.utils.BukkitUtils;
import com.doctordark.utils.JavaUtils;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import raton.meme.hcf.HCF;

public class SotwCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMPLETIONS = ImmutableList.of("start", "end");

    private final HCF plugin;

    public SotwCommand(HCF plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("Core.staff.advanced")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("start")) {
                if (args.length < 2) {
                    sender.sendMessage(
                            ChatColor.RED + "Usage: /" + label + " " + args[0].toLowerCase() + " <duration>");
                    return true;
                }

                long duration = JavaUtils.parse(args[1]);

                if (duration == -1L) {
                    sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is an invalid duration.");
                    return true;
                }

                if (duration < 1000L) {
                    sender.sendMessage(ChatColor.RED + "SOTW protection time must last for at least 20 ticks.");
                    return true;
                }

                SotwTimer.SotwRunnable sotwRunnable = plugin.getSotwTimer().getSotwRunnable();

                if (sotwRunnable != null) {
                    sender.sendMessage(
                            ChatColor.RED + "SOTW protection is already enabled, use /" + label + " cancel to end it.");
                    return true;
                }

                plugin.getSotwTimer().start(duration);
                sender.sendMessage(ChatColor.RED + "Started SOTW protection for "
                        + DurationFormatUtils.formatDurationWords(duration, true, true) + ".");
                return true;
            }

            if (args[0].equalsIgnoreCase("end") || args[0].equalsIgnoreCase("cancel")) {
                if (plugin.getSotwTimer().cancel()) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8&m--------------------------------"));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&cThe &e&lSOTW Timer &chas ended."));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&c&lGoodluck and cheers to a new map!"));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8&m--------------------------------"));
                    return true;
                }

                sender.sendMessage(ChatColor.RED + "SOTW protection is not active.");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <start|end>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 1 ? BukkitUtils.getCompletions(args, COMPLETIONS) : Collections.emptyList();
    }
}

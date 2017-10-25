package raton.meme.hcf.factionutils.args;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.doctordark.utils.JavaUtils;
import com.doctordark.utils.other.command.CommandArgument;

import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.type.Faction;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.ymls.SettingsYML;

/**
 * Faction argument used to create a new {@link Faction}.
 */
public class FactionCreateArgument extends CommandArgument {

    private final HCF plugin;

    public FactionCreateArgument(HCF plugin) {
        super("create", "Create a faction.", new String[] { "make", "define" });
        this.plugin = plugin;
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName() + " <factionName>";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command may only be executed by players.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
            return true;
        }

        String name = args[1];

        if (SettingsYML.DISALLOWED_FACTION_NAMES.contains(name.toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "'" + name + "' is a blocked faction name.");
            return true;
        }

        if (name.length() < SettingsYML.FACTION_NAME_CHARACTERS_MIN) {
            sender.sendMessage(ChatColor.RED + "Faction names must have at least " + SettingsYML.FACTION_NAME_CHARACTERS_MIN + " characters.");
            return true;
        }

        if (name.length() > SettingsYML.FACTION_NAME_CHARACTERS_MAX) {
            sender.sendMessage(ChatColor.RED + "Faction names cannot be longer than " + SettingsYML.FACTION_NAME_CHARACTERS_MAX + " characters.");
            return true;
        }

        if (!JavaUtils.isAlphanumeric(name)) {
            sender.sendMessage(ChatColor.RED + "Faction names may only be alphanumeric.");
            return true;
        }

        if (plugin.getFactionManager().getFaction(name) != null) {
            sender.sendMessage(ChatColor.RED + "Faction '" + name + "' already exists.");
            return true;
        }

        if (plugin.getFactionManager().getPlayerFaction((Player) sender) != null) {
            sender.sendMessage(ChatColor.RED + "You are already in a faction.");
            return true;
        }

        plugin.getFactionManager().createFaction(new PlayerFaction(name), sender);
        return true;
    }
}

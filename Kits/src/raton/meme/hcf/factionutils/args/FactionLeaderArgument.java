package raton.meme.hcf.factionutils.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.doctordark.utils.other.command.CommandArgument;

import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.FactionMember;
import raton.meme.hcf.factionutils.struct.Role;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.ymls.SettingsYML;

import java.util.*;

public class FactionLeaderArgument extends CommandArgument {

    private final HCF plugin;

    public FactionLeaderArgument(HCF plugin) {
        super("leader", "Sets the new leader for your faction.");
        this.plugin = plugin;
        this.aliases = new String[] { "setleader", "newleader" };
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName() + " <playerName>";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can set faction leaders.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
            return true;
        }

        Player player = (Player) sender;
        PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);

        if (playerFaction == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a faction.");
            return true;
        }

        UUID uuid = player.getUniqueId();
        FactionMember selfMember = playerFaction.getMember(uuid);
        Role selfRole = selfMember.getRole();

        if (selfRole != Role.LEADER) {
            sender.sendMessage(ChatColor.RED + "You must be the current faction leader to transfer the faction.");
            return true;
        }

        FactionMember targetMember = playerFaction.getMember(args[1]);

        if (targetMember == null) {
            sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' is not in your faction.");
            return true;
        }

        if (targetMember.getUniqueId().equals(uuid)) {
            sender.sendMessage(ChatColor.RED + "You are already the faction leader.");
            return true;
        }

        targetMember.setRole(Role.LEADER);
        selfMember.setRole(Role.CAPTAIN);
        playerFaction.broadcast(SettingsYML.TEAMMATE_COLOUR + selfMember.getRole().getAstrix() + selfMember.getName() + ChatColor.YELLOW + " has transferred the faction to "
                + SettingsYML.TEAMMATE_COLOUR + targetMember.getRole().getAstrix() + targetMember.getName() + ChatColor.YELLOW + '.');

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2 || !(sender instanceof Player)) {
            return Collections.emptyList();
        }

        Player player = (Player) sender;
        PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);
        if (playerFaction == null || (playerFaction.getMember(player.getUniqueId()).getRole() != Role.LEADER)) {
            return Collections.emptyList();
        }

        List<String> results = new ArrayList<>();
        Map<UUID, FactionMember> members = playerFaction.getMembers();
        for (Map.Entry<UUID, FactionMember> entry : members.entrySet()) {
            if (entry.getValue().getRole() != Role.LEADER) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(entry.getKey());
                String targetName = target.getName();
                if (targetName != null && !results.contains(targetName)) {
                    results.add(targetName);
                }
            }
        }

        return results;
    }
}

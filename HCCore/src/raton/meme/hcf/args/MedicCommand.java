package raton.meme.hcf.args;

import java.util.*;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import raton.meme.hcf.HCF;
import raton.meme.hcf.deathban.Deathban;
import raton.meme.hcf.factionutils.FactionUser;
import raton.meme.hcf.factionutils.struct.Relation;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.listener.Cooldowns;

public class MedicCommand implements CommandExecutor
{
    private final HCF plugin;

    public MedicCommand(final HCF plugin) {
        this.plugin = plugin;
    }

    public String getUsage(final String label) {
        return String.valueOf('/') + label + ' ' + "revive <playerName>";
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage(label));
            return true;
        }
        if (Cooldowns.isOnCooldown("medic_cooldown", p)) {
            p.sendMessage(ChatColor.RED + "You still have a " + ChatColor.DARK_GRAY + ChatColor.BOLD + "Titanium" + ChatColor.RED + " cooldown for another " + (Cooldowns.getCooldownForPlayerLong("medic_cooldown", p)) + ChatColor.RED + '.');
            return true;
        }
        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not online.");
            return true;
        }
        final UUID targetUUID = target.getUniqueId();
        final FactionUser factionTarget = this.plugin.getUserManager().getUser(targetUUID);
        final Deathban deathban = factionTarget.getDeathban();
        if (deathban == null || !deathban.isActive()) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not death-banned.");
            return true;
        }
        Relation relation = Relation.ENEMY;
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final UUID playerUUID = player.getUniqueId();
            final int selfLives = this.plugin.getDeathbanManager().getLives(playerUUID);
            Cooldowns.addCooldown("medic_cooldown", p, 5400);
            final PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
            relation = ((playerFaction == null) ? Relation.ENEMY : playerFaction.getFactionRelation(this.plugin.getFactionManager().getPlayerFaction(targetUUID)));
            sender.sendMessage(ChatColor.YELLOW + "You have revived " + relation.toChatColour() + target.getName() + ChatColor.YELLOW + '.');
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8&l" + p.getName().toUpperCase() + " &7has used their Titanium Rank &eto revive &8&l" + target.getName().toUpperCase() + "&7."));
        }
        factionTarget.removeDeathban();
        return true;
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 2) {
            return Collections.emptyList();
        }
        final List<String> results = new ArrayList<String>();
        final Collection<FactionUser> factionUsers = this.plugin.getUserManager().getUsers().values();
        for (final FactionUser factionUser : factionUsers) {
            final Deathban deathban = factionUser.getDeathban();
            if (deathban != null && deathban.isActive()) {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(factionUser.getUserUUID());
                final String offlineName = offlinePlayer.getName();
                if (offlineName == null) {
                    continue;
                }
                results.add(offlinePlayer.getName());
            }
        }
        return results;
    }
}
package raton.meme.hcf.factionutils.args;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.doctordark.utils.other.command.CommandArgument;

import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.FactionMember;
import raton.meme.hcf.factionutils.claim.Claim;
import raton.meme.hcf.factionutils.struct.Role;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.ymls.SettingsYML;

public class FactionSetHomeArgument extends CommandArgument {

    private final HCF plugin;

    public FactionSetHomeArgument(HCF plugin) {
        super("sethome", "Sets the faction home location.");
        this.plugin = plugin;
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }

        Player player = (Player) sender;
        PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);

        if (playerFaction == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a faction.");
            return true;
        }

        FactionMember factionMember = playerFaction.getMember(player);

        if (factionMember.getRole() == Role.MEMBER) {
            sender.sendMessage(ChatColor.RED + "You must be a faction officer to set the home.");
            return true;
        }

        Location location = player.getLocation();

        boolean insideTerritory = false;
        for (Claim claim : playerFaction.getClaims()) {
            if (claim.contains(location)) {
                insideTerritory = true;
                break;
            }
        }

        if (!insideTerritory) {
            player.sendMessage(ChatColor.RED + "You may only set your home in your territory.");
            return true;
        }

        playerFaction.setHome(location);
        playerFaction.broadcast(SettingsYML.TEAMMATE_COLOUR + factionMember.getRole().getAstrix() + sender.getName() + ChatColor.RED + " has updated the faction home point.");
        return true;
    }

}

package raton.meme.hcf.args;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.doctordark.utils.BukkitUtils;
import com.doctordark.utils.internal.com.doctordark.base.BaseConstants;

import raton.meme.hcf.HCF;

public class PlayTimeCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        OfflinePlayer target;
        if (args.length >= 1)
        {
            target = BukkitUtils.offlinePlayerWithNameOrUUID(args[0]);
        }
        else
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("You can only use this if you are a player!");
                return true;
            }
            target = (OfflinePlayer)sender;
        }
        if ((!target.hasPlayedBefore()) && (!target.isOnline()))
        {
            sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, new Object[] { args[0] }));
            return true;
        }
        sender.sendMessage(ChatColor.RED + target.getName() + ChatColor.GRAY +  " has been playing for " + ChatColor.RED + DurationFormatUtils.formatDurationWords(HCF.getPlugin().getPlayTimeManager().getTotalPlayTime(target.getUniqueId()), true, true) + ChatColor.GRAY + ".");
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return args.length == 1 ? null : Collections.emptyList();
    }
}

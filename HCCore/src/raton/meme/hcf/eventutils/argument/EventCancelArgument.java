package raton.meme.hcf.eventutils.argument;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.doctordark.utils.other.command.CommandArgument;

import raton.meme.hcf.HCF;
import raton.meme.hcf.eventutils.EventTimer;
import raton.meme.hcf.factionutils.type.Faction;

/**
 * An {@link CommandArgument} used for cancelling the current running event.
 */
public class EventCancelArgument extends CommandArgument {

    private final HCF plugin;

    public EventCancelArgument(HCF plugin) {
        super("cancel", "Cancels a running event", new String[] { "stop", "end" });
        this.plugin = plugin;
        this.permission = "hcf.command.event.argument." + getName();
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        EventTimer eventTimer = plugin.getTimerManager().getEventTimer();
        Faction eventFaction = eventTimer.getEventFaction();

        if (!eventTimer.clearCooldown()) {
            sender.sendMessage(ChatColor.RED + "There is not a running event.");
            return true;
        }

        Bukkit.broadcastMessage(sender.getName() + ChatColor.YELLOW + " has cancelled " + (eventFaction == null ? "the active event" : eventFaction.getName() + ChatColor.YELLOW) + ".");
        return true;
    }
}

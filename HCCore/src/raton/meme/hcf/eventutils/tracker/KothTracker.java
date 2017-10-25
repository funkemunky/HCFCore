package raton.meme.hcf.eventutils.tracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import raton.meme.hcf.HCF;
import raton.meme.hcf.eventutils.CaptureZone;
import raton.meme.hcf.eventutils.EventTimer;
import raton.meme.hcf.eventutils.EventType;
import raton.meme.hcf.factionutils.type.EventFaction;
import raton.meme.hcf.factionutils.type.KothFaction;
import raton.meme.hcf.listener.DateTimeFormats;

import java.util.concurrent.TimeUnit;

@Deprecated
public class KothTracker implements EventTracker {

    /**
     * Minimum time the KOTH has to be controlled before this tracker will announce when control has been lost.
     */
    private static final long MINIMUM_CONTROL_TIME_ANNOUNCE = TimeUnit.SECONDS.toMillis(25L);

    public static final long DEFAULT_CAP_MILLIS = TimeUnit.MINUTES.toMillis(15L);

    private final HCF plugin;

    public KothTracker(HCF plugin) {
        this.plugin = plugin;
    }

    @Override
    public EventType getEventType() {
        return EventType.KOTH;
    }

    @Override
    public void tick(EventTimer eventTimer, EventFaction eventFaction) {
        CaptureZone captureZone = ((KothFaction) eventFaction).getCaptureZone();
        captureZone.updateScoreboardRemaining();
        long remainingMillis = captureZone.getRemainingCaptureMillis();
        if (remainingMillis <= 0L) { // has been captured.
            plugin.getTimerManager().getEventTimer().handleWinner(captureZone.getCappingPlayer());
            eventTimer.clearCooldown();
            return;
        }

        if (remainingMillis == captureZone.getDefaultCaptureMillis())
            return;

        int remainingSeconds = (int) (remainingMillis / 1000L);
        if (remainingSeconds > 0 && remainingSeconds % 30 == 0) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[" + eventFaction.getEventType().getDisplayName() + "] " + ChatColor.YELLOW + "Someone is controlling " + ChatColor.RED
                    + captureZone.getDisplayName() + ChatColor.YELLOW + ". " + ChatColor.RED + '(' + DateTimeFormats.KOTH_FORMAT.format(remainingMillis) + ')');
        }
    }

    @Override
    public void onContest(EventFaction eventFaction, EventTimer eventTimer) {
        Bukkit.broadcastMessage(ChatColor.GOLD + "[" + eventFaction.getEventType().getDisplayName() + "] " + ChatColor.RED + eventFaction.getName() + ChatColor.YELLOW + " can now be contested. "
                + ChatColor.RED + '(' + DateTimeFormats.KOTH_FORMAT.format(eventTimer.getRemaining()) + ')');
    }

    @Override
    public boolean onControlTake(Player player, CaptureZone captureZone) {
        player.sendMessage(ChatColor.RED + "You are now in control of " + ChatColor.RED + captureZone.getDisplayName() + ChatColor.RED + '.');
        return true;
    }

    @Override
    public void onControlLoss(Player player, CaptureZone captureZone, EventFaction eventFaction) {
        player.sendMessage(ChatColor.RED + "You are no longer in control of " + ChatColor.RED + captureZone.getDisplayName() + ChatColor.DARK_AQUA + '.');

        // Only broadcast if the KOTH has been controlled for at least 25 seconds to prevent spam.
        long remainingMillis = captureZone.getRemainingCaptureMillis();
        if (remainingMillis > 0L && captureZone.getDefaultCaptureMillis() - remainingMillis > MINIMUM_CONTROL_TIME_ANNOUNCE) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[" + eventFaction.getEventType().getDisplayName() + "] " + ChatColor.RED + captureZone.getDisplayName() + ChatColor.YELLOW + " has been knocked!"
                    + " (" + captureZone.getScoreboardRemaining() + ')');
        }
    }

    @Override
    public void stopTiming() {

    }
}

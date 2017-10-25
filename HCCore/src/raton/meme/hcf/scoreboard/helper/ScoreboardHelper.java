package raton.meme.hcf.scoreboard.helper;

import com.doctordark.utils.BukkitUtils;
import com.doctordark.utils.DurationFormatter;
import com.funkemunky.Riots.StaffMode;
import com.google.common.collect.Ordering;

import net.md_5.bungee.api.ChatColor;
import raton.meme.hcf.HCF;
import raton.meme.hcf.args.CobbleCommand;
import raton.meme.hcf.armors.PvpClass;
import raton.meme.hcf.armors.archer.ArcherClass;
import raton.meme.hcf.armors.archer.ArcherMark;
import raton.meme.hcf.armors.bard.BardClass;
import raton.meme.hcf.armors.miner.MinerClass;
import raton.meme.hcf.endoftheworld.EotwHandler;
import raton.meme.hcf.eventutils.EventTimer;
import raton.meme.hcf.eventutils.tracker.ConquestTracker;
import raton.meme.hcf.factionutils.FactionManager;
import raton.meme.hcf.factionutils.FactionUser;
import raton.meme.hcf.factionutils.type.ConquestFaction;
import raton.meme.hcf.factionutils.type.EventFaction;
import raton.meme.hcf.factionutils.type.KothFaction;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.listener.DateTimeFormats;
import raton.meme.hcf.scoreboard.SidebarEntry;
import raton.meme.hcf.scoreboard.SidebarProvider;
import raton.meme.hcf.startoftheworld.SotwTimer;
import raton.meme.hcf.timer.PlayerTimer;
import raton.meme.hcf.timer.Timer;
import raton.meme.hcf.timer.type.CombatTimer;
import raton.meme.hcf.ymls.SettingsYML;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;


import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;
public class ScoreboardHelper implements SidebarProvider {

    public static final ThreadLocal<DecimalFormat> CONQUEST_FORMATTER = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("00.0");
        }
    };
    protected static final String STRAIGHT_LINE = BukkitUtils.STRAIGHT_LINE_DEFAULT.substring(0, 14);
    private static final SidebarEntry EMPTY_ENTRY_FILLER = new SidebarEntry(" "," "," ");
   private static final Comparator<Map.Entry<UUID, ArcherMark>> ARCHER_MARK_COMPARATOR = (o1, o2) -> o1.getValue().compareTo(o2.getValue());

    private final HCF plugin;

    public ScoreboardHelper(HCF plugin) {
        this.plugin = plugin;
    }

    public SidebarEntry add(String s){

        if(s.length() < 10){
            return new SidebarEntry(s);
        }

        if(s.length() > 10 && s.length() < 20){
            return new SidebarEntry(s.substring(0, 10), s.substring(10, s.length()), "");
        }

        if(s.length() > 20){
            return new SidebarEntry(s.substring(0, 10), s.substring(10, 20), s.substring(20, s.length()));
        }

        return null;
    }

    @Override
    public String getTitle() {
        return SettingsYML.SCOREBOARD_TITLE;
    }

    @Override
    public List<SidebarEntry> getLines(Player player) {
        PlayerFaction playerFaction = HCF.getPlugin().getFactionManager().getPlayerFaction(player);
        List<SidebarEntry> lines = new ArrayList<>();
        
        SotwTimer.SotwRunnable sotwRunnable = plugin.getSotwTimer().getSotwRunnable();
        if (sotwRunnable != null) {
            lines.add(new SidebarEntry(ChatColor.GREEN.toString() + ChatColor.BOLD, "SOTW Timer", ChatColor.GRAY + ": " + ChatColor.WHITE + DurationFormatter.getRemaining(sotwRunnable.getRemaining(), true)));
        }
        
        EotwHandler.EotwRunnable eotwRunnable = plugin.getEotwHandler().getRunnable();

        if (eotwRunnable != null) {
            long remaining = eotwRunnable.getMillisUntilStarting();
            if (remaining > 0L) {
                lines.add(new SidebarEntry(ChatColor.RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GOLD + " starts", " in " + ChatColor.BOLD + DurationFormatter.getRemaining(remaining, true)));
            } else if ((remaining = eotwRunnable.getMillisUntilCappable()) > 0L) {
                lines.add(new SidebarEntry(ChatColor.RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GOLD + " cappable", " in " + ChatColor.BOLD + DurationFormatter.getRemaining(remaining, true)));
            }
        }

        if (SettingsYML.KIT_MAP == true) {
        	lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Statistics"));
        	lines.add(new SidebarEntry(ChatColor.RED.toString() + "Kills" + "§7: §f"+ player.getStatistic(Statistic.PLAYER_KILLS)));
        	lines.add(new SidebarEntry(ChatColor.RED.toString() + "Deaths" + "§7: §f"+ player.getStatistic(Statistic.DEATHS)));
        }
        
        // Show the current PVP Class statistics of the player.
        PvpClass pvpClass = plugin.getPvpClassManager().getEquippedClass(player);
        if (pvpClass != null) {
            if (pvpClass instanceof BardClass) {
                BardClass bardClass = (BardClass) pvpClass;
                lines.add(new SidebarEntry(ChatColor.AQUA + "", ChatColor.BOLD + "Bard Energy", ChatColor.AQUA + ": " + ChatColor.WHITE + handleBardFormat(bardClass.getEnergyMillis(player), true)));
                long remaining2 = bardClass.getRemainingBuffDelay(player);
                if (remaining2 > 0L) {
                    lines.add(new SidebarEntry(ChatColor.GREEN + "", ChatColor.BOLD + "Bard Effect", ChatColor.GREEN + ": " + ChatColor.WHITE +  DurationFormatter.getRemaining(remaining2, true)));
                }
                long remaining = bardClass.getRemainingBuffDelay(player);
                if (remaining > 0) {
                }
            }
            if (pvpClass instanceof ArcherClass) {
                UUID uuid = player.getUniqueId();
                ArcherClass archerClass = (ArcherClass)pvpClass;
                long timestamp = ArcherClass.archerSpeedCooldowns.get(uuid);
                long millis = System.currentTimeMillis();
                long remaining3 = (timestamp == ArcherClass.archerSpeedCooldowns.getNoEntryValue()) ? -1L : (timestamp - millis);
                if (remaining3 > 0L) {
                    lines.add(new SidebarEntry(" " + ChatColor.GOLD + ChatColor.BOLD, "Delay", ChatColor.GRAY + ": " + DurationFormatter.getRemaining(remaining3, true)));
                }
            }
        }

        Collection<Timer> timers = (Collection<Timer>)this.plugin.getTimerManager().getTimers();
        for (Timer timer : timers) {
            if (timer instanceof PlayerTimer) {
                PlayerTimer playerTimer = (PlayerTimer)timer;
                long remaining4 = playerTimer.getRemaining(player);
                if (remaining4 <= 0L) {
                    continue;
                }
                String timerName = playerTimer.getName();
                if (timerName.length() > 14) {
                    timerName = timerName.substring(0, timerName.length());
                }
                lines.add(new SidebarEntry(playerTimer.getScoreboardPrefix(), String.valueOf(timerName) + ChatColor.GRAY, ": " + ChatColor.WHITE + ((timer instanceof CombatTimer) ? DurationFormatter.getRemaining(remaining4, false) : DurationFormatter.getRemaining(remaining4, true))));
            }
        }
        
        EventTimer eventTimer = plugin.getTimerManager().getEventTimer();
        List<SidebarEntry> conquestLines = null;

        EventFaction eventFaction = eventTimer.getEventFaction();
        if (eventFaction instanceof KothFaction) {
           // lines.add(new SidebarEntry(ChatColor.AQUA.toString(), ChatColor.BOLD + "Active Events", null));
            lines.add(new SidebarEntry(eventTimer.getScoreboardPrefix(), eventFaction.getScoreboardName() + ChatColor.GRAY, ": " +
                    ChatColor.WHITE + DurationFormatter.getRemaining(eventTimer.getRemaining(), true)));
        } else if (eventFaction instanceof ConquestFaction) {
            ConquestFaction conquestFaction = (ConquestFaction) eventFaction;
            DecimalFormat format = CONQUEST_FORMATTER.get();

            conquestLines = new ArrayList<>();
            conquestLines.add(new SidebarEntry(ChatColor.GOLD.toString(), ChatColor.BOLD + "Conquest Event", ""));

            conquestLines.add(new SidebarEntry(" " +
                    ChatColor.GOLD.toString() + conquestFaction.getRed().getScoreboardRemaining(),
                    ChatColor.GOLD + " | ",
                    ChatColor.YELLOW.toString() + conquestFaction.getYellow().getScoreboardRemaining()));

            conquestLines.add(new SidebarEntry(" " +
                    ChatColor.GREEN.toString() + conquestFaction.getGreen().getScoreboardRemaining(),
                    ChatColor.GOLD + " | " + ChatColor.RESET,
                    ChatColor.AQUA.toString() + conquestFaction.getBlue().getScoreboardRemaining()));

            // Show the top 3 factions next.
            ConquestTracker conquestTracker = (ConquestTracker) conquestFaction.getEventType().getEventTracker();
            int count = 0;
            for (Map.Entry<PlayerFaction, Integer> entry : conquestTracker.getFactionPointsMap().entrySet()) {
                String factionName = entry.getKey().getName();
                if (factionName.length() > 14) factionName = factionName.substring(0, 14);
                for (int i = 0; i < 3; i++) {
                    conquestLines.add(new SidebarEntry(ChatColor.GOLD.toString() + ChatColor.BOLD + " " + count++ + ". ", ChatColor.YELLOW + factionName, ChatColor.GRAY + ": " + ChatColor.WHITE + entry.getValue()));
                }
                if (++count == 3) break;
            }
        }

        if (conquestLines != null && !conquestLines.isEmpty()) {
            if (!lines.isEmpty()) {
                conquestLines.add(new SidebarEntry("", "", ""));
            }

            conquestLines.addAll(lines);
            lines = conquestLines;
        }
    if (!lines.isEmpty())
   {
      lines.add(0, new SidebarEntry(ChatColor.DARK_GRAY, STRAIGHT_LINE, STRAIGHT_LINE));
        lines.add(lines.size(), new SidebarEntry(ChatColor.DARK_GRAY, ChatColor.STRIKETHROUGH + STRAIGHT_LINE, STRAIGHT_LINE));
   }

    return lines;
}

    private static String handleBardFormat(long millis, boolean trailingZero) {
        return (trailingZero ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get().format(millis * 0.001);
    }

    public static String translate(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}

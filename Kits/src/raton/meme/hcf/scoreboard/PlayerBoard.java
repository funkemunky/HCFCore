package raton.meme.hcf.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import raton.meme.hcf.HCF;
import raton.meme.hcf.armors.archer.ArcherClass;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.ymls.SettingsYML;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerBoard
{
    private boolean sidebarVisible;
    private SidebarProvider defaultProvider;
    private SidebarProvider temporaryProvider;
    private BukkitRunnable runnable;
    private final Team archers;
    private final AtomicBoolean removed;
    private final Team members;
    private final Team neutrals;
    private final Team allies;
    private final BufferedObjective bufferedObjective;
    private final Scoreboard scoreboard;
    private final Player player;
    private final HCF plugin;
    
    public PlayerBoard(final HCF plugin, final Player player) {
        this.sidebarVisible = false;
        this.removed = new AtomicBoolean(false);
        this.plugin = plugin;
        this.player = player;
        this.scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        this.bufferedObjective = new BufferedObjective(this.scoreboard);
        (this.members = this.scoreboard.registerNewTeam("members")).setPrefix(SettingsYML.TEAMMATE_COLOUR.toString());
        this.members.setCanSeeFriendlyInvisibles(true);
        (this.neutrals = this.scoreboard.registerNewTeam("neutrals")).setPrefix(SettingsYML.ENEMY_COLOUR.toString());
        (this.archers = this.scoreboard.registerNewTeam("archers")).setPrefix(ChatColor.DARK_RED.toString());
        (this.allies = this.scoreboard.registerNewTeam("enemies")).setPrefix(SettingsYML.ALLY_COLOUR.toString());
        player.setScoreboard(this.scoreboard);
    }
    
    public void remove() {
        if (!this.removed.getAndSet(true) && this.scoreboard != null) {
            for (final Team team : this.scoreboard.getTeams()) {
                team.unregister();
            }
            for (final Objective objective : this.scoreboard.getObjectives()) {
                objective.unregister();
            }
        }
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public boolean isSidebarVisible() {
        return this.sidebarVisible;
    }
    
    public void setSidebarVisible(final boolean visible) {
        this.sidebarVisible = visible;
        this.bufferedObjective.setDisplaySlot(visible ? DisplaySlot.SIDEBAR : null);
    }
    
    public void setDefaultSidebar(final SidebarProvider provider, final long updateInterval) {
        if (provider != this.defaultProvider) {
            this.defaultProvider = provider;
            if (this.runnable != null) {
                this.runnable.cancel();
            }
            if (provider == null) {
                this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                return;
            }
            (this.runnable = new BukkitRunnable() {
                public void run() {
                    if (PlayerBoard.this.removed.get()) {
                        this.cancel();
                        return;
                    }
                    if (provider == PlayerBoard.this.defaultProvider) {
                        PlayerBoard.this.updateObjective();
                    }
                }
            }).runTaskTimerAsynchronously((Plugin)this.plugin, updateInterval, updateInterval);
        }
    }
    
    public void setTemporarySidebar(final SidebarProvider provider, final long expiration) {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }
        this.temporaryProvider = provider;
        this.updateObjective();
        new BukkitRunnable() {
            public void run() {
                if (PlayerBoard.this.removed.get()) {
                    this.cancel();
                    return;
                }
                if (PlayerBoard.this.temporaryProvider == provider) {
                    PlayerBoard.access$4(PlayerBoard.this, null);
                    PlayerBoard.this.updateObjective();
                }
            }
        }.runTaskLaterAsynchronously((Plugin)this.plugin, expiration);
    }
    
    private void updateObjective() {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }
        final SidebarProvider provider = (this.temporaryProvider != null) ? this.temporaryProvider : this.defaultProvider;
        if (provider == null) {
            this.bufferedObjective.setVisible(false);
        }
        else {
            this.bufferedObjective.setTitle(provider.getTitle());
            this.bufferedObjective.setAllLines(provider.getLines(this.player));
            this.bufferedObjective.flip();
        }
    }
    
    public void addUpdate(final Player target) {
        this.addUpdates(Collections.singleton(target));
    }
    
    public void addUpdates(final Iterable<? extends Player> updates) {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }
        new BukkitRunnable() {
            public void run() {
                if (PlayerBoard.this.removed.get()) {
                    this.cancel();
                    return;
                }
                PlayerFaction playerFaction = null;
                boolean firstExecute = false;
                for (final Player update : updates) {
                    if (PlayerBoard.this.player.equals(update)) {
                        PlayerBoard.this.members.addPlayer((OfflinePlayer)update);
                    }
                    if (ArcherClass.TAGGED.containsKey(update.getUniqueId())) {
                        PlayerBoard.this.archers.addPlayer((OfflinePlayer)update);
                    }
                    else {
                        if (!firstExecute) {
                            playerFaction = PlayerBoard.this.plugin.getFactionManager().getPlayerFaction(PlayerBoard.this.player);
                            firstExecute = true;
                        }
                        final PlayerFaction targetFaction;
                        if (playerFaction == null || (targetFaction = PlayerBoard.this.plugin.getFactionManager().getPlayerFaction(update)) == null) {
                            PlayerBoard.this.neutrals.addPlayer((OfflinePlayer)update);
                        }
                        else if (playerFaction == targetFaction) {
                            PlayerBoard.this.members.addPlayer((OfflinePlayer)update);
                        }
                        else if (ArcherClass.TAGGED.containsKey(update.getUniqueId())) {
                            PlayerBoard.this.archers.addPlayer((OfflinePlayer)update);
                        }
                        else if (playerFaction.getAllied().contains(targetFaction.getUniqueID())) {
                            PlayerBoard.this.allies.addPlayer((OfflinePlayer)update);
                        }
                        else {
                            PlayerBoard.this.neutrals.addPlayer((OfflinePlayer)update);
                        }
                    }
                }
            }
        }.runTaskAsynchronously((Plugin)this.plugin);
    }
    
    static /* synthetic */ void access$4(final PlayerBoard playerBoard, final SidebarProvider temporaryProvider) {
        playerBoard.temporaryProvider = temporaryProvider;
    }
}

/*     */ package raton.meme.hcf.deathban;
/*     */
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.gnu.trove.map.TObjectIntMap;
/*     */ import net.minecraft.util.gnu.trove.map.TObjectLongMap;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TObjectLongHashMap;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent.Result;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import raton.meme.hcf.listener.DelayedMessageRunnable;
import com.doctordark.utils.DurationFormatter;
/*     */ import raton.meme.hcf.HCF;
/*     */ import raton.meme.hcf.endoftheworld.EotwHandler;
/*     */ import raton.meme.hcf.factionutils.FactionUser;
/*     */ import raton.meme.hcf.listener.UserManager;
/*     */
/*     */ public class DeathbanListener implements Listener
/*     */ {
    /*  29 */   private static final long RESPAWN_KICK_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(10L);
    /*  30 */   private static final long RESPAWN_KICK_DELAY_TICKS = RESPAWN_KICK_DELAY_MILLIS / 50L;
    /*  31 */   private static final long LIFE_USE_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(30L);
    /*  32 */   private static final String LIFE_USE_DELAY_WORDS = org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(LIFE_USE_DELAY_MILLIS, true, true);
    /*     */
/*     */   private static final String DEATH_BAN_BYPASS_PERMISSION = "hcf.deathban.bypass";
    /*  35 */   private final TObjectIntMap<UUID> respawnTickTasks = new net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap();
    /*  36 */   private final TObjectLongMap<UUID> lastAttemptedJoinMap = new TObjectLongHashMap();
    /*     */   private final HCF plugin;
    /*     */
/*     */   public DeathbanListener(HCF plugin) {
/*  40 */     this.plugin = plugin;
/*     */   }
    /*     */
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGH)
/*     */   public void onPlayerLogin(PlayerLoginEvent event) {
/*  45 */     Player player = event.getPlayer();
/*  46 */     if (player.hasPermission("hcf.deathban.bypass")) {
/*  47 */       return;
/*     */     }
/*     */
/*  50 */     FactionUser user = this.plugin.getUserManager().getUser(player.getUniqueId());
/*  51 */     Deathban deathban = user.getDeathban();
/*  52 */     if ((deathban == null) || (!deathban.isActive())) {
/*  53 */       return;
/*     */     }
/*     */
/*  56 */     if (this.plugin.getEotwHandler().isEndOfTheWorld()) {
/*  57 */       event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Deathbanned for the entirety of the map due to EOTW.\nVisit www.hcriots.com to view SOTW info.");
/*  58 */       return;
/*     */     }
/*     */
/*  61 */     UUID uuid = player.getUniqueId();
/*  62 */     int lives = this.plugin.getDeathbanManager().getLives(uuid);
/*     */
/*  64 */     String formattedRemaining = DurationFormatter.getRemaining(deathban.getRemaining(), true, false);
/*     */
/*  66 */     if (lives <= 0) {
/*  67 */       event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You are still Deathbanned \n Duration: " + ChatColor.WHITE + formattedRemaining);
/*     */
/*  70 */       return;
/*     */     }
/*     */
/*  73 */     long millis = System.currentTimeMillis();
/*  74 */     long lastAttemptedJoinMillis = this.lastAttemptedJoinMap.get(uuid);
/*     */
/*     */
/*  77 */     if ((lastAttemptedJoinMillis != this.lastAttemptedJoinMap.getNoEntryValue()) && (lastAttemptedJoinMillis - millis < LIFE_USE_DELAY_MILLIS)) {
/*  78 */       this.lastAttemptedJoinMap.remove(uuid);
/*  79 */       user.removeDeathban();
/*  80 */       lives = this.plugin.getDeathbanManager().takeLives(uuid, 1);
/*     */
/*  82 */       event.setResult(PlayerLoginEvent.Result.ALLOWED);
/*  83 */       new DelayedMessageRunnable(player, ChatColor.YELLOW + "You have used a life for entry. You now have " + ChatColor.WHITE + lives + ChatColor.YELLOW + " lives.").runTask(this.plugin);
/*     */
/*  85 */       return;
/*     */     }
/*     */
/*     */
/*  89 */     String reason = deathban.getReason();
/*  90 */     this.lastAttemptedJoinMap.put(uuid, millis + LIFE_USE_DELAY_MILLIS);
/*     */
/*  92 */     event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You are currently Deathbanned for Duration: " + ChatColor.WHITE + formattedRemaining + "\n" +
/*  93 */       ChatColor.RED + "You currently have " + ChatColor.WHITE + (lives <= 0 ? "no" : Integer.valueOf(lives)) + " lives." + ChatColor.RED + "You may use a life by reconnecting within " + ChatColor.WHITE +
/*  94 */       LIFE_USE_DELAY_WORDS + ChatColor.RED + ".");
/*     */   }
    /*     */
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.LOW)
/*     */   public void onPlayerDeath(PlayerDeathEvent event) {
/*  99 */     final Player player = event.getEntity();
/* 100 */     final Deathban deathban = this.plugin.getDeathbanManager().applyDeathBan(player, event.getDeathMessage());
/* 101 */     long remaining = deathban.getRemaining();
/* 102 */     if ((remaining <= 0L) || (player.hasPermission("hcf.deathban.bypass"))) {
/* 103 */       return;
/*     */     }
/*     */
/* 106 */     if ((RESPAWN_KICK_DELAY_MILLIS <= 0L) || (remaining < RESPAWN_KICK_DELAY_MILLIS)) {
/* 107 */       handleKick(player, deathban);
/* 108 */       return;
/*     */     }
/*     */
/*     */
/* 112 */     this.respawnTickTasks.put(player.getUniqueId(), new BukkitRunnable()
/*     */     {
            /*     */       public void run() {
/* 115 */         DeathbanListener.this.handleKick(player, deathban);
/*     */       }
/* 117 */     }.runTaskLater(this.plugin, RESPAWN_KICK_DELAY_TICKS).getTaskId());
/*     */   }
    /*     */
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
/*     */   public void onPlayerRequestRespawn(PlayerRespawnEvent event) {
/* 122 */     Player player = event.getPlayer();
/* 123 */     FactionUser user = this.plugin.getUserManager().getUser(player.getUniqueId());
/* 124 */     Deathban deathban = user.getDeathban();
/* 125 */     if ((deathban != null) && (deathban.getRemaining() > 0L)) {
/* 126 */       if (player.hasPermission("hcf.deathban.bypass")) {
/* 127 */         cancelRespawnKickTask(player);
/* 128 */         user.removeDeathban();
/* 129 */         new DelayedMessageRunnable(player, ChatColor.RED + "Bypass access granted.").runTask(this.plugin);
/*     */
/* 131 */         return;
/*     */       }
/*     */
/*     */
/*     */
/* 136 */       handleKick(player, deathban);
/*     */     }
/*     */   }
    /*     */
/*     */
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
/*     */   public void onPlayerQuit(PlayerQuitEvent event)
/*     */   {
/* 144 */     cancelRespawnKickTask(event.getPlayer());
/*     */   }
    /*     */
/*     */   private void cancelRespawnKickTask(Player player) {
/* 148 */     int taskId = this.respawnTickTasks.remove(player.getUniqueId());
/* 149 */     if (taskId != this.respawnTickTasks.getNoEntryValue()) {
/* 150 */       org.bukkit.Bukkit.getScheduler().cancelTask(taskId);
/*     */     }
/*     */   }
    /*     */
/*     */   private void handleKick(Player player, Deathban deathban) {
/* 155 */     if (this.plugin.getEotwHandler().isEndOfTheWorld()) {
/* 156 */       player.kickPlayer(ChatColor.RED + "Deathbanned for the entirety of the map due to EOTW.\nVisit www.hcriots.com for SOTW info!");
/*     */     } else {
/* 158 */       player.kickPlayer(ChatColor.RED + "You are now Deathbanned \n Duration: " + ChatColor.WHITE + DurationFormatter.getRemaining(deathban.getRemaining(), true, false) + "" + ChatColor.WHITE + deathban.getReason());
/*     */     }
/*     */   }
/*     */ }


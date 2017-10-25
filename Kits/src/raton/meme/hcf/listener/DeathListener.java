package raton.meme.hcf.listener;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.doctordark.utils.JavaUtils;

import net.minecraft.server.v1_7_R4.EntityLightning;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_7_R4.WorldServer;
import raton.meme.hcf.HCF;
import raton.meme.hcf.economy.EconomyManager;
import raton.meme.hcf.factionutils.FactionUser;
import raton.meme.hcf.factionutils.struct.Role;
import raton.meme.hcf.factionutils.type.Faction;
import raton.meme.hcf.factionutils.type.PlayerFaction;
import raton.meme.hcf.ymls.SettingsYML;

public class DeathListener implements Listener {

    private final HCF plugin;

    public DeathListener(HCF plugin) {
        this.plugin = plugin;
    }

    public static HashMap<UUID, ItemStack[]> PlayerInventoryContents;
    public static HashMap<UUID, ItemStack[]> PlayerArmorContents;

    static {
        PlayerInventoryContents = new HashMap<>();
        PlayerArmorContents = new HashMap<>();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDeathKillIncrement(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            FactionUser user = plugin.getUserManager().getUser(killer.getUniqueId());
            user.setKills(user.getKills() + 1);
            user.setStreak(user.getStreak() + 1);
            if(user.getStreak() == 5) {
            	killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
            	killer.updateInventory();
            	killer.sendMessage(ChatColor.GREEN + "You earned 3 Golden Apples with a KillStreak of 5!");
            }
            if(user.getStreak() == 10) {
            	killer.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 0, (byte) 16388));
            	killer.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 0, (byte) 16426));
            	killer.updateInventory();
            	killer.sendMessage(ChatColor.GREEN + "You earned 1 Gopple with a KillStreak of 20!");
            }
            if(user.getStreak() == 20) {
            	killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 0, (byte) 1));
            	killer.updateInventory();
            	killer.sendMessage(ChatColor.GREEN + "You earned 1 Gopple with a KillStreak of 20!");
            }
        }
        Player death = event.getEntity();
        
        if(death != null) {
        	FactionUser user = plugin.getUserManager().getUser(death.getUniqueId());
        	user.setStreak(0);
        }
    }

    //*HCF* private static final long BASE_REGEN_DELAY = TimeUnit.MINUTES.toMillis(40L);
    private static final long BASE_REGEN_DELAY = TimeUnit.MINUTES.toMillis(0L);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);

        if (playerFaction != null) {
            Faction factionAt = plugin.getFactionManager().getFactionAt(player.getLocation());
            double dtrLoss = (0.0D);
            double newDtr = playerFaction.getDeathsUntilRaidable();

            Role role = playerFaction.getMember(player.getUniqueId()).getRole();
            playerFaction.setRemainingRegenerationTime(BASE_REGEN_DELAY + (playerFaction.getOnlinePlayers().size() * TimeUnit.MINUTES.toMillis(2L)));
            playerFaction.broadcast(ChatColor.GOLD + "Member Death: " + SettingsYML.TEAMMATE_COLOUR + role.getAstrix() + player.getName() + ChatColor.GOLD + ". " + "DTR: (" + ChatColor.WHITE
                    + JavaUtils.format(newDtr, 2) + '/' + JavaUtils.format(playerFaction.getMaximumDeathsUntilRaidable(), 2) + ChatColor.GOLD + ").");
            }
            int balance = 100;
            int bronze = balance * (110/100);
            int silver = balance * (125/100);
            int cobalt = balance * (150/100);
            int ruby = balance * 2;
            int sapphire = balance * 3;
            int titanium = balance * 4;
            
            if(plugin.getEconomyManager().getBalance(player.getUniqueId()) >= 0){
                if (player.getKiller() instanceof Player) {
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Bronze")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), bronze);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+bronze + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Silver")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), silver);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+silver + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Cobalt")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), cobalt);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+cobalt + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Ruby")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), ruby);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+ruby + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }
                    
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Sapphire")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), sapphire);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+sapphire + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }
                    
                    if(HCF.getInstance().getPermissions().getPrimaryGroup(player.getKiller()).equalsIgnoreCase("Titanium")) {
                    	plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), titanium);
                        plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                        player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+titanium + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                        return;
                    }

                    plugin.getEconomyManager().addBalance(player.getKiller().getUniqueId(), balance);
                    plugin.getEconomyManager().subtractBalance(player.getUniqueId(), balance / 4);
                    player.getKiller().sendMessage(ChatColor.GRAY + "You earned "  +ChatColor.GREEN+ ChatColor.BOLD + EconomyManager.ECONOMY_SYMBOL+balance + ChatColor.GRAY +" since you killed "+ ChatColor.WHITE +  player.getName() + "");
                    return;
                }
            }


        if (Bukkit.spigot().getTPS()[0] > 17) { // Prevent unnecessary lag during prime times.
            Location location = player.getLocation();
            WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();

            EntityLightning entityLightning = new EntityLightning(worldServer, location.getX(), location.getY(), location.getZ(), false);
            PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(entityLightning);
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (plugin.getUserManager().getUser(target.getUniqueId()).isShowLightning()) {
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);
                    target.playSound(target.getLocation(), Sound.AMBIENCE_THUNDER, 1.0F, 1.0F);
                }
            }
        }
    }
}

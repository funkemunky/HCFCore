package raton.meme.hcf.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftItem;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import net.minecraft.server.v1_7_R4.EntityItem;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagShort;
import raton.meme.hcf.HCF;

/**
 * Listener that handles events for the {@link World} such as explosions.
 */
public class WorldListener implements Listener {

    public static final String DEFAULT_WORLD_NAME = "world";
    private short itemAge = 5800;

    private final HCF plugin;

    public WorldListener(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
        if (event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
        	public void run() {
        		e.getItemDrop().remove();
        	}
        }, 20L * 8L);
    }
    
    @EventHandler
    public void deathDrop(PlayerDeathEvent e) {
        World w = e.getEntity().getWorld();
        Location l = e.getEntity().getLocation();
        EntityItem ei;
        NBTTagCompound nbt;
        NBTTagShort nbts = new NBTTagShort(itemAge);
        for (ItemStack d : e.getDrops()) {
            ei = ((EntityItem)((CraftItem)w.dropItemNaturally(l, d)).getHandle());
            nbt = new NBTTagCompound();
            ei.b(nbt);
            nbt.set("Age", nbts);
            ei.a(nbt);
        }
        e.getDrops().clear();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockChange(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityPortalEnter(EntityPortalEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onWitherChangeBlock(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Wither || entity instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockFade(BlockFadeEvent event) {
        switch (event.getBlock().getType()) {
        case SNOW:
        case ICE:
            event.setCancelled(true);
            break;
        default:
            break;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getWorld(DEFAULT_WORLD_NAME).getSpawnLocation().add(-3.5, 76, 6.5));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            plugin.getEconomyManager().addBalance(player.getUniqueId(), 250); // give player some starting money
            event.setSpawnLocation(Bukkit.getWorld(DEFAULT_WORLD_NAME).getSpawnLocation().add(-3.5, 76, 6.5));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory() instanceof EnderChest) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Squid) {
            event.setCancelled(true);
        }
    }
}

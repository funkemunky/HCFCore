package raton.meme.hcf.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import raton.meme.hcf.HCF;

public class ElevatorListener implements Listener {

    private final HCF plugin;

    public ElevatorListener(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[Elevator]") && e.getLine(1).equalsIgnoreCase("Up")) {
            e.setLine(0, ChatColor.RED + "[Elevator]");
            e.setLine(1, ChatColor.DARK_GRAY + "Up");
            return;
        }
        if (e.getLine(0).equalsIgnoreCase("[Elevator]") && e.getLine(1).equalsIgnoreCase("Down")) {
            e.setLine(0, ChatColor.RED + "[Elevator]");
            e.setLine(1, ChatColor.DARK_GRAY + "Down");
            return;
        }
        if(e.getLine(0).equalsIgnoreCase("[Elevator]")) {
        	e.setLine(0, ChatColor.RED.toString() + ChatColor.BOLD + "ERROR");
        	e.setLine(1, ChatColor.GRAY + "Use 'Up'");
        	e.setLine(2, ChatColor.GRAY + "or");
        	e.setLine(3, "'Down'");
        }
    }

    @EventHandler
    public void onSignElevator(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        Block block = e.getClickedBlock();
        BlockState state = block.getState();
        if (state instanceof Sign) {
        	if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        		Sign sign = (Sign)state;
                double zdif = Math.abs(e.getPlayer().getLocation().getZ() - block.getLocation().getZ());
                double xdif = Math.abs(e.getPlayer().getLocation().getX() - block.getLocation().getX());
                String lineZero = sign.getLine(0);
                String lineOne = sign.getLine(1);
                if(ChatColor.stripColor(lineZero).equalsIgnoreCase("[Elevator]")) {
                    if(zdif < 1.5D && xdif < 1.5D ) {
                    	if (ChatColor.stripColor(lineOne).equalsIgnoreCase("Up")) {
                            e.getPlayer().teleport(this.teleportSpotUp(block.getLocation(), block.getLocation().getBlockY(), 254));
                        }
                        if (ChatColor.stripColor(lineOne).equalsIgnoreCase("Down")) {
                            e.getPlayer().teleport(this.teleportSpotDown(block.getLocation(), block.getLocation().getBlockY(), 1));
                        }
                    } else {
                    	e.getPlayer().sendMessage(ChatColor.RED + "You must be standing next to the sign!");
                    }
                }
                e.setCancelled(true);
                return;
        	}
        }
    }

    public Location teleportSpotUp(Location loc, int min, int max) {
        int k = min;
        while (k < max) {
        	Material m = new Location(loc.getWorld(), loc.getBlockX(), k - 1, loc.getBlockZ()).getBlock().getType();
            Material m1 = new Location(loc.getWorld(), loc.getBlockX(), k, loc.getBlockZ()).getBlock().getType();
            Material m2 = new Location(loc.getWorld(), loc.getBlockX(), (k + 1), loc.getBlockZ()).getBlock().getType();
            if (m1.equals(Material.AIR) && m2.equals(Material.AIR) && m.isSolid() && !m.equals(Material.SIGN_POST) && !m.equals(Material.WALL_SIGN)) {
                return new Location(loc.getWorld(), loc.getBlockX(), k, loc.getBlockZ());
            }
            ++k;
        }
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getBlockZ());
    }
    
    public Location teleportSpotDown(Location loc, int min, int max) {
        int k = min;
        while (k > max) {
        	Material m = new Location(loc.getWorld(), loc.getBlockX(), k - 1, loc.getBlockZ()).getBlock().getType();
            Material m1 = new Location(loc.getWorld(), loc.getBlockX(), k, loc.getBlockZ()).getBlock().getType();
            Material m2 = new Location(loc.getWorld(), loc.getBlockX(), (k + 1), loc.getBlockZ()).getBlock().getType();
            if (m1.equals(Material.AIR) && m2.equals(Material.AIR) && m.isSolid() && !m.equals(Material.SIGN_POST) && !m.equals(Material.WALL_SIGN)) {
                return new Location(loc.getWorld(), loc.getBlockX(), k, loc.getBlockZ());
            }
            --k;
        }
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getBlockZ());
    }
}

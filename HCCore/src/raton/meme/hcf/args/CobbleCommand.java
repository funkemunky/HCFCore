package raton.meme.hcf.args;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.Sets;

import raton.meme.hcf.HCF;


public class CobbleCommand implements Listener, CommandExecutor {

    public static Set<Player> disabled = Sets.newHashSet();

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can focus.");
            return true;
        }
        Player player = (Player) sender;
        if (disabled.contains(player)) {
            disabled.remove(player);
            player.sendMessage(ChatColor.GRAY + " § " + ChatColor.YELLOW + "You have " + ChatColor.RED + "disabled " + ChatColor.YELLOW + "Cobblestone and Stone pickup!");
        } else {
            disabled.add(player);
            player.sendMessage(ChatColor.GRAY + " § " + ChatColor.YELLOW + "You have " + ChatColor.GREEN + "enabled " + ChatColor.YELLOW + "Cobblestone and Stone pickup!");
        }
            return true;
        }
    
        

        @EventHandler
        public void onPlayerPickup(PlayerQuitEvent event){
            disabled.remove(event.getPlayer());
        }
        @EventHandler
        public void onPlayerPickup(PlayerPickupItemEvent event){
            Material type = event.getItem().getItemStack().getType();
            if(type == Material.STONE || type == Material.COBBLESTONE){
                if(disabled.contains(event.getPlayer())){
                    event.setCancelled(true);
                }
            }
        }
    }

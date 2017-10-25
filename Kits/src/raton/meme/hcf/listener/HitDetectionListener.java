package raton.meme.hcf.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import raton.meme.hcf.HCF;

public class HitDetectionListener implements Listener {

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, HCF.getPlugin());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setMaximumNoDamageTicks(17);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setMaximumNoDamageTicks(17);
    }
}
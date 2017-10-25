package raton.meme.hcf.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PotListener implements Listener {
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onProjectileLaunch(final ProjectileLaunchEvent event) {
        final Projectile projectile = event.getEntity();
        if (event.getEntityType() == EntityType.SPLASH_POTION && projectile.getType().equals((Object)PotionEffectType.HEAL)) {
            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {
                final Vector velocity = projectile.getVelocity();

                velocity.setY(velocity.getY() - 0.4);
            }
        }
    }



}

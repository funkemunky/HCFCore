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
    
	@EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.SPLASH_POTION) {
            final Projectile projectile = event.getEntity();

            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {
                final Vector velocity = projectile.getVelocity();

                velocity.setY(velocity.getY() - 0.8);
                projectile.setVelocity(velocity);
            }
        }
    }

    @EventHandler
    public void onPotionSplash(final PotionSplashEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            final Player shooter = (Player) event.getEntity().getShooter();

            if (shooter.isSprinting() && event.getIntensity(shooter) > 0.5D) {
                event.setIntensity(shooter, 1.0D);
            }
        }
    }



}

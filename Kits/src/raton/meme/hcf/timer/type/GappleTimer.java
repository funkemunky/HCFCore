package raton.meme.hcf.timer.type;

import com.doctordark.utils.DurationFormatter;
import com.doctordark.utils.other.imagemessage.ImageChar;
import com.doctordark.utils.other.imagemessage.ImageMessage;
import com.google.common.base.Predicate;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import raton.meme.hcf.HCF;
import raton.meme.hcf.timer.PlayerTimer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Timer used to prevent {@link Player}s from using Notch Apples too often.
 */
public class GappleTimer extends PlayerTimer implements Listener {

    private static final String GOPPLE_ART_FILE_TYPE = "png";
    private static final String GOPPLE_ART_FILE_NAME = "/gopple-art." + GOPPLE_ART_FILE_TYPE;

    // private final ImageMessage goppleArtMessage;

    public GappleTimer(JavaPlugin plugin) {
        super("Golden Apple", TimeUnit.HOURS.toMillis(8L));
    }

    @Override
    public String getScoreboardPrefix() {
        return ChatColor.YELLOW.toString() + ChatColor.BOLD;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        ItemStack stack = event.getItem();
        if (stack != null && stack.getType() == Material.GOLDEN_APPLE && stack.getDurability() == 1) {
            Player player = event.getPlayer();
            if (setCooldown(player, player.getUniqueId(), defaultCooldown, false, new Predicate<Long>() {
                @Override
                public boolean apply(@Nullable Long value) {
                    return false;
                }
            })) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588\u2588\u2588\u2588\u2588&c\u2588\u2588\u2588"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588\u2588\u2588&e\u2588\u2588&c\u2588\u2588\u2588"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&c\u2588\u2588\u2588&e\u2588&c\u2588\u2588\u2588\u2588 &6&l " + this.name + ": ")));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588\u2588&6\u2588\u2588\u2588\u2588&c\u2588\u2588 &7  Consumed"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588&6\u2588\u2588&f\u2588&6\u2588&6\u2588\u2588&c\u2588"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588&6\u2588&f\u2588&6\u2588&6\u2588&6\u2588\u2588&c\u2588 &6 Cooldown Remaining:"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&c\u2588&6\u2588\u2588&6\u2588&6\u2588&6\u2588\u2588&c\u2588 &7  " + DurationFormatter.getRemaining(getRemaining(player), true, false))));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588&6\u2588\u2588&6\u2588&6\u2588&6\u2588\u2588&c\u2588"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588\u2588&6\u2588\u2588\u2588\u2588&c\u2588\u2588"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\u2588\u2588\u2588\u2588\u2588&c\u2588\u2588\u2588"));
            } else {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You still have a " + getDisplayName() + ChatColor.RED + " cooldown for another " + ChatColor.BOLD
                        + DurationFormatter.getRemaining(getRemaining(player), true, false) + ChatColor.RED + '.');
            }
        }
    }
}

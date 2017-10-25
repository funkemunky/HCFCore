package raton.meme.hcf.armors.miner;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.doctordark.utils.BukkitUtils;

import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TObjectLongHashMap;
import raton.meme.hcf.HCF;
import raton.meme.hcf.armors.PvpClass;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Represents a {@link PvpClass} used to enhance mining quality.
 */
public class MinerClass extends PvpClass implements Listener {

    private static final long MINER_SPEED_COOLDOWN_DELAY = TimeUnit.SECONDS.toMillis(45L);
    private static final PotionEffect MINER_SPEED_EFFECT = new PotionEffect(PotionEffectType.SPEED, 200, 2);
    public static final TObjectLongMap<UUID> minerSpeedCooldowns = new TObjectLongHashMap();

    private final HCF plugin;

    public MinerClass(HCF plugin) {
        super("Miner", TimeUnit.SECONDS.toMillis(2L));

        this.plugin = plugin;
        this.passiveEffects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
        this.passiveEffects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        this.passiveEffects.add(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        this.passiveEffects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
    }

    /**
     * Applies the {@link MinerClass} invisibility {@link PotionEffect} depending on the {@link Player}s {@link Location}.
     *
     * @param player
     *            the {@link Player} to apply for
     * @param from
     *            the from {@link Location}
     * @param to
     *            the to {@link Location}
     */
    
    @EventHandler(ignoreCancelled=false, priority=EventPriority.HIGH)
    public void onMinerSpeedClick(PlayerInteractEvent event)
    {
        Action action = event.getAction();
        if (((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK)) &&
                (event.hasItem()) && (event.getItem().getType() == Material.SUGAR))
        {
            if (this.plugin.getPvpClassManager().getEquippedClass(event.getPlayer()) != this) {
                return;
            }
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            long timestamp = this.minerSpeedCooldowns.get(uuid);
            long millis = System.currentTimeMillis();
            long remaining = timestamp == this.minerSpeedCooldowns.getNoEntryValue() ? -1L : timestamp - millis;
            if (remaining > 0L)
            {
                player.sendMessage(ChatColor.RED + "Cannot use Speed Boost for another " + DurationFormatUtils.formatDurationWords(remaining, true, true) + ".");
            }
            else
            {
                ItemStack stack = player.getItemInHand();
                if (stack.getAmount() == 1) {
                    player.setItemInHand(new ItemStack(Material.AIR, 1));
                } else {
                    stack.setAmount(stack.getAmount() - 1);
                }
                player.sendMessage(ChatColor.GREEN + "Speed 3 activated for 10 seconds.");

                this.plugin.getEffectRestorer().setRestoreEffect(player, MINER_SPEED_EFFECT);
                this.minerSpeedCooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + MINER_SPEED_COOLDOWN_DELAY);
            }
        }
    }

    @Override
    public boolean isApplicableFor(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack helmet = playerInventory.getHelmet();
        if (helmet == null || helmet.getType() != Material.IRON_HELMET)
            return false;

        ItemStack chestplate = playerInventory.getChestplate();
        if (chestplate == null || chestplate.getType() != Material.IRON_CHESTPLATE)
            return false;

        ItemStack leggings = playerInventory.getLeggings();
        if (leggings == null || leggings.getType() != Material.IRON_LEGGINGS)
            return false;

        ItemStack boots = playerInventory.getBoots();
        return !(boots == null || boots.getType() != Material.IRON_BOOTS);
    }
}

package raton.meme.hcf.listener;

import org.bukkit.event.player.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;

import com.doctordark.utils.internal.com.doctordark.base.BasePlugin;

import raton.meme.hcf.HCF;

public class KitSigns implements Listener
{
    ItemStack healthpot;
    ItemStack speedpot;
    ItemStack frespot;
    
    private HCF plugin;
    public KitSigns(HCF plugin) {
        this.healthpot = new ItemStack(Material.POTION, 1, (short)16421);
        this.speedpot = new ItemStack(Material.POTION, 1, (short)8226);
        this.frespot = new ItemStack(Material.POTION, 1, (short)8259);
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)) {
            final BlockState state = e.getClickedBlock().getState();
            if (state instanceof Sign) {
                final Sign sign = (Sign)state;
                if (sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "[Kit]")) {
                    String[] fakeLines = Arrays.copyOf(sign.getLines(), 4);
                    final String kit = sign.getLine(1);
                    if (kit.contains("Diamond")) {
                        this.giveDiamondKit(p);
                        fakeLines[0] = ChatColor.RED + "Received your";
                        fakeLines[2] = ChatColor.RED + "class";
                        fakeLines[3] = "";
                    }
                    if (kit.contains("Archer")) {
                        this.giveArcherKit(p);
                        fakeLines[0] = ChatColor.RED + "Received your";
                        fakeLines[2] = ChatColor.RED + "class";
                        fakeLines[3] = "";
                    }
                    if (kit.contains("Bard")) {
                        this.giveBardKit(p);
                        fakeLines[0] = ChatColor.RED + "Received your";
                        fakeLines[2] = ChatColor.RED + "class";
                        fakeLines[3] = "";
                    }
                    if (kit.contains("Builder")) {
                        this.giveBuilderKit(p);
                        fakeLines[0] = ChatColor.RED + "Received your";
                        fakeLines[2] = ChatColor.RED + "class";
                        fakeLines[3] = "";
                    }
                    BasePlugin.getPlugin().getSignHandler().showLines(p, sign, fakeLines, 55L, true);
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onSign(SignChangeEvent e) {
        Player p = e.getPlayer();
        Sign sign = (Sign) e.getBlock().getState();
        String[] lines = sign.getLines();
        if (p.hasPermission("staff.createkitmapsign") && e.getLine(0).equalsIgnoreCase("[Kit]")) {
        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccesfully created a &a" + ChatColor.stripColor(e.getLine(1)) + "&a kit sign!"));
            e.setLine(0, ChatColor.AQUA + "[Kit]");
            e.setLine(1, ChatColor.BLACK + e.getLine(1));
        }
    }
    
    public void giveDiamondKit(final Player p) {
        final ItemStack diamondhelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        diamondhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamondhelmet.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack diamondchestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        diamondchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamondchestplate.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack diamondleggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        diamondleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamondleggings.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack diamondboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        diamondboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamondboots.addEnchantment(Enchantment.DURABILITY, 3);
        diamondboots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        final PlayerInventory pi = p.getInventory();
        pi.setItem(0, sword);
        pi.setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        pi.setItem(2, this.frespot);
        pi.setItem(3, this.speedpot);
        pi.setItem(4, this.healthpot);
        pi.setItem(5, this.healthpot);
        pi.setItem(6, this.healthpot);
        pi.setItem(7, this.healthpot);
        pi.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
        pi.setItem(9, this.healthpot);
        pi.setItem(10, this.healthpot);
        pi.setItem(11, this.healthpot);
        pi.setItem(12, this.healthpot);
        pi.setItem(13, this.healthpot);
        pi.setItem(14, this.healthpot);
        pi.setItem(15, this.healthpot);
        pi.setItem(16, this.healthpot);
        pi.setItem(17, this.speedpot);
        pi.setItem(18, this.healthpot);
        pi.setItem(19, this.healthpot);
        pi.setItem(20, this.healthpot);
        pi.setItem(21, this.healthpot);
        pi.setItem(22, this.healthpot);
        pi.setItem(23, this.healthpot);
        pi.setItem(24, this.healthpot);
        pi.setItem(25, this.speedpot);
        pi.setItem(26, this.speedpot);
        pi.setItem(27, this.healthpot);
        pi.setItem(28, this.healthpot);
        pi.setItem(29, this.healthpot);
        pi.setItem(30, this.healthpot);
        pi.setItem(31, this.healthpot);
        pi.setItem(32, this.healthpot);
        pi.setItem(33, this.healthpot);
        pi.setItem(34, this.speedpot);
        pi.setItem(35, this.speedpot);
        pi.setBoots(diamondboots);
        pi.setLeggings(diamondleggings);
        pi.setChestplate(diamondchestplate);
        pi.setHelmet(diamondhelmet);
        p.updateInventory();
    }
    
    public void giveBardKit(final Player p) {
        final ItemStack goldhelmet = new ItemStack(Material.GOLD_HELMET, 1);
        goldhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        goldhelmet.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack goldchestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        goldchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        goldchestplate.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack goldleggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        goldleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        goldleggings.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack goldboots = new ItemStack(Material.GOLD_BOOTS, 1);
        goldboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        goldboots.addEnchantment(Enchantment.DURABILITY, 3);
        goldboots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        final PlayerInventory pi = p.getInventory();
        pi.setItem(0, sword);
        pi.setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        pi.setItem(2, this.healthpot);
        pi.setItem(3, this.healthpot);
        pi.setItem(4, this.healthpot);
        pi.setItem(5, this.healthpot);
        pi.setItem(6, new ItemStack(Material.SUGAR, 64));
        pi.setItem(7, new ItemStack(Material.BLAZE_POWDER, 32));
        pi.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
        pi.setItem(9, this.healthpot);
        pi.setItem(10, this.healthpot);
        pi.setItem(11, this.healthpot);
        pi.setItem(12, this.healthpot);
        pi.setItem(13, this.healthpot);
        pi.setItem(14, this.healthpot);
        pi.setItem(15, new ItemStack(Material.FEATHER, 64));
        pi.setItem(16, new ItemStack(Material.IRON_INGOT, 64));
        pi.setItem(17, new ItemStack(Material.GHAST_TEAR, 16));
        pi.setItem(18, this.healthpot);
        pi.setItem(19, this.healthpot);
        pi.setItem(20, this.healthpot);
        pi.setItem(21, this.healthpot);
        pi.setItem(22, this.healthpot);
        pi.setItem(23, this.healthpot);
        pi.setItem(24, this.healthpot);
        pi.setItem(25, this.healthpot);
        pi.setItem(26, this.healthpot);
        pi.setItem(27, this.healthpot);
        pi.setItem(28, this.healthpot);
        pi.setItem(29, this.healthpot);
        pi.setItem(30, this.healthpot);
        pi.setItem(31, this.healthpot);
        pi.setItem(32, this.healthpot);
        pi.setItem(33, this.healthpot);
        pi.setItem(34, this.healthpot);
        pi.setItem(35, this.healthpot);
        pi.setBoots(goldboots);
        pi.setLeggings(goldleggings);
        pi.setChestplate(goldchestplate);
        pi.setHelmet(goldhelmet);
        p.updateInventory();
    }
    
    public void giveBuilderKit(final Player p) {
        final ItemStack ironhelm = new ItemStack(Material.IRON_HELMET, 1);
        ironhelm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ironhelm.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack ironchestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ironchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ironchestplate.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack ironleggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ironleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ironleggings.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack ironboots = new ItemStack(Material.IRON_BOOTS, 1);
        ironboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ironboots.addEnchantment(Enchantment.DURABILITY, 3);
        ironboots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 5);
        pickaxe.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
        axe.addEnchantment(Enchantment.DIG_SPEED, 5);
        axe.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack spade = new ItemStack(Material.DIAMOND_SPADE, 1);
        spade.addEnchantment(Enchantment.DIG_SPEED, 5);
        spade.addEnchantment(Enchantment.DURABILITY, 3);
        final PlayerInventory pi = p.getInventory();
        pi.setItem(0, sword);
        pi.setItem(1, pickaxe);
        pi.setItem(2, axe);
        pi.setItem(3, spade);
        pi.setItem(4, new ItemStack(Material.FENCE_GATE, 64));
        pi.setItem(5, new ItemStack(Material.WOOD_PLATE, 64));
        pi.setItem(6, new ItemStack(159, 64, (short)14));
        pi.setItem(7, new ItemStack(Material.SUGAR, 64));
        pi.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
        pi.setItem(9, new ItemStack(17, 64));
        pi.setItem(10, new ItemStack(162, 64));
        pi.setItem(11, new ItemStack(Material.DIRT, 64));
        pi.setItem(12, new ItemStack(22, 64));
        pi.setItem(13, new ItemStack(155, 64, (short)1));
        pi.setItem(14, new ItemStack(Material.QUARTZ_BLOCK, 64));
        pi.setItem(15, new ItemStack(98, 64));
        pi.setItem(16, new ItemStack(Material.COBBLESTONE, 64));
        pi.setItem(17, new ItemStack(Material.STONE, 64));
        pi.setItem(18, new ItemStack(Material.GLOWSTONE, 64));
        pi.setItem(19, new ItemStack(Material.WOOD_STAIRS, 64));
        pi.setItem(20, new ItemStack(Material.COBBLESTONE_STAIRS, 64));
        pi.setItem(21, new ItemStack(159, 64, (short)5));
        pi.setItem(22, new ItemStack(Material.WATER_BUCKET, 1));
        pi.setItem(23, new ItemStack(Material.GLASS, 64));
        pi.setItem(24, new ItemStack(95, 64, (short)13));
        pi.setItem(25, new ItemStack(95, 64, (short)3));
        pi.setItem(26, new ItemStack(96, 64, (short)6));
        pi.setItem(27, new ItemStack(29, 32));
        pi.setItem(28, new ItemStack(33, 32));
        pi.setItem(29, new ItemStack(356, 32));
        pi.setItem(30, new ItemStack(404, 10));
        pi.setItem(31, new ItemStack(Material.HOPPER, 64));
        pi.setItem(32, new ItemStack(Material.REDSTONE, 64));
        pi.setItem(33, new ItemStack(Material.REDSTONE, 64));
        pi.setItem(34, new ItemStack(Material.STRING, 64));
        pi.setItem(35, new ItemStack(Material.STRING, 64));
        pi.setBoots(ironboots);
        pi.setLeggings(ironleggings);
        pi.setChestplate(ironchestplate);
        pi.setHelmet(ironhelm);
        p.updateInventory();
    }
    
    public void giveArcherKit(final Player p) {
        final ItemStack leatherhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        leatherhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherhelmet.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack leatherchestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        leatherchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherchestplate.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack leatherleggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        leatherleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherleggings.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack leatherboots = new ItemStack(Material.LEATHER_BOOTS, 1);
        leatherboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherboots.addEnchantment(Enchantment.DURABILITY, 3);
        leatherboots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        final ItemStack bow = new ItemStack(Material.BOW, 1);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        bow.addEnchantment(Enchantment.DURABILITY, 3);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        final PlayerInventory pi = p.getInventory();
        pi.setItem(0, sword);
        pi.setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        pi.setItem(2, bow);
        pi.setItem(3, this.frespot);
        pi.setItem(4, this.healthpot);
        pi.setItem(5, this.healthpot);
        pi.setItem(6, this.healthpot);
        pi.setItem(7, new ItemStack(Material.SUGAR, 64));
        pi.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
        pi.setItem(9, new ItemStack(Material.ARROW, 1));
        pi.setItem(10, this.healthpot);
        pi.setItem(11, this.healthpot);
        pi.setItem(12, this.healthpot);
        pi.setItem(13, this.healthpot);
        pi.setItem(14, this.healthpot);
        pi.setItem(15, this.healthpot);
        pi.setItem(16, this.healthpot);
        pi.setItem(17, new ItemStack(Material.FEATHER, 64));
        pi.setItem(18, this.healthpot);
        pi.setItem(19, this.healthpot);
        pi.setItem(20, this.healthpot);
        pi.setItem(21, this.healthpot);
        pi.setItem(22, this.healthpot);
        pi.setItem(23, this.healthpot);
        pi.setItem(24, this.healthpot);
        pi.setItem(25, this.healthpot);
        pi.setItem(26, this.healthpot);
        pi.setItem(27, this.healthpot);
        pi.setItem(28, this.healthpot);
        pi.setItem(29, this.healthpot);
        pi.setItem(30, this.healthpot);
        pi.setItem(31, this.healthpot);
        pi.setItem(32, this.healthpot);
        pi.setItem(33, this.healthpot);
        pi.setItem(34, this.healthpot);
        pi.setItem(35, this.healthpot);
        pi.setBoots(leatherboots);
        pi.setLeggings(leatherleggings);
        pi.setChestplate(leatherchestplate);
        pi.setHelmet(leatherhelmet);
        p.updateInventory();
    }
}

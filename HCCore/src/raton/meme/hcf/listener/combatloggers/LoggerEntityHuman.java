package raton.meme.hcf.listener.combatloggers;

import java.util.UUID;
import net.minecraft.server.v1_7_R4.CombatTracker;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IPlayerFileData;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.PlayerList;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import raton.meme.hcf.HCF;
import raton.meme.hcf.ymls.SettingsYML;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LoggerEntityHuman
        extends EntityPlayer
        implements LoggerEntity {
    private BukkitTask removalTask;

    public LoggerEntityHuman(Player player, org.bukkit.World world) {
        this(player, ((CraftWorld)world).getHandle());
    }

    private LoggerEntityHuman(Player player, WorldServer world) {
        super(MinecraftServer.getServer(), world, new GameProfile(player.getUniqueId(), player.getName()), new PlayerInteractManager((World)world));
        Location location = player.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        new FakePlayerConnection(this);
        this.playerConnection.a(x, y, z, yaw, pitch);
        EntityPlayer originPlayer = ((CraftPlayer)player).getHandle();
        this.lastDamager = originPlayer.lastDamager;
        this.invulnerableTicks = originPlayer.invulnerableTicks;
        this.combatTracker = originPlayer.combatTracker;
    }

    protected boolean d(DamageSource source, float amount) {
        if (this.dead || !super.d(source, amount)) {
            return false;
        }
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        super.die(source);
        this.dead = true;
        this.setHealth(0.0f);
        MinecraftServer.getServer().getPlayerList().playerFileData.save((EntityHuman)this);
        return true;
    }

    @Override
    public void postSpawn(HCF plugin) {
        if (this.world.addEntity((Entity)this)) {
            Bukkit.getConsoleSender().sendMessage(String.format((Object)ChatColor.GOLD + "Combat logger of " + this.getName() + " has spawned at %.2f, %.2f, %.2f", this.locX, this.locY, this.locZ));
            MinecraftServer.getServer().getPlayerList().playerFileData.load((EntityHuman)this);
        } else {
            Bukkit.getConsoleSender().sendMessage(String.format((Object)ChatColor.RED + "Combat logger of " + this.getName() + " failed to spawned at %.2f, %.2f, %.2f", this.locX, this.locY, this.locZ));
        }
        this.removalTask = new BukkitRunnable(){

            public void run() {
                MinecraftServer.getServer().getPlayerList().sendAll((Packet)PacketPlayOutPlayerInfo.removePlayer((EntityPlayer)LoggerEntityHuman.this.getBukkitEntity().getHandle()));
                LoggerEntityHuman.this.destroy();
            }
        }.runTaskLater((Plugin)plugin, SettingsYML.COMBAT_LOG_DESPAWN_TICKS);
    }

    public void closeInventory() {
    }

    private void cancelTask() {
        if (this.removalTask != null) {
            this.removalTask.cancel();
            this.removalTask = null;
        }
    }

    public void die(DamageSource damageSource) {
        if (!this.dead) {
            super.die(damageSource);
            Bukkit.getPluginManager().callEvent((Event)new LoggerDeathEvent(this));
            MinecraftServer.getServer().getPlayerList().playerFileData.save((EntityHuman)this);
            this.cancelTask();
        }
    }

    @Override
    public void destroy() {
        if (!this.dead) {
            Bukkit.getPluginManager().callEvent((Event)new LoggerRemovedEvent(this));
            this.dead = true;
            this.cancelTask();
        }
    }

    public void b(int i) {
    }

    public void dropDeathLoot(boolean flag, int i) {
    }

    public boolean a(EntityHuman entityHuman) {
        return super.a(entityHuman);
    }

    public void collide(Entity entity) {
    }

    private static class FakeNetworkManager
            extends NetworkManager {
        private FakeNetworkManager() {
            super(false);
        }

        public int getVersion() {
            return super.getVersion();
        }
    }

    private static class FakePlayerConnection
            extends PlayerConnection {
        private FakePlayerConnection(EntityPlayer entityplayer) {
            super(MinecraftServer.getServer(), (NetworkManager)new FakeNetworkManager(), entityplayer);
        }

        public void disconnect(String reason) {
        }

        public void sendPacket(Packet packet) {
        }
    }

}
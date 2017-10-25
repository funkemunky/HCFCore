package raton.meme.hcf.listener.combatloggers;

import java.util.UUID;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

import raton.meme.hcf.HCF;

public abstract interface LoggerEntity
{
    public abstract void postSpawn(HCF paramHCF);

    public abstract CraftPlayer getBukkitEntity();

    public abstract UUID getUniqueID();

    public abstract void destroy();
}

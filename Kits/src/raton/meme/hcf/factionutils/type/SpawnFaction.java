package raton.meme.hcf.factionutils.type;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import raton.meme.hcf.factionutils.claim.Claim;
import raton.meme.hcf.ymls.SettingsYML;

import java.util.Map;

/**
 * Represents the {@link SpawnFaction}.
 */
public class SpawnFaction extends ClaimableFaction implements ConfigurationSerializable {

    public SpawnFaction() {
        super("Spawn");

        this.safezone = true;
                    }
            
        
    

    public SpawnFaction(Map<String, Object> map) {
        super(map);
    }
    @Override
    public boolean isDeathban() {
        return false;
    }
}
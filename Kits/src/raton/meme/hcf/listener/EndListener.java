package raton.meme.hcf.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EndListener
  implements Listener
{
  private final Location endExitLocation = new Location(Bukkit.getWorld("world"), 0.0D, 75.0D, 200.0D);
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
  public void onPlayerPortal(PlayerPortalEvent event)
  {
    if (event.getCause() == TeleportCause.END_PORTAL) {
      if (event.getTo().getWorld().getEnvironment() == Environment.THE_END) {
        event.setTo(event.getTo().getWorld().getSpawnLocation().clone().add(0.5D, 0.0D, 0.5D));
      } else if (event.getFrom().getWorld().getEnvironment() == Environment.THE_END) {
        event.setTo(this.endExitLocation);
      }
    }
  }
}

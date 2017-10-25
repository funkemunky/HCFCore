package raton.meme.hcf.listener;

import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import raton.meme.hcf.HCF;

import java.util.Random;

public class FastBrewingListener
        implements Listener
{
    public FastBrewingListener(HCF plugin) {}

    private void startUpdate(final Furnace tile, final int increase)
    {
        new BukkitRunnable()
        {
            public void run()
            {
                if ((tile.getCookTime() > 0) || (tile.getBurnTime() > 0))
                {
                    tile.setCookTime((short)(tile.getCookTime() + increase));
                    tile.update();
                }
                else
                {
                    cancel();
                }
            }
        }.runTaskTimer(HCF.getPlugin(), 1L, 1L);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event)
    {
        Random RND = new Random();
        startUpdate((Furnace)event.getBlock().getState(), RND.nextBoolean() ? 1 : 2);
    }
}

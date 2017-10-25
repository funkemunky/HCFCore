package raton.meme.hcf.args;

import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.minecraft.util.org.apache.commons.io.output.ByteArrayOutputStream;
import raton.meme.hcf.HCF;

public class HubCommand implements CommandExecutor{

    private final HCF plugin;
    public HubCommand(final HCF plugin) {
        this.plugin = plugin;
    }

    public static void teleport(Player pl, String input)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(input);
        }
        catch (IOException localIOException) {}
        pl.sendPluginMessage(HCF.getPlugin(), "BungeeCord", b.toByteArray());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        Player p = (Player)sender;
        teleport(p, "lobby");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&oWooooosh!"));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HCF.getPlugin(), new Runnable(){
            public void run(){
                if(p.isOnline()){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cWe couldn't connect to the &4&lHUB&c, try again later."));
                }
            }
        }, 20 * 5);
        return true;
    }

}

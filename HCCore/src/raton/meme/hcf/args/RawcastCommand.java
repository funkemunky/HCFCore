package raton.meme.hcf.args;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RawcastCommand
  implements CommandExecutor
{

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      sender.sendMessage(ChatColor.RED + "Usage: " + "/rawcast");
      return true;
    }
    String arg;
    String requiredNode;
    int position = 0;
    if ((args.length > 2) && ((arg = args[0]).startsWith("-p")))
    {
      int position1 = 1;
      requiredNode = arg.substring(2, arg.length());
    }
    else
    {
      position = 0;
      requiredNode = null;
    }
    String message = StringUtils.join(args, ' ', position, args.length);
    if (message.length() < 3)
    {
      sender.sendMessage(ChatColor.RED + "Broadcasts must be at least 3 characters.");
      return true;
    }
    message = ChatColor.translateAlternateColorCodes('&', message);
    if (requiredNode != null) {
      Bukkit.broadcast(message, requiredNode);
    } else {
      Bukkit.broadcastMessage(message);
    }
    return true;
  }
}

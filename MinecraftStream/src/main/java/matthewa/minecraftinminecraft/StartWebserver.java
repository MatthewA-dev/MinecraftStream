package matthewa.minecraftinminecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartWebserver implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(MinecraftInMinecraft.webserver.isAlive()){
            MinecraftInMinecraft.webserver.kill();
        }
        sender.sendMessage(ChatColor.GREEN + "Starting Server Thread...");
        if(MinecraftInMinecraft.parser.isAlive()){
            MinecraftInMinecraft.parser.kill();
        }
        sender.sendMessage(ChatColor.GREEN + "Starting Parser Thread...");
        MinecraftInMinecraft.redefineThreads();
        MinecraftInMinecraft.webserver.start();
        //MinecraftInMinecraft.parser.start();
        if(sender instanceof Player){
            MinecraftInMinecraft.startingLoc = ((Player) sender).getLocation();
        }else{
            sender.sendMessage("Screen set to 0,0");
            MinecraftInMinecraft.startingLoc = new Location(Bukkit.getServer().getWorlds().get(0),0,0,0);
        }
        return true;
    }
}

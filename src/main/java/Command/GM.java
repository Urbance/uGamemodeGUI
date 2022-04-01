package Command;

import UI.GUI;
import Utils.MSG;
import de.urbance.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GM implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String prefix  = plugin.getConfig().getString("config.Prefix");


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("gmgui.gm.open") || sender.hasPermission("gmgui.gm.*") || sender.hasPermission("gmgui.*")) {
                    GUI.openGUI(sender);
                } else {
                    sender.sendMessage(MSG.builder(prefix + plugin.getConfig().getString("config.messages.NoPermission")));
                }
            } else {
                sender.sendMessage(MSG.collection("cannot_execute_console"));
            }
        }
        return false;
    }


}

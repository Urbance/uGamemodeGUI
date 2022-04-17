package Command;

import UI.GUI;
import Utils.MSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GM implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (!(sender instanceof Player)) {
                MSG.sendError(sender.getName(), "CANNOT_EXECUTE_AS_CONSOLE");
                return false;
            }

            if (!(sender.hasPermission("gmgui.gm.open") || sender.hasPermission("gmgui.gm.*") || sender.hasPermission("gmgui.*"))) {
                MSG.sendError(sender.getName(), "NO_PERMISSION");
                return false;
            }

            GUI.openGUI(sender);
        }
        return false;
    }

}

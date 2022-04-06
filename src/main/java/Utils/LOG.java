package Utils;

import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LOG {

    private static Plugin plugin = Main.getPlugin(Main.class);

    public static void create(String message) {
        if (!(plugin.getConfig().getBoolean("config.sendLogMessages"))) {
            return;
        }
        Bukkit.getLogger().info(message);
    }
}

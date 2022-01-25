package Configs;

import de.urbance.main.Main;
import org.bukkit.plugin.Plugin;

public class Config {
    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final String prefix = Main.prefix;

    public static void setDefaultValues() {
        plugin.getConfig().addDefault("config.no_permission", prefix + "You do not have the permissions to do that!");
        plugin.getConfig().addDefault("config.UpdateNotification", true);
        plugin.saveConfig();
    }

}

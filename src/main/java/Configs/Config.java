package Configs;

import de.urbance.main.Main;
import org.bukkit.plugin.Plugin;

public class Config {
    private static final Plugin plugin = Main.getPlugin(Main.class);

    public static void load() {
        plugin.getConfig().addDefault("config.NoPermission", "You do not have the permissions to do that!");
        plugin.getConfig().addDefault("config.UpdateNotification", true);
        plugin.getConfig().addDefault("config.showCurrentGamemode", true);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

}

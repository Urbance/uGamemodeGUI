package YML;

import de.urbance.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class Config {
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String prefix = Main.prefix;

    public static void setDefaultValues() {
        plugin.getConfig().addDefault("config.no_permission", ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have the permissions to do that!"));
        plugin.saveConfig();
    }

}

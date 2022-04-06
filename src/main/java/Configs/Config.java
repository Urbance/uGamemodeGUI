package Configs;

import de.urbance.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final FileConfiguration config = Main.getPlugin(Main.class).getConfig();

    public static void setValues() {
        config.addDefault("config.UpdateNotification", true);
        config.addDefault("config.showCurrentGamemode", true);
        config.addDefault("config.printPrefix", true);
        config.addDefault("config.Prefix", "&7[&cuGamemodeGUI&7] ");
        config.addDefault("config.sendLogMessages", true);

        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}

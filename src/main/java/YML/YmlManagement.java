package YML;

import de.urbance.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YmlManagement {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static File gui = Main.gui;
    private static Plugin plugin = Main.getPlugin(Main.class);

    public static void save(File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload(File file) {
        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void reloadAll() {
         plugin.reloadConfig();
        try {
            yamlConfiguration.load(gui);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

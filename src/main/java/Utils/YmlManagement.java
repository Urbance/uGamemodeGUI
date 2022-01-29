package Utils;

import Configs.Config;
import Configs.GuiConfig;
import de.urbance.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YmlManagement {
    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static final File gui = Main.gui;
    private static final File config = Main.config;
    private static final Plugin plugin = Main.getPlugin(Main.class);

    public static void save(File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadAll() {
        if (!(gui.exists()) || !(config.exists())) {
            if (!(gui.exists())) {
                GuiConfig.load();
            }
            if (!(config.exists())) {
                Config.load();
            }
            return;
        }

        plugin.reloadConfig();
        reloadConfig(gui);
    }

    public static void reloadConfig(File file) {
        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

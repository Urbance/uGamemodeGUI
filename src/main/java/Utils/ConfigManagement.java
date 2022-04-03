package Utils;

import Configs.Config;
import Configs.GuiConfig;
import Configs.Messages;
import de.urbance.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigManagement {
    private static final YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static final File gui = Main.gui;

    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final File config = new File("plugins//uGamemodeGUI//config.yml");
    private static File messages = Main.messages;
    private static YamlConfiguration messagesConfiguration = Main.messagesConfiguration;

    public static void save(File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadAll() {
        reloadGUIConfiguration();
        reloadMessagesConfiguration();
        reloadConfig();
    }

    private static void reloadConfig() {
        if (!(config.exists())) {
            Config.setValues();
            return;
        }

        plugin.reloadConfig();
        plugin.saveConfig();
    }

    private static void reloadGUIConfiguration() {
        if (!(gui.exists())) {
            GuiConfig.setValues();
            return;
        }

        try {
            yamlConfiguration.load(gui);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void reloadMessagesConfiguration() {
        if (!(messages.exists())) {
            Messages.setValues();
            return;
        }

        try {
            messagesConfiguration.load(messages);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

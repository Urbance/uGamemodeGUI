package de.urbance.main;

import Command.GmGUI;
import Listeners.Listeners;
import YML.Config;
import YML.GUI;
import YML.YmlManagement;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Main extends JavaPlugin {

    public static File gui = new File("plugins//uGamemodeGUI//gui.yml");
    public static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(gui);
    public static String prefix = "§7[§cuGamemodeGUI§7] ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Starting up..");
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        getCommand("gmgui").setExecutor(new GmGUI());

        loadConfig();
        loadGuiConfiguration();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Shutdown..");
    }

    public void loadGuiConfiguration() {
        yamlConfiguration.options().copyDefaults(true);
        YmlManagement.save(gui);
        GUI.setDefaultValues();

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Config.setDefaultValues();

    }
}

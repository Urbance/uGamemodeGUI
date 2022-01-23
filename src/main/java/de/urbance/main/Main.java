package de.urbance.main;

import Command.GmGUI;
import Listeners.Listeners;
import Metrics.Metrics;
import YML.Config;
import YML.GUI;
import YML.YmlManagement;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Main extends JavaPlugin {

    public static File gui = new File("plugins//uGamemodeGUI//gui.yml");
    public static File config = new File("plugins//uGamemodeGUI//config.yml");
    public static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(gui);
    private static String cmdprefix = "§7[§cuGamemodeGUI§7] ";
    public static String prefix = "&7[&cuGamemodeGUI&7] ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(cmdprefix + "Starting up..");
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        getCommand("gmgui").setExecutor(new GmGUI());

        Metrics metrics = new Metrics(this, 14027);

        loadConfig();
        loadGuiConfiguration();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(cmdprefix + "Shutdown..");
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

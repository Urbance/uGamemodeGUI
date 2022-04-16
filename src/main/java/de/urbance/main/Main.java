package de.urbance.main;

import Command.GM;
import Command.GmGUI;
import Configs.Config;
import Configs.GuiConfig;
import Configs.Messages;
import Listeners.Listeners;
import Utils.Metrics;
import Utils.UpdateChecker;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public static boolean currentVersionInUse;
    public static String pluginPrefix = "&7[&cuGamemodeGUI&7] ";
    public static File gui = new File("plugins//uGamemodeGUI//gui.yml");
    public static File messages = new File("plugins//uGamemodeGUI//messages.yml");
    public static YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(messages);
    public static YamlConfiguration guiConfiguration = YamlConfiguration.loadConfiguration(gui);

    @Override
    public void onEnable() {
        getLogger().info("Starting up..");
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        getCommand("gmgui").setExecutor(new GmGUI());
        getCommand("gm").setExecutor(new GM());

        Metrics metrics = new Metrics(this, 14027);

        setupConfigurations();
        checkUpdate();
    }

    private static void setupConfigurations() {
        Config.setValues();
        Messages.setValues();
        GuiConfig.setValues();
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutdown..");
    }

    private void checkUpdate() {
        new UpdateChecker(this, 99422).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version) && getConfig().getBoolean("config.UpdateNotification")) {
                getLogger().info("There is not a new update available.");
                currentVersionInUse = true;
            } else if (getConfig().getBoolean("config.UpdateNotification")){
                getLogger().info("There is a new update available. Check out the plugin page!");
                currentVersionInUse = false;
            }
        });
    }

}

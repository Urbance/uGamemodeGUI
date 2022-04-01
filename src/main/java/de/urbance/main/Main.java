package de.urbance.main;

import Command.GM;
import Command.GmGUI;
import Configs.Config;
import Configs.GuiConfig;
import Listeners.Listeners;
import Utils.Metrics;
import Utils.UpdateChecker;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Main extends JavaPlugin {

    public static File gui = new File("plugins//uGamemodeGUI//gui.yml");
    private static File configFile = new File("plugins//uGamemodeGUI//config.yml");

    public static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(gui);

    @Override
    public void onEnable() {
        getLogger().info("Starting up..");
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        getCommand("gmgui").setExecutor(new GmGUI());
        getCommand("gm").setExecutor(new GM());

        Metrics metrics = new Metrics(this, 14027);

        if (!(configFile.exists())) {
            Config.setConfigDefaultValues();
        }

        checkUpdate();
        GuiConfig.load();
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutdown..");
    }

    private void checkUpdate() {
        new UpdateChecker(this, 99422).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version) && getConfig().getBoolean("config.UpdateNotification")) {
                getLogger().info("There is not a new update available.");
            } else if (getConfig().getBoolean("config.UpdateNotification")){
                getLogger().info("There is a new update available. Check out the plugin page!");
            }
        });
    }

}

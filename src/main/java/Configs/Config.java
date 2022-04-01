package Configs;

import de.urbance.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final FileConfiguration config = Main.getPlugin(Main.class).getConfig();

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    public static void setConfigDefaultValues() {
        config.addDefault("config.UpdateNotification", true);
        config.addDefault("config.showCurrentGamemode", true);
        config.addDefault("config.messages.NoPermission", "You do not have the permissions to do that!");
        config.addDefault("config.printPrefix", true);
        config.addDefault("config.Prefix", "&7[&cuGamemodeGUI&7] ");
        config.addDefault("config.messages.ChangeSurvival", "&7Set gamemode to survival");
        config.addDefault("config.messages.ChangeCreative", "&7Set gamemode to creative");
        config.addDefault("config.messages.ChangeSpectator", "&7Set gamemode to spectator");
        config.addDefault("config.messages.ChangeAdventure", "&7Set gamemode to adventure");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}

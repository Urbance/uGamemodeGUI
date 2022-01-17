package YML;

import de.urbance.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class GUI {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static File gui = Main.gui;

    public static void setDefaultValues() {
        yamlConfiguration.addDefault("gui.title", "GamemodeGUI Menu");
        yamlConfiguration.addDefault("gui.slot.SURVIVAL.name", "&eSurvival");
        yamlConfiguration.addDefault("gui.slot.CREATIVE.name", "&eCreative");
        yamlConfiguration.addDefault("gui.slot.SPECTATOR.name", "&eSpectator");
        yamlConfiguration.addDefault("gui.slot.ADVENTURE.name", "&eAdventure");
        yamlConfiguration.addDefault("gui.slot.EMPTY.name", ChatColor.translateAlternateColorCodes('&', " "));
        YmlManagement.save(gui);
    }

}

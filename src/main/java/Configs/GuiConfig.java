package Configs;

import Utils.MSG;
import Utils.YmlManagement;
import de.urbance.main.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GuiConfig {
    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static final File gui = Main.gui;

    public static void load() {
        yamlConfiguration.addDefault("gui.title", "GamemodeGUI Menu");
        yamlConfiguration.addDefault("gui.slot.SURVIVAL.name", "&eSurvival");
        yamlConfiguration.addDefault("gui.slot.SURVIVAL.item", "APPLE");
        yamlConfiguration.addDefault("gui.slot.CREATIVE.name", "&eCreative");
        yamlConfiguration.addDefault("gui.slot.CREATIVE.item", "GOLDEN_AXE");
        yamlConfiguration.addDefault("gui.slot.SPECTATOR.name", "&eSpectator");
        yamlConfiguration.addDefault("gui.slot.SPECTATOR.item", "SPYGLASS");
        yamlConfiguration.addDefault("gui.slot.ADVENTURE.name", "&eAdventure");
        yamlConfiguration.addDefault("gui.slot.ADVENTURE.item", "IRON_SWORD");
        yamlConfiguration.addDefault("gui.slot.EMPTY.name", MSG.color(" "));
        yamlConfiguration.addDefault("gui.slot.EMPTY.item", "BLUE_STAINED_GLASS_PANE");
        yamlConfiguration.options().copyDefaults(true);
        YmlManagement.save(gui);
    }

}

package Configs;

import de.urbance.main.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {
    private static final YamlConfiguration yamlConfiguration = Main.messagesConfiguration;
    private static final File messages = Main.messages;

    public static void setValues() {
        yamlConfiguration.addDefault("messages.ChangeSurvival", "&7Set gamemode to survival");
        yamlConfiguration.addDefault("messages.ChangeCreative", "&7Set gamemode to creative");
        yamlConfiguration.addDefault("messages.ChangeSpectator", "&7Set gamemode to spectator");
        yamlConfiguration.addDefault("messages.ChangeAdventure", "&7Set gamemode to adventure");
        yamlConfiguration.addDefault("messages.NoPermission", "&7You do not have the permissions to do that!");
        yamlConfiguration.options().copyDefaults(true);
        save();
    }

    private static void save() {
        try {
            yamlConfiguration.save(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Utils;

import de.urbance.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class MessageManagement {
    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    public static String setChatColorTranslation(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ItemStack itemStack(String requiredItem) {
        switch (requiredItem) {
            case "survival":
                requiredItem = yamlConfiguration.getString("gui.slot.SURVIVAL.item");
                break;
            case "creative":
                requiredItem = yamlConfiguration.getString("gui.slot.CREATIVE.item");
                break;
            case "spectator":
                requiredItem = yamlConfiguration.getString("gui.slot.SPECTATOR.item");
                break;
            case "adventure":
                requiredItem = yamlConfiguration.getString("gui.slot.ADVENTURE.item");
                break;
            case "empty":
                requiredItem = yamlConfiguration.getString("gui.slot.EMTPY.item");
                break;
        }

        return new ItemStack(Material.valueOf(requiredItem));
    }
}

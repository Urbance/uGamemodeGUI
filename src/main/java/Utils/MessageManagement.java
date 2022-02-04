package Utils;

import de.urbance.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class MessageManagement {
    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static String prefix = Main.prefix;
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
                requiredItem = yamlConfiguration.getString("gui.slot.EMPTY.item");
                break;
        }

        return new ItemStack(Material.valueOf(requiredItem));
    }

    public static String messageCollection(String message) {
        switch (message) {
            case "updated_material":
                message = prefix + "Updated Material.";
                break;
            case "invalid_argument.setMaterial":
                message = prefix + "Invalid Argument. Use survival/creative/spectator/adventure/empty";
                break;
            case "invalid_argument.setTitle":
                message = prefix + "Invalid Argument. Please type a valid title!";
                break;
            case "cannot_execute_console":
                message = prefix + "You cannot execute the command as a console.";
                break;
            default:
                message = prefix + "Error! Please contact the server owner with the error code. [cannot_locate_message]";
                break;
        }
        return MessageManagement.setChatColorTranslation(message);
    }
}

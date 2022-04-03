package Utils;

import de.urbance.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MSG {
    private static final YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static final String pluginPrefix = Main.pluginPrefix;
    private static Plugin plugin = Main.getPlugin(Main.class);

    public static String createCostumMessage(String message) {
        if (plugin.getConfig().getBoolean("config.printPrefix")) {
            return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config.Prefix") + message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String createMessage(String message) {
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

    public static String collection(String message) {
        switch (message) {
            case "version":
                message = pluginPrefix + "Plugin Version &c" + plugin.getDescription().getVersion();
                break;
            case "updated_name":
                message = pluginPrefix + "Updated Name.";
                break;
            case "updated_material":
                message = pluginPrefix + "Updated Material.";
                break;
            case "updated_title":
                message = pluginPrefix + "Updated Title.";
                break;
            case "invalid_argument":
                message = pluginPrefix + "Invalid Argument.";
                break;
            case "invalid_argument.setMaterial":
                message = pluginPrefix + "Invalid Argument. Pick between survival/creative/spectator/adventure/empty";
                break;
            case "invalid_material.setMaterial":
                message = pluginPrefix + "Invalid Material. Please hold an item in your main hand!";
                break;
            case "incomplete_command.setMaterial":
                message = pluginPrefix + "Incomplete Command. Use /gmgui material [survival/creative/spectator/adventure/empty]";
                break;
            case "invalid_argument.setTitle":
                message = pluginPrefix + "Invalid Argument. Please type a valid title!";
                break;
            case "incomplete_command.setTitle":
                message = pluginPrefix + "Incomplete Command. Use /gmgui title [title]";
                break;
            case "invalid_argument.setName":
                message = pluginPrefix + "Invalid Argument. Please type a valid name!";
                break;
            case "incomplete_command.setName":
                message = pluginPrefix + "Incomplete Command. Use /gmgui name [survival/creative/spectator/adventure/empty] [name]";
                break;
            case "cannot_execute_console":
                message = pluginPrefix + "You cannot execute the command as console.";
                break;
            default:
                message = pluginPrefix + "Error! Please contact the server owner with the error code. [cannot_locate_message]";
                break;
        }
        return MSG.createMessage(message);
    }
}

package Utils;

import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MSG {
    private static final YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static final String pluginPrefix = Main.pluginPrefix;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static YamlConfiguration messagesConfiguration = Main.messagesConfiguration;

    public static String createCostumMessage(String message) {
        if (plugin.getConfig().getBoolean("config.printPrefix")) {
            return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config.Prefix") + message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String createMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(String playerName, String message) { Bukkit.getPlayer(playerName).sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + message)); }

    public static void sendError(String player, String type) {

        switch (type) {
            case "CANNOT_EXECUTE_AS_CONSOLE":
                Bukkit.getLogger().info("You cannot execute the command as console.");
                break;
            case "NO_PERMISSION":
                sendMessage(player, messagesConfiguration.getString("messages.NoPermission"));
                break;
            default:
                sendMessage(player, "&7Whoops! There is an unknown error!");
        }
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
}

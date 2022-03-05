package Command;

import Utils.MessageManagement;
import Utils.YmlManagement;
import de.urbance.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.Prefix;
import org.w3c.dom.Text;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.awt.*;

public class GmGUI implements CommandExecutor {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String prefix = Main.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("gmgui")) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "version":
                        version(args, player);
                        break;
                    case "reload":
                        reload(args, player);
                        break;
                    case "setMaterial":
                        setMaterial(args, player);
                        break;
                    case "setTitle":
                        setTitle(args, player);
                        break;
                    case "setName":
                        setName(args, player);
                        break;
                    default:
                        sender.sendMessage(MessageManagement.messageCollection("invalid_argument"));
                        break;
                }
            } else if (player.hasPermission("gmgui.showHelp") || player.hasPermission("gmgui.*")) {
                helpNotice(player);
            } else {
                player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            }
        }
        return false;
    }

    public static void version(String[] args, Player player) {
        if (args.length == 1) {
            if (player.hasPermission("gmgui.version") || player.hasPermission("gmgui.*")) {
                player.sendMessage(MessageManagement.messageCollection("version"));
            } else {
                player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            }
        }
    }

    public static void reload(String[] args, Player player) {
        if (args.length == 1) {
            if (player.hasPermission("gmgui.reload") || player.hasPermission("gmgui.*")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Configs reloaded"));
                YmlManagement.reloadAll();
            } else {
                player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            }
        }
    }

    public static void setTitle(String[] args, Player player) {
        if (player.hasPermission("gmgui.editGUI") || player.hasPermission("gmgui.*")) {
            if (args.length == 2) {
                yamlConfiguration.set("gui.title", args[1]);
                player.sendMessage(MessageManagement.messageCollection("updated_title"));
                YmlManagement.save(Main.gui);
            } else {
                player.sendMessage(MessageManagement.messageCollection("incomplete_command.setTitle"));
                return;
            }
        } else {
            player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
        }
    }

    public static void setName(String[] args, Player player) {
        if (player.hasPermission("gmgui.editGUI") || player.hasPermission("gmgui.*")) {
            if (args.length == 3) {
                setName(player, args);
            } else {
                player.sendMessage(MessageManagement.messageCollection("incomplete_command.setName"));
                return;
            }
            YmlManagement.save(Main.gui);
            player.sendMessage(MessageManagement.messageCollection("updated_name"));

        } else {
            player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            return;
        }
    }

    public static void setMaterial(String[] args, Player player) {
        String item = player.getInventory().getItemInMainHand().getType().name();
        String path;
        if (player.hasPermission("gmgui.editGUI") || player.hasPermission("gmgui.*")) {
            if (args.length == 2) {
                switch (args[1]) {
                    case "survival":
                        path = "gui.slot.SURVIVAL.item";
                        break;
                    case "creative":
                        path = "gui.slot.CREATIVE.item";
                        break;
                    case "spectator":
                        path = "gui.slot.SPECTATOR.item";
                        break;
                    case "adventure":
                        path = "gui.slot.ADVENTURE.item";
                        break;
                    case "empty":
                        path = "gui.slot.EMPTY.item";
                        break;
                    default:
                        player.sendMessage(MessageManagement.messageCollection("invalid_argument.setMaterial"));
                        return;
                }
                if (item != "AIR" || item == null) {
                    yamlConfiguration.set(path, player.getInventory().getItemInMainHand().getType().name());
                    player.sendMessage(MessageManagement.messageCollection("updated_material"));
                    YmlManagement.save(Main.gui);
                } else {
                    player.sendMessage(MessageManagement.messageCollection("invalid_material.setMaterial"));
                }
            } else {
                player.sendMessage(MessageManagement.messageCollection("incomplete_command.setMaterial"));
            }
        } else {
            player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            return;
        }
    }

    public static void setName(CommandSender sender, String[] args) {
        switch (args[1]) {
            case "survival":
                yamlConfiguration.set("gui.slot.SURVIVAL.name", args[2]);
                break;
            case "creative":
                yamlConfiguration.set("gui.slot.CREATIVE.name", args[2]);
                break;
            case "spectator":
                yamlConfiguration.set("gui.slot.SPECTATOR.name", args[2]);
                break;
            case "adventure":
                yamlConfiguration.set("gui.slot.ADVENTURE.name", args[2]);
                break;
            case "empty":
                yamlConfiguration.set("gui.slot.EMPTY.name", args[2]);
                break;
            default:
                sender.sendMessage(MessageManagement.messageCollection("invalid_argument.setName"));
                return;
        }
    }

    public static void helpNotice(Player player) {
        TextComponent message = new TextComponent(MessageManagement.setChatColorTranslation("&7[&cuGamemodeGUI&7] For help and commands see "));
        message.setColor(net.md_5.bungee.api.ChatColor.GRAY);

        ComponentBuilder component = new ComponentBuilder("Left Click").bold(true).color(net.md_5.bungee.api.ChatColor.GRAY);

        TextComponent pluginPage = new TextComponent(MessageManagement.setChatColorTranslation("plugin page"));
        pluginPage.setColor(net.md_5.bungee.api.ChatColor.RED);
        pluginPage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component.create()));
        pluginPage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/ugamemodegui-configurable-1-18-open-source.99422/"));
        pluginPage.setBold(true);

        message.addExtra(pluginPage);

        player.spigot().sendMessage(message);
    }

}

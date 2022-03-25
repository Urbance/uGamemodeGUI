package Command;

import Utils.MSG;
import Utils.YmlManagement;
import de.urbance.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.chat.TextComponent;

public class GmGUI implements CommandExecutor {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String prefix = Main.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gmgui")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("gmgui.*") || player.hasPermission("gmgui.modify")) {
                    if (args.length > 0) {
                        switch (args[0]) {
                            case "version":
                                version(args, player);
                                break;
                            case "reload":
                                reload(args, player);
                                break;
                            case "material":
                                setMaterial(args, player);
                                break;
                            case "title":
                                setTitle(args, player);
                                break;
                            case "name":
                                setName(args, player);
                                break;
                            default:
                                sender.sendMessage(MSG.collection("invalid_argument"));
                                break;
                        }
                    } else {
                        player.sendMessage(MSG.color("&7========= &6Help Page&7 =========\n" +
                                "&c/gm&7 - Open GUI\n" +
                                "&c/gmgui reload&7 - Reload plugin\n" +
                                "&c/gmgui version&7 - Show current plugin verison\n" +
                                "&c/gmgui material [gamemode/empty]&7 - Set material from your main hand\n" +
                                "&c/gmgui name [gamemode] (name)&7 - Set new material name\n" +
                                "&c/gmgui title (title)&7 - Set new GUI title\n" +
                                "&7For detailed help check out the plugin page!\n" +
                                "&7========= &6Help Page&7 ========="));
                    }
                } else {
                    player.sendMessage(MSG.color(prefix + plugin.getConfig().getString("config.NoPermission")));
                }

            } else {
                sender.sendMessage(MSG.color(prefix + MSG.collection("cannot_execute_console")));
            }
        }
        return false;
    }

    public static void version(String[] args, Player player) {
        if (args.length == 1) {
            player.sendMessage(MSG.collection("version"));
        }
    }

    public static void reload(String[] args, Player player) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Configs reloaded"));
            YmlManagement.reloadAll();
        }
    }

    public static void setTitle(String[] args, Player player) {
        if (args.length == 2) {
            yamlConfiguration.set("gui.title", args[1]);
            player.sendMessage(MSG.collection("updated_title"));
            YmlManagement.save(Main.gui);
        } else {
            player.sendMessage(MSG.collection("incomplete_command.setTitle"));
            return;
        }
    }

    public static void setName(String[] args, Player player) {
        if (args.length == 3) {
            setName(player, args);
        } else {
            player.sendMessage(MSG.collection("incomplete_command.setName"));
            return;
        }
        YmlManagement.save(Main.gui);
        player.sendMessage(MSG.collection("updated_name"));
    }

    public static void setMaterial(String[] args, Player player) {
        String item = player.getInventory().getItemInMainHand().getType().name();
        String path;
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
                    player.sendMessage(MSG.collection("invalid_argument.setMaterial"));
                    return;
            }
            if (item != "AIR" || item == null) {
                yamlConfiguration.set(path, player.getInventory().getItemInMainHand().getType().name());
                player.sendMessage(MSG.collection("updated_material"));
                YmlManagement.save(Main.gui);
            } else {
                player.sendMessage(MSG.collection("invalid_material.setMaterial"));
            }
        } else {
            player.sendMessage(MSG.collection("incomplete_command.setMaterial"));
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
                sender.sendMessage(MSG.collection("invalid_argument.setName"));
                return;
        }
    }

}

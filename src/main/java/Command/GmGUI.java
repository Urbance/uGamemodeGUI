package Command;

import Utils.ConfigManagement;
import Utils.MSG;
import de.urbance.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class GmGUI implements CommandExecutor, TabCompleter {
    private static YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String pluginPrefix = Main.pluginPrefix;

    YamlConfiguration messagesConfiguration = Main.messagesConfiguration;

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
                        createAndSendHelpMessage(player);
                    }
                } else {
                    player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.NoPermission")));
                }

            } else {
                sender.sendMessage(MSG.createMessage(MSG.collection("cannot_execute_console")));
            }
        }
        return false;
    }

    public static void version(String[] args, Player player) {
        if (!(args.length == 1)) {
            return;
        }

        String currentVersionStatus = "&2You have the newest version!";
        if (!Main.currentVersionInUse) {
            currentVersionStatus = "&cYou have an outdated version!";
        }

        player.sendMessage(MSG.createMessage(pluginPrefix + "Plugin Version &6" + plugin.getDescription().getVersion() + " &7- " + currentVersionStatus));
    }

    public void reload(String[] args, Player player) {
        if (!(args.length == 1)) {
        }

        ConfigManagement.reloadAll();
        plugin.reloadConfig();

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "&7Configs reloaded"));
    }

    public static void setTitle(String[] args, Player player) {
        if (args.length == 2) {
            yamlConfiguration.set("gui.title", args[1]);
            player.sendMessage(MSG.collection("updated_title"));
            ConfigManagement.save(Main.gui);
        } else {
            player.sendMessage(MSG.collection("incomplete_command.setTitle"));
        }
    }

    public static void setName(String[] args, Player player) {
        if (!(args.length == 3)) {
            player.sendMessage(MSG.createMessage(pluginPrefix + "&7Incomplete Command. Use /gmgui name [survival/creative/spectator/adventure] (name)"));
            return;
        }

        if (!(args[1].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("adventure"))) {
            player.sendMessage(MSG.createMessage(pluginPrefix + "&7Invalid Argument! Please use survival/creative/spectator/adventure instead of &6" + args[1] + "&7!"));
            return;
        }

        setName(player, args);
        ConfigManagement.save(Main.gui);
        player.sendMessage(MSG.collection("updated_name"));
    }

    public static void setMaterial(String[] args, Player player) {
        String item = player.getInventory().getItemInMainHand().getType().name();
        String path = null;
        
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
            }
            if (item != "AIR" || item == null) {
                yamlConfiguration.set(path, player.getInventory().getItemInMainHand().getType().name());
                player.sendMessage(MSG.collection("updated_material"));
                ConfigManagement.save(Main.gui);
            } else {
                player.sendMessage(MSG.collection("invalid_material.setMaterial"));
            }
        } else {
            player.sendMessage(MSG.collection("incomplete_command.setMaterial"));
        }
    }

    private static void setName(CommandSender sender, String[] args) {
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
            default:
                sender.sendMessage(MSG.collection("invalid_argument.setName"));
        }
    }

    private static void createAndSendHelpMessage(Player player) {
        player.sendMessage(MSG.createMessage("&7========= &6Help Page&7 =========\n" +
                "&c/gm&7 - Open GUI\n" +
                "&c/gmgui reload&7 - Reload plugin\n" +
                "&c/gmgui version&7 - Show current plugin verison\n" +
                "&c/gmgui material [gamemode/empty]&7 - Set material from your main hand\n" +
                "&c/gmgui name [gamemode] (name)&7 - Set new material name\n" +
                "&c/gmgui title (title)&7 - Set new GUI title\n" +
                " \n"));

        TextComponent captionChangelog = new TextComponent("§7Latest Changelog: ");

        TextComponent linkChangelog = new TextComponent("Open Changelogs");
        linkChangelog.setColor(ChatColor.YELLOW);
        linkChangelog.setItalic(true);
        linkChangelog.setBold(true);
        linkChangelog.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/ugamemodegui-configurable-1-18-open-source.99422/updates"));
        linkChangelog.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClick!").create()));

        captionChangelog.addExtra(linkChangelog);
        player.spigot().sendMessage(captionChangelog);


        TextComponent captionSupport = new TextComponent("§7Get Support: ");

        TextComponent linkSupport = new TextComponent("Join my Discord");
        linkSupport.setColor(ChatColor.YELLOW);
        linkSupport.setBold(true);
        linkSupport.setItalic(true);
        linkSupport.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/hDqPms3MbH"));
        linkSupport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClick!").create()));

        captionSupport.addExtra(linkSupport);
        player.spigot().sendMessage(captionSupport);

        player.sendMessage(MSG.createMessage("&7========= &6Help Page&7 ========="));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        tabComplete.clear();

        if (!(sender.hasPermission("gmgui.*") || !(sender.hasPermission("gmgui.modify")))) {
            return tabComplete;
        }

        if (args.length == 1) {
           tabComplete.add("reload");
           tabComplete.add("version");
           tabComplete.add("material");
           tabComplete.add("name");
           tabComplete.add("title");
        }
        if (args.length == 2) {
            switch(args[0]) {
                case "material":
                    tabComplete.add("survival");
                    tabComplete.add("creative");
                    tabComplete.add("spectator");
                    tabComplete.add("adventure");
                    tabComplete.add("empty");
                    break;
                case "name":
                    tabComplete.add("survival");
                    tabComplete.add("creative");
                    tabComplete.add("spectator");
                    tabComplete.add("adventure");
                    break;
            }
        }
        return tabComplete;
    }
}

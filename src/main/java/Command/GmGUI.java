package Command;

import Utils.ConfigManagement;
import Utils.MSG;
import de.urbance.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class GmGUI implements CommandExecutor, TabCompleter {
    private static YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String pluginPrefix = Main.pluginPrefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gmgui")) {
            if (!(sender instanceof Player)) {
                MSG.sendError(sender.getName(), "CANNOT_EXECUTE_AS_CONSOLE");
                return false;
            }

            if (!(sender.hasPermission("gmgui.*") || sender.hasPermission("gmgui.modify"))) {
                MSG.sendError(sender.getName(), "NO_PERMISSION");
                return false;
            }

            if (!(args.length > 0)) {
                sendInfoPage(sender);
                return false;
            }

            Player player = (Player) sender;
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
                    MSG.sendMessage(player.getName(), "&7Invalid argument! Use &6/gmgui &7for more informations!");
                    return false;
            }
        }
        return false;
    }

    private static void version(String[] args, Player player) {
        if (!(args.length == 1)) {
            return;
        }

        String currentVersionStatus = "&2You have the newest version!";

        if (!Main.currentVersionInUse) {
            currentVersionStatus = "&cYou have an outdated version!";
        }
        player.sendMessage(MSG.createMessage(pluginPrefix + "Plugin Version &6" + plugin.getDescription().getVersion() + " &7- " + currentVersionStatus));
    }

    private void reload(String[] args, Player player) {
        if (!(args.length == 1)) {
            return;
        }

        ConfigManagement.reloadAll();
        plugin.reloadConfig();

        MSG.sendMessage(player.getName(), "&7Configs reloaded");
    }

    private static void setTitle(String[] args, Player player) {
        if (!(args.length == 2)) {
            MSG.sendMessage(player.getName(), "&7Incomplete Command! Use /gmgui title (title)");
            return;
        }

        yamlConfiguration.set("gui.title", args[1]);
        MSG.sendMessage(player.getName(), "&7The GUI has now the title &r" + args[1] + "&7!");
        ConfigManagement.save(Main.gui);
    }

    private static void setName(String[] args, Player player) {
        if (!(args.length == 3)) {
            MSG.sendMessage(player.getName(), "&7Incomplete Command. Use /gmgui name [survival/creative/spectator/adventure] (name)");
            return;
        }

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
                MSG.sendMessage(player.getName(), "&7Invalid Argument! Please use survival/creative/spectator/adventure instead of &6" + args[1] + "&7!");
                return;
        }

        ConfigManagement.save(Main.gui);
        MSG.sendMessage(player.getName(), "&7Updated name to &r" + args[2] + "&7!");
    }

    private static void setMaterial(String[] args, Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        String path;

        if (!(args.length == 2)) {
            MSG.sendMessage(player.getName(), "Incomplete Command! Use /gmgui material [survival/creative/spectator/adventure/empty]");
            return;
        }

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
               MSG.sendMessage(player.getName(), "&7Invalid Argument. Pick between survival/creative/spectator/adventure/empty");
               return;
        }

        if (itemInMainHand.getType().equals(Material.AIR)) {
            MSG.sendMessage(player.getName(), "Invalid Material. Please hold an item in your main hand!");
            return;
        }

        yamlConfiguration.set(path, itemInMainHand.getType().name());
        MSG.sendMessage(player.getName(), "&7Updated material to &6" + itemInMainHand.getType().name() + "&7!");
        ConfigManagement.save(Main.gui);
    }

    private static void sendInfoPage(CommandSender sender) {
        sender.sendMessage(MSG.createMessage("&7========= &6Help Page&7 =========\n" +
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
        sender.spigot().sendMessage(captionChangelog);

        TextComponent captionSupport = new TextComponent("§7Get Support: ");

        TextComponent linkSupport = new TextComponent("Join my Discord");
        linkSupport.setColor(ChatColor.YELLOW);
        linkSupport.setBold(true);
        linkSupport.setItalic(true);
        linkSupport.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/hDqPms3MbH"));
        linkSupport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClick!").create()));

        captionSupport.addExtra(linkSupport);
        sender.spigot().sendMessage(captionSupport);

        sender.sendMessage(MSG.createMessage("&7========= &6Help Page&7 ========="));
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

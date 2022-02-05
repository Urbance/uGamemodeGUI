package Command;

import Utils.MessageManagement;
import Utils.YmlManagement;
import de.urbance.main.Main;
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
import org.checkerframework.checker.units.qual.Prefix;

public class GmGUI implements CommandExecutor {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String prefix = Main.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("gmgui")) {
            if (args.length > 0) {
                moreThanTwoArgs(sender, args);
            } else if (sender.hasPermission("gmgui.open") || sender.hasPermission("gmgui.*")) {
                if (sender instanceof Player) {
                    openGUI(player);
                } else {
                    sender.sendMessage(MessageManagement.messageCollection("cannot_execute_console"));
                }
            } else {
                sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
            }
        }
        return false;
    }

    public static void moreThanTwoArgs(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        switch (args[0]) {
            case "reload":
                if (args.length == 1) {
                    if (sender.hasPermission("gmgui.reload") || sender.hasPermission("gmgui.*")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Configs reloaded"));
                        YmlManagement.reloadAll();
                    } else {
                        sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                    }
                }
                break;
            case "setMaterial":
                if (sender.hasPermission("gmgui.editGUI") || sender.hasPermission("gmgui.*")) {
                    if (args.length == 2) {
                        setMaterial(player, args);
                    } else {
                        sender.sendMessage(MessageManagement.messageCollection("incomplete_command.setMaterial"));
                    }
                } else {
                    sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                    return;
                }
                break;
            case "setTitle":
                if (sender.hasPermission("gmgui.editGUI") || sender.hasPermission("gmgui.*")) {
                    if (args.length == 2) {
                        yamlConfiguration.set("gui.title", args[1]);
                        sender.sendMessage(MessageManagement.messageCollection("updated_title"));
                        YmlManagement.save(Main.gui);
                    } else {
                        sender.sendMessage(MessageManagement.messageCollection("incomplete_command.setTitle"));
                        return;
                    }
                } else {
                    sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                }
                break;
            case "setName":
                if (sender.hasPermission("gmgui.editGUI") || sender.hasPermission("gmgui.*")) {
                    if (args.length == 3) {
                        setName(sender, args);
                    } else {
                        sender.sendMessage(MessageManagement.messageCollection("incomplete_command.setName"));
                        return;
                    }
                    YmlManagement.save(Main.gui);
                    player.sendMessage(MessageManagement.messageCollection("updated_name"));

                } else {
                    sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                    return;
                }
                break;
            default:
                sender.sendMessage(MessageManagement.messageCollection("invalid_argument"));
                return;
        }
    }

    public static void setMaterial(Player player, String[] args) {
        String item = player.getInventory().getItemInMainHand().getType().name();
        String path;

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
    public static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.title")));

        ItemStack survival = new ItemStack(MessageManagement.itemStack("survival"));
        ItemStack creative = new ItemStack(MessageManagement.itemStack("creative"));
        ItemStack spectator = new ItemStack(MessageManagement.itemStack("spectator"));
        ItemStack adventure = new ItemStack(MessageManagement.itemStack("adventure"));
        ItemStack empty = new ItemStack(MessageManagement.itemStack("empty"));

        ItemMeta survivalMeta = survival.getItemMeta();
        ItemMeta creativeMeta = creative.getItemMeta();
        ItemMeta spectatorMeta = spectator.getItemMeta();
        ItemMeta adventureMeta = adventure.getItemMeta();
        ItemMeta emptyMeta = empty.getItemMeta();

        survivalMeta.setDisplayName(MessageManagement.setChatColorTranslation((yamlConfiguration.getString("gui.slot.SURVIVAL.name"))));
        creativeMeta.setDisplayName(MessageManagement.setChatColorTranslation((yamlConfiguration.getString("gui.slot.CREATIVE.name"))));
        spectatorMeta.setDisplayName(MessageManagement.setChatColorTranslation((yamlConfiguration.getString("gui.slot.SPECTATOR.name"))));
        adventureMeta.setDisplayName(MessageManagement.setChatColorTranslation((yamlConfiguration.getString("gui.slot.ADVENTURE.name"))));
        emptyMeta.setDisplayName(" ");

        survival.setItemMeta(survivalMeta);
        creative.setItemMeta(creativeMeta);
        spectator.setItemMeta(spectatorMeta);
        adventure.setItemMeta(adventureMeta);
        empty.setItemMeta(emptyMeta);

        int slot = 0;
        do {
            if (inv.getItem(slot) == null) {
                inv.setItem(slot, empty);
            }
            slot = slot + 1;
        } while (slot < 27);

        inv.setItem(10, survival);
        inv.setItem(12, creative);
        inv.setItem(14, spectator);
        inv.setItem(16, adventure);
        player.openInventory(inv);
    }

}

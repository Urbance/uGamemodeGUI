package Command;

import YML.YmlManagement;
import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class GmGUI implements CommandExecutor {
    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static Plugin plugin = Main.getPlugin(Main.class);
    private static String prefix = Main.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("gmgui")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("gmgui.reload")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cConfigs reloaded"));
                        YmlManagement.reloadAll();

                    } else {
                        sender.sendMessage(setDisplayName(prefix + plugin.getConfig().getString("config.no_permission")));
                    }
                }
            } else {
                if (sender.hasPermission("gmgui.open")) {
                    Player player = (Player) sender;
                    Inventory inv = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("gui.title")));

                    ItemStack survival = new ItemStack(Material.APPLE);
                    ItemStack creative = new ItemStack(Material.GOLDEN_AXE);
                    ItemStack spectator = new ItemStack(Material.SPYGLASS);
                    ItemStack adventure = new ItemStack(Material.IRON_SWORD);
                    ItemStack empty = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

                    ItemMeta survivalMeta = survival.getItemMeta();
                    ItemMeta creativeMeta = creative.getItemMeta();
                    ItemMeta spectatorMeta = spectator.getItemMeta();
                    ItemMeta adventureMeta = adventure.getItemMeta();
                    ItemMeta emptyMeta = empty.getItemMeta();

                    survivalMeta.setDisplayName(setDisplayName(yamlConfiguration.getString("gui.slot.SURVIVAL.name")));
                    creativeMeta.setDisplayName(setDisplayName(yamlConfiguration.getString("gui.slot.CREATIVE.name")));
                    spectatorMeta.setDisplayName(setDisplayName(yamlConfiguration.getString("gui.slot.SPECTATOR.name")));
                    adventureMeta.setDisplayName(setDisplayName(yamlConfiguration.getString("gui.slot.ADVENTURE.name")));
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

                } else {
                    sender.sendMessage(setDisplayName(prefix + plugin.getConfig().getString("config.no_permission")));
                }
            }
        }
        return false;
    }

    public String setDisplayName(String displayname) {
        return ChatColor.translateAlternateColorCodes('&', displayname);
    }

}

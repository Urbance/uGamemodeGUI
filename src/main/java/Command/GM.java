package Command;

import Utils.MessageManagement;
import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class GM implements CommandExecutor {
    YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    String prefix = Main.prefix;
    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    if (sender.hasPermission("gmgui.gm.open") || sender.hasPermission("gmgui.gm.*") || sender.hasPermission("gmgui.*")) {
                        Player player = (Player) sender;
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
                    } else {
                        sender.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                    }
                }
            } else {
                sender.sendMessage(MessageManagement.messageCollection("cannot_execute_console"));
            }
        }
        return false;
    }
}

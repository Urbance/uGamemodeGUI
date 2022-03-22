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
import org.bukkit.inventory.ItemFlag;
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
                        Inventory inventory = Bukkit.createInventory(player, 27, MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.title")));
                        setEmpty(inventory);
                        itemGenerator(MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.slot.SURVIVAL.name")), MessageManagement.itemStack("survival"), 10, inventory);
                        itemGenerator(MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.slot.CREATIVE.name")), MessageManagement.itemStack("creative"), 12, inventory);
                        itemGenerator(MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.slot.SPECTATOR.name")), MessageManagement.itemStack("spectator"), 14, inventory);
                        itemGenerator(MessageManagement.setChatColorTranslation(yamlConfiguration.getString("gui.slot.ADVENTURE.name")), MessageManagement.itemStack("adventure"), 16, inventory);

                        player.openInventory(inventory);
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

    private void itemGenerator(String itemName, ItemStack itemStack, Integer slot, Inventory inventory) {
        itemStack = new ItemStack(itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(itemName);

        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private void setEmpty(Inventory inventory) {
        ItemStack empty = new ItemStack(MessageManagement.itemStack("empty"));
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        empty.setItemMeta(emptyMeta);
        
        int slot = 0;
        do {
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, empty);
            }
            slot = slot + 1;
        } while (slot < 27);
    }
}

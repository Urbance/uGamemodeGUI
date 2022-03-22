package Command;

import Utils.MSG;
import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
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
                if (sender.hasPermission("gmgui.gm.open") || sender.hasPermission("gmgui.gm.*") || sender.hasPermission("gmgui.*")) {
                    Player player = (Player) sender;

                    Inventory inventory = Bukkit.createInventory(player, 27, MSG.color(yamlConfiguration.getString("gui.title")));

                    setEmpty(inventory);
                    itemGenerator(MSG.color(yamlConfiguration.getString("gui.slot.SURVIVAL.name")), MSG.itemStack("survival"), 10, inventory, player);
                    itemGenerator(MSG.color(yamlConfiguration.getString("gui.slot.CREATIVE.name")), MSG.itemStack("creative"), 12, inventory, player);
                    itemGenerator(MSG.color(yamlConfiguration.getString("gui.slot.SPECTATOR.name")), MSG.itemStack("spectator"), 14, inventory, player);
                    itemGenerator(MSG.color(yamlConfiguration.getString("gui.slot.ADVENTURE.name")), MSG.itemStack("adventure"), 16, inventory, player);

                    player.openInventory(inventory);
                } else {
                    sender.sendMessage(MSG.color(prefix + plugin.getConfig().getString("config.NoPermission")));
                }
            } else {
                sender.sendMessage(MSG.collection("cannot_execute_console"));
            }
        }
        return false;
    }

    private void itemGenerator(String itemName, ItemStack itemStack, Integer slot, Inventory inventory, Player player) {
        itemStack = new ItemStack(itemStack);
        setEntchantEffect(itemStack, player);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(itemName);

        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private void setEntchantEffect(ItemStack itemStack, Player player) {
        GameMode gamemode = player.getGameMode();
        Material material = itemStack.getType();

        if (plugin.getConfig().getBoolean("config.showCurrentGamemode")) {
            if (gamemode.equals(GameMode.SURVIVAL) && material == MSG.itemStack("survival").getType() || gamemode.equals(GameMode.CREATIVE) && material == MSG.itemStack("creative").getType() || gamemode.equals(GameMode.SPECTATOR) && material == MSG.itemStack("spectator").getType() || gamemode.equals(GameMode.ADVENTURE) && material == MSG.itemStack("adventure").getType()) {
                itemStack.addUnsafeEnchantment(Enchantment.THORNS, 1);
            }
        }
    }

    private void setEmpty(Inventory inventory) {
        ItemStack empty = new ItemStack(MSG.itemStack("empty"));
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

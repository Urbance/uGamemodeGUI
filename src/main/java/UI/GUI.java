package UI;

import Utils.MSG;
import de.urbance.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class GUI {
    static YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    static Plugin plugin = Main.getPlugin(Main.class);
    public static void openGUI(CommandSender sender) {

        Player player = (Player) sender;

        Inventory inventory = Bukkit.createInventory(player, 27, MSG.createMessage(yamlConfiguration.getString("gui.title")));

        setEmpty(inventory);
        itemGenerator(MSG.createMessage(yamlConfiguration.getString("gui.slot.SURVIVAL.name")), MSG.itemStack("survival"), 10, inventory, player);
        itemGenerator(MSG.createMessage(yamlConfiguration.getString("gui.slot.CREATIVE.name")), MSG.itemStack("creative"), 12, inventory, player);
        itemGenerator(MSG.createMessage(yamlConfiguration.getString("gui.slot.SPECTATOR.name")), MSG.itemStack("spectator"), 14, inventory, player);
        itemGenerator(MSG.createMessage(yamlConfiguration.getString("gui.slot.ADVENTURE.name")), MSG.itemStack("adventure"), 16, inventory, player);

        player.openInventory(inventory);
    }
    private static void itemGenerator(String itemName, ItemStack itemStack, Integer slot, Inventory inventory, Player player) {
        itemStack = new ItemStack(itemStack);
        setEntchantEffect(itemStack, player);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(itemName);

        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private static void setEntchantEffect(ItemStack itemStack, Player player) {
        GameMode gamemode = player.getGameMode();
        Material material = itemStack.getType();

        if (!plugin.getConfig().getBoolean("config.showCurrentGamemode")) {
            return;
        }
        if (gamemode.equals(GameMode.SURVIVAL) && material == MSG.itemStack("survival").getType() || gamemode.equals(GameMode.CREATIVE) && material == MSG.itemStack("creative").getType() || gamemode.equals(GameMode.SPECTATOR) && material == MSG.itemStack("spectator").getType() || gamemode.equals(GameMode.ADVENTURE) && material == MSG.itemStack("adventure").getType()) {
            itemStack.addUnsafeEnchantment(Enchantment.THORNS, 1);
        }
    }

    private static void setEmpty(Inventory inventory) {
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

package Listeners;

import Utils.MessageManagement;
import Utils.UpdateChecker;
import de.urbance.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Listeners implements Listener {

    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static final String prefix = Main.prefix;

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack currentItem = event.getCurrentItem();

            if(!(event.getCurrentItem() == null)) {

                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.SURVIVAL.item")) && event.getSlot() == 10) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to survival"));
                    player.closeInventory();
                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.CREATIVE.item")) && event.getSlot() == 12) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to creative"));
                    player.closeInventory();
                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.SPECTATOR.item")) && event.getSlot() == 14) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to spectator"));
                    player.closeInventory();
                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.ADVENTURE.item")) && event.getSlot() == 16) {
                    player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to adventure"));
                    player.setGameMode(GameMode.ADVENTURE);
                    player.closeInventory();
                }
            } else {
                return;
            }
            event.setCancelled(true);
        }

    }
}

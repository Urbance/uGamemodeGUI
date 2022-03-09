package Listeners;

import Utils.MessageManagement;
import de.urbance.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {

    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static final String prefix = Main.prefix;
    private static final Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack currentItem = event.getCurrentItem();

            if(!(event.getCurrentItem() == null)) {
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.SURVIVAL.item")) && event.getSlot() == 10) {
                    if (player.hasPermission("gmgui.gm.survival") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to survival"));
                        player.closeInventory();
                    } else {
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                        player.closeInventory();
                    }
                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.CREATIVE.item")) && event.getSlot() == 12) {
                    if (player.hasPermission("gmgui.gm.creative") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to creative"));
                        player.closeInventory();
                    } else {
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                        player.closeInventory();
                    }

                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.SPECTATOR.item")) && event.getSlot() == 14) {
                    if (player.hasPermission("gmgui.gm.spectator") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to spectator"));
                        player.closeInventory();
                    } else {
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                        player.closeInventory();
                    }

                }
                if (currentItem.getType() == Material.valueOf(yamlConfiguration.getString("gui.slot.ADVENTURE.item")) && event.getSlot() == 16) {
                    if (player.hasPermission("gmgui.gm.adventure") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + "Set gamemode to adventure"));
                        player.setGameMode(GameMode.ADVENTURE);
                        player.closeInventory();
                    } else {
                        player.sendMessage(MessageManagement.setChatColorTranslation(prefix + plugin.getConfig().getString("config.NoPermission")));
                        player.closeInventory();
                    }
                }
            } else {
                return;
            }
            event.setCancelled(true);
        }

    }

}

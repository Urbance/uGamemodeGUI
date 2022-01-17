package Listeners;

import de.urbance.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class Listeners implements Listener {

    private static YamlConfiguration yamlConfiguration = Main.yamlConfiguration;

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            Player player = (Player) event.getWhoClicked();

            if(!(event.getCurrentItem() == null)) {
                if (event.getCurrentItem().getType().equals(Material.APPLE)) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getType().equals(Material.GOLDEN_AXE)) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getType().equals(Material.SPYGLASS)) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
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

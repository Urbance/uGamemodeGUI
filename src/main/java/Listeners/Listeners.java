package Listeners;

import Utils.MSG;
import de.urbance.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {

    private static final YamlConfiguration yamlConfiguration = Main.yamlConfiguration;
    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final String prefix = plugin.getConfig().getString("config.Prefix");

    @EventHandler
    public void blockItemMove(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void handleItemStackLogic(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            if (event.getCurrentItem() == null) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            Material material = event.getCurrentItem().getType();
            Integer slot = event.getSlot();

            handleGamemodeItemsLogic(player, material, slot);
        }
    }

    private void handleGamemodeItemsLogic(Player player, Material material, Integer slot) {
        if (material == Material.valueOf(yamlConfiguration.getString("gui.slot.SURVIVAL.item")) && slot.equals(10)) {
            if (player.hasPermission("gmgui.gm.survival") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(MSG.builder(plugin.getConfig().getString("config.messages.ChangeSurvival")));
            } else {
                player.sendMessage(MSG.builder(prefix + plugin.getConfig().getString("config.messages.NoPermission")));
            }
            player.closeInventory();
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.CREATIVE.item"))) && slot.equals(12)) {
            if (player.hasPermission("gmgui.gm.creative") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(MSG.builder(plugin.getConfig().getString("config.messages.ChangeCreative")));
            } else {
                player.sendMessage(MSG.builder(prefix + plugin.getConfig().getString("config.messages.NoPermission")));
            }
            player.closeInventory();
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.SPECTATOR.item"))) && slot.equals(14)) {
            if (player.hasPermission("gmgui.gm.spectator") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(MSG.builder(plugin.getConfig().getString("config.messages.ChangeSpectator")));

            } else {
                player.sendMessage(MSG.builder(prefix + plugin.getConfig().getString("config.messages.NoPermission")));
            }
            player.closeInventory();
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.ADVENTURE.item"))) && slot.equals(16)) {
            if (player.hasPermission("gmgui.gm.adventure") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*")) {
                player.sendMessage(MSG.builder(plugin.getConfig().getString("config.messages.ChangeAdventure")));
                player.setGameMode(GameMode.ADVENTURE);
            } else {
                player.sendMessage(MSG.builder(prefix + plugin.getConfig().getString("config.messages.NoPermission")));
            }
            player.closeInventory();
        }
    }

}

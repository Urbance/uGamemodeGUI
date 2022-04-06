package Listeners;

import Utils.LOG;
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

    private static final YamlConfiguration yamlConfiguration = Main.guiConfiguration;
    private static YamlConfiguration messagesConfiguration = Main.messagesConfiguration;
    private static final Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void blockItemMove(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleGamemodeGUI(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equalsIgnoreCase(yamlConfiguration.getString("gui.title"))) {
            if (event.getCurrentItem() == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Material material = event.getCurrentItem().getType();
            Integer slot = event.getSlot();

            handleGamemodeItemSlotLogic(player, material, slot);
        }
    }

    private void handleGamemodeItemSlotLogic(Player player, Material material, Integer slot) {
        if (material == Material.valueOf(yamlConfiguration.getString("gui.slot.EMPTY.item")) && !(slot.equals(10) || slot.equals(12) || slot.equals(14) || slot.equals(16))) {
            return;
        }
        if (material == Material.valueOf(yamlConfiguration.getString("gui.slot.SURVIVAL.item")) && slot.equals(10)) {
            setGamemodeToSurvival(player);
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.CREATIVE.item"))) && slot.equals(12)) {
            setGamemodeToCreative(player);
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.SPECTATOR.item"))) && slot.equals(14)) {
            setGamemodeToSpecator(player);
        }
        if (material.equals(Material.valueOf(yamlConfiguration.getString("gui.slot.ADVENTURE.item"))) && slot.equals(16)) {
            setGamemodeToAdventure(player);
        }
        player.closeInventory();
    }

    private static void setGamemodeToSurvival(Player player) {
        if (!(player.hasPermission("gmgui.gm.survival") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*"))) {
            player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.NoPermission")));
            return;
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.ChangeSurvival")));
        LOG.create("The player " + player.getName() + " switched gamemode to survival.");
    }

    private static void setGamemodeToCreative(Player player) {
        if (!(player.hasPermission("gmgui.gm.creative") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*"))) {
            player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.NoPermission")));
            return;
        }
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.ChangeCreative")));
        LOG.create("The player " + player.getName() + " switched gamemode to creative.");
    }

    private static void setGamemodeToSpecator(Player player) {
        if (!(player.hasPermission("gmgui.gm.spectator") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*"))) {
            player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.NoPermission")));
            return;
        }
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.ChangeSpectator")));
        LOG.create("The player " + player.getName() + " switched gamemode to spectator.");
    }

    private static void setGamemodeToAdventure(Player player) {
        if (!(player.hasPermission("gmgui.gm.adventure") || player.hasPermission("gmgui.gm.*") || player.hasPermission("gmgui.*"))) {
            player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.NoPermission")));
            return;
        }
        player.sendMessage(MSG.createCostumMessage(messagesConfiguration.getString("messages.ChangeAdventure")));
        player.setGameMode(GameMode.ADVENTURE);
        LOG.create("The player " + player.getName() + " switched gamemode to adventure.");
    }

}

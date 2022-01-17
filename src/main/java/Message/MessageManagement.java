package Message;

import org.bukkit.ChatColor;

public class MessageManagement {

    public static String setChatColorTranslation(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

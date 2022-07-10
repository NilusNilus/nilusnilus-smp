package online.nilusnilus.paper.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventQuit implements Listener {

    // ======================================================================

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§7[§c-§7]§r " + event.getPlayer().getDisplayName());
    }

    // ======================================================================

}

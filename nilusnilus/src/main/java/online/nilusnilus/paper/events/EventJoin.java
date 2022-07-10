package online.nilusnilus.paper.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventJoin implements Listener {

    // ======================================================================

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("§7[§a+§7]§r " + event.getPlayer().getDisplayName());
    }

    // ======================================================================

}

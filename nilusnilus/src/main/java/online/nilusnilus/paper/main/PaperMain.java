package online.nilusnilus.paper.main;

import online.nilusnilus.paper.events.EventJoin;
import online.nilusnilus.paper.events.EventQuit;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperMain extends JavaPlugin {

    // ======================================================================
    // Paragraph symbol: §          Checkmark symbol: ✔
    // ======================================================================

    // ======================================================================

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventJoin(), this);
        getServer().getPluginManager().registerEvents(new EventQuit(), this);
    }

    // ======================================================================

}

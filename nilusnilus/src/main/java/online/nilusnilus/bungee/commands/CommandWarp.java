package online.nilusnilus.bungee.commands;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import online.nilusnilus.bungee.main.BungeeMain;

public class CommandWarp extends Command implements TabExecutor {

    // ======================================================================

    public CommandWarp(Plugin plugin, String name) {
        super(name);
        this.plugin = plugin;
        config = new BungeeMain.CustomConfig(plugin, "warps-config.yml");
        config.initialize();
    }

    // ======================================================================

    // Declare variables
    private Plugin plugin;
    private BungeeMain.CustomConfig config;

    // ======================================================================

    // This function is ran when the command gets executed
    @Override
    public void execute(CommandSender commandSender, String[] args) {

        // This command should only be able to run when players execute it
        if(!(commandSender instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        // Checks if the player has permission
        if(!player.hasPermission("online.nilusnilus.default")) {
            player.sendMessage(new TextComponent("§cYou do not have permission to perform this command."));
            return;
        }

        // The command should at least have 1 argument
        if(args.length < 1) {
            player.sendMessage(new TextComponent("§cPlease specify a server!"));
            return;
        }

        // Checks if the given server exists
        ServerInfo target = plugin.getProxy().getServerInfo(args[0]);
        if(target == null) {
            player.sendMessage(new TextComponent("§cThe server " + args[0] + " does not exist!"));
            return;
        }

        // Checks if the version is compatible with the server
        int minProtocolVer = config.getConfig().getInt(target.getName() + ".min_protocol_version");
        if(!isPlayerCorrectVersion(player, minProtocolVer)) {
            player.sendMessage(new TextComponent("§cYou are in the wrong version!"));
            player.sendMessage(new TextComponent("§cThis server requires at least protocol version " +
                    minProtocolVer + "."));
            TextComponent protocolLink = new TextComponent("§cFind the correct protocol version: https://wiki.vg/Protocol_version_numbers");
            protocolLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://wiki.vg/Protocol_version_numbers"));
            player.sendMessage(protocolLink);
            return;
        }

        // Connects the player to the server
        player.sendMessage(new TextComponent("§6Warping to " + target.getName() + "..."));
        player.connect(target);
    }

    // ======================================================================

    // Tab completion
    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        return plugin.getProxy().getServers().keySet();
    }

    // ======================================================================

    // This function checks if the player version is compatible with the server version
    private boolean isPlayerCorrectVersion(ProxiedPlayer player, int minProtocolVersion) {
        // Current protocol version for 1.19 is 759 and above
        // So this checks if the players protocol version is correct
        ViaAPI api = Via.getAPI();
        if(api.getPlayerVersion(player) < minProtocolVersion)
            return false; // Player is wrong version
        else
            return true; // Player is correct version
    }

    // ======================================================================

}

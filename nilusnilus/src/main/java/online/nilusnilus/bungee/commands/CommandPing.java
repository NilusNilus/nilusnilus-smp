package online.nilusnilus.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandPing extends Command {

    /*
    TODO: Comments
     */

    // ======================================================================

    public CommandPing(String name) {
        super(name);
    }

    // ======================================================================

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if(!player.hasPermission("online.nilusnilus.default")) {
            player.sendMessage(new TextComponent("§cYou do not have permission to perform this command."));
            return;
        }

        player.sendMessage(new TextComponent("§7You ping is: " + player.getPing()));
    }

    // ======================================================================

}

package net.md_5.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.stream.Collectors;

/**
 * @deprecated internal use only
 */
@Deprecated public abstract class PlayerCommand extends Command implements TabExecutor {

    public PlayerCommand(String name) {
        super(name);
    }

    public PlayerCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        final String lastArg = (args.length > 0) ? args[args.length - 1].toLowerCase() : "";
        return ProxyServer.getInstance().getPlayers().stream()
            .filter(player -> player.getName().toLowerCase().startsWith(lastArg))
            .collect(Collectors.toList()).stream().map(CommandSender::getName)
            .collect(Collectors.toList());
    }
}

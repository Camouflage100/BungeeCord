package net.md_5.bungee;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.security.Security;
import java.util.Arrays;

public class BungeeCordLauncher {

    public static void main(String[] args) throws Exception {
        Security.setProperty("networkaddress.cache.ttl", "30");
        Security.setProperty("networkaddress.cache.negative.ttl", "10");

        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.acceptsAll(Arrays.asList("v", "version"));
        parser.acceptsAll(Arrays.asList("noconsole"));

        OptionSet options = parser.parse(args);

        if (options.has("version")) {
            System.out.println(Bootstrap.class.getPackage().getImplementationVersion());
            return;
        }

        BungeeCord bungee = new BungeeCord();
        ProxyServer.setInstance(bungee);
        bungee.getLogger().info("Enabled BungeeCord version " + bungee.getVersion());
        bungee.start();

        if (!options.has("noconsole")) {
            String line;
            while (bungee.isRunning && (line = bungee.getConsoleReader().readLine(">")) != null) {
                if (!bungee.getPluginManager()
                    .dispatchCommand(ConsoleCommandSender.getInstance(), line)) {
                    bungee.getConsole().sendMessage(ChatColor.RED + "Command not found");
                }
            }
        }
    }
}

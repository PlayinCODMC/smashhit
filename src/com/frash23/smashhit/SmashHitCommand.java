package com.frash23.smashhit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SmashHitCommand implements CommandExecutor {
	private SmashHit plugin;

	SmashHitCommand(SmashHit pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


		String subcmd = args.length < 1?  "help" : args[0];
		switch( subcmd.toLowerCase() ) {
			case "reload":
				plugin.reload();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSmashHit &2» &7Reloaded"));
			return true;

			case "toggle":
				if( plugin.isListening() ) {
					plugin.unregisterHitListener();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSmashHit disabled"));
				} else {
					plugin.registerHitListener();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSmashHit enabled"));
				}
			return true;

			case "debug":
				if( plugin.isDebug() ) {
					plugin.unregisterDebugListener();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSmashHit debug disabled"));
				} else {
					plugin.registerDebugListener();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSmashHit debug enabled"));
				}
			return true;

			default:
				sender.sendMessage(new String[]{
						"§2SmashHit v§a0.5b",
						"§a/bss help §7- Show this message",
						"§a/bss reload §7- Reload config § restart hit interception",
						"§a/bss toggle §7- Disable/enable hit interception"
				});
			return true;
		}
	}
}

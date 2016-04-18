package com.frash23.smashhit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.async.AsyncListenerHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SmashHit extends JavaPlugin implements Listener {
	private boolean listening = false;
	private boolean debugging = false;
	private static SmashHit instance;
	private SmashHitListener hitListener = null;
	private SmashHitDebugListener debugListener = null;
	private AsyncListenerHandler hitListenerHandler;
	private ProtocolManager pmgr;

	@Override
	public void onEnable() {
		instance = this;
		pmgr = ProtocolLibrary.getProtocolManager();

		getCommand("smashhit").setExecutor( new SmashHitCommand(this) );
		reload();
	}

	@Override
	public void onDisable() {
		if(hitListener != null) unregisterHitListener();

		pmgr = null;
		instance = null;
	}

	public void registerHitListener() {
		if(hitListener == null) hitListener = new SmashHitListener(
				this,
				getConfig().getBoolean("enable-criticals"),
				getConfig().getInt("max-cps"),
				getConfig().getDouble("max-reach"),
				getConfig().getBoolean("use-bridge.worldguard")
		);

		hitListenerHandler = pmgr.getAsynchronousManager().registerAsyncHandler(hitListener);
		hitListenerHandler.start();
		listening = true;
	}
	public void unregisterHitListener() {
		if(hitListener != null) {
			pmgr.getAsynchronousManager().unregisterAsyncHandler(hitListenerHandler);
			listening = false;
			hitListener = null;
		}
	}

	public void registerDebugListener() {
		if(debugListener == null) debugListener = new SmashHitDebugListener(this);
		getServer().getPluginManager().registerEvents(debugListener, this);
		debugging = true;
	}
	public void unregisterDebugListener() {
		if(debugListener != null) {
			HandlerList.unregisterAll(debugListener);
			debugging = false;
			debugListener = null;
		}
	}

	public void reload() {
		saveDefaultConfig();
		reloadConfig();

		if(hitListener != null) hitListener.stop();
		unregisterHitListener();
		registerHitListener();
	}

	public boolean isListening() { return listening; }
	public boolean isDebug() { return debugging; }

	public static SmashHit getInstance() { return instance; }
}

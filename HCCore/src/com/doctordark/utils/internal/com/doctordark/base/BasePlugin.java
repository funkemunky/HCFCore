package com.doctordark.utils.internal.com.doctordark.base;

import java.util.*;
import org.bukkit.plugin.java.*;

import com.doctordark.utils.PersistableLocation;
import com.doctordark.utils.SignHandler;
import com.doctordark.utils.other.*;
import com.doctordark.utils.other.chat.*;
import com.doctordark.utils.other.cuboid.*;
import com.doctordark.utils.other.itemdb.*;

import org.bukkit.configuration.serialization.*;

import java.io.*;
import org.bukkit.block.*;

public class BasePlugin {
    private Random random;
    private ItemDb itemDb;
    private SignHandler signHandler;
    private static BasePlugin plugin;
    private JavaPlugin javaPlugin;

    private BasePlugin() {
        this.random = new Random();
    }

    public void init(final JavaPlugin plugin) {
        this.javaPlugin = plugin;
        ConfigurationSerialization.registerClass((Class) PersistableLocation.class);
        ConfigurationSerialization.registerClass((Class) Cuboid.class);
        ConfigurationSerialization.registerClass((Class) NamedCuboid.class);
        this.itemDb = new SimpleItemDb(plugin);
        this.signHandler = new SignHandler(plugin);
        try {
            Lang.initialize("en_US");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void disable() {
        this.signHandler.cancelTasks(null);
        this.javaPlugin = null;
        BasePlugin.plugin = null;
    }

    public Random getRandom() {
        return this.random;
    }

    public ItemDb getItemDb() {
        return this.itemDb;
    }

    public SignHandler getSignHandler() {
        return this.signHandler;
    }

    public static BasePlugin getPlugin() {
        return BasePlugin.plugin;
    }

    public JavaPlugin getJavaPlugin() {
        return this.javaPlugin;
    }

    static {
        BasePlugin.plugin = new BasePlugin();
    }
}

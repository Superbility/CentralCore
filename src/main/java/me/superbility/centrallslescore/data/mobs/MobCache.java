package me.superbility.centrallslescore.data.mobs;

import me.superbility.centrallslescore.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobCache {
    public static HashMap<String, CoreMob> mobs = new HashMap<>();

    public MobCache(Main plugin) {
        File folder = new File(plugin.getDataFolder() + "/mobs");
        for (File file : folder.listFiles()) {
            String identifier = file.getName().replace(".yml", "");
            mobs.put(identifier, new CoreMob(file));
        }
    }

    public static void clear() {
        mobs = new HashMap<>();
    }
}

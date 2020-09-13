package me.nicbo.nicbows;

import me.nicbo.nicbows.manager.NicbowManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author Nicbo
 */

public class Nicbows extends JavaPlugin {
    private NicbowManager nicbowManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.nicbowManager = new NicbowManager(this);
        nicbowManager.registerCrafts();
    }

    public NicbowManager getNicbowManager() {
        return nicbowManager;
    }
}

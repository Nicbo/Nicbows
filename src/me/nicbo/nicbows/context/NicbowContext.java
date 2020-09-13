package me.nicbo.nicbows.context;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

/**
 * @author Nicbo
 */

public class NicbowContext {
    private final Player player;
    private final Arrow arrow;

    public NicbowContext(Player player, Arrow arrow) {
        this.player = player;
        this.arrow = arrow;
    }

    public Player getPlayer() {
        return player;
    }

    public Arrow getArrow() {
        return arrow;
    }
}

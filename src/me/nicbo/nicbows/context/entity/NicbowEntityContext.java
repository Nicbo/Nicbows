package me.nicbo.nicbows.context.entity;

import me.nicbo.nicbows.context.NicbowContext;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * @author Nicbo
 */

public class NicbowEntityContext extends NicbowContext {
    private final Entity entity;

    public NicbowEntityContext(Player player, Entity entity, Arrow arrow) {
        super(player, arrow);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}

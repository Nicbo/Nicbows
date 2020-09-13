package me.nicbo.nicbows.context.block;

import me.nicbo.nicbows.context.NicbowContext;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

/**
 * @author Nicbo
 */

public class NicbowBlockContext extends NicbowContext {
    private final Block block;

    public NicbowBlockContext(Player player, Block block, Arrow arrow) {
        super(player, arrow);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}

package me.nicbo.nicbows.bows;

import me.nicbo.nicbows.Nicbows;
import me.nicbo.nicbows.context.NicbowContext;
import me.nicbo.nicbows.context.block.NicbowBlockContext;
import me.nicbo.nicbows.context.entity.NicbowEntityContext;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nicbo
 */

public abstract class Nicbow {
    protected final Nicbows plugin;

    private final ItemStack bow;
    private final String key;

    private final Map<Player, Set<Arrow>> activeArrows;

    public Nicbow(Nicbows plugin, ItemStack bow, String key) {
        this.plugin = plugin;
        this.bow = bow;
        this.key = key;
        this.activeArrows = new HashMap<>();
    }

    public void onShoot(NicbowContext context) {
        Set<Arrow> playerArrows = activeArrows.computeIfAbsent(context.getPlayer(), k -> new HashSet<>());
        playerArrows.add(context.getArrow());
    }

    public void onBlockHit(NicbowBlockContext context) {
        this.activeArrows.get(context.getPlayer()).remove(context.getArrow());
    }

    public void onEntityHit(NicbowEntityContext context) {
        this.activeArrows.get(context.getPlayer()).remove(context.getArrow());
    }

    public Set<Arrow> getActiveArrows(Player player) {
        return activeArrows.get(player);
    }

    public void removeActiveArrows(Player player) {
        activeArrows.remove(player);
    }

    public ItemStack getBow() {
        return bow;
    }

    public String getKey() {
        return key;
    }

    public abstract ShapedRecipe getRecipe();

    // TODO: 2020-09-13 Make a onTick method
}

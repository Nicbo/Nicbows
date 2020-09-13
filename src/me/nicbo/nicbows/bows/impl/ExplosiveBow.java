package me.nicbo.nicbows.bows.impl;

import me.nicbo.nicbows.Nicbows;
import me.nicbo.nicbows.bows.Nicbow;
import me.nicbo.nicbows.context.block.NicbowBlockContext;
import me.nicbo.nicbows.context.entity.NicbowEntityContext;
import me.nicbo.nicbows.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ShapedRecipe;

/**
 * @author Nicbo
 */

public class ExplosiveBow extends Nicbow {
    public ExplosiveBow(Nicbows plugin) {
        super(plugin, new ItemBuilder(Material.BOW)
                .setName("&c&lExplosive Bow")
                .setLore("&7This bow shoots explosive arrows.", "&7Be careful when using this!")
                .setDurability(20)
                .build(), "explosive_bow");
    }

    @Override
    public void onBlockHit(NicbowBlockContext context) {
        super.onBlockHit(context);
        explode(context.getArrow());
    }

    @Override
    public void onEntityHit(NicbowEntityContext context) {
        super.onEntityHit(context);
        explode(context.getArrow());
    }

    private void explode(Arrow arrow) {
        World world = arrow.getWorld();
        world.createExplosion(arrow.getLocation(), 3);
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, getKey()), getBow());
        recipe.shape("TTT", "TBT", "TTT");
        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('B', Material.BOW);
        return recipe;
    }
}

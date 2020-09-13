package me.nicbo.nicbows.bows.impl;

import me.nicbo.nicbows.Nicbows;
import me.nicbo.nicbows.bows.Nicbow;
import me.nicbo.nicbows.context.NicbowContext;
import me.nicbo.nicbows.context.block.NicbowBlockContext;
import me.nicbo.nicbows.context.entity.NicbowEntityContext;
import me.nicbo.nicbows.util.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ShapedRecipe;

/**
 * @author Nicbo
 */

public class LightningBow extends Nicbow {
    public LightningBow(Nicbows plugin) {
        super(plugin, new ItemBuilder(Material.BOW)
                .setName("&b&lLightning Bow")
                .setLore("&7This bow shoots lighting arrows.", "&7All living entities around the arrow will be struck by lightning.")
                .setDurability(20)
                .build(), "lightning_bow");
    }

    @Override
    public void onBlockHit(NicbowBlockContext context) {
        super.onBlockHit(context);
        strikeNearby(context);
    }

    @Override
    public void onEntityHit(NicbowEntityContext context) {
        super.onEntityHit(context);
        strikeNearby(context);
    }

    private void strikeNearby(NicbowContext context) {
        for (Entity entity : context.getArrow().getNearbyEntities(5, 5, 5)) {
            // Entity is alive and not the player who shot
            if (entity instanceof LivingEntity && !(entity.equals(context.getPlayer()))) {
                Location location = entity.getLocation();
                World world = entity.getWorld();
                world.strikeLightning(location);
            }
        }
    }

    @Override
    public ShapedRecipe getRecipe() {
        // TODO: 2020-09-13 Make this an actual recipe
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, getKey()), getBow());
        recipe.shape("EEE", "EBE", "EEE");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('B', Material.BOW);
        return recipe;
    }
}

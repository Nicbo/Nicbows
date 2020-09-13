package me.nicbo.nicbows.manager;

import com.google.common.collect.ImmutableSet;
import me.nicbo.nicbows.Nicbows;
import me.nicbo.nicbows.bows.Nicbow;
import me.nicbo.nicbows.bows.impl.ExplosiveBow;
import me.nicbo.nicbows.bows.impl.LightningBow;
import me.nicbo.nicbows.context.NicbowContext;
import me.nicbo.nicbows.context.block.NicbowBlockContext;
import me.nicbo.nicbows.context.entity.NicbowEntityContext;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

/**
 * @author Nicbo
 */

public final class NicbowManager implements Listener {
    private final Nicbows plugin;

    private final Set<Nicbow> nicbows;

    public NicbowManager(Nicbows plugin) {
        this.plugin = plugin;

        ExplosiveBow EXPLOSIVE_BOW = new ExplosiveBow(plugin);
        LightningBow LIGHTNING_BOW = new LightningBow(plugin);

        this.nicbows = ImmutableSet.<Nicbow>
                builder()
                .add(EXPLOSIVE_BOW)
                .add(LIGHTNING_BOW)
                .build();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // TODO: 2020-09-13 Actually make recipes and durability balanced
    public void registerCrafts() {
        for (Nicbow nicbow : nicbows) {
            plugin.getServer().addRecipe(nicbow.getRecipe());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onShootArrow(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();
                Nicbow nicbow = getNicbowFromPlayer(player);

                if (nicbow != null) {
                    nicbow.onShoot(new NicbowContext(player, arrow));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();

                for (Nicbow nicbow : nicbows) {
                    Set<Arrow> arrows = nicbow.getActiveArrows(player);
                    if (arrows != null && arrows.contains(arrow)) {
                        Block block = event.getHitBlock();

                        if (block != null) {
                            nicbow.onBlockHit(new NicbowBlockContext(player, block, arrow));
                        } else {
                            nicbow.onEntityHit(new NicbowEntityContext(player, event.getHitEntity(), arrow));
                        }
                    }
                }

            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        for (Nicbow nicbow : nicbows) {
            nicbow.removeActiveArrows(event.getPlayer());
        }
    }

    private Nicbow getNicbowFromPlayer(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        for (Nicbow bow : nicbows) {
            ItemStack bowItem = bow.getBow();
            if (isEqual(mainHand, bowItem) || isEqual(offHand, bowItem)) {
                return bow;
            }
        }
        return null;
    }

    /**
     * Checks if 2 items are equal, ignoring durability
     * TODO: See if there is a more efficient way of identifying Nicbows
     *
     * @param item1 the first item
     * @param item2 the second item
     * @return true if the items are equal
     */
    private static boolean isEqual(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        } else if (item1.getType() != item2.getType()) {
            return false;
        } else if (item1.hasItemMeta() != item2.hasItemMeta()) {
            return false;
        } else if (item1.hasItemMeta()) {
            ItemMeta meta1 = item1.getItemMeta();
            ItemMeta meta2 = item2.getItemMeta();

            if (meta1 == null || meta2 == null) {
                return false;
            } else if (!meta1.getDisplayName().equalsIgnoreCase(meta2.getDisplayName())) {
                return false;
            } else if (meta1.getLore() != null && !meta1.getLore().equals(meta2.getLore())) {
                return false;
            } else {
                return meta1.getEnchants().equals(meta2.getEnchants());
            }
        }

        return true;
    }
}

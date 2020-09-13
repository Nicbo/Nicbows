package me.nicbo.nicbows.util.item;

import org.bukkit.enchantments.Enchantment;

/**
 * Represents an enchantment
 *
 * @author Nicbo
 */

public class Enchant {
    private final Enchantment enchant;
    private final int level;

    public Enchant(Enchantment enchant, int level) {
        this.enchant = enchant;
        this.level = level;
    }

    public Enchantment getEnchant() {
        return enchant;
    }

    public int getLevel() {
        return level;
    }
}
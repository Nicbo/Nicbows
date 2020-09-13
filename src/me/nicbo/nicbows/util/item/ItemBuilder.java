package me.nicbo.nicbows.util.item;

import me.nicbo.nicbows.util.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Builder class for creating ItemStacks
 *
 * @author Nicbo
 */

public final class ItemBuilder {
    private final Material material;
    private String name;
    private Enchant[] enchants;
    private int amount;
    private List<String> lore;
    private int durability;

    public ItemBuilder(Material material) {
        this.material = material;
        this.name = null;
        this.enchants = new Enchant[0];
        this.amount = 1;
        this.lore = null;
        this.durability = -1;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setEnchants(Enchant... enchants) {
        this.enchants = enchants;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            throw new IllegalStateException("Item meta can not be created for " + material.name());
        }

        if (name != null) {
            meta.setDisplayName(StringUtils.colour(name));
        }

        if (lore != null) {
            meta.setLore(StringUtils.colour(lore));
        }

        for (Enchant enchant : enchants) {
            meta.addEnchant(enchant.getEnchant(), enchant.getLevel(), true);
        }

        if (durability != -1 && meta instanceof Damageable) {
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(material.getMaxDurability() - durability);
        }

        item.setItemMeta(meta);
        return item;
    }
}

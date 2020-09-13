package me.nicbo.nicbows.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicbo
 */

public final class StringUtils {
    private StringUtils() {
    }

    public static String colour(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colour(List<String> messages) {
        List<String> translated = new ArrayList<>();
        for (String str : list) {
            translated.add(colour(str));
        }
        return translated;
    }
}

package com.marcusslover.plus.lib.text;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Pattern;

public class ColorUtil {
    public static final Pattern HEX_TO_BUKKIT = Pattern.compile("&#([a-fA-F0-9]{6})");
    public static final Pattern HEX_FROM_BUKKIT = Pattern.compile("&x(&[a-fA-F0-9]){6}");
    public static final char COLOR_CHAR = '§';
    private static final Pattern HEX_PATTERN = Pattern.compile("^#([a-fA-F0-9]{6})$");
    private static final EnumMap<DyeColor, ChatColor> dyeChatMap = new EnumMap<>(DyeColor.class);

    static {
        dyeChatMap.put(DyeColor.BLACK, ChatColor.BLACK);
        dyeChatMap.put(DyeColor.BLUE, ChatColor.DARK_BLUE);
        dyeChatMap.put(DyeColor.BROWN, ChatColor.GOLD);
        dyeChatMap.put(DyeColor.CYAN, ChatColor.AQUA);
        dyeChatMap.put(DyeColor.GRAY, ChatColor.DARK_GRAY);
        dyeChatMap.put(DyeColor.GREEN, ChatColor.DARK_GREEN);
        dyeChatMap.put(DyeColor.LIGHT_BLUE, ChatColor.BLUE);
        dyeChatMap.put(DyeColor.LIME, ChatColor.GREEN);
        dyeChatMap.put(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE);
        dyeChatMap.put(DyeColor.ORANGE, ChatColor.GOLD);
        dyeChatMap.put(DyeColor.PINK, ChatColor.LIGHT_PURPLE);
        dyeChatMap.put(DyeColor.PURPLE, ChatColor.DARK_PURPLE);
        dyeChatMap.put(DyeColor.RED, ChatColor.DARK_RED);
        dyeChatMap.put(DyeColor.LIGHT_GRAY, ChatColor.GRAY);
        dyeChatMap.put(DyeColor.WHITE, ChatColor.WHITE);
        dyeChatMap.put(DyeColor.YELLOW, ChatColor.YELLOW);
    }

    private static final EnumMap<Material, DyeColor> matDyeMap = new EnumMap<>(Material.class);
    static {
        Arrays.stream(Material.values()).forEach(material -> {
            if (material.name().startsWith("WHITE")) {
                matDyeMap.put(material, DyeColor.WHITE);
            } else if (material.name().startsWith("ORANGE")) {
                matDyeMap.put(material, DyeColor.ORANGE);
            } else if (material.name().startsWith("MAGENTA")) {
                matDyeMap.put(material, DyeColor.MAGENTA);
            } else if (material.name().startsWith("LIGHT_BLUE")) {
                matDyeMap.put(material, DyeColor.LIGHT_BLUE);
            } else if (material.name().startsWith("YELLOW")) {
                matDyeMap.put(material, DyeColor.YELLOW);
            } else if (material.name().startsWith("LIME")) {
                matDyeMap.put(material, DyeColor.LIME);
            } else if (material.name().startsWith("PINK")) {
                matDyeMap.put(material, DyeColor.PINK);
            } else if (material.name().startsWith("GRAY")) {
                matDyeMap.put(material, DyeColor.GRAY);
            } else if (material.name().startsWith("LIGHT_GRAY")) {
                matDyeMap.put(material, DyeColor.LIGHT_GRAY);
            } else if (material.name().startsWith("CYAN")) {
                matDyeMap.put(material, DyeColor.CYAN);
            } else if (material.name().startsWith("PURPLE")) {
                matDyeMap.put(material, DyeColor.PURPLE);
            } else if (material.name().startsWith("BLUE")) {
                matDyeMap.put(material, DyeColor.BLUE);
            } else if (material.name().startsWith("BROWN")) {
                matDyeMap.put(material, DyeColor.BROWN);
            } else if (material.name().startsWith("GREEN")) {
                matDyeMap.put(material, DyeColor.GREEN);
            } else if (material.name().startsWith("RED")) {
                matDyeMap.put(material, DyeColor.RED);
            } else if (material.name().startsWith("BLACK")) {
                matDyeMap.put(material, DyeColor.BLACK);
            }
        });
    }

    private ColorUtil() {
    }

    public static @NotNull String hex(@NotNull String text) {
        return hexColorization(text);
    }

    public static @NotNull String color(@NotNull String textToTranslate) {
        return color('&', textToTranslate);
    }

    public static @NotNull String color(char altColorChar, @NotNull String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static @NotNull LegacyComponentSerializer legacySection() {
        return LegacyComponentSerializer.legacySection();
    }

    public static @NotNull LegacyComponentSerializer legacyAmpersand() {
        return LegacyComponentSerializer.legacyAmpersand();
    }

    private static @NotNull String hexColorization(@NotNull String text) {
        var matcher = HEX_TO_BUKKIT.matcher(text);

        while (matcher.find()) {
            var bukkitColor = new StringBuilder("&x");
            char[] chars = matcher.group(1).toCharArray();
            for (char character : chars) {
                bukkitColor.append("&").append(character);
            }
            text = text.replaceAll(matcher.group(), String.valueOf(bukkitColor));
        }

        return text;
    }

    public static @NotNull String hexTranslation(@NotNull String text) {
        text = text.replace("§", "&"); // legacy fix
        var matcher = HEX_FROM_BUKKIT.matcher(text);
        while (matcher.find()) {
            String group = matcher.group();
            String translation = group.replace("&x", "");
            translation = translation.replace("&", "");
            translation = "&#" + translation;
            text = text.replaceAll(group, translation);

        }
        return text;
    }

    /**
     * Convert color codes and hex codes to Color container (RGB Container)
     *
     * @param value Must match one of these formats "#FFFFFF" or "ampersand-c"
     * @return org.bukkit.Color
     */
    public static @NotNull Color getRGBFromCode(@NotNull String value) {
        value = value.replaceAll("[{}]", "").replace("&", "");
        return HEX_PATTERN.matcher(value).matches() ? hex2Rgb(value) : minecraft2Rgb(value);
    }

    /**
     * Converts "#FFFFFF" format to a Color object containing RGB values
     *
     * @param value Must be in format "#FFFFFF"
     * @return org.bukkit.Color
     */
    public static @NotNull Color hex2Rgb(@NotNull String value) {
        try {
            Color color;
            if (value.startsWith("#")) {
                color = Color.fromRGB(
                        Integer.valueOf(value.substring(1, 3), 16),
                        Integer.valueOf(value.substring(3, 5), 16),
                        Integer.valueOf(value.substring(5, 7), 16));
            } else {
                color = Color.fromRGB(
                        Integer.valueOf(value.substring(0, 2), 16),
                        Integer.valueOf(value.substring(2, 4), 16),
                        Integer.valueOf(value.substring(4, 6), 16));
            }
            return color;
        } catch (IllegalArgumentException ignored) {
            return Color.WHITE;
        }
    }

    /**
     * Converts ampersand-c format to a Color object containing RGB values
     *
     * @param value Must be in format "ampersand-0"
     * @return org.bukkit.Color
     */
    public static @NotNull Color minecraft2Rgb(@NotNull String value) {
        try {
            return switch (value) {
                case "&0" -> Color.fromRGB(0, 0, 0);
                case "&1" -> Color.fromRGB(0, 0, 170);
                case "&2" -> Color.fromRGB(0, 170, 0);
                case "&3" -> Color.fromRGB(0, 170, 170);
                case "&4" -> Color.fromRGB(170, 0, 0);
                case "&5" -> Color.fromRGB(170, 0, 170);
                case "&6" -> Color.fromRGB(255, 170, 0);
                case "&7" -> Color.fromRGB(170, 170, 170);
                case "&8" -> Color.fromRGB(85, 85, 85);
                case "&9" -> Color.fromRGB(85, 85, 255);
                case "&a" -> Color.fromRGB(85, 255, 85);
                case "&b" -> Color.fromRGB(85, 255, 255);
                case "&c" -> Color.fromRGB(255, 85, 85);
                case "&d" -> Color.fromRGB(255, 85, 255);
                case "&e" -> Color.fromRGB(255, 255, 85);
                default -> Color.fromRGB(255, 255, 255);
            };
        } catch (IllegalArgumentException ignored) {
            return Color.WHITE;
        }
    }

    public static @NotNull ChatColor dyeToChat(@NotNull DyeColor dye) {
        return dyeChatMap.getOrDefault(dye, ChatColor.RESET);
    }

    public static @NotNull DyeColor materialToDye(@NotNull Material material) {
        return matDyeMap.getOrDefault(material, DyeColor.WHITE);
    }

    public static @NotNull ChatColor materialToChat(@NotNull Material material) {
        return dyeToChat(materialToDye(material));
    }

    public static @NotNull List<String> translateList(@NotNull List<String> lore) {
        return lore.stream().map(ColorUtil::hexTranslation).toList();
    }
}

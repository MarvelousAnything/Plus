package com.marcusslover.plus.lib.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ColorTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Color#Color(int)}
     *   <li>{@link Color#toString()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertEquals("Color{rgb=1}", (new Color(1)).toString());
        assertEquals(0xffff5b, (new Color(10.0f, 10.0f, 10.0f)).rgb());
        assertEquals(0x010101, (new Color(1, 1, 1)).rgb());
        assertEquals(0x00002a, (new Color(java.awt.Color.decode("#00002A"))).rgb());
        assertEquals(0x00002a, (new Color("#00002A")).rgb());
        assertEquals(0x010101, (new Color(org.bukkit.Color.fromBGR(1, 1, 1))).rgb());
    }

    /**
     * Method under test: {@link Color#Color(java.awt.Color)}
     */
    @Test
    void testConstructorAwtColor() throws NumberFormatException {
        assertEquals("#FF0000", new Color(java.awt.Color.red).hex());
    }

    /**
     * Method under test: {@link Color#Color(String)}
     */
    @Test
    void testConstructorHex() {
        assertEquals(java.awt.Color.red, new Color("#FF0000").java());
    }

    /**
     * Method under test: {@link Color#Color(TextColor)}
     */
    @Test
    void testConstructor4() {
        TextColor textColor = mock(TextColor.class);
        when(textColor.value()).thenReturn(42);
        assertEquals(42, (new Color(textColor)).rgb());
        verify(textColor).value();
    }

    /**
     * Method under test: {@link Color#of(float, float, float)}
     */
    @Test
    void testOf() {
        assertEquals(0xffff5b, Color.of(10.0f, 10.0f, 10.0f).rgb(), "Of HSB");
        assertEquals(0x000001, Color.of(1).rgb(), "Of rgb literal.");
        assertEquals(0x010101, Color.of(1, 1, 1).rgb(), "Of components");
        assertEquals(0x00002A, Color.of(java.awt.Color.decode("#00002A")).rgb(), "Of Awt");
        assertEquals(0x00002A, Color.of("#00002A").rgb(), "Of hex");
        assertEquals(0x010101, Color.of(org.bukkit.Color.fromBGR(1, 1, 1)).rgb(), "Of bukkit");
    }

    /**
     * Method under test: {@link Color#of(java.awt.Color)}
     */
    @Test
    void testOfAwtColor() throws NumberFormatException {
        assertEquals(Color.of(255, 0, 0).rgb(), Color.of(java.awt.Color.red).rgb());
    }

    /**
     * Method under test: {@link Color#of(String)}
     */
    @Test
    void testOfHex() {
        assertEquals(java.awt.Color.red, Color.of("#FF0000").java());
    }

    /**
     * Method under test: {@link Color#of(TextColor)}
     */
    @Test
    void testOf5() {
        TextColor textColor = mock(TextColor.class);
        when(textColor.value()).thenReturn(42);
        assertEquals(42, Color.of(textColor).rgb());
        verify(textColor).value();
    }

    /**
     * Method under test: {@link Color#red()}
     */
    @Test
    void testRed() {
        assertEquals(0, Color.of(1).red());
    }

    /**
     * Method under test: {@link Color#green()}
     */
    @Test
    void testGreen() {
        assertEquals(0, Color.of(1).green());
    }

    /**
     * Method under test: {@link Color#blue()}
     */
    @Test
    void testBlue() {
        assertEquals(1, Color.of(1).blue());
    }

    /**
     * Method under test: {@link Color#alpha()}
     */
    @Test
    void testAlpha() {
        assertEquals(0, Color.of(1).alpha());
    }

    /**
     * Method under test: {@link Color#hex()}
     */
    @Test
    void testHex() {
        assertEquals("#000001", Color.of(1).hex());
    }

    /**
     * Method under test: {@link Color#plus()}
     */
    @Test
    void testPlus() {
        assertEquals("&#000001", Color.of(1).plus());
    }

    /**
     * Method under test: {@link Color#adventure()}
     */
    @Test
    void testAdventure() {
        assertEquals(1, Color.of(1).adventure().value());
    }

    /**
     * Method under test: {@link Color#adventure()}
     */
    @Test
    void testAdventure2() {
        TextColor actualAdventureResult = Color.of(0).adventure();
        assertSame(NamedTextColor.BLACK, actualAdventureResult);
        assertEquals(0, actualAdventureResult.value());
    }

    /**
     * Method under test: {@link Color#bukkit()}
     */
    @Test
    void testBukkit() {
        org.bukkit.Color actualBukkitResult = Color.of(1).bukkit();
        assertEquals(1, actualBukkitResult.getBlue());
        assertEquals(0, actualBukkitResult.getRed());
        assertEquals(0, actualBukkitResult.getGreen());
    }

    /**
     * Method under test: {@link Color#java()}
     */
    @Test
    void testJava() {
        assertEquals(java.awt.Color.red, Color.of(255, 0, 0).java());
    }

    /**
     * Method under test: {@link Color#legacy()}
     */
    @Test
    void testLegacy() {
        assertEquals("§1", Color.of(1).legacy());
        assertEquals("§0", Color.of(0).legacy());
        assertEquals("§f", Color.of(-1).legacy());
        assertEquals("§e", Color.of(10.0f, 10.0f, 10.0f).legacy());
        assertEquals("§b", Color.of(0.5f, 10.0f, 10.0f).legacy());
        assertEquals("§d", Color.of(Float.NaN, -0.5f, 10.0f).legacy());
    }

    /**
     * Method under test: {@link Color#darker(double)}
     */
    @Test
    void testDarker() {
        assertEquals(10, Color.of(1).darker(10.0d).rgb());
    }

    /**
     * Method under test: {@link Color#brighter(double)}
     */
    @Test
    void testBrighter() {
        assertEquals(0, Color.of(1).brighter(10.0d).rgb());
        assertEquals(0, Color.of(0).brighter(10.0d).rgb());
        assertEquals(1644825, Color.of(-1).brighter(10.0d).rgb());
        assertEquals(255, Color.of(1).brighter(1.0d).rgb());
        assertEquals(16777215, Color.of(-1).brighter(1.0d).rgb());
        assertEquals(1572864, Color.of(10.0f, Float.NaN, 10.0f).brighter(10.0d).rgb());
        assertEquals(24, Color.of(0.5f, Float.NaN, 10.0f).brighter(10.0d).rgb());
    }
}


package dev.isxander.yacl.gui.utils;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class GuiUtils {
    public static MutableComponent translatableFallback(String key, Component fallback) {
        if (Language.getInstance().has(key))
            return Component.translatable(key);
        return fallback.copy();
    }

    public static void enableScissor(int x, int y, int width, int height) {
        Window window = Minecraft.getInstance().getWindow();
        double d = window.getGuiScale();
        RenderSystem.enableScissor((int)(x * d), (int)((window.getGuiScaledHeight() - y - height) * d), (int)(width * d), (int)(height * d));
    }

    public static String shortenString(String string, Font font, int maxWidth, String suffix) {
        if (string.isEmpty())
            return string;

        boolean firstIter = true;
        while (font.width(string) > maxWidth) {
            string = string.substring(0, Math.max(string.length() - 1 - (firstIter ? 1 : suffix.length() + 1), 0)).trim();
            string += suffix;

            if (string.equals(suffix))
                break;

            firstIter = false;
        }

        return string;
    }
}

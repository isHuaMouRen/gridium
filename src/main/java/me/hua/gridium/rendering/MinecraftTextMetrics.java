package me.hua.gridium.rendering;

import me.hua.gridium.controls.TextMetrics;
import me.hua.gridium.core.Size;
import net.minecraft.client.gui.Font;

import java.util.Objects;

public class MinecraftTextMetrics implements TextMetrics {
	private final Font font;

	public MinecraftTextMetrics(Font font) {
		this.font = Objects.requireNonNull(font, "font");
	}

	@Override
	public Size measureText(String text) {
		return new Size(font.width(text), font.lineHeight);
	}
}

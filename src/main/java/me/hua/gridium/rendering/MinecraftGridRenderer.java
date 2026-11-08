package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.Objects;

public class MinecraftGridRenderer implements GridRenderer {
	private final GuiGraphicsExtractor graphics;
	private final Font font;

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics, Font font) {
		this.graphics = Objects.requireNonNull(graphics, "graphics");
		this.font = Objects.requireNonNull(font, "font");
	}

	@Override
	public void text(String value, Rect bounds, int color) {
		graphics.text(font, value, (int) Math.round(bounds.getX()), (int) Math.round(bounds.getY()), color);
	}

	@Override
	public void rectangle(Rect bounds, int color) {
		int left = (int) Math.round(bounds.getX());
		int top = (int) Math.round(bounds.getY());
		int right = (int) Math.round(bounds.getRight());
		int bottom = (int) Math.round(bounds.getBottom());
		graphics.fill(left, top, right, bottom, color);
	}
}

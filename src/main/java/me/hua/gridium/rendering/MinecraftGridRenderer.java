package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class MinecraftGridRenderer implements GridRenderer {
	private final GuiGraphicsExtractor graphics;
	private final Font font;

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics, Font font) {
		this.graphics = Objects.requireNonNull(graphics, "graphics");
		this.font = Objects.requireNonNull(font, "font");
	}

	@Override
	public void text(Component component, Rect bounds, int color, boolean drawShadow) {
		graphics.text(
			font,
			component,
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			color,
			drawShadow
		);
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

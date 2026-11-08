package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.Objects;

public class MinecraftGridRenderer implements GridRenderer {
	private final GuiGraphicsExtractor graphics;

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics) {
		this.graphics = Objects.requireNonNull(graphics, "graphics");
	}

	@Override
	public void render(Text text) {
		Rect bounds = text.getBounds();
		graphics.text(
			text.getFont(),
			text.getComponent(),
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			text.getColor(),
			text.drawsShadow()
		);
	}

	@Override
	public void render(Rectangle rectangle) {
		Rect bounds = rectangle.getBounds();
		int left = (int) Math.round(bounds.getX());
		int top = (int) Math.round(bounds.getY());
		int right = (int) Math.round(bounds.getRight());
		int bottom = (int) Math.round(bounds.getBottom());
		graphics.fill(left, top, right, bottom, rectangle.getColor());
	}
}

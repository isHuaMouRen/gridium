package me.hua.gridium.layout;

import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.UIElement;
import me.hua.gridium.controls.GridTooltip;
import me.hua.gridium.rendering.MinecraftGridRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Overlapping layout container. By default children are arranged from the top-left. Horizontal and
 * vertical alignment control each child's position or stretching independently.
 *
 * <p>Children are measured against the same available size. Grid does not compress or clip
 * overflowing children.</p>
 */
public class Grid extends Panel<Grid> {
	@Override
	protected Grid self() {
		return this;
	}

	/**
	 * Measures and arranges this grid for the graphics viewport, then renders its elements.
	 *
	 * <p>The partial tick defaults to zero.</p>
	 */
	public void draw(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
		draw(graphics, mouseX, mouseY, 0.0F);
	}

	public void draw(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		Objects.requireNonNull(graphics, "graphics");
		layout(graphics.guiWidth(), graphics.guiHeight());
		render(new MinecraftGridRenderer(graphics, mouseX, mouseY, partialTick));
		GridTooltip tooltip = findTooltipAt(mouseX, mouseY);
		if (tooltip != null) {
			tooltip.show(graphics, mouseX, mouseY);
		}
	}

	private void layout(double width, double height) {
		Size viewportSize = new Size(width, height);
		measure(viewportSize);
		arrange(Rect.fromOriginAndSize(0.0, 0.0, viewportSize));
	}

	@Override
	protected Size measureContent(Size availableSize) {
		double desiredWidth = 0.0;
		double desiredHeight = 0.0;

		for (UIElement child : getChildren()) {
			child.measure(availableSize);
			desiredWidth = Math.max(desiredWidth, child.getDesiredSize().getWidth());
			desiredHeight = Math.max(desiredHeight, child.getDesiredSize().getHeight());
		}

		return new Size(desiredWidth, desiredHeight);
	}

	@Override
	protected void arrangeContent(Rect finalRect) {
		for (UIElement child : getChildren()) {
			arrangeChild(child, finalRect);
		}
	}
}

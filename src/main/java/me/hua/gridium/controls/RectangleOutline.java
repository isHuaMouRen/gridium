package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class RectangleOutline extends FrameworkElement<RectangleOutline> {
	private final double width;
	private final double height;
	private final int color;

	public RectangleOutline(double width, double height, int color) {
		Size size = new Size(width, height);
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	@Override
	protected RectangleOutline self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(width, height);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

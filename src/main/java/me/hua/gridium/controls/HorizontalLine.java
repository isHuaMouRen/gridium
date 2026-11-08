package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class HorizontalLine extends FrameworkElement<HorizontalLine> {
	private final double length;
	private final int color;

	public HorizontalLine(double length, int color) {
		this.length = new Size(length, 0.0).getWidth();
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	@Override
	protected HorizontalLine self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(length, 1.0);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

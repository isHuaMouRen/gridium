package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class VerticalLine extends FrameworkElement<VerticalLine> {
	private final double length;
	private final int color;

	public VerticalLine(double length, int color) {
		this.length = new Size(0.0, length).getHeight();
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	@Override
	protected VerticalLine self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(1.0, length);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

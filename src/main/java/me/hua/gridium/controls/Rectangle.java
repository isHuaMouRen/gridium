package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class Rectangle extends FrameworkElement<Rectangle> {
	private final double width;
	private final double height;
	private final int color;

	public Rectangle(double width, double height, int color) {
		super("rectangle");
		this.width = Math.max(0.0, width);
		this.height = Math.max(0.0, height);
		this.color = color;
	}

	@Override
	protected Rectangle self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(width, height);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.rectangle(getBounds(), color);
	}
}

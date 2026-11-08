package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class GradientRectangle extends FrameworkElement<GradientRectangle> {
	private final double width;
	private final double height;
	private final int firstColor;
	private final int secondColor;

	public GradientRectangle(double width, double height, int firstColor, int secondColor) {
		Size size = new Size(width, height);
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.firstColor = firstColor;
		this.secondColor = secondColor;
	}

	public int getFirstColor() {
		return firstColor;
	}

	public int getSecondColor() {
		return secondColor;
	}

	@Override
	protected GradientRectangle self() {
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

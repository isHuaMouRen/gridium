package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

public class TextHighlight extends FrameworkElement<TextHighlight> {
	private final double width;
	private final double height;
	private final boolean invertText;

	public TextHighlight(double width, double height, boolean invertText) {
		Size size = new Size(width, height);
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.invertText = invertText;
	}

	public boolean invertsText() {
		return invertText;
	}

	@Override
	protected TextHighlight self() {
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

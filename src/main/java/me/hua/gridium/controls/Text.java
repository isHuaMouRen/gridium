package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.rendering.GridRenderer;

import java.util.Objects;

public class Text extends FrameworkElement {
	private final String value;
	private final TextMetrics textMetrics;
	private final int color;

	public Text(String value, TextMetrics textMetrics) {
		this(value, textMetrics, 0xFFFFFFFF);
	}

	public Text(String value, TextMetrics textMetrics, int color) {
		super("text");
		this.value = Objects.requireNonNull(value, "value");
		this.textMetrics = Objects.requireNonNull(textMetrics, "textMetrics");
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	@Override
	public Text setMargin(Thickness margin) {
		super.setMargin(margin);
		return this;
	}

	@Override
	public Text setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		super.setHorizontalAlignment(horizontalAlignment);
		return this;
	}

	@Override
	public Text setVerticalAlignment(VerticalAlignment verticalAlignment) {
		super.setVerticalAlignment(verticalAlignment);
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return Objects.requireNonNull(textMetrics.measureText(value), "measured text size");
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		Rect bounds = getBounds();
		renderer.text(value, bounds, color);
	}
}

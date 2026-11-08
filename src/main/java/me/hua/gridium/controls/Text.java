package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;

import java.util.Objects;

public class Text extends FrameworkElement<Text> {
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
	protected Text self() {
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

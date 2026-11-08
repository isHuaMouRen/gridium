package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;

public class TextBlock extends FrameworkElement {
	private String text = "";
	private double fontSize = 12.0;

	public TextBlock() {
		this("");
	}

	public TextBlock(String text) {
		this.text = text == null ? "" : text;
	}

	public static TextBlock create(String text) {
		return new TextBlock(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text == null ? "" : text;
		invalidateMeasure();
	}

	public double getFontSize() {
		return fontSize;
	}

	public void setFontSize(double fontSize) {
		this.fontSize = Math.max(0.0, fontSize);
		invalidateMeasure();
	}

	@Override
	protected void onMeasure(Size availableSize) {
		double width = Math.max(0.0, text.length() * fontSize * 0.6);
		double height = Math.max(0.0, fontSize * 1.2);
		setDesiredSize(new Size(width, height));
	}
}

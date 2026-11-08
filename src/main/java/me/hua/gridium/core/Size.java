package me.hua.gridium.core;

public class Size {
	public static final Size ZERO = new Size(0.0, 0.0);

	private final double width;
	private final double height;

	public Size(double width, double height) {
		this.width = requireDimension(width, "width");
		this.height = requireDimension(height, "height");
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public boolean isEmpty() {
		return width == 0.0 || height == 0.0;
	}

	public Size withWidth(double width) {
		return new Size(width, height);
	}

	public Size withHeight(double height) {
		return new Size(width, height);
	}

	@Override
	public String toString() {
		return "Size[width=" + width + ", height=" + height + "]";
	}

	private static double requireDimension(double value, String name) {
		if (!Double.isFinite(value) || value < 0.0) {
			throw new IllegalArgumentException(name + " must be finite and non-negative");
		}
		return value;
	}
}

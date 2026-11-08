package me.hua.gridium.core;

import java.util.Objects;

public class Rect {
	public static final Rect EMPTY = new Rect(0.0, 0.0, 0.0, 0.0);

	private final double x;
	private final double y;
	private final double width;
	private final double height;

	public Rect(double x, double y, double width, double height) {
		this.x = requireFinite(x, "x");
		this.y = requireFinite(y, "y");
		this.width = requireDimension(width, "width");
		this.height = requireDimension(height, "height");
		requireFinite(this.x + this.width, "right");
		requireFinite(this.y + this.height, "bottom");
	}

	public static Rect fromOriginAndSize(double x, double y, Size size) {
		Objects.requireNonNull(size, "size");
		return new Rect(x, y, size.getWidth(), size.getHeight());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getRight() {
		return x + width;
	}

	public double getBottom() {
		return y + height;
	}

	public Size getSize() {
		return new Size(width, height);
	}

	public Rect inset(Thickness thickness) {
		Objects.requireNonNull(thickness, "thickness");
		return new Rect(
			x + thickness.getLeft(),
			y + thickness.getTop(),
			Math.max(0.0, width - thickness.getHorizontal()),
			Math.max(0.0, height - thickness.getVertical())
		);
	}

	@Override
	public String toString() {
		return "Rect[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	private static double requireFinite(double value, String name) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException(name + " must be finite");
		}
		return value;
	}

	private static double requireDimension(double value, String name) {
		if (!Double.isFinite(value) || value < 0.0) {
			throw new IllegalArgumentException(name + " must be finite and non-negative");
		}
		return value;
	}
}

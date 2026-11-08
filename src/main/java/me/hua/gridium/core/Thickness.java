package me.hua.gridium.core;

public class Thickness {
	public static final Thickness ZERO = new Thickness(0.0, 0.0, 0.0, 0.0);

	private final double left;
	private final double top;
	private final double right;
	private final double bottom;

	public Thickness(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public static Thickness uniform(double uniform) {
		return new Thickness(uniform, uniform, uniform, uniform);
	}

	public double getLeft() {
		return left;
	}

	public double getTop() {
		return top;
	}

	public double getRight() {
		return right;
	}

	public double getBottom() {
		return bottom;
	}

	public double getHorizontal() {
		return left + right;
	}

	public double getVertical() {
		return top + bottom;
	}

	public boolean isZero() {
		return left == 0.0 && top == 0.0 && right == 0.0 && bottom == 0.0;
	}

	@Override
	public String toString() {
		return "Thickness[left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom + "]";
	}
}

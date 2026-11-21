package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;

import java.util.Objects;

/**
 * Integer GUI bounds produced by rounding each layout edge independently to the nearest pixel.
 * Ties follow {@link Math#round(double)} and move toward positive infinity.
 */
public record PixelBounds(int left, int top, int right, int bottom) {
	public PixelBounds {
		if (right < left || bottom < top) {
			throw new IllegalArgumentException("right and bottom must not precede left and top");
		}
	}

	public static PixelBounds from(Rect bounds) {
		Objects.requireNonNull(bounds, "bounds");
		return new PixelBounds(
				roundCoordinate(bounds.getX()),
				roundCoordinate(bounds.getY()),
				roundCoordinate(bounds.getRight()),
				roundCoordinate(bounds.getBottom())
		);
	}

	public static int roundCoordinate(double coordinate) {
		if (!Double.isFinite(coordinate)) {
			throw new IllegalArgumentException("coordinate must be finite");
		}
		long rounded = Math.round(coordinate);
		if (rounded < Integer.MIN_VALUE || rounded > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("coordinate must fit in an integer");
		}
		return (int) rounded;
	}

	public int width() {
		return difference(right, left, "width");
	}

	public int height() {
		return difference(bottom, top, "height");
	}

	private static int difference(int end, int start, String name) {
		long difference = (long) end - start;
		if (difference > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(name + " must fit in an integer");
		}
		return (int) difference;
	}
}

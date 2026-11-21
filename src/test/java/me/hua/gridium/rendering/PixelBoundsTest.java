package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PixelBoundsTest {
	@Test
	void roundsEachEdgeIndependentlyAndDerivesDimensionsFromRoundedEdges() {
		PixelBounds bounds = PixelBounds.from(new Rect(10.6, 5.25, 30.6, 20.6));

		assertEquals(11, bounds.left());
		assertEquals(5, bounds.top());
		assertEquals(41, bounds.right());
		assertEquals(26, bounds.bottom());
		assertEquals(30, bounds.width());
		assertEquals(21, bounds.height());
	}

	@Test
	void roundsHalfPixelsTowardPositiveInfinityForPositiveAndNegativeCoordinates() {
		PixelBounds bounds = PixelBounds.from(new Rect(-2.5, 4.5, 3.0, 4.0));

		assertEquals(-2, bounds.left());
		assertEquals(5, bounds.top());
		assertEquals(1, bounds.right());
		assertEquals(9, bounds.bottom());
	}

	@Test
	void subpixelBoundsCanCollapseToZeroNativeWidthAndHeight() {
		PixelBounds bounds = PixelBounds.from(new Rect(1.1, 2.1, 0.2, 0.2));

		assertEquals(1, bounds.left());
		assertEquals(1, bounds.right());
		assertEquals(2, bounds.top());
		assertEquals(2, bounds.bottom());
		assertEquals(0, bounds.width());
		assertEquals(0, bounds.height());
	}
}

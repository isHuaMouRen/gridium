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
}

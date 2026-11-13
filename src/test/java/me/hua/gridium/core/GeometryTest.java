package me.hua.gridium.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeometryTest {
	@Test
	void sizeAcceptsNonNegativeFiniteDimensions() {
		Size size = new Size(12.5, 0.0);

		assertEquals(12.5, size.getWidth());
		assertEquals(0.0, size.getHeight());
		assertTrue(size.isEmpty());
		assertEquals(new Size(8.0, 0.0).getWidth(), size.withWidth(8.0).getWidth());
		assertEquals(6.0, size.withHeight(6.0).getHeight());
	}

	@Test
	void sizeRejectsInvalidDimensions() {
		assertThrows(IllegalArgumentException.class, () -> new Size(-1.0, 0.0));
		assertThrows(IllegalArgumentException.class, () -> new Size(Double.NaN, 0.0));
		assertThrows(IllegalArgumentException.class, () -> new Size(0.0, Double.POSITIVE_INFINITY));
	}

	@Test
	void rectComputesEdgesAndInsetsWithoutNegativeDimensions() {
		Rect rect = new Rect(10.0, 20.0, 30.0, 40.0);

		assertEquals(40.0, rect.getRight());
		assertEquals(60.0, rect.getBottom());
		assertEquals(new Size(30.0, 40.0).getWidth(), rect.getSize().getWidth());
		Rect inset = rect.inset(new Thickness(2.0, 3.0, 4.0, 4.0));
		assertEquals(12.0, inset.getX());
		assertEquals(23.0, inset.getY());
		assertEquals(24.0, inset.getWidth());
		assertEquals(33.0, inset.getHeight());
		assertEquals(0.0, rect.inset(Thickness.uniform(100.0)).getWidth());
		assertEquals(0.0, rect.inset(Thickness.uniform(100.0)).getHeight());
	}

	@Test
	void rectRejectsInvalidCoordinatesAndDimensions() {
		assertThrows(IllegalArgumentException.class, () -> new Rect(Double.NaN, 0.0, 1.0, 1.0));
		assertThrows(IllegalArgumentException.class, () -> new Rect(0.0, 0.0, -1.0, 1.0));
		assertThrows(IllegalArgumentException.class, () -> new Rect(0.0, 0.0, 1.0, Double.POSITIVE_INFINITY));
		assertThrows(IllegalArgumentException.class,
				() -> new Rect(Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 1.0));
		assertThrows(NullPointerException.class, () -> Rect.fromOriginAndSize(0.0, 0.0, null));
		assertThrows(NullPointerException.class, () -> Rect.EMPTY.inset(null));
	}

	@Test
	void thicknessCalculatesInsetsAndRejectsInvalidValues() {
		Thickness thickness = new Thickness(1.0, 2.0, 3.0, 4.0);

		assertEquals(4.0, thickness.getHorizontal());
		assertEquals(6.0, thickness.getVertical());
		Thickness uniform = Thickness.uniform(5.0);
		assertEquals(5.0, uniform.getLeft());
		assertEquals(5.0, uniform.getTop());
		assertEquals(5.0, uniform.getRight());
		assertEquals(5.0, uniform.getBottom());
		assertFalse(thickness.isZero());
		assertTrue(Thickness.ZERO.isZero());

		assertThrows(IllegalArgumentException.class, () -> new Thickness(0.0, Double.NaN, 0.0, 0.0));
		assertThrows(IllegalArgumentException.class,
				() -> new Thickness(Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 0.0));
	}
}

package me.hua.gridium.core;

import me.hua.gridium.testutil.FixedElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FrameworkElementTransformTest {
	@Test
	void inverseHitTestingReversesLayoutScaleAndVisualTransformAroundOrigin() {
		FixedElement element = new FixedElement(40.0, 20.0)
				.setLayoutScale(2.0, 1.5)
				.setRenderTransform(new RenderTransform(5.0, -4.0, 2.0, 3.0, 90.0))
				.setRenderTransformOrigin(0.5, 0.5);
		element.arrange(new Rect(10.0, 20.0, 80.0, 30.0));

		FrameworkElement.LocalPoint localPoint = element.toLocalPoint(77.5, -9.0);

		assertEquals(20.0, localPoint.x(), 1.0e-9);
		assertEquals(25.0, localPoint.y(), 1.0e-9);
		assertTrue(element.containsPoint(77.5, -9.0));
		assertFalse(element.containsPoint(77.5, -53.0));
	}

	@Test
	void zeroVisualScaleAndNonFinitePointerCoordinatesCannotHit() {
		FixedElement element = new FixedElement(20.0, 10.0)
				.setRenderTransform(new RenderTransform(3.0, 4.0, 0.0, 1.0, 0.0));
		element.arrange(new Rect(10.0, 20.0, 20.0, 10.0));

		assertNull(element.toLocalPoint(13.0, 24.0));
		assertFalse(element.containsPoint(13.0, 24.0));

		FixedElement normalElement = new FixedElement(20.0, 10.0);
		normalElement.arrange(new Rect(10.0, 20.0, 20.0, 10.0));
		assertNull(normalElement.toLocalPoint(Double.NaN, 20.0));
		assertNull(normalElement.toLocalPoint(10.0, Double.POSITIVE_INFINITY));
		assertFalse(normalElement.containsPoint(Double.NEGATIVE_INFINITY, 20.0));
	}
}

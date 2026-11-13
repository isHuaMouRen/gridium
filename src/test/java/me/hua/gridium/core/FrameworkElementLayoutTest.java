package me.hua.gridium.core;

import me.hua.gridium.testutil.FixedElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FrameworkElementLayoutTest {
	@Test
	void measureAccountsForMarginsAndLayoutScale() {
		FixedElement element = new FixedElement(10.0, 5.0)
				.setMargin(new Thickness(1.0, 2.0, 3.0, 4.0))
				.setLayoutScale(2.0, 3.0);

		element.measure(new Size(50.0, 40.0));

		assertEquals(new Size(23.0, 34.0).getWidth(), element.getLastAvailableSize().getWidth());
		assertEquals(34.0 / 3.0, element.getLastAvailableSize().getHeight());
		assertEquals(24.0, element.getDesiredSize().getWidth());
		assertEquals(21.0, element.getDesiredSize().getHeight());
	}

	@Test
	void arrangeExcludesMarginsAndReportsScaledActualSize() {
		FixedElement element = new FixedElement(10.0, 5.0)
				.setMargin(new Thickness(1.0, 2.0, 3.0, 4.0))
				.setLayoutScale(2.0, 3.0);

		element.measure(new Size(200.0, 200.0));
		element.arrange(new Rect(10.0, 20.0, 104.0, 96.0));

		assertEquals(11.0, element.getBounds().getX());
		assertEquals(22.0, element.getBounds().getY());
		assertEquals(50.0, element.getBounds().getWidth());
		assertEquals(30.0, element.getBounds().getHeight());
		assertEquals(100.0, element.getActualWidth());
		assertEquals(90.0, element.getActualHeight());
	}

	@Test
	void layoutScaleMustBeFinitePositiveAndRepresentableAsFloat() {
		FixedElement element = new FixedElement(1.0, 1.0);

		assertThrows(IllegalArgumentException.class, () -> element.setLayoutScale(0.0, 1.0));
		assertThrows(IllegalArgumentException.class, () -> element.setLayoutScale(1.0, -1.0));
		assertThrows(IllegalArgumentException.class, () -> element.setLayoutScale(Double.NaN, 1.0));
		assertThrows(IllegalArgumentException.class, () -> element.setLayoutScale(1.0, Double.MAX_VALUE));
		assertThrows(NullPointerException.class, () -> element.setMargin(null));
		assertThrows(NullPointerException.class, () -> element.setHorizontalAlignment(null));
		assertThrows(NullPointerException.class, () -> element.setVerticalAlignment(null));
	}

	@Test
	void measureAndArrangeRejectNullArguments() {
		FixedElement element = new FixedElement(1.0, 1.0);

		assertThrows(NullPointerException.class, () -> element.measure(null));
		assertThrows(NullPointerException.class, () -> element.arrange(null));
	}
}

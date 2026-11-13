package me.hua.gridium.layout;

import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.testutil.FixedElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridLayoutTest {
	@Test
	void defaultAlignmentPlacesChildAtTopLeft() {
		Grid grid = new Grid();
		FixedElement child = new FixedElement(20.0, 10.0);
		grid.add(child);

		layout(grid, new Rect(7.0, 11.0, 100.0, 80.0));

		assertBounds(child, new Rect(7.0, 11.0, 20.0, 10.0));
	}

	@Test
	void centerAndBottomAlignmentUseAvailableArea() {
		Grid grid = new Grid();
		FixedElement child = new FixedElement(20.0, 10.0)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.BOTTOM);
		grid.add(child);

		layout(grid, new Rect(7.0, 11.0, 100.0, 80.0));

		assertBounds(child, new Rect(47.0, 81.0, 20.0, 10.0));
	}

	@Test
	void rightAndCenterAlignmentUseAvailableArea() {
		Grid grid = new Grid();
		FixedElement child = new FixedElement(20.0, 10.0)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setVerticalAlignment(VerticalAlignment.CENTER);
		grid.add(child);

		layout(grid, new Rect(7.0, 11.0, 100.0, 80.0));

		assertBounds(child, new Rect(87.0, 46.0, 20.0, 10.0));
	}

	@Test
	void stretchFillsAvailableAreaAfterMargins() {
		Grid grid = new Grid();
		FixedElement child = new FixedElement(20.0, 10.0)
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(3.0, 4.0, 5.0, 6.0));
		grid.add(child);

		layout(grid, new Rect(10.0, 20.0, 100.0, 80.0));

		assertBounds(child, new Rect(13.0, 24.0, 92.0, 70.0));
	}

	@Test
	void childMeasurementUsesAvailableSize() {
		Grid grid = new Grid();
		FixedElement child = new FixedElement(20.0, 10.0)
				.setMargin(new Thickness(2.0, 3.0, 4.0, 5.0));
		grid.add(child);

		grid.measure(new Size(100.0, 80.0));

		assertEquals(94.0, child.getLastAvailableSize().getWidth());
		assertEquals(72.0, child.getLastAvailableSize().getHeight());
		assertEquals(26.0, child.getDesiredSize().getWidth());
		assertEquals(18.0, child.getDesiredSize().getHeight());
		assertEquals(26.0, grid.getDesiredSize().getWidth());
		assertEquals(18.0, grid.getDesiredSize().getHeight());
	}

	private static void layout(Grid grid, Rect bounds) {
		grid.measure(bounds.getSize());
		grid.arrange(bounds);
	}

	private static void assertBounds(FixedElement element, Rect expected) {
		assertEquals(expected.getX(), element.getBounds().getX());
		assertEquals(expected.getY(), element.getBounds().getY());
		assertEquals(expected.getWidth(), element.getBounds().getWidth());
		assertEquals(expected.getHeight(), element.getBounds().getHeight());
	}
}

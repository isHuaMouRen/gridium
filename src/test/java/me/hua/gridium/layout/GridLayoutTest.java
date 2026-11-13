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

	@Test
	void nestedGridMarginOffsetsItsChildrenAsAGroup() {
		Grid root = new Grid();
		Grid childGrid = new Grid().setMargin(new Thickness(10.0, 20.0, 30.0, 40.0));
		FixedElement firstChild = new FixedElement(12.0, 6.0)
				.setMargin(new Thickness(1.0, 2.0, 3.0, 4.0));
		FixedElement secondChild = new FixedElement(8.0, 4.0);
		childGrid.add(firstChild);
		childGrid.add(secondChild);
		root.add(childGrid);

		root.measure(new Size(200.0, 120.0));
		root.arrange(new Rect(5.0, 7.0, 200.0, 120.0));

		assertEquals(56.0, childGrid.getDesiredSize().getWidth());
		assertEquals(72.0, childGrid.getDesiredSize().getHeight());
		assertEquals(15.0, childGrid.getBounds().getX());
		assertEquals(27.0, childGrid.getBounds().getY());
		assertEquals(16.0, childGrid.getBounds().getWidth());
		assertEquals(12.0, childGrid.getBounds().getHeight());
		assertBounds(firstChild, new Rect(16.0, 29.0, 12.0, 6.0));
		assertBounds(secondChild, new Rect(15.0, 27.0, 8.0, 4.0));
	}

	@Test
	void nestedGridMarginsReduceAvailableSizeForItsChildren() {
		Grid root = new Grid();
		Grid childGrid = new Grid().setMargin(new Thickness(10.0, 20.0, 30.0, 40.0));
		FixedElement child = new FixedElement(12.0, 6.0)
				.setMargin(new Thickness(1.0, 2.0, 3.0, 4.0));
		childGrid.add(child);
		root.add(childGrid);

		root.measure(new Size(200.0, 120.0));

		assertEquals(156.0, child.getLastAvailableSize().getWidth());
		assertEquals(54.0, child.getLastAvailableSize().getHeight());
		assertEquals(56.0, root.getDesiredSize().getWidth());
		assertEquals(72.0, root.getDesiredSize().getHeight());
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

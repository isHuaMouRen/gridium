package me.hua.gridium.layout;

import me.hua.gridium.controls.GridTooltip;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.testutil.FixedElement;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StackPanelLayoutTest {
	@Test
	void horizontalStackMeasuresAndArrangesChildrenInOrderWithSpacing() {
		StackPanel panel = new StackPanel(StackOrientation.HORIZONTAL).setSpacing(5.0);
		FixedElement first = new FixedElement(20.0, 10.0);
		FixedElement second = new FixedElement(30.0, 15.0);
		panel.add(first);
		panel.add(second);

		panel.measure(new Size(200.0, 100.0));
		panel.arrange(new Rect(10.0, 20.0, 100.0, 40.0));

		assertSize(panel.getDesiredSize(), new Size(55.0, 15.0));
		assertBounds(first, new Rect(10.0, 20.0, 20.0, 10.0));
		assertBounds(second, new Rect(35.0, 20.0, 30.0, 15.0));
	}

	@Test
	void horizontalStackKeepsDesiredSizesAndSpacingWhenItOverflows() {
		StackPanel panel = new StackPanel(StackOrientation.HORIZONTAL).setSpacing(5.0);
		FixedElement first = new FixedElement(60.0, 10.0);
		FixedElement second = new FixedElement(50.0, 10.0);
		panel.add(first);
		panel.add(second);

		panel.measure(new Size(100.0, 30.0));
		panel.arrange(new Rect(10.0, 20.0, 100.0, 30.0));

		assertSize(panel.getDesiredSize(), new Size(115.0, 10.0));
		assertBounds(first, new Rect(10.0, 20.0, 60.0, 10.0));
		assertBounds(second, new Rect(75.0, 20.0, 50.0, 10.0));
	}

	@Test
	void verticalStackMeasuresChildrenAndAppliesCrossAxisAlignment() {
		StackPanel panel = new StackPanel().setSpacing(3.0);
		FixedElement first = new FixedElement(20.0, 10.0)
				.setHorizontalAlignment(HorizontalAlignment.CENTER);
		FixedElement second = new FixedElement(30.0, 15.0)
				.setHorizontalAlignment(HorizontalAlignment.STRETCH);
		panel.add(first);
		panel.add(second);

		panel.measure(new Size(100.0, 200.0));
		panel.arrange(new Rect(5.0, 7.0, 50.0, 100.0));

		assertSize(panel.getDesiredSize(), new Size(30.0, 28.0));
		assertBounds(first, new Rect(20.0, 7.0, 20.0, 10.0));
		assertBounds(second, new Rect(5.0, 20.0, 50.0, 15.0));
	}

	@Test
	void spacingIsAddedBetweenVisibleChildrenAndMarginsAndScaleArePreserved() {
		StackPanel panel = new StackPanel(StackOrientation.HORIZONTAL).setSpacing(2.0);
		FixedElement first = new FixedElement(10.0, 4.0)
				.setMargin(new Thickness(1.0, 2.0, 3.0, 4.0))
				.setLayoutScale(2.0, 3.0);
		FixedElement hidden = new FixedElement(80.0, 8.0);
		hidden.setVisible(false);
		FixedElement last = new FixedElement(5.0, 6.0);
		panel.add(first);
		panel.add(hidden);
		panel.add(last);

		panel.measure(new Size(200.0, 100.0));
		panel.arrange(new Rect(0.0, 0.0, 100.0, 30.0));

		assertSize(panel.getDesiredSize(), new Size(31.0, 18.0));
		assertBounds(first, new Rect(1.0, 2.0, 10.0, 4.0));
		assertBounds(hidden, Rect.EMPTY);
		assertBounds(last, new Rect(26.0, 0.0, 5.0, 6.0));
	}

	@Test
	void hiddenChildrenDoNotConsumeSpacing() {
		StackPanel panel = new StackPanel(StackOrientation.VERTICAL).setSpacing(4.0);
		FixedElement first = new FixedElement(10.0, 8.0);
		FixedElement hidden = new FixedElement(20.0, 40.0);
		hidden.setVisible(false);
		FixedElement last = new FixedElement(12.0, 6.0);
		panel.add(first);
		panel.add(hidden);
		panel.add(last);

		panel.measure(new Size(100.0, 100.0));
		panel.arrange(new Rect(0.0, 0.0, 100.0, 50.0));

		assertSize(panel.getDesiredSize(), new Size(12.0, 18.0));
		assertBounds(first, new Rect(0.0, 0.0, 10.0, 8.0));
		assertBounds(hidden, Rect.EMPTY);
		assertBounds(last, new Rect(0.0, 12.0, 12.0, 6.0));
	}

	@Test
	void zeroSizedVisibleChildStillSeparatesStackedChildrenBySpacing() {
		StackPanel panel = new StackPanel(StackOrientation.VERTICAL).setSpacing(4.0);
		FixedElement zeroSized = new FixedElement(0.0, 0.0);
		FixedElement next = new FixedElement(10.0, 5.0);
		panel.add(zeroSized);
		panel.add(next);

		panel.measure(new Size(0.0, 0.0));
		panel.arrange(new Rect(2.0, 3.0, 0.0, 0.0));

		assertSize(panel.getDesiredSize(), new Size(10.0, 9.0));
		assertBounds(zeroSized, new Rect(2.0, 3.0, 0.0, 0.0));
		assertBounds(next, new Rect(2.0, 7.0, 10.0, 5.0));
	}

	@Test
	void addingAndRemovingChildrenUpdatesTheNextLayoutPass() {
		StackPanel panel = new StackPanel(StackOrientation.VERTICAL).setSpacing(2.0);
		FixedElement first = new FixedElement(10.0, 5.0);
		FixedElement second = new FixedElement(20.0, 7.0);
		panel.add(first);

		panel.measure(new Size(100.0, 100.0));
		panel.arrange(new Rect(0.0, 0.0, 100.0, 100.0));
		assertSize(panel.getDesiredSize(), new Size(10.0, 5.0));

		panel.add(second);
		panel.measure(new Size(100.0, 100.0));
		panel.arrange(new Rect(0.0, 0.0, 100.0, 100.0));
		assertSize(panel.getDesiredSize(), new Size(20.0, 14.0));
		assertBounds(second, new Rect(0.0, 7.0, 20.0, 7.0));

		assertTrue(panel.remove(first));
		panel.measure(new Size(100.0, 100.0));
		panel.arrange(new Rect(0.0, 0.0, 100.0, 100.0));
		assertSize(panel.getDesiredSize(), new Size(20.0, 7.0));
		assertBounds(second, new Rect(0.0, 0.0, 20.0, 7.0));
		assertFalse(panel.remove(first));
	}

	@Test
	void nestedGridAndStackPanelArrangeChildren() {
		Grid root = new Grid();
		StackPanel stack = new StackPanel(StackOrientation.VERTICAL).setSpacing(2.0);
		FixedElement first = new FixedElement(10.0, 5.0);
		FixedElement second = new FixedElement(12.0, 7.0);
		stack.add(first);
		stack.add(second);
		root.add(stack);

		root.measure(new Size(100.0, 100.0));
		root.arrange(new Rect(3.0, 4.0, 100.0, 100.0));

		assertBounds(first, new Rect(3.0, 4.0, 10.0, 5.0));
		assertBounds(second, new Rect(3.0, 11.0, 12.0, 7.0));
	}

	@Test
	void mixedPanelsRejectCyclesInEitherNestingOrder() {
		Grid grid = new Grid();
		StackPanel stack = new StackPanel();
		grid.add(stack);
		assertThrows(IllegalArgumentException.class, () -> stack.add(grid));

		Grid otherGrid = new Grid();
		StackPanel otherStack = new StackPanel();
		otherStack.add(otherGrid);
		assertThrows(IllegalArgumentException.class, () -> otherGrid.add(otherStack));
		assertThrows(IllegalArgumentException.class, () -> otherStack.add(otherStack));
	}

	@Test
	void tooltipCanBeFoundThroughMixedNestedPanels() {
		Grid root = new Grid();
		StackPanel stack = new StackPanel();
		Grid nestedGrid = new Grid();
		GridTooltip tooltip = new GridTooltip(testFont(), Component.literal("Nested tooltip"));
		FixedElement child = new FixedElement(20.0, 10.0).setTooltip(tooltip);
		nestedGrid.add(child);
		stack.add(nestedGrid);
		root.add(stack);
		root.measure(new Size(100.0, 100.0));
		root.arrange(new Rect(0.0, 0.0, 100.0, 100.0));

		assertSame(tooltip, root.findTooltipAt(5.0, 5.0));
	}

	@Test
	void tooltipCanBeFoundOutsideAnAncestorPanelBoundsWhenItsChildOverflows() {
		Grid root = new Grid();
		Grid nestedGrid = new Grid()
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(0.0, 0.0, 50.0, 0.0));
		GridTooltip tooltip = new GridTooltip(testFont(), Component.literal("Overflow tooltip"));
		FixedElement child = new FixedElement(80.0, 10.0).setTooltip(tooltip);
		nestedGrid.add(child);
		root.add(nestedGrid);

		root.measure(new Size(100.0, 50.0));
		root.arrange(new Rect(0.0, 0.0, 100.0, 50.0));

		assertBounds(child, new Rect(0.0, 0.0, 80.0, 10.0));
		assertSame(tooltip, root.findTooltipAt(60.0, 5.0));
	}

	@Test
	void spacingMustBeFiniteAndNonNegative() {
		StackPanel panel = new StackPanel();

		assertThrows(IllegalArgumentException.class, () -> panel.setSpacing(-1.0));
		assertThrows(IllegalArgumentException.class, () -> panel.setSpacing(Double.NaN));
		assertThrows(IllegalArgumentException.class, () -> panel.setSpacing(Double.POSITIVE_INFINITY));
	}

	private static Font testFont() {
		return new Font(new Font.Provider() {
			@Override
			public net.minecraft.client.gui.GlyphSource glyphs(net.minecraft.network.chat.FontDescription description) {
				return null;
			}

			@Override
			public net.minecraft.client.gui.font.glyphs.EffectGlyph effect() {
				return null;
			}
		});
	}

	private static void assertBounds(FixedElement element, Rect expected) {
		assertEquals(expected.getX(), element.getBounds().getX());
		assertEquals(expected.getY(), element.getBounds().getY());
		assertEquals(expected.getWidth(), element.getBounds().getWidth());
		assertEquals(expected.getHeight(), element.getBounds().getHeight());
	}

	private static void assertSize(Size actual, Size expected) {
		assertEquals(expected.getWidth(), actual.getWidth());
		assertEquals(expected.getHeight(), actual.getHeight());
	}
}

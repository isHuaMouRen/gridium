package me.hua.gridium.integration.minecraft;

import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.layout.Grid;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinecraftWidgetTest {
	@Test
	void measuresAndAppliesArrangedBoundsToNativeWidget() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).bounds(1, 2, 80, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);

		element.measure(new Size(200.0, 100.0));
		element.arrange(new Rect(10.0, 15.0, 120.0, 30.0));

		assertEquals(80.0, element.getDesiredSize().getWidth());
		assertEquals(20.0, element.getDesiredSize().getHeight());
		assertEquals(10, widget.getX());
		assertEquals(15, widget.getY());
		assertEquals(120, widget.getWidth());
		assertEquals(30, widget.getHeight());
	}

	@Test
	void adapterVisibilityStaysInSyncWithNativeWidget() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).size(80, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);

		element.setVisible(false);

		assertFalse(element.isVisible());
		assertFalse(widget.visible);

		widget.visible = true;
		assertFalse(element.isVisible());

		element.setVisible(true);
		assertTrue(element.isVisible());
		assertTrue(widget.visible);
	}

	@Test
	void layoutScaleIsRejectedBecauseItWouldMoveVisualAndInputBoundsApart() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).size(80, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);

		IllegalStateException exception = assertThrows(
				IllegalStateException.class,
				() -> element.setLayoutScale(2.0, 2.0)
		);

		assertTrue(exception.getMessage().contains("native input"));
	}

	@Test
	void renderTransformIsRejectedBecauseItWouldMoveVisualAndInputBoundsApart() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).size(80, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);

		IllegalStateException exception = assertThrows(
				IllegalStateException.class,
				() -> element.setRenderTransform(new RenderTransform(4.0, 5.0, 1.0, 1.0, 30.0))
		);

		assertTrue(exception.getMessage().contains("native input"));
	}

	@Test
	void nativeHitboxMatchesRoundedArrangedBoundsWithoutClipping() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).size(80, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);

		element.arrange(new Rect(10.6, 15.2, 30.6, 20.6));

		assertEquals(11, widget.getX());
		assertEquals(15, widget.getY());
		assertEquals(30, widget.getWidth());
		assertEquals(21, widget.getHeight());
		assertTrue(widget.isMouseOver(11.0, 15.0));
		assertTrue(widget.isMouseOver(40.0, 35.0));
		assertFalse(widget.isMouseOver(41.0, 36.0));
	}

	@Test
	void nestedWidgetTracksCoordinatesAndSizeAcrossLayoutPasses() {
		Grid root = new Grid();
		Grid nested = new Grid()
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(5.0));
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).size(30, 10).build();
		MinecraftWidget element = new MinecraftWidget(widget)
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH);
		nested.add(element);
		root.add(nested);

		root.measure(new Size(120.0, 80.0));
		root.arrange(new Rect(0.0, 0.0, 120.0, 80.0));

		assertEquals(5, widget.getX());
		assertEquals(5, widget.getY());
		assertEquals(110, widget.getWidth());
		assertEquals(70, widget.getHeight());
		assertTrue(widget.isMouseOver(6.0, 6.0));

		root.measure(new Size(160.0, 100.0));
		root.arrange(new Rect(0.0, 0.0, 160.0, 100.0));

		assertEquals(5, widget.getX());
		assertEquals(5, widget.getY());
		assertEquals(150, widget.getWidth());
		assertEquals(90, widget.getHeight());
		assertTrue(widget.isMouseOver(154.0, 94.0));
	}

	@Test
	void hiddenFocusedNativeWidgetRejectsInputAndResumesWithFocusWhenShown() {
		EditBox widget = new EditBox(testFont(), Component.literal("Test"));
		widget.setEditable(false);
		widget.setFocused(true);
		MinecraftWidget element = new MinecraftWidget(widget);
		KeyEvent backspace = new KeyEvent(0, 8, 0);

		element.setVisible(false);

		assertFalse(element.isVisible());
		assertFalse(widget.isActive());
		assertFalse(widget.keyPressed(backspace));
		assertEquals(0, widget.getCursorPosition());
		assertTrue(widget.isFocused());

		element.setVisible(true);

		assertTrue(element.isVisible());
		assertTrue(widget.isActive());
		assertTrue(widget.keyPressed(backspace));
		assertEquals(0, widget.getCursorPosition());
		assertTrue(widget.isFocused());
	}

	@Test
	void hiddenNativeButtonRejectsMouseInput() {
		Button widget = Button.builder(Component.literal("Test"), button -> {
		}).bounds(10, 10, 40, 20).build();
		MinecraftWidget element = new MinecraftWidget(widget);
		element.setVisible(false);

		assertFalse(widget.isMouseOver(20.0, 20.0));
		assertFalse(widget.mouseClicked(
				new MouseButtonEvent(
						20.0,
						20.0,
						new MouseButtonInfo(0, 0)
				),
				false
		));
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
}

package me.hua.gridium.integration.minecraft;

import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.RenderTransform;
import net.minecraft.client.gui.components.Button;
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

		element.arrange(new Rect(10.4, 15.6, 30.6, 20.4));

		assertEquals(10, widget.getX());
		assertEquals(16, widget.getY());
		assertEquals(31, widget.getWidth());
		assertEquals(20, widget.getHeight());
		assertTrue(widget.isMouseOver(10.0, 16.0));
		assertTrue(widget.isMouseOver(40.0, 35.0));
		assertFalse(widget.isMouseOver(41.0, 36.0));
	}
}

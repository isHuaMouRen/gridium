package me.hua.gridium.integration.minecraft;

import me.hua.gridium.controls.MinecraftWidget;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
}

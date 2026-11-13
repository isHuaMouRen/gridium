package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.Objects;

/**
 * Adapts a native Minecraft widget to Grid layout. Register the wrapped widget with its Screen
 * using {@code addWidget}, not {@code addRenderableWidget}; this element extracts its render state.
 * Layout transforms and clipping are not supported because native input still uses widget bounds.
 */
public class MinecraftWidget extends FrameworkElement<MinecraftWidget> {
	private final AbstractWidget widget;

	public MinecraftWidget(AbstractWidget widget) {
		this.widget = Objects.requireNonNull(widget, "widget");
		super.setVisible(widget.visible);
	}

	public AbstractWidget getWidget() {
		return widget;
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && widget.visible;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		widget.visible = visible;
	}

	@Override
	protected MinecraftWidget self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(widget.getWidth(), widget.getHeight());
	}

	@Override
	protected void arrangeContent(Rect finalRect) {
		widget.setPosition(
				(int) Math.round(finalRect.getX()),
				(int) Math.round(finalRect.getY())
		);
		widget.setSize(
				(int) Math.round(finalRect.getWidth()),
				(int) Math.round(finalRect.getHeight())
		);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

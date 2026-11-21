package me.hua.gridium.integration.minecraft;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.Objects;

/**
 * Adapts a native Minecraft widget to Grid layout. Register the wrapped widget with its Screen
 * using {@code addWidget}, not {@code addRenderableWidget}; this element extracts its render state.
 *
 * <p>Layout scaling and visual render transforms are not supported because they would separate the
 * rendered widget from its native hitbox. The native widget's hitbox is exactly the rounded,
 * arranged {@link Rect} position and size, and may extend beyond a panel or the screen viewport;
 * Gridium does not clip widget drawing or input. Use a native widget only inside an area you intend
 * to expose for input. Hiding the element also disables native input, while preserving the native
 * widget's focus; showing it again makes it interactive with that focus state intact.</p>
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
	public MinecraftWidget setLayoutScale(double scaleX, double scaleY) {
		if (scaleX != 1.0 || scaleY != 1.0) {
			throw new IllegalStateException(
					"MinecraftWidget does not support layout scaling; native input uses the widget bounds"
			);
		}
		return super.setLayoutScale(scaleX, scaleY);
	}

	@Override
	public MinecraftWidget setRenderTransform(RenderTransform renderTransform) {
		Objects.requireNonNull(renderTransform, "renderTransform");
		if (!renderTransform.isIdentity()) {
			throw new IllegalStateException(
					"MinecraftWidget does not support render transforms; native input uses the widget bounds"
			);
		}
		return super.setRenderTransform(renderTransform);
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

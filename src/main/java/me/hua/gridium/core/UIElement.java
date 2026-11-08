package me.hua.gridium.core;

import me.hua.gridium.rendering.GridRenderer;

import java.util.Objects;

public class UIElement {
	private Rect bounds = Rect.EMPTY;
	private Size desiredSize = Size.ZERO;
	private boolean visible = true;

	public final Rect getBounds() {
		return bounds;
	}

	public final Size getDesiredSize() {
		return desiredSize;
	}

	public final boolean isVisible() {
		return visible;
	}

	public final void setVisible(boolean visible) {
		this.visible = visible;
	}

	public final void measure(Size availableSize) {
		Objects.requireNonNull(availableSize, "availableSize");
		desiredSize = visible
			? Objects.requireNonNull(measureOverride(availableSize), "measureOverride result")
			: Size.ZERO;
	}

	public final void arrange(Rect finalRect) {
		Objects.requireNonNull(finalRect, "finalRect");
		bounds = visible
			? Objects.requireNonNull(arrangeOverride(finalRect), "arrangeOverride result")
			: new Rect(finalRect.getX(), finalRect.getY(), 0.0, 0.0);
	}

	public final void render(GridRenderer renderer) {
		Objects.requireNonNull(renderer, "renderer");
		if (visible) {
			renderOverride(renderer);
		}
	}

	protected Size measureOverride(Size availableSize) {
		return Size.ZERO;
	}

	protected Rect arrangeOverride(Rect finalRect) {
		return finalRect;
	}

	protected void renderOverride(GridRenderer renderer) {
	}

	@Override
	public String toString() {
		return "UIElement[bounds=" + bounds + ", desiredSize=" + desiredSize + "]";
	}
}

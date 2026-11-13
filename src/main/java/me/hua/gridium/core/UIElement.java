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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
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
			if (this instanceof FrameworkElement<?> frameworkElement
					&& (frameworkElement.getLayoutScaleX() != 1.0
					|| frameworkElement.getLayoutScaleY() != 1.0
					|| !frameworkElement.getRenderTransform().isIdentity())) {
				Rect bounds = getBounds();
				renderer.pushElementTransform(
						bounds,
						frameworkElement.getLayoutScaleX(),
						frameworkElement.getLayoutScaleY(),
						frameworkElement.getRenderTransform(),
						frameworkElement.getRenderTransformOriginX(),
						frameworkElement.getRenderTransformOriginY()
				);
				try {
					renderOverride(renderer);
				} finally {
					renderer.popElementTransform();
				}
			} else {
				renderOverride(renderer);
			}
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

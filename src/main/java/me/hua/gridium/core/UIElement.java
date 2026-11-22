package me.hua.gridium.core;

import me.hua.gridium.rendering.GridRenderer;

import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Base layout and rendering node. An element is first measured against an available size, then
 * arranged into a final {@link Rect}, and optionally rendered through a {@link GridRenderer}.
 *
 * <p>{@link #setVisible(boolean)} removes the element from visible layout: measurement reports
 * {@link Size#ZERO} and arrangement produces a zero-sized bounds at the arranged position.</p>
 */
public class UIElement {
	private Rect bounds = Rect.EMPTY;
	private Size desiredSize = Size.ZERO;
	private boolean visible = true;
	private @Nullable UIElement parent;

	public final Rect getBounds() {
		return bounds;
	}

	/** Returns the last measured desired size, or {@link Size#ZERO} before the first layout pass. */
	public final Size getDesiredSize() {
		return desiredSize;
	}

	/** Returns whether the element participates in measurement, arrangement, and rendering. */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets whether the element participates in layout and rendering.
	 *
	 * <p>Hidden elements do not consume their desired size. Call {@link #measure(Size)} and
	 * {@link #arrange(Rect)} again, or run the owning container's next layout pass, to update
	 * the layout.</p>
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/** Returns the panel that owns this element, or {@code null} if it is not attached. */
	public final @Nullable UIElement getParent() {
		return parent;
	}

	/** Updates parent ownership for panel implementations. */
	protected static final void setParentInternal(UIElement element, @Nullable UIElement parent) {
		element.parent = parent;
	}

	/** Measures this element against a finite, non-negative available size. */
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

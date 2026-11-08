package me.hua.gridium.core;

import java.util.Objects;

public class UIElement {
	private final String name;
	private Rect bounds = Rect.EMPTY;
	private Size desiredSize = Size.ZERO;
	private Size lastAvailableSize = Size.ZERO;
	private boolean visible = true;
	private boolean measureValid;
	private boolean arrangeValid;

	public UIElement() {
		this("ui-element");
	}

	public UIElement(String name) {
		this.name = Objects.requireNonNull(name, "name");
	}

	public final String getName() {
		return name;
	}

	public final Rect getBounds() {
		return bounds;
	}

	public final Size getDesiredSize() {
		return desiredSize;
	}

	public final Size getLastAvailableSize() {
		return lastAvailableSize;
	}

	public final boolean isVisible() {
		return visible;
	}

	public final void setVisible(boolean visible) {
		if (this.visible != visible) {
			this.visible = visible;
			invalidateMeasure();
		}
	}

	public final boolean isMeasureValid() {
		return measureValid;
	}

	public final boolean isArrangeValid() {
		return arrangeValid;
	}

	public final void measure(Size availableSize) {
		Objects.requireNonNull(availableSize, "availableSize");
		measureValid = false;
		arrangeValid = false;
		lastAvailableSize = availableSize;
		desiredSize = visible
			? Objects.requireNonNull(measureOverride(availableSize), "measureOverride result")
			: Size.ZERO;
		measureValid = true;
	}

	public final void arrange(Rect finalRect) {
		Objects.requireNonNull(finalRect, "finalRect");
		arrangeValid = false;
		bounds = visible
			? Objects.requireNonNull(arrangeOverride(finalRect), "arrangeOverride result")
			: new Rect(finalRect.getX(), finalRect.getY(), 0.0, 0.0);
		arrangeValid = true;
	}

	protected Size measureOverride(Size availableSize) {
		return Size.ZERO;
	}

	protected Rect arrangeOverride(Rect finalRect) {
		return finalRect;
	}

	public final void invalidateMeasure() {
		measureValid = false;
		invalidateArrange();
	}

	public final void invalidateArrange() {
		arrangeValid = false;
	}

	@Override
	public String toString() {
		return "UIElement[name=" + name + ", bounds=" + bounds + ", desiredSize=" + desiredSize + "]";
	}
}

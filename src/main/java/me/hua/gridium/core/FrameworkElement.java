package me.hua.gridium.core;

import java.util.Objects;

public class FrameworkElement extends UIElement {
	private Thickness margin = Thickness.ZERO;
	private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;

	public FrameworkElement() {
		super();
	}

	public FrameworkElement(String name) {
		super(name);
	}

	public final Thickness getMargin() {
		return margin;
	}

	public final void setMargin(Thickness margin) {
		Thickness newMargin = Objects.requireNonNull(margin, "margin");
		if (!hasSameValues(this.margin, newMargin)) {
			this.margin = newMargin;
			invalidateMeasure();
		}
	}

	public final HorizontalAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public final void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		HorizontalAlignment newAlignment = Objects.requireNonNull(horizontalAlignment, "horizontalAlignment");
		if (this.horizontalAlignment != newAlignment) {
			this.horizontalAlignment = newAlignment;
			invalidateArrange();
		}
	}

	public final VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public final void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		VerticalAlignment newAlignment = Objects.requireNonNull(verticalAlignment, "verticalAlignment");
		if (this.verticalAlignment != newAlignment) {
			this.verticalAlignment = newAlignment;
			invalidateArrange();
		}
	}

	@Override
	protected final Size measureOverride(Size availableSize) {
		Size contentAvailableSize = new Size(
			Math.max(0.0, availableSize.getWidth() - margin.getHorizontal()),
			Math.max(0.0, availableSize.getHeight() - margin.getVertical())
		);
		Size contentDesiredSize = Objects.requireNonNull(
			measureContent(contentAvailableSize),
			"measureContent result"
		);
		return new Size(
			contentDesiredSize.getWidth() + margin.getHorizontal(),
			contentDesiredSize.getHeight() + margin.getVertical()
		);
	}

	@Override
	protected final Rect arrangeOverride(Rect finalRect) {
		Rect contentRect = finalRect.inset(margin);
		arrangeContent(contentRect);
		return contentRect;
	}

	protected Size measureContent(Size availableSize) {
		return Size.ZERO;
	}

	protected void arrangeContent(Rect finalRect) {
	}

	private static boolean hasSameValues(Thickness first, Thickness second) {
		return Double.compare(first.getLeft(), second.getLeft()) == 0
			&& Double.compare(first.getTop(), second.getTop()) == 0
			&& Double.compare(first.getRight(), second.getRight()) == 0
			&& Double.compare(first.getBottom(), second.getBottom()) == 0;
	}
}

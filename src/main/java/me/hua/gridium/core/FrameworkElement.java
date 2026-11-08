package me.hua.gridium.core;

import java.util.Objects;

public abstract class FrameworkElement<T extends FrameworkElement<T>> extends UIElement {
	private Thickness margin = Thickness.ZERO;
	private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;

	public final Thickness getMargin() {
		return margin;
	}

	public final T setMargin(Thickness margin) {
		this.margin = Objects.requireNonNull(margin, "margin");
		return self();
	}

	public final HorizontalAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public final T setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		this.horizontalAlignment = Objects.requireNonNull(horizontalAlignment, "horizontalAlignment");
		return self();
	}

	public final VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public final T setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = Objects.requireNonNull(verticalAlignment, "verticalAlignment");
		return self();
	}

	protected abstract T self();

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

}

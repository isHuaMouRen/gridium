package me.hua.gridium.core;

import java.util.Objects;

public abstract class FrameworkElement<T extends FrameworkElement<T>> extends UIElement {
	private Thickness margin = Thickness.ZERO;
	private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
	private double layoutScaleX = 1.0;
	private double layoutScaleY = 1.0;
	private RenderTransform renderTransform = RenderTransform.IDENTITY;
	private double renderTransformOriginX;
	private double renderTransformOriginY;

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

	public final double getLayoutScaleX() {
		return layoutScaleX;
	}

	public final double getLayoutScaleY() {
		return layoutScaleY;
	}

	/**
	 * Returns the arranged content width including layout scaling, excluding the margin.
	 * Returns zero until the element has been arranged.
	 */
	public final double getActualWidth() {
		return getBounds().getWidth() * layoutScaleX;
	}

	/**
	 * Returns the arranged content height including layout scaling, excluding the margin.
	 * Returns zero until the element has been arranged.
	 */
	public final double getActualHeight() {
		return getBounds().getHeight() * layoutScaleY;
	}

	/**
	 * Scales content during measurement, arrangement, and drawing. Margins remain unscaled.
	 */
	public final T setLayoutScale(double scaleX, double scaleY) {
		double newScaleX = requireLayoutScale(scaleX, "scaleX");
		double newScaleY = requireLayoutScale(scaleY, "scaleY");
		layoutScaleX = newScaleX;
		layoutScaleY = newScaleY;
		return self();
	}

	public final RenderTransform getRenderTransform() {
		return renderTransform;
	}

	/**
	 * Sets a visual transform that does not affect measurement, arrangement, or actual size.
	 */
	public final T setRenderTransform(RenderTransform renderTransform) {
		this.renderTransform = Objects.requireNonNull(renderTransform, "renderTransform");
		return self();
	}

	public final double getRenderTransformOriginX() {
		return renderTransformOriginX;
	}

	public final double getRenderTransformOriginY() {
		return renderTransformOriginY;
	}

	/**
	 * Sets the normalized visual-transform origin within the content bounds. Each coordinate ranges
	 * from 0.0 to 1.0; the default is the top-left.
	 */
	public final T setRenderTransformOrigin(double originX, double originY) {
		requireTransformOrigin(originX, "originX");
		requireTransformOrigin(originY, "originY");
		renderTransformOriginX = originX;
		renderTransformOriginY = originY;
		return self();
	}

	protected abstract T self();

	@Override
	protected final Size measureOverride(Size availableSize) {
		Size contentAvailableSize = new Size(
			Math.max(0.0, availableSize.getWidth() - margin.getHorizontal()) / layoutScaleX,
			Math.max(0.0, availableSize.getHeight() - margin.getVertical()) / layoutScaleY
		);
		Size contentDesiredSize = Objects.requireNonNull(
			measureContent(contentAvailableSize),
			"measureContent result"
		);
		return new Size(
			contentDesiredSize.getWidth() * layoutScaleX + margin.getHorizontal(),
			contentDesiredSize.getHeight() * layoutScaleY + margin.getVertical()
		);
	}

	@Override
	protected final Rect arrangeOverride(Rect finalRect) {
		Rect contentRect = finalRect.inset(margin);
		Rect unscaledContentRect = new Rect(
			contentRect.getX(),
			contentRect.getY(),
			contentRect.getWidth() / layoutScaleX,
			contentRect.getHeight() / layoutScaleY
		);
		arrangeContent(unscaledContentRect);
		return unscaledContentRect;
	}

	protected Size measureContent(Size availableSize) {
		return Size.ZERO;
	}

	protected void arrangeContent(Rect finalRect) {
	}

	private static double requireLayoutScale(double scale, String name) {
		if (!Double.isFinite(scale) || scale <= 0.0 || scale > Float.MAX_VALUE || scale < Float.MIN_VALUE) {
			throw new IllegalArgumentException(name + " must be finite, positive, and representable as a float");
		}
		return scale;
	}

	private static void requireTransformOrigin(double origin, String name) {
		if (!Double.isFinite(origin) || origin < 0.0 || origin > 1.0) {
			throw new IllegalArgumentException(name + " must be between 0.0 and 1.0");
		}
	}

}

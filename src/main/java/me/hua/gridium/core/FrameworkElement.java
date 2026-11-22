package me.hua.gridium.core;

import me.hua.gridium.controls.GridTooltip;

import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Layout-enabled element with margin, alignment, layout scaling, render transform, and tooltip
 * support. Layout properties do not change the element's drawing content.
 *
 * <p>Margins reduce the content area and contribute to desired size. Layout scaling affects
 * measurement and arrangement but leaves margins unscaled. Render transforms affect only visual
 * drawing and do not participate in hit testing or layout bounds. Minimum and maximum sizes
 * constrain the measured outer desired size, including margins. During arrangement, STRETCH fills
 * the available slot up to the maximum; if the slot is smaller than the minimum, the arranged
 * element is clamped to the slot and may be smaller than its minimum.</p>
 */
public abstract class FrameworkElement<T extends FrameworkElement<T>> extends UIElement {
	private Thickness margin = Thickness.ZERO;
	private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
	private double minWidth;
	private double minHeight;
	private double maxWidth = Double.MAX_VALUE;
	private double maxHeight = Double.MAX_VALUE;
	private double layoutScaleX = 1.0;
	private double layoutScaleY = 1.0;
	private RenderTransform renderTransform = RenderTransform.IDENTITY;
	private double renderTransformOriginX;
	private double renderTransformOriginY;
	private @Nullable GridTooltip tooltip;

	public final @Nullable GridTooltip getTooltip() {
		return tooltip;
	}

	public final T setTooltip(GridTooltip tooltip) {
		this.tooltip = Objects.requireNonNull(tooltip, "tooltip");
		return self();
	}

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

	public final double getMinWidth() {
		return minWidth;
	}

	public final double getMinHeight() {
		return minHeight;
	}

	public final double getMaxWidth() {
		return maxWidth;
	}

	public final double getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Sets minimum outer width and height, including margins. The minimum constrains measured
	 * desired size; arrangement may be smaller when the allocated slot cannot fit it.
	 */
	public final T setMinSize(double width, double height) {
		double newMinWidth = requireConstraint(width, "minWidth");
		double newMinHeight = requireConstraint(height, "minHeight");
		if (newMinWidth > maxWidth) {
			throw new IllegalArgumentException("minWidth must not exceed maxWidth");
		}
		if (newMinHeight > maxHeight) {
			throw new IllegalArgumentException("minHeight must not exceed maxHeight");
		}
		minWidth = newMinWidth;
		minHeight = newMinHeight;
		return self();
	}

	/** Sets maximum outer width and height, including margins, for measurement and arrangement. */
	public final T setMaxSize(double width, double height) {
		double newMaxWidth = requireConstraint(width, "maxWidth");
		double newMaxHeight = requireConstraint(height, "maxHeight");
		if (newMaxWidth < minWidth) {
			throw new IllegalArgumentException("maxWidth must not be less than minWidth");
		}
		if (newMaxHeight < minHeight) {
			throw new IllegalArgumentException("maxHeight must not be less than minHeight");
		}
		maxWidth = newMaxWidth;
		maxHeight = newMaxHeight;
		return self();
	}

	public final T setMinWidth(double width) {
		return setMinSize(width, minHeight);
	}

	public final T setMinHeight(double height) {
		return setMinSize(minWidth, height);
	}

	public final T setMaxWidth(double width) {
		return setMaxSize(width, maxHeight);
	}

	public final T setMaxHeight(double height) {
		return setMaxSize(maxWidth, height);
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
	public T setLayoutScale(double scaleX, double scaleY) {
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
	public T setRenderTransform(RenderTransform renderTransform) {
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

	public final boolean containsPoint(double x, double y) {
		LocalPoint point = toLocalPoint(x, y);
		Rect bounds = getBounds();
		return point != null
				&& point.x() >= bounds.getX()
				&& point.x() <= bounds.getRight()
				&& point.y() >= bounds.getY()
				&& point.y() <= bounds.getBottom();
	}

	public final @Nullable LocalPoint toLocalPoint(double x, double y) {
		if (!Double.isFinite(x) || !Double.isFinite(y)) {
			return null;
		}

		Rect bounds = getBounds();
		RenderTransform transform = getRenderTransform();
		if (transform.scaleX() == 0.0 || transform.scaleY() == 0.0) {
			return null;
		}

		double originX = bounds.getX() + bounds.getWidth() * layoutScaleX * renderTransformOriginX;
		double originY = bounds.getY() + bounds.getHeight() * layoutScaleY * renderTransformOriginY;
		double translatedX = x - transform.translationX() - originX;
		double translatedY = y - transform.translationY() - originY;
		double radians = Math.toRadians(transform.rotationDegrees());
		double cosine = Math.cos(radians);
		double sine = Math.sin(radians);
		double scaledX = (cosine * translatedX + sine * translatedY) / transform.scaleX();
		double scaledY = (-sine * translatedX + cosine * translatedY) / transform.scaleY();
		double layoutX = originX + scaledX;
		double layoutY = originY + scaledY;
		return new LocalPoint(
				bounds.getX() + (layoutX - bounds.getX()) / layoutScaleX,
				bounds.getY() + (layoutY - bounds.getY()) / layoutScaleY
		);
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
		Size measuredSize = new Size(
				contentDesiredSize.getWidth() * layoutScaleX + margin.getHorizontal(),
				contentDesiredSize.getHeight() * layoutScaleY + margin.getVertical()
		);
		return new Size(
				clamp(measuredSize.getWidth(), minWidth, maxWidth),
				clamp(measuredSize.getHeight(), minHeight, maxHeight)
		);
	}

	@Override
	protected final Rect arrangeOverride(Rect finalRect) {
		Rect contentRect = finalRect.inset(margin);
		double constrainedWidth = clamp(finalRect.getWidth(), minWidth, maxWidth);
		double constrainedHeight = clamp(finalRect.getHeight(), minHeight, maxHeight);
		double constrainedContentWidth = Math.max(
				0.0,
				constrainedWidth - margin.getHorizontal()
		) / layoutScaleX;
		double constrainedContentHeight = Math.max(
				0.0,
				constrainedHeight - margin.getVertical()
		) / layoutScaleY;
		Rect unscaledContentRect = new Rect(
				contentRect.getX(),
				contentRect.getY(),
				Math.min(contentRect.getWidth() / layoutScaleX, constrainedContentWidth),
				Math.min(contentRect.getHeight() / layoutScaleY, constrainedContentHeight)
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

	private static double requireConstraint(double value, String name) {
		if (!Double.isFinite(value) || value < 0.0) {
			throw new IllegalArgumentException(name + " must be finite and non-negative");
		}
		return value;
	}

	private static double clamp(double value, double minimum, double maximum) {
		return Math.max(minimum, Math.min(value, maximum));
	}

	public record LocalPoint(double x, double y) {
	}
}

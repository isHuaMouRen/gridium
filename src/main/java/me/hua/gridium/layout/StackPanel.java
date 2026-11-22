package me.hua.gridium.layout;

import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.StackOrientation;
import me.hua.gridium.core.UIElement;

import java.util.Objects;

/**
 * Sequential layout container that arranges visible children along a horizontal or vertical axis.
 *
 * <p>Children keep their measured desired size on the stack axis. Spacing is inserted only between
 * visible children. Overflow remains outside the container and is not clipped or compressed.</p>
 */
public class StackPanel extends Panel<StackPanel> {
	private StackOrientation orientation;
	private double spacing;

	public StackPanel() {
		this(StackOrientation.VERTICAL);
	}

	public StackPanel(StackOrientation orientation) {
		this.orientation = Objects.requireNonNull(orientation, "orientation");
	}

	public StackOrientation getOrientation() {
		return orientation;
	}

	/** Changes the stacking axis and returns this panel for fluent composition. */
	public StackPanel setOrientation(StackOrientation orientation) {
		this.orientation = Objects.requireNonNull(orientation, "orientation");
		return this;
	}

	public double getSpacing() {
		return spacing;
	}

	/** Sets the distance between visible children and returns this panel for fluent composition. */
	public StackPanel setSpacing(double spacing) {
		if (!Double.isFinite(spacing) || spacing < 0.0) {
			throw new IllegalArgumentException("spacing must be finite and non-negative");
		}
		this.spacing = spacing;
		return this;
	}

	@Override
	protected StackPanel self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		double desiredWidth = 0.0;
		double desiredHeight = 0.0;
		int visibleChildCount = 0;

		for (UIElement child : getChildren()) {
			child.measure(availableSize);
			if (!child.isVisible()) {
				continue;
			}

			Size childSize = child.getDesiredSize();
			if (orientation == StackOrientation.HORIZONTAL) {
				desiredWidth += childSize.getWidth();
				desiredHeight = Math.max(desiredHeight, childSize.getHeight());
			} else {
				desiredWidth = Math.max(desiredWidth, childSize.getWidth());
				desiredHeight += childSize.getHeight();
			}

			visibleChildCount++;
		}

		double totalSpacing = spacing * Math.max(0, visibleChildCount - 1);
		if (orientation == StackOrientation.HORIZONTAL) {
			desiredWidth += totalSpacing;
		} else {
			desiredHeight += totalSpacing;
		}
		return new Size(desiredWidth, desiredHeight);
	}

	@Override
	protected void arrangeContent(Rect finalRect) {
		double cursor = orientation == StackOrientation.HORIZONTAL
				? finalRect.getX()
				: finalRect.getY();
		boolean hasVisibleChild = false;

		for (UIElement child : getChildren()) {
			if (!child.isVisible()) {
				arrangeChild(child, new Rect(finalRect.getX(), finalRect.getY(), 0.0, 0.0));
				continue;
			}
			if (hasVisibleChild) {
				cursor += spacing;
			}

			Size desiredSize = child.getDesiredSize();
			Rect childRect;
			if (orientation == StackOrientation.HORIZONTAL) {
				childRect = new Rect(cursor, finalRect.getY(), desiredSize.getWidth(), finalRect.getHeight());
				cursor += desiredSize.getWidth();
			} else {
				childRect = new Rect(finalRect.getX(), cursor, finalRect.getWidth(), desiredSize.getHeight());
				cursor += desiredSize.getHeight();
			}
			arrangeChild(child, childRect);
			hasVisibleChild = true;
		}
	}
}

package me.hua.gridium.layout;

import me.hua.gridium.controls.GridTooltip;
import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.UIElement;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.rendering.GridRenderer;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Panel<T extends Panel<T>> extends FrameworkElement<T> {
	private final List<UIElement> children = new ArrayList<>();

	public final void add(UIElement element) {
		Objects.requireNonNull(element, "element");
		if (element == this) {
			throw new IllegalArgumentException("A panel cannot contain itself");
		}
		if (element instanceof Panel<?> panel && panel.contains(this)) {
			throw new IllegalArgumentException("Adding this panel would create a cycle");
		}
		if (children.contains(element)) {
			throw new IllegalArgumentException("An element cannot be added to the same panel twice");
		}

		children.add(element);
	}

	public final boolean remove(UIElement element) {
		return children.remove(element);
	}

	public final List<UIElement> getChildren() {
		return List.copyOf(children);
	}

	@Override
	protected final void renderOverride(GridRenderer renderer) {
		for (UIElement child : children) {
			child.render(renderer);
		}
	}

	protected final void arrangeChild(UIElement child, Rect availableRect) {
		if (!child.isVisible()) {
			child.arrange(new Rect(availableRect.getX(), availableRect.getY(), 0.0, 0.0));
			return;
		}

		Size desiredSize = child.getDesiredSize();
		double childWidth = desiredSize.getWidth();
		double childHeight = desiredSize.getHeight();
		double childX = availableRect.getX();
		double childY = availableRect.getY();

		if (child instanceof FrameworkElement<?> frameworkElement) {
			HorizontalAlignment horizontalAlignment = frameworkElement.getHorizontalAlignment();
			VerticalAlignment verticalAlignment = frameworkElement.getVerticalAlignment();

			if (horizontalAlignment == HorizontalAlignment.STRETCH) {
				childWidth = availableRect.getWidth();
			} else if (horizontalAlignment == HorizontalAlignment.CENTER) {
				childX += (availableRect.getWidth() - childWidth) / 2.0;
			} else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
				childX += availableRect.getWidth() - childWidth;
			}

			if (verticalAlignment == VerticalAlignment.STRETCH) {
				childHeight = availableRect.getHeight();
			} else if (verticalAlignment == VerticalAlignment.CENTER) {
				childY += (availableRect.getHeight() - childHeight) / 2.0;
			} else if (verticalAlignment == VerticalAlignment.BOTTOM) {
				childY += availableRect.getHeight() - childHeight;
			}
		}

		child.arrange(new Rect(childX, childY, childWidth, childHeight));
	}

	final @Nullable GridTooltip findTooltipAt(double x, double y) {
		FrameworkElement.LocalPoint localPoint = toLocalPoint(x, y);
		Rect bounds = getBounds();
		if (!isVisible()
				|| localPoint == null
				|| !contains(localPoint, bounds)) {
			return null;
		}

		for (int i = children.size() - 1; i >= 0; i--) {
			UIElement child = children.get(i);
			if (child instanceof Panel<?> panel) {
				GridTooltip tooltip = panel.findTooltipAt(localPoint.x(), localPoint.y());
				if (tooltip != null) {
					return tooltip;
				}
			} else if (child instanceof FrameworkElement<?> element
					&& element.isVisible()
					&& element.getTooltip() != null
					&& element.containsPoint(localPoint.x(), localPoint.y())) {
				return element.getTooltip();
			}
		}
		return getTooltip();
	}

	private boolean contains(Panel<?> target) {
		for (UIElement child : children) {
			if (child == target || child instanceof Panel<?> panel && panel.contains(target)) {
				return true;
			}
		}
		return false;
	}

	private static boolean contains(FrameworkElement.LocalPoint point, Rect bounds) {
		return point.x() >= bounds.getX()
				&& point.x() <= bounds.getRight()
				&& point.y() >= bounds.getY()
				&& point.y() <= bounds.getBottom();
	}
}

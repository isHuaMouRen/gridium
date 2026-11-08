package me.hua.gridium.layout;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.UIElement;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.rendering.GridRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grid extends FrameworkElement {
	private final List<UIElement> children = new ArrayList<>();

	public void add(UIElement element) {
		Objects.requireNonNull(element, "element");
		if (element == this) {
			throw new IllegalArgumentException("A Grid cannot contain itself");
		}
		if (element instanceof Grid grid && grid.contains(this)) {
			throw new IllegalArgumentException("Adding this Grid would create a cycle");
		}
		if (children.contains(element)) {
			throw new IllegalArgumentException("An element cannot be added to the same Grid twice");
		}

		children.add(element);
		invalidateMeasure();
	}

	public boolean remove(UIElement element) {
		if (children.remove(element)) {
			invalidateMeasure();
			return true;
		}
		return false;
	}

	public List<UIElement> getChildren() {
		return List.copyOf(children);
	}

	public void draw(GridRenderer renderer, double width, double height) {
		Objects.requireNonNull(renderer, "renderer");
		Size viewportSize = new Size(width, height);
		measure(viewportSize);
		arrange(Rect.fromOriginAndSize(0.0, 0.0, viewportSize));
		render(renderer);
	}

	@Override
	protected Size measureContent(Size availableSize) {
		double desiredWidth = 0.0;
		double desiredHeight = 0.0;

		for (UIElement child : children) {
			child.measure(availableSize);
			desiredWidth = Math.max(desiredWidth, child.getDesiredSize().getWidth());
			desiredHeight = Math.max(desiredHeight, child.getDesiredSize().getHeight());
		}

		return new Size(desiredWidth, desiredHeight);
	}

	@Override
	protected void arrangeContent(Rect finalRect) {
		for (UIElement child : children) {
			if (!child.isVisible()) {
				child.arrange(new Rect(finalRect.getX(), finalRect.getY(), 0.0, 0.0));
				continue;
			}

			Size desiredSize = child.getDesiredSize();
			double childWidth = desiredSize.getWidth();
			double childHeight = desiredSize.getHeight();
			double childX = finalRect.getX();
			double childY = finalRect.getY();

			if (child instanceof FrameworkElement frameworkElement) {
				HorizontalAlignment horizontalAlignment = frameworkElement.getHorizontalAlignment();
				VerticalAlignment verticalAlignment = frameworkElement.getVerticalAlignment();

				if (horizontalAlignment == HorizontalAlignment.STRETCH) {
					childWidth = finalRect.getWidth();
				} else if (horizontalAlignment == HorizontalAlignment.CENTER) {
					childX += (finalRect.getWidth() - childWidth) / 2.0;
				} else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
					childX += finalRect.getWidth() - childWidth;
				}

				if (verticalAlignment == VerticalAlignment.STRETCH) {
					childHeight = finalRect.getHeight();
				} else if (verticalAlignment == VerticalAlignment.CENTER) {
					childY += (finalRect.getHeight() - childHeight) / 2.0;
				} else if (verticalAlignment == VerticalAlignment.BOTTOM) {
					childY += finalRect.getHeight() - childHeight;
				}
			}

			child.arrange(new Rect(childX, childY, childWidth, childHeight));
		}
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		for (UIElement child : children) {
			child.render(renderer);
		}
	}

	private boolean contains(Grid target) {
		for (UIElement child : children) {
			if (child == target || child instanceof Grid grid && grid.contains(target)) {
				return true;
			}
		}
		return false;
	}
}

package me.hua.gridium.layout;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.UIElement;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.controls.GridTooltip;
import me.hua.gridium.rendering.GridRenderer;
import me.hua.gridium.rendering.MinecraftGridRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

public class Grid extends FrameworkElement<Grid> {
	private final List<UIElement> children = new ArrayList<>();

	@Override
	protected Grid self() {
		return this;
	}

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
	}

	public boolean remove(UIElement element) {
		return children.remove(element);
	}

	public List<UIElement> getChildren() {
		return List.copyOf(children);
	}

	public void draw(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
		draw(graphics, mouseX, mouseY, 0.0F);
	}

	public void draw(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		Objects.requireNonNull(graphics, "graphics");
		layout(graphics.guiWidth(), graphics.guiHeight());
		render(new MinecraftGridRenderer(graphics, mouseX, mouseY, partialTick));
		GridTooltip tooltip = findTooltipAt(mouseX, mouseY);
		if (tooltip != null) {
			tooltip.show(graphics, mouseX, mouseY);
		}
	}

	private void layout(double width, double height) {
		Size viewportSize = new Size(width, height);
		measure(viewportSize);
		arrange(Rect.fromOriginAndSize(0.0, 0.0, viewportSize));
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

	private @Nullable GridTooltip findTooltipAt(double x, double y) {
		FrameworkElement.LocalPoint localPoint = toLocalPoint(x, y);
		Rect bounds = getBounds();
		if (!isVisible()
				|| localPoint == null
				|| !contains(localPoint, bounds)) {
			return null;
		}

		List<UIElement> orderedChildren = children;
		for (int i = orderedChildren.size() - 1; i >= 0; i--) {
			UIElement child = orderedChildren.get(i);
			if (child instanceof Grid grid) {
				GridTooltip tooltip = grid.findTooltipAt(localPoint.x(), localPoint.y());
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

	private static boolean contains(FrameworkElement.LocalPoint point, Rect bounds) {
		return point.x() >= bounds.getX()
				&& point.x() <= bounds.getRight()
				&& point.y() >= bounds.getY()
				&& point.y() <= bounds.getBottom();
	}
}

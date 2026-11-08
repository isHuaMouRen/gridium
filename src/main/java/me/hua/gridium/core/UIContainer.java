package me.hua.gridium.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIContainer extends FrameworkElement {
	private final List<UIElement> children = new ArrayList<>();

	public void add(UIElement element) {
		if (element == null) {
			return;
		}
		children.add(element);
		invalidateMeasure();
		invalidateArrange();
	}

	public void remove(UIElement element) {
		if (element == null) {
			return;
		}
		children.remove(element);
		invalidateMeasure();
		invalidateArrange();
	}

	public List<UIElement> getChildren() {
		return Collections.unmodifiableList(children);
	}

	@Override
	public void measure(Size availableSize) {
		super.measure(availableSize);
		for (UIElement child : children) {
			child.measure(availableSize);
		}
	}

	@Override
	public void arrange(Rect finalRect) {
		super.arrange(finalRect);
		for (UIElement child : children) {
			child.arrange(finalRect);
		}
	}
}

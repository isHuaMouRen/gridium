package me.hua.gridium.testutil;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;

public final class FixedElement extends FrameworkElement<FixedElement> {
	private final Size contentSize;
	private Size lastAvailableSize;

	public FixedElement(double width, double height) {
		this.contentSize = new Size(width, height);
	}

	public Size getLastAvailableSize() {
		return lastAvailableSize;
	}

	@Override
	protected FixedElement self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		lastAvailableSize = availableSize;
		return contentSize;
	}
}

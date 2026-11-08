package me.hua.gridium.core;

public class FrameworkElement extends UIElement {
	private Size lastAvailableSize = Size.ZERO;
	private boolean isMeasureValid = false;
	private boolean isArrangeValid = false;

	public FrameworkElement() {
		super();
	}

	public FrameworkElement(String name) {
		super(name);
	}

	@Override
	public void measure(Size availableSize) {
		lastAvailableSize = availableSize == null ? Size.ZERO : availableSize;
		isMeasureValid = true;
		onMeasure(lastAvailableSize);
	}

	@Override
	public void arrange(Rect finalRect) {
		isArrangeValid = true;
		onArrange(finalRect == null ? Rect.EMPTY : finalRect);
		super.arrange(finalRect);
	}

	protected void onMeasure(Size availableSize) {
		setDesiredSize(Size.ZERO);
	}

	protected void onArrange(Rect finalRect) {
		setBounds(finalRect == null ? Rect.EMPTY : finalRect);
	}

	public Size getLastAvailableSize() {
		return lastAvailableSize;
	}

	public boolean isMeasureValid() {
		return isMeasureValid;
	}

	public boolean isArrangeValid() {
		return isArrangeValid;
	}

	public void invalidateMeasure() {
		isMeasureValid = false;
	}

	public void invalidateArrange() {
		isArrangeValid = false;
	}
}

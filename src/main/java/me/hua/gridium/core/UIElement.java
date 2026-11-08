package me.hua.gridium.core;

public class UIElement {
	private final String name;
	private Thickness margin = Thickness.ZERO;
	private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
	private Rect bounds = Rect.EMPTY;
	private Size desiredSize = Size.ZERO;
	private boolean isVisible = true;

	public UIElement() {
		this("ui-element");
	}

	public UIElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Thickness getMargin() {
		return margin;
	}

	public void setMargin(Thickness margin) {
		this.margin = margin == null ? Thickness.ZERO : margin;
	}

	public HorizontalAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment == null ? HorizontalAlignment.LEFT : horizontalAlignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment == null ? VerticalAlignment.TOP : verticalAlignment;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds == null ? Rect.EMPTY : bounds;
	}

	public Size getDesiredSize() {
		return desiredSize;
	}

	public void setDesiredSize(Size desiredSize) {
		this.desiredSize = desiredSize == null ? Size.ZERO : desiredSize;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	public void measure(Size availableSize) {
		setDesiredSize(Size.ZERO);
	}

	public void arrange(Rect finalRect) {
		setBounds(finalRect == null ? Rect.EMPTY : finalRect);
	}

	@Override
	public String toString() {
		return "UIElement[name=" + name + ", bounds=" + bounds + ", desiredSize=" + desiredSize + "]";
	}
}

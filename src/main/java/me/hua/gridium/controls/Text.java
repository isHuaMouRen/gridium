package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class Text extends FrameworkElement<Text> {
	private final Component component;
	private final int color;
	private final boolean drawShadow;

	public Text(Component component) {
		this(component, 0xFFFFFFFF, false);
	}

	public Text(Component component,int color){
		this(component,color,false);
	}

	public Text(Component component, int color, boolean drawShadow) {
		super("text");
		this.component = Objects.requireNonNull(component, "component");
		this.color = color;
		this.drawShadow = drawShadow;
	}

	public Component getComponent() {
		return component;
	}

	@Override
	protected Text self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		var font = Minecraft.getInstance().font;
		return new Size(font.width(component), font.lineHeight);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		Rect bounds = getBounds();
		renderer.text(component, bounds, color, drawShadow);
	}
}

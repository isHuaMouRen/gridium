package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class Text extends FrameworkElement<Text> {
	private final Font font;
	private final Component component;
	private final int color;
	private final boolean drawShadow;

	public Text(Font font, Component component, int color, boolean drawShadow) {
		this.font = Objects.requireNonNull(font, "font");
		this.component = Objects.requireNonNull(component, "component");
		this.color = color;
		this.drawShadow = drawShadow;
	}

	public Font getFont() {
		return font;
	}

	public Component getComponent() {
		return component;
	}

	public int getColor() {
		return color;
	}

	public boolean drawsShadow() {
		return drawShadow;
	}

	@Override
	protected Text self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(font.width(component), font.lineHeight);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

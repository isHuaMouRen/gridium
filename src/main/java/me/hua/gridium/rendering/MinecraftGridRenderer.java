package me.hua.gridium.rendering;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.Rect;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class MinecraftGridRenderer implements GridRenderer {
	private final GuiGraphicsExtractor graphics;

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics) {
		this.graphics = Objects.requireNonNull(graphics, "graphics");
	}

	@Override
	public void render(Text text, @Nullable String string) {
		Rect bounds = text.getBounds();
		graphics.text(
			text.getFont(),
			string,
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			text.getColor(),
			text.drawsShadow()
		);
	}

	@Override
	public void render(Text text, FormattedCharSequence formattedCharSequence) {
		Rect bounds = text.getBounds();
		graphics.text(
			text.getFont(),
			formattedCharSequence,
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			text.getColor(),
			text.drawsShadow()
		);
	}

	@Override
	public void render(Text text, Component component) {
		Rect bounds = text.getBounds();
		graphics.text(
			text.getFont(),
			component,
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			text.getColor(),
			text.drawsShadow()
		);
	}

	@Override
	public void render(Rectangle rectangle) {
		Rect bounds = rectangle.getBounds();
		int left = (int) Math.round(bounds.getX());
		int top = (int) Math.round(bounds.getY());
		int right = (int) Math.round(bounds.getRight());
		int bottom = (int) Math.round(bounds.getBottom());
		RenderPipeline renderPipeline = rectangle.getRenderPipeline();
		TextureSetup textureSetup = rectangle.getTextureSetup();
		if (textureSetup != null) {
			graphics.fill(
				Objects.requireNonNull(renderPipeline, "renderPipeline"),
				textureSetup,
				left,
				top,
				right,
				bottom
			);
		} else if (renderPipeline != null) {
			graphics.fill(
				renderPipeline,
				left,
				top,
				right,
				bottom,
				Objects.requireNonNull(rectangle.getColor(), "color")
			);
		} else {
			graphics.fill(
				left,
				top,
				right,
				bottom,
				Objects.requireNonNull(rectangle.getColor(), "color")
			);
		}
	}

	@Override
	public void render(GradientRectangle rectangle) {
		Rect bounds = rectangle.getBounds();
		graphics.fillGradient(
			(int) Math.round(bounds.getX()),
			(int) Math.round(bounds.getY()),
			(int) Math.round(bounds.getRight()),
			(int) Math.round(bounds.getBottom()),
			rectangle.getFirstColor(),
			rectangle.getSecondColor()
		);
	}

	@Override
	public void render(HorizontalLine line) {
		Rect bounds = line.getBounds();
		int length = (int) Math.round(bounds.getWidth());
		if (length > 0) {
			graphics.horizontalLine(
				(int) Math.round(bounds.getX()),
				(int) Math.round(bounds.getX()) + length - 1,
				(int) Math.round(bounds.getY()),
				line.getColor()
			);
		}
	}

	@Override
	public void render(VerticalLine line) {
		Rect bounds = line.getBounds();
		int length = (int) Math.round(bounds.getHeight());
		if (length > 0) {
			int top = (int) Math.round(bounds.getY());
			// The native verticalLine offsets its first Y by one and treats the second as exclusive.
			graphics.verticalLine(
				(int) Math.round(bounds.getX()),
				top - 1,
				top + length,
				line.getColor()
			);
		}
	}
}

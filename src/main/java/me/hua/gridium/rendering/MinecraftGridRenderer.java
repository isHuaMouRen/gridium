package me.hua.gridium.rendering;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.TextureRectangle;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
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
	public void pushElementTransform(
			Rect bounds,
			double layoutScaleX,
			double layoutScaleY,
			RenderTransform renderTransform,
			double renderOriginX,
			double renderOriginY
	) {
		var pose = graphics.pose();
		pose.pushMatrix();
		double originX = bounds.getX() + bounds.getWidth() * layoutScaleX * renderOriginX;
		double originY = bounds.getY() + bounds.getHeight() * layoutScaleY * renderOriginY;
		pose.translate((float) renderTransform.translationX(), (float) renderTransform.translationY());
		pose.translate((float) originX, (float) originY);
		pose.rotate((float) Math.toRadians(renderTransform.rotationDegrees()));
		pose.scale((float) renderTransform.scaleX(), (float) renderTransform.scaleY());
		pose.translate((float) -originX, (float) -originY);
		pose.translate((float) bounds.getX(), (float) bounds.getY());
		pose.scale((float) layoutScaleX, (float) layoutScaleY);
		pose.translate((float) -bounds.getX(), (float) -bounds.getY());
	}

	@Override
	public void popElementTransform() {
		graphics.pose().popMatrix();
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
	public void render(TextureRectangle rectangle) {
		Rect bounds = rectangle.getBounds();
		int left = (int) Math.round(bounds.getX());
		int top = (int) Math.round(bounds.getY());
		int right = (int) Math.round(bounds.getRight());
		int bottom = (int) Math.round(bounds.getBottom());
		TextureRectangle.PixelRegion pixelRegion = rectangle.getPixelRegion();
		if (pixelRegion != null) {
			graphics.blit(
					Objects.requireNonNull(rectangle.getRenderPipeline(), "renderPipeline"),
					rectangle.getTexture(),
					left,
					top,
					pixelRegion.u(),
					pixelRegion.v(),
					right - left,
					bottom - top,
					pixelRegion.sourceWidth(),
					pixelRegion.sourceHeight(),
					pixelRegion.textureWidth(),
					pixelRegion.textureHeight(),
					rectangle.getColor()
			);
		} else {
			TextureRectangle.UvRegion uvRegion = Objects.requireNonNull(rectangle.getUvRegion(), "uvRegion");
			graphics.blit(
					rectangle.getTexture(),
					left,
					top,
					right,
					bottom,
					uvRegion.u0(),
					uvRegion.u1(),
					uvRegion.v0(),
					uvRegion.v1()
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

	@Override
	public void render(RectangleOutline outline) {
		Rect bounds = outline.getBounds();
		graphics.outline(
				(int) Math.round(bounds.getX()),
				(int) Math.round(bounds.getY()),
				(int) Math.round(bounds.getWidth()),
				(int) Math.round(bounds.getHeight()),
				outline.getColor()
		);
	}
}

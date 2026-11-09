package me.hua.gridium.controls;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.render.TextureSetup;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class Rectangle extends FrameworkElement<Rectangle> {
	private final double width;
	private final double height;
	private final @Nullable Integer color;
	private final @Nullable RenderPipeline renderPipeline;
	private final @Nullable TextureSetup textureSetup;

	public Rectangle(double width, double height, int color) {
		this(width, height, color, null, null);
	}

	public Rectangle(RenderPipeline renderPipeline, double width, double height, int color) {
		this(width, height, color, Objects.requireNonNull(renderPipeline, "renderPipeline"), null);
	}

	public Rectangle(RenderPipeline renderPipeline, TextureSetup textureSetup, double width, double height) {
		this(
				width,
				height,
				null,
				Objects.requireNonNull(renderPipeline, "renderPipeline"),
				Objects.requireNonNull(textureSetup, "textureSetup")
		);
	}

	private Rectangle(
			double width,
			double height,
			@Nullable Integer color,
			@Nullable RenderPipeline renderPipeline,
			@Nullable TextureSetup textureSetup
	) {
		Size size = new Size(width, height);
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.color = color;
		this.renderPipeline = renderPipeline;
		this.textureSetup = textureSetup;
	}

	public @Nullable Integer getColor() {
		return color;
	}

	public @Nullable RenderPipeline getRenderPipeline() {
		return renderPipeline;
	}

	public @Nullable TextureSetup getTextureSetup() {
		return textureSetup;
	}

	@Override
	protected Rectangle self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(width, height);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

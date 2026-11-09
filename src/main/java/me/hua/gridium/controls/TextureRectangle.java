package me.hua.gridium.controls;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class TextureRectangle extends FrameworkElement<TextureRectangle> {
	private final Identifier texture;
	private final double width;
	private final double height;
	private final @Nullable UvRegion uvRegion;
	private final @Nullable RenderPipeline renderPipeline;
	private final @Nullable PixelRegion pixelRegion;
	private final int color;

	public TextureRectangle(Identifier texture, double width, double height) {
		this(texture, width, height, new UvRegion(0.0F, 1.0F, 0.0F, 1.0F));
	}

	public TextureRectangle(
			Identifier texture,
			double width,
			double height,
			float u0,
			float u1,
			float v0,
			float v1
	) {
		this(texture, width, height, new UvRegion(u0, u1, v0, v1));
	}

	private TextureRectangle(Identifier texture, double width, double height, UvRegion uvRegion) {
		Size size = new Size(width, height);
		this.texture = Objects.requireNonNull(texture, "texture");
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.uvRegion = Objects.requireNonNull(uvRegion, "uvRegion");
		this.renderPipeline = null;
		this.pixelRegion = null;
		this.color = -1;
	}

	public TextureRectangle(
			RenderPipeline renderPipeline,
			Identifier texture,
			double width,
			double height,
			float u,
			float v,
			int sourceWidth,
			int sourceHeight,
			int textureWidth,
			int textureHeight
	) {
		this(
				renderPipeline,
				texture,
				width,
				height,
				u,
				v,
				sourceWidth,
				sourceHeight,
				textureWidth,
				textureHeight,
				-1
		);
	}

	public TextureRectangle(
			RenderPipeline renderPipeline,
			Identifier texture,
			double width,
			double height,
			float u,
			float v,
			int sourceWidth,
			int sourceHeight,
			int textureWidth,
			int textureHeight,
			int color
	) {
		Size size = new Size(width, height);
		this.texture = Objects.requireNonNull(texture, "texture");
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.uvRegion = null;
		this.renderPipeline = Objects.requireNonNull(renderPipeline, "renderPipeline");
		this.pixelRegion = new PixelRegion(u, v, sourceWidth, sourceHeight, textureWidth, textureHeight);
		this.color = color;
	}

	public Identifier getTexture() {
		return texture;
	}

	public @Nullable UvRegion getUvRegion() {
		return uvRegion;
	}

	public @Nullable RenderPipeline getRenderPipeline() {
		return renderPipeline;
	}

	public @Nullable PixelRegion getPixelRegion() {
		return pixelRegion;
	}

	public int getColor() {
		return color;
	}

	@Override
	protected TextureRectangle self() {
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

	public record UvRegion(float u0, float u1, float v0, float v1) {
		public UvRegion {
			requireFinite(u0, "u0");
			requireFinite(u1, "u1");
			requireFinite(v0, "v0");
			requireFinite(v1, "v1");
		}
	}

	public record PixelRegion(
			float u,
			float v,
			int sourceWidth,
			int sourceHeight,
			int textureWidth,
			int textureHeight
	) {
		public PixelRegion {
			requireFinite(u, "u");
			requireFinite(v, "v");
			if (textureWidth <= 0 || textureHeight <= 0) {
				throw new IllegalArgumentException("texture dimensions must be positive");
			}
		}
	}

	private static void requireFinite(float value, String name) {
		if (!Float.isFinite(value)) {
			throw new IllegalArgumentException(name + " must be finite");
		}
	}
}

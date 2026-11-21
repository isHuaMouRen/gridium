package me.hua.gridium.rendering;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.textures.GpuSampler;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import me.hua.gridium.controls.TextureRectangle;
import net.minecraft.resources.Identifier;

import java.util.Objects;

final class TextureRectangleRenderer {
	private final TextureBlitter blitter;

	TextureRectangleRenderer(TextureBlitter blitter) {
		this.blitter = Objects.requireNonNull(blitter, "blitter");
	}

	void render(TextureRectangle rectangle) {
		PixelBounds bounds = PixelBounds.from(rectangle.getBounds());
		int left = bounds.left();
		int top = bounds.top();
		int right = bounds.right();
		int bottom = bounds.bottom();
		GpuTextureView textureView = rectangle.getTextureView();
		if (textureView != null) {
			TextureRectangle.UvRegion uvRegion = Objects.requireNonNull(rectangle.getUvRegion(), "uvRegion");
			blitter.blit(
					textureView,
					Objects.requireNonNull(rectangle.getSampler(), "sampler"),
					left,
					top,
					right,
					bottom,
					uvRegion.u0(),
					uvRegion.u1(),
					uvRegion.v0(),
					uvRegion.v1()
			);
			return;
		}

		Identifier texture = Objects.requireNonNull(rectangle.getTexture(), "texture");
		TextureRectangle.PixelRegion pixelRegion = rectangle.getPixelRegion();
		if (pixelRegion != null) {
			blitter.blit(
					Objects.requireNonNull(rectangle.getRenderPipeline(), "renderPipeline"),
					texture,
					left,
					top,
					pixelRegion.u(),
					pixelRegion.v(),
					bounds.width(),
					bounds.height(),
					pixelRegion.sourceWidth(),
					pixelRegion.sourceHeight(),
					pixelRegion.textureWidth(),
					pixelRegion.textureHeight(),
					rectangle.getColor()
			);
		} else {
			TextureRectangle.UvRegion uvRegion = Objects.requireNonNull(rectangle.getUvRegion(), "uvRegion");
			blitter.blit(
					texture,
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

	interface TextureBlitter {
		void blit(
				Identifier texture,
				int left,
				int top,
				int right,
				int bottom,
				float u0,
				float u1,
				float v0,
				float v1
		);

		void blit(
				GpuTextureView textureView,
				GpuSampler sampler,
				int left,
				int top,
				int right,
				int bottom,
				float u0,
				float u1,
				float v0,
				float v1
		);

		void blit(
				RenderPipeline renderPipeline,
				Identifier texture,
				int x,
				int y,
				float u,
				float v,
				int width,
				int height,
				int sourceWidth,
				int sourceHeight,
				int textureWidth,
				int textureHeight,
				int color
		);
	}
}

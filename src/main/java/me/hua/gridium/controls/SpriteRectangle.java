package me.hua.gridium.controls;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class SpriteRectangle extends FrameworkElement<SpriteRectangle> {
	private final RenderPipeline renderPipeline;
	private final @Nullable Identifier spriteId;
	private final @Nullable TextureAtlasSprite sprite;
	private final double width;
	private final double height;
	private final int color;
	private final @Nullable Float alpha;
	private final @Nullable SourceRegion sourceRegion;

	public SpriteRectangle(RenderPipeline renderPipeline, Identifier spriteId, double width, double height) {
		this(renderPipeline, spriteId, width, height, -1, null, null);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			Identifier spriteId,
			double width,
			double height,
			int color
	) {
		this(renderPipeline, spriteId, width, height, color, null, null);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			Identifier spriteId,
			double width,
			double height,
			float alpha
	) {
		this(renderPipeline, spriteId, width, height, -1, requireAlpha(alpha), null);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			Identifier spriteId,
			double width,
			double height,
			int spriteWidth,
			int spriteHeight,
			int sourceX,
			int sourceY,
			int sourceWidth,
			int sourceHeight
	) {
		this(
				renderPipeline,
				spriteId,
				width,
				height,
				-1,
				null,
				new SourceRegion(spriteWidth, spriteHeight, sourceX, sourceY, sourceWidth, sourceHeight)
		);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			Identifier spriteId,
			double width,
			double height,
			int spriteWidth,
			int spriteHeight,
			int sourceX,
			int sourceY,
			int sourceWidth,
			int sourceHeight,
			int color
	) {
		this(
				renderPipeline,
				spriteId,
				width,
				height,
				color,
				null,
				new SourceRegion(spriteWidth, spriteHeight, sourceX, sourceY, sourceWidth, sourceHeight)
		);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			TextureAtlasSprite sprite,
			double width,
			double height
	) {
		this(renderPipeline, sprite, width, height, -1);
	}

	public SpriteRectangle(
			RenderPipeline renderPipeline,
			TextureAtlasSprite sprite,
			double width,
			double height,
			int color
	) {
		Size size = new Size(width, height);
		this.renderPipeline = Objects.requireNonNull(renderPipeline, "renderPipeline");
		this.spriteId = null;
		this.sprite = Objects.requireNonNull(sprite, "sprite");
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.color = color;
		this.alpha = null;
		this.sourceRegion = null;
	}

	private SpriteRectangle(
			RenderPipeline renderPipeline,
			Identifier spriteId,
			double width,
			double height,
			int color,
			@Nullable Float alpha,
			@Nullable SourceRegion sourceRegion
	) {
		Size size = new Size(width, height);
		this.renderPipeline = Objects.requireNonNull(renderPipeline, "renderPipeline");
		this.spriteId = Objects.requireNonNull(spriteId, "spriteId");
		this.sprite = null;
		this.width = size.getWidth();
		this.height = size.getHeight();
		this.color = color;
		this.alpha = alpha;
		this.sourceRegion = sourceRegion;
	}

	public RenderPipeline getRenderPipeline() {
		return renderPipeline;
	}

	public @Nullable Identifier getSpriteId() {
		return spriteId;
	}

	public @Nullable TextureAtlasSprite getSprite() {
		return sprite;
	}

	public int getColor() {
		return color;
	}

	public @Nullable Float getAlpha() {
		return alpha;
	}

	public @Nullable SourceRegion getSourceRegion() {
		return sourceRegion;
	}

	@Override
	protected SpriteRectangle self() {
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

	public record SourceRegion(
			int spriteWidth,
			int spriteHeight,
			int sourceX,
			int sourceY,
			int sourceWidth,
			int sourceHeight
	) {
		public SourceRegion {
			if (spriteWidth <= 0 || spriteHeight <= 0
					|| sourceX < 0 || sourceY < 0
					|| sourceWidth <= 0 || sourceHeight <= 0
					|| sourceWidth > spriteWidth || sourceHeight > spriteHeight
					|| sourceX > spriteWidth - sourceWidth
					|| sourceY > spriteHeight - sourceHeight) {
				throw new IllegalArgumentException("source region must be positive and contained within the sprite");
			}
		}
	}

	private static float requireAlpha(float alpha) {
		if (!Float.isFinite(alpha) || alpha < 0.0F || alpha > 1.0F) {
			throw new IllegalArgumentException("alpha must be between 0.0 and 1.0");
		}
		return alpha;
	}
}

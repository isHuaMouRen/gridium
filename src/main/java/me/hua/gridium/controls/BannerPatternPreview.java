package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.Objects;

public class BannerPatternPreview extends FrameworkElement<BannerPatternPreview> {
	private final BannerFlagModel flagModel;
	private final DyeColor baseColor;
	private final BannerPatternLayers patterns;
	private final Size viewportSize;

	public BannerPatternPreview(
			BannerFlagModel flagModel,
			DyeColor baseColor,
			BannerPatternLayers patterns,
			double width,
			double height
	) {
		this.flagModel = Objects.requireNonNull(flagModel, "flagModel");
		this.baseColor = Objects.requireNonNull(baseColor, "baseColor");
		this.patterns = Objects.requireNonNull(patterns, "patterns");
		this.viewportSize = new Size(width, height);
	}

	public BannerFlagModel getFlagModel() {
		return flagModel;
	}

	public DyeColor getBaseColor() {
		return baseColor;
	}

	public BannerPatternLayers getPatterns() {
		return patterns;
	}

	@Override
	protected BannerPatternPreview self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return viewportSize;
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

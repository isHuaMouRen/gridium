package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.state.MapRenderState;

import java.util.Objects;

/**
 * Renders Minecraft's 128-by-128 map view. The state must be populated by MapRenderer before drawing.
 */
public class MapImage extends FrameworkElement<MapImage> {
	private final MapRenderState mapRenderState;

	public MapImage(MapRenderState mapRenderState) {
		this.mapRenderState = Objects.requireNonNull(mapRenderState, "mapRenderState");
	}

	public MapRenderState getMapRenderState() {
		return mapRenderState;
	}

	@Override
	protected MapImage self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(MapRenderer.WIDTH, MapRenderer.HEIGHT);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.resources.Identifier;

import java.util.Objects;

/**
 * Renders a player skin model and texture into a viewport. Width and height define the layout
 * viewport; the remaining rendering options correspond to Minecraft's native skin preview.
 */
public class SkinPreview extends FrameworkElement<SkinPreview> {
	private final Model.Simple playerModel;
	private final Identifier texture;
	private final Size viewportSize;
	private final float scale;
	private final float rotationX;
	private final float rotationY;
	private final float pivotY;

	public SkinPreview(
			Model.Simple playerModel,
			Identifier texture,
			double width,
			double height,
			float scale,
			float rotationX,
			float rotationY,
			float pivotY
	) {
		this.playerModel = Objects.requireNonNull(playerModel, "playerModel");
		this.texture = Objects.requireNonNull(texture, "texture");
		this.viewportSize = new Size(width, height);
		this.scale = requireFinite(scale, "scale");
		this.rotationX = requireFinite(rotationX, "rotationX");
		this.rotationY = requireFinite(rotationY, "rotationY");
		this.pivotY = requireFinite(pivotY, "pivotY");
	}

	public Model.Simple getPlayerModel() {
		return playerModel;
	}

	public Identifier getTexture() {
		return texture;
	}

	public float getScale() {
		return scale;
	}

	public float getRotationX() {
		return rotationX;
	}

	public float getRotationY() {
		return rotationY;
	}

	public float getPivotY() {
		return pivotY;
	}

	@Override
	protected SkinPreview self() {
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

	private static float requireFinite(float value, String name) {
		if (!Float.isFinite(value)) {
			throw new IllegalArgumentException(name + " must be finite");
		}
		return value;
	}
}

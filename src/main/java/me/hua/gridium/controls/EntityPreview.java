package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Renders an entity state inside a layout-sized viewport. The state can be replaced as the entity
 * changes; transforms and the optional camera override retain the native GUI entity-rendering semantics.
 */
public class EntityPreview extends FrameworkElement<EntityPreview> {
	private final Size viewportSize;
	private final float scale;
	private final Vector3f translation;
	private final Quaternionf rotation;
	private final @Nullable Quaternionf overrideCameraAngle;
	private EntityRenderState renderState;

	public EntityPreview(
			EntityRenderState renderState,
			double width,
			double height,
			float scale,
			Vector3fc translation,
			Quaternionfc rotation,
			@Nullable Quaternionfc overrideCameraAngle
	) {
		this.renderState = Objects.requireNonNull(renderState, "renderState");
		this.viewportSize = new Size(width, height);
		if (!Float.isFinite(scale)) {
			throw new IllegalArgumentException("scale must be finite");
		}
		this.scale = scale;
		this.translation = new Vector3f(Objects.requireNonNull(translation, "translation"));
		this.rotation = new Quaternionf(Objects.requireNonNull(rotation, "rotation"));
		this.overrideCameraAngle = overrideCameraAngle == null
				? null
				: new Quaternionf(overrideCameraAngle);
		requireFinite(this.translation.x, this.translation.y, this.translation.z, "translation");
		requireFinite(this.rotation.x, this.rotation.y, this.rotation.z, this.rotation.w, "rotation");
		if (this.overrideCameraAngle != null) {
			requireFinite(
					this.overrideCameraAngle.x,
					this.overrideCameraAngle.y,
					this.overrideCameraAngle.z,
					this.overrideCameraAngle.w,
					"overrideCameraAngle"
			);
		}
	}

	public EntityRenderState getRenderState() {
		return renderState;
	}

	public EntityPreview setRenderState(EntityRenderState renderState) {
		this.renderState = Objects.requireNonNull(renderState, "renderState");
		return this;
	}

	public float getScale() {
		return scale;
	}

	public Vector3fc getTranslation() {
		return new Vector3f(translation);
	}

	public Quaternionfc getRotation() {
		return new Quaternionf(rotation);
	}

	public @Nullable Quaternionfc getOverrideCameraAngle() {
		return overrideCameraAngle == null ? null : new Quaternionf(overrideCameraAngle);
	}

	@Override
	protected EntityPreview self() {
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

	private static void requireFinite(float x, float y, float z, String name) {
		if (!Float.isFinite(x) || !Float.isFinite(y) || !Float.isFinite(z)) {
			throw new IllegalArgumentException(name + " components must be finite");
		}
	}

	private static void requireFinite(float x, float y, float z, float w, String name) {
		if (!Float.isFinite(x) || !Float.isFinite(y) || !Float.isFinite(z) || !Float.isFinite(w)) {
			throw new IllegalArgumentException(name + " components must be finite");
		}
	}
}

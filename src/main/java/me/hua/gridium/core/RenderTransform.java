package me.hua.gridium.core;

/**
 * A visual transform applied after layout. Scale is applied first, then rotation, then translation.
 * Translation is measured in GUI pixels; rotation is measured in degrees. Zero and negative scales
 * are allowed to collapse or flip the rendered content.
 */
public record RenderTransform(
		double translationX,
		double translationY,
		double scaleX,
		double scaleY,
		double rotationDegrees
) {
	public static final RenderTransform IDENTITY = new RenderTransform(0.0, 0.0, 1.0, 1.0, 0.0);

	public RenderTransform {
		requireFiniteFloat(translationX, "translationX");
		requireFiniteFloat(translationY, "translationY");
		requireFiniteFloat(scaleX, "scaleX");
		requireFiniteFloat(scaleY, "scaleY");
		requireFiniteFloat(rotationDegrees, "rotationDegrees");
	}

	public boolean isIdentity() {
		return translationX == 0.0
				&& translationY == 0.0
				&& scaleX == 1.0
				&& scaleY == 1.0
				&& rotationDegrees == 0.0;
	}

	private static void requireFiniteFloat(double value, String name) {
		if (!Double.isFinite(value) || Math.abs(value) > Float.MAX_VALUE) {
			throw new IllegalArgumentException(name + " must be finite and representable as a float");
		}
	}
}

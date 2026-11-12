package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.resources.Identifier;

import java.util.Objects;

/**
 * Renders Minecraft's book model into a layout-sized viewport, preserving native scale,
 * openness, and page-flip parameters. The scale is in native model units, not pixels.
 */
public class BookPreview extends FrameworkElement<BookPreview> {
	private final BookModel bookModel;
	private final Identifier texture;
	private final Size viewportSize;
	private final float scale;
	private final float open;
	private final float flip;

	public BookPreview(
			BookModel bookModel,
			Identifier texture,
			double width,
			double height,
			float scale,
			float open,
			float flip
	) {
		this.bookModel = Objects.requireNonNull(bookModel, "bookModel");
		this.texture = Objects.requireNonNull(texture, "texture");
		this.viewportSize = new Size(width, height);
		this.scale = requireFinite(scale, "scale");
		this.open = requireFinite(open, "open");
		this.flip = requireFinite(flip, "flip");
	}

	public BookModel getBookModel() {
		return bookModel;
	}

	public Identifier getTexture() {
		return texture;
	}

	public float getScale() {
		return scale;
	}

	public float getOpen() {
		return open;
	}

	public float getFlip() {
		return flip;
	}

	@Override
	protected BookPreview self() {
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

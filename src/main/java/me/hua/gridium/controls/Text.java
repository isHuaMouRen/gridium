package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class Text extends FrameworkElement<Text> {
	private final Font font;
	private final ContentType contentType;
	private final @Nullable String string;
	private final @Nullable FormattedCharSequence formattedCharSequence;
	private final @Nullable Component component;
	private final @Nullable FormattedText formattedText;
	private final int wrapWidth;
	private final int backdropWidth;
	private final int color;
	private final boolean drawShadow;
	private boolean centered;

	public Text(Font font, @Nullable String string, int color) {
		this(font, ContentType.STRING, string, null, null, null, 0, 0, color, true);
	}

	public Text(Font font, @Nullable String string, int color, boolean drawShadow) {
		this(font, ContentType.STRING, string, null, null, null, 0, 0, color, drawShadow);
	}

	public Text(Font font, FormattedCharSequence formattedCharSequence, int color) {
		this(font, ContentType.FORMATTED_CHAR_SEQUENCE, null,
				Objects.requireNonNull(formattedCharSequence, "formattedCharSequence"), null, null, 0, 0, color, true);
	}

	public Text(Font font, FormattedCharSequence formattedCharSequence, int color, boolean drawShadow) {
		this(font, ContentType.FORMATTED_CHAR_SEQUENCE, null,
				Objects.requireNonNull(formattedCharSequence, "formattedCharSequence"), null, null, 0, 0, color, drawShadow);
	}

	public Text(Font font, Component component, int color) {
		this(font, ContentType.COMPONENT, null, null, Objects.requireNonNull(component, "component"), null, 0, 0, color, true);
	}

	public Text(Font font, Component component, int color, boolean drawShadow) {
		this(font, ContentType.COMPONENT, null, null, Objects.requireNonNull(component, "component"), null, 0, 0, color, drawShadow);
	}

	public Text(Font font, Component component, int backdropWidth, int color) {
		this(
				font,
				ContentType.BACKDROP,
				null,
				null,
				Objects.requireNonNull(component, "component"),
				null,
				0,
				requireBackdropWidth(backdropWidth),
				color,
				true
		);
	}

	public Text(Font font, FormattedText formattedText, int wrapWidth, int color) {
		this(font, formattedText, wrapWidth, color, true);
	}

	public Text(Font font, FormattedText formattedText, int wrapWidth, int color, boolean drawShadow) {
		this(
				font,
				ContentType.WRAPPED,
				null,
				null,
				null,
				Objects.requireNonNull(formattedText, "formattedText"),
				requireWrapWidth(wrapWidth),
				0,
				color,
				drawShadow
		);
	}

	private Text(
			Font font,
			ContentType contentType,
			@Nullable String string,
			@Nullable FormattedCharSequence formattedCharSequence,
			@Nullable Component component,
			@Nullable FormattedText formattedText,
			int wrapWidth,
			int backdropWidth,
			int color,
			boolean drawShadow
	) {
		this.font = Objects.requireNonNull(font, "font");
		this.contentType = Objects.requireNonNull(contentType, "contentType");
		this.string = string;
		this.formattedCharSequence = formattedCharSequence;
		this.component = component;
		this.formattedText = formattedText;
		this.wrapWidth = wrapWidth;
		this.backdropWidth = backdropWidth;
		this.color = color;
		this.drawShadow = drawShadow;
	}

	public Font getFont() {
		return font;
	}

	public Component getComponent() {
		return Objects.requireNonNull(component, "Text content is not a Component");
	}

	public @Nullable String getString() {
		return string;
	}

	public FormattedCharSequence getFormattedCharSequence() {
		return Objects.requireNonNull(formattedCharSequence, "Text content is not a FormattedCharSequence");
	}

	public FormattedText getFormattedText() {
		return Objects.requireNonNull(formattedText, "Text content is not FormattedText for word wrapping");
	}

	public int getWrapWidth() {
		return wrapWidth;
	}

	public int getBackdropWidth() {
		return backdropWidth;
	}

	public int getColor() {
		return color;
	}

	public boolean drawsShadow() {
		return drawShadow;
	}

	/**
	 * @deprecated Use {@link #setHorizontalAlignment(me.hua.gridium.core.HorizontalAlignment)
	 * setHorizontalAlignment(HorizontalAlignment.CENTER)} instead.
	 */
	@Deprecated
	public boolean isCentered() {
		return centered;
	}

	/**
	 * @deprecated Use {@link #setHorizontalAlignment(me.hua.gridium.core.HorizontalAlignment)
	 * setHorizontalAlignment(HorizontalAlignment.CENTER)} instead.
	 */
	@Deprecated
	public Text setCentered(boolean centered) {
		this.centered = centered;
		return this;
	}

	@Override
	protected Text self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return switch (contentType) {
			case STRING -> new Size(string == null ? 0 : font.width(string),
					string == null ? 0 : font.lineHeight);
			case FORMATTED_CHAR_SEQUENCE -> new Size(font.width(getFormattedCharSequence()), font.lineHeight);
			case COMPONENT -> new Size(font.width(getComponent()), font.lineHeight);
			case WRAPPED -> new Size(wrapWidth, font.wordWrapHeight(getFormattedText(), wrapWidth));
			case BACKDROP -> new Size(backdropWidth + 4.0, 13.0);
		};
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		switch (contentType) {
			case STRING -> renderer.render(this, string);
			case FORMATTED_CHAR_SEQUENCE -> renderer.render(this, getFormattedCharSequence());
			case COMPONENT -> renderer.render(this, getComponent());
			case WRAPPED -> renderer.renderWrapped(this, getFormattedText());
			case BACKDROP -> renderer.renderWithBackdrop(this, getComponent());
		}
	}

	private static int requireWrapWidth(int wrapWidth) {
		if (wrapWidth < 0) {
			throw new IllegalArgumentException("wrapWidth must be non-negative");
		}
		return wrapWidth;
	}

	private static int requireBackdropWidth(int backdropWidth) {
		if (backdropWidth < 0) {
			throw new IllegalArgumentException("backdropWidth must be non-negative");
		}
		return backdropWidth;
	}

	private enum ContentType {
		STRING,
		FORMATTED_CHAR_SEQUENCE,
		COMPONENT,
		WRAPPED,
		BACKDROP
	}
}

package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class Text extends FrameworkElement<Text> {
	private final Font font;
	private final ContentType contentType;
	private final @Nullable String string;
	private final @Nullable FormattedCharSequence formattedCharSequence;
	private final @Nullable Component component;
	private final int color;
	private final boolean drawShadow;

	public Text(Font font, @Nullable String string, int color) {
		this(font, ContentType.STRING, string, null, null, color, true);
	}

	public Text(Font font, @Nullable String string, int color, boolean drawShadow) {
		this(font, ContentType.STRING, string, null, null, color, drawShadow);
	}

	public Text(Font font, FormattedCharSequence formattedCharSequence, int color) {
		this(font, ContentType.FORMATTED_CHAR_SEQUENCE, null,
			Objects.requireNonNull(formattedCharSequence, "formattedCharSequence"), null, color, true);
	}

	public Text(Font font, FormattedCharSequence formattedCharSequence, int color, boolean drawShadow) {
		this(font, ContentType.FORMATTED_CHAR_SEQUENCE, null,
			Objects.requireNonNull(formattedCharSequence, "formattedCharSequence"), null, color, drawShadow);
	}

	public Text(Font font, Component component, int color) {
		this(font, ContentType.COMPONENT, null, null, Objects.requireNonNull(component, "component"), color, true);
	}

	public Text(Font font, Component component, int color, boolean drawShadow) {
		this(font, ContentType.COMPONENT, null, null, Objects.requireNonNull(component, "component"), color, drawShadow);
	}

	private Text(
		Font font,
		ContentType contentType,
		@Nullable String string,
		@Nullable FormattedCharSequence formattedCharSequence,
		@Nullable Component component,
		int color,
		boolean drawShadow
	) {
		this.font = Objects.requireNonNull(font, "font");
		this.contentType = Objects.requireNonNull(contentType, "contentType");
		this.string = string;
		this.formattedCharSequence = formattedCharSequence;
		this.component = component;
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

	public int getColor() {
		return color;
	}

	public boolean drawsShadow() {
		return drawShadow;
	}

	@Override
	protected Text self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		int width = switch (contentType) {
			case STRING -> string == null ? 0 : font.width(string);
			case FORMATTED_CHAR_SEQUENCE -> font.width(getFormattedCharSequence());
			case COMPONENT -> font.width(getComponent());
		};
		return new Size(width, contentType == ContentType.STRING && string == null ? 0 : font.lineHeight);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		switch (contentType) {
			case STRING -> renderer.render(this, string);
			case FORMATTED_CHAR_SEQUENCE -> renderer.render(this, getFormattedCharSequence());
			case COMPONENT -> renderer.render(this, getComponent());
		}
	}

	private enum ContentType {
		STRING,
		FORMATTED_CHAR_SEQUENCE,
		COMPONENT
	}
}

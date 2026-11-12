package me.hua.gridium.rendering;

import me.hua.gridium.controls.BookPreview;
import me.hua.gridium.controls.EntityPreview;
import me.hua.gridium.controls.FakeItemIcon;
import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.ItemDecorations;
import me.hua.gridium.controls.ItemIcon;
import me.hua.gridium.controls.MapImage;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.SkinPreview;
import me.hua.gridium.controls.SpriteRectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.TextHighlight;
import me.hua.gridium.controls.TextureRectangle;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.jspecify.annotations.Nullable;

public interface GridRenderer {
	void pushElementTransform(
			Rect bounds,
			double layoutScaleX,
			double layoutScaleY,
			RenderTransform renderTransform,
			double renderOriginX,
			double renderOriginY
	);

	void popElementTransform();

	void render(Text text, @Nullable String string);

	void render(Text text, FormattedCharSequence formattedCharSequence);

	void render(Text text, Component component);

	void renderWrapped(Text text, FormattedText formattedText);

	void renderWithBackdrop(Text text, Component component);

	void render(TextHighlight highlight);

	void render(ItemIcon itemIcon);

	void render(FakeItemIcon itemIcon);

	void render(ItemDecorations itemDecorations);

	void render(MapImage mapImage);

	void render(EntityPreview entityPreview);

	void render(SkinPreview skinPreview);

	void render(BookPreview bookPreview);

	void render(Rectangle rectangle);

	void render(SpriteRectangle sprite);

	void render(TextureRectangle rectangle);

	void render(GradientRectangle rectangle);

	void render(HorizontalLine line);

	void render(VerticalLine line);

	void render(RectangleOutline outline);
}

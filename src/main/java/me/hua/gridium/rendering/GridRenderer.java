package me.hua.gridium.rendering;

import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import net.minecraft.network.chat.Component;
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

	void render(Rectangle rectangle);

	void render(GradientRectangle rectangle);

	void render(HorizontalLine line);

	void render(VerticalLine line);

	void render(RectangleOutline outline);
}

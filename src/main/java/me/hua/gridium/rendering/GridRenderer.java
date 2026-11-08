package me.hua.gridium.rendering;

import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jspecify.annotations.Nullable;

public interface GridRenderer {
	void render(Text text, @Nullable String string);

	void render(Text text, FormattedCharSequence formattedCharSequence);

	void render(Text text, Component component);

	void render(Rectangle rectangle);

	void render(GradientRectangle rectangle);
}

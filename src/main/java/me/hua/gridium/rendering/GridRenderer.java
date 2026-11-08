package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;
import net.minecraft.network.chat.Component;

public interface GridRenderer {
	void text(Component component, Rect bounds, int color, boolean drawShadow);

	void rectangle(Rect bounds, int color);
}

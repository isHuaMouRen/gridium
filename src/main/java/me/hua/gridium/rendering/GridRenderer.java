package me.hua.gridium.rendering;

import me.hua.gridium.core.Rect;

public interface GridRenderer {
	void text(String value, Rect bounds, int color);

	void rectangle(Rect bounds, int color);
}

package me.hua.gridium.rendering;

import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;

public interface GridRenderer {
	void render(Text text);

	void render(Rectangle rectangle);
}

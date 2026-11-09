package me.hua.gridium.screen;

import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.layout.Grid;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
	private final Screen parent;
	private Grid grid;

	public TestScreen(Screen parent) {
		super(Component.literal("Gridium Test Screen"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		grid = new Grid();

		grid.add(new Text(font, Component.literal("Gridium layout test"), 0xFFFFFFFF, true)
			.setLayoutScale(1.5, 1.5)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.TOP)
			.setMargin(new Thickness(0.0, 12.0, 0.0, 0.0))
		);

		grid.add(new HorizontalLine(180.0, 0xFFFF4040)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(0.0, 24.0, 0.0, 0.0))
		);

		grid.add(new VerticalLine(48.0, 0xFF40FF40)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(0.0,24.0,0.0,0.0))
		);

		grid.add(new GradientRectangle(180.0, 48.0, 0xFF202020, 0xFF405080)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.CENTER)
		);

		grid.add(new RectangleOutline(180.0, 48.0, 0xFFFFFFFF)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.CENTER)
			.setRenderTransform(new RenderTransform(20, 20, 1.5, 3, 0))
			.setRenderTransformOrigin(0.5, 0.5)
		);

		grid.add(new Text(font, "Centered by Grid", 0xFFFFFFFF)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.CENTER)
		);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
		grid.draw(graphics);
	}

	@Override
	public void onClose() {
		minecraft.setScreenAndShow(parent);
	}
}

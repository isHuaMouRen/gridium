package me.hua.gridium.screen;

import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.layout.Grid;
import me.hua.gridium.rendering.MinecraftGridRenderer;
import me.hua.gridium.rendering.MinecraftTextMetrics;
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
		MinecraftTextMetrics textMetrics = new MinecraftTextMetrics(font);

		Text title = new Text("Gridium layout test", textMetrics)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.TOP)
			.setMargin(new Thickness(0.0, 24.0, 0.0, 0.0));
		grid.add(title);

		Rectangle panel = new Rectangle(180.0, 48.0, 0xFF202020)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.CENTER);
		grid.add(panel);

		Text label = new Text("Centered by Grid", textMetrics)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setVerticalAlignment(VerticalAlignment.CENTER);
		grid.add(label);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
		grid.layout(width, height);
		grid.render(new MinecraftGridRenderer(graphics, font));
	}

	@Override
	public void onClose() {
		minecraft.setScreenAndShow(parent);
	}
}

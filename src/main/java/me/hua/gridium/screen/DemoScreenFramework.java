package me.hua.gridium.screen;

import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import me.hua.gridium.layout.Grid;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DemoScreenFramework extends Screen {
	public final Screen parent;
	private Grid grid;

	public DemoScreenFramework(Screen parent) {
		super(Component.literal("Gridium Demo - Screen Framework"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		grid = new Grid();

		//back button
		var backButton = Button.builder(Component.literal("§l<"), clicked -> onClose())
				.size(24, 24)
				.build();
		addWidget(backButton);
		grid.add(new MinecraftWidget(backButton)
				.setMargin(new Thickness(10))
		);

		//Title
		grid.add(new Text(font, Component.literal("§lDemo Screen Framework"), 0xFFFFFFFF, true)
				.setLayoutScale(2, 2)
				.setMargin(new Thickness(10 + 24 + 10, 10, 10, 0))
		);

		//Title underline
		grid.add(new Rectangle(width - (10 + 24 + 10) - 10, 2, 0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setMargin(new Thickness(0, 10 + 24 - 2, 10, 0))
		);

		//MAIN CONTENTS
		var content = new Grid();
		grid.add(content
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(0, 10 + 24, 0, 0))
		);

	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
		grid.draw(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		minecraft.setScreenAndShow(parent);
	}
}

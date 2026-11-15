package me.hua.gridium.screen;

import me.hua.gridium.Gridium;
import me.hua.gridium.controls.*;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import me.hua.gridium.layout.Grid;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DemoScreen extends Screen {
	private final Screen parent;
	private Grid grid;

	public DemoScreen(Screen parent) {
		super(Component.literal("Gridium Demo"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		grid = new Grid();
		//GRID START
		grid.add(new TextureRectangle(
				RenderPipelines.GUI_TEXTURED,
				Gridium.id("icon.png"),
				24, 24,
				0, 0,
				128, 128,
				128, 128,
				0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(10, 10, 0, 0))
		);

		grid.add(new Text(font, Component.literal("§lGridium Demo"), 0xFFFFFFFF, true)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(10 + 24 + 10, 10, 0, 0))
				.setLayoutScale(2, 2)
		);

		grid.add(new Rectangle(width - (10 + 24 + 10) - (10 + 24 + 10), 2, 0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(10 + 24 + 10, 10 + 24 - 2, 0, 0))
		);

		var exitButton = Button.builder(Component.literal("❌"), clicked -> onClose())
				.size(24, 24)
				.build();
		addWidget(exitButton);
		grid.add(new MinecraftWidget(exitButton)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(0, 10, 10, 0))
		);


		//GRID2 START
		var grid2 = new Grid();
		grid.add(grid2
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(0, 10 + 24, 0, 0))
		);

		var basicControlsButton = Button.builder(Component.literal("Basic Controls"), clicked -> minecraft.setScreenAndShow(new DemoBasicControlsScreen(this)))
				.build();
		addWidget(basicControlsButton);
		grid2.add(new MinecraftWidget(basicControlsButton)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(0, 10, 0, 0))
		);


		//GRID2 END


		//GRID END
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
		grid.draw(graphics, mouseX, mouseY);
	}

	@Override
	public void onClose() {
		minecraft.setScreenAndShow(parent);
	}
}

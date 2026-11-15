package me.hua.gridium.screen;

import me.hua.gridium.Gridium;
import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.SpriteRectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.TextHighlight;
import me.hua.gridium.controls.TextureRectangle;
import me.hua.gridium.controls.VerticalLine;
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
import net.minecraft.resources.Identifier;

public class DemoBasicControlsScreen extends Screen {
	public final Screen parent;
	public Grid grid;

	public DemoBasicControlsScreen(Screen parent) {
		super(Component.literal("Gridium Demo - Basic Controls"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		var grid = new Grid();
		this.grid = grid;

		//GRID START
		var backButton = Button.builder(Component.literal("<"), clicked -> onClose())
				.size(24, 24)
				.build();
		addWidget(backButton);
		grid.add(new MinecraftWidget(backButton)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(10, 10, 0, 0))
		);

		grid.add(new Text(font, Component.literal("§lBasic Controls"), 0xFFFFFFFF, true)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(10 + 24 + 10, 10, 0, 0))
				.setLayoutScale(2, 2)
		);

		grid.add(new Rectangle(width - (10 + 24 + 10) - 10, 2, 0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(0, 10 + 24 - 2, 10, 0))
		);

		//GRID2 START
		var grid2 = new Grid();
		grid.add(grid2
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(0, 10 + 24, 0, 0))
		);

		int rightColumn = width / 2;

		grid2.add(new Text(font, Component.literal("Text"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(10, 10, 0, 0)));
		grid2.add(new Text(font, Component.literal("Hello, Gridium!"), 0xFFFFFF55, true)
				.setMargin(new Thickness(10, 24, 0, 0)));

		grid2.add(new Text(font, Component.literal("Rectangle"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(10, 48, 0, 0)));
		grid2.add(new Rectangle(64, 18, 0xFF3F76E4)
				.setMargin(new Thickness(10, 62, 0, 0)));

		grid2.add(new Text(font, Component.literal("RectangleOutline"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(10, 88, 0, 0)));
		grid2.add(new RectangleOutline(64, 18, 0xFFFFFFFF)
				.setMargin(new Thickness(10, 102, 0, 0)));

		grid2.add(new Text(font, Component.literal("GradientRectangle"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(10, 128, 0, 0)));
		grid2.add(new GradientRectangle(64, 18, 0xFF3F76E4, 0xFF8A44CC)
				.setMargin(new Thickness(10, 142, 0, 0)));

		grid2.add(new Text(font, Component.literal("TextureRectangle"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(10, 168, 0, 0)));
		grid2.add(new TextureRectangle(
				RenderPipelines.GUI_TEXTURED,
				Gridium.id("icon.png"),
				24, 24,
				0, 0,
				128, 128,
				128, 128
		).setMargin(new Thickness(10, 182, 0, 0)));

		grid2.add(new Text(font, Component.literal("SpriteRectangle"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn, 10, 0, 0)));
		grid2.add(new SpriteRectangle(
				RenderPipelines.GUI_TEXTURED,
				Identifier.fromNamespaceAndPath("minecraft", "widget/button"),
				80, 20
		).setMargin(new Thickness(rightColumn, 24, 0, 0)));

		grid2.add(new Text(font, Component.literal("HorizontalLine"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn, 52, 0, 0)));
		grid2.add(new HorizontalLine(80, 0xFFFF5555)
				.setMargin(new Thickness(rightColumn, 72, 0, 0)));

		grid2.add(new Text(font, Component.literal("VerticalLine"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn, 92, 0, 0)));
		grid2.add(new VerticalLine(24, 0xFF55FF55)
				.setMargin(new Thickness(rightColumn, 110, 0, 0)));

		grid2.add(new Text(font, Component.literal("TextHighlight"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn, 132, 0, 0)));
		grid2.add(new TextHighlight(80, 18, false)
				.setMargin(new Thickness(rightColumn, 146, 0, 0)));
		grid2.add(new Text(font, Component.literal("Highlighted text"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn + 3, 148, 0, 0)));

		grid2.add(new Text(font, Component.literal("Minecraft Button"), 0xFFFFFFFF, false)
				.setMargin(new Thickness(rightColumn, 168, 0, 0)));
		var nativeButton = Button.builder(Component.literal("Click me"), clicked -> clicked.setMessage(Component.literal("Clicked!"))).size(80, 20).build();
		addWidget(nativeButton);
		grid2.add(new MinecraftWidget(nativeButton)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(rightColumn, 182, 0, 0)));


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

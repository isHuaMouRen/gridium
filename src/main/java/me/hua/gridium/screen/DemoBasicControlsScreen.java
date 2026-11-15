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
import me.hua.gridium.layout.StackPanel;
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

		//STACKPANEL START
		var panel = new StackPanel().setSpacing(5);
		grid.add(panel
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(0, 10 + 24 + 10, 0, 0))
		);

		panel.add(new Text(font, Component.literal("§lText"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new Text(font, Component.literal("Hello, Gridium!"), 0xFFFFFF55, true)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lRectangle"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new Rectangle(64, 18, 0xFF3F76E4).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lRectangleOutline"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new RectangleOutline(64, 18, 0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lGradientRectangle"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new GradientRectangle(64, 18, 0xFF3F76E4, 0xFF8A44CC)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lTextureRectangle"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new TextureRectangle(
				RenderPipelines.GUI_TEXTURED,
				Gridium.id("icon.png"),
				24, 24,
				0, 0,
				128, 128,
				128, 128)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lSpriteRectangle"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new SpriteRectangle(
				RenderPipelines.GUI_TEXTURED,
				Identifier.fromNamespaceAndPath("minecraft", "widget/button"),
				80, 20)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lHorizontalLine"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new HorizontalLine(80, 0xFFFF5555)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lVerticalLine"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new VerticalLine(24, 0xFF55FF55)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lTextHighlight"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new TextHighlight(80, 18, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		panel.add(new Text(font, Component.literal("Highlighted text"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		panel.add(new Text(font, Component.literal("§lMinecraft Button"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		var nativeButton = Button.builder(Component.literal("Click me"), clicked -> clicked.setMessage(Component.literal("Clicked!"))).size(80, 20).build();
		addWidget(nativeButton);
		panel.add(new MinecraftWidget(nativeButton)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));


		//STACKPANEL END
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

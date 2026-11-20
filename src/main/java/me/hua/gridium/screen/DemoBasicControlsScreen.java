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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class DemoBasicControlsScreen extends Screen {
	private final Screen parent;
	private Grid grid;

	public DemoBasicControlsScreen(Screen parent) {
		super(Component.literal("Gridium Demo - Basic Controls"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		grid = new Grid();

		var backButton = Button.builder(Component.literal("<"), clicked -> onClose())
				.size(24, 24)
				.build();
		addWidget(backButton);
		grid.add(new MinecraftWidget(backButton)
				.setMargin(new Thickness(10))
		);

		grid.add(new Text(font, Component.literal("§lBasic Controls"), 0xFFFFFFFF, true)
				.setLayoutScale(2, 2)
				.setMargin(new Thickness(44, 10, 10, 0))
		);

		grid.add(new Rectangle(width - 54, 2, 0xFFFFFFFF)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setMargin(new Thickness(44, 32, 10, 0))
		);

		var leftColumn = new StackPanel().setSpacing(5);
		var rightColumn = new StackPanel().setSpacing(5);

		grid.add(leftColumn
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness(10, 44, (double) width / 2, 10))
		);
		grid.add(rightColumn
				.setHorizontalAlignment(HorizontalAlignment.STRETCH)
				.setVerticalAlignment(VerticalAlignment.STRETCH)
				.setMargin(new Thickness((double) width / 2 + 10, 44, 10, 10))
		);

		leftColumn.add(new Text(font, "§lText", 0xFFFFFFFF, false));
		leftColumn.add(new Text(font, Component.literal("§lHello World!"), 0xFFFFFFFF, false)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
		);

		leftColumn.add(sectionTitle("Rectangle"));
		leftColumn.add(new Rectangle(64, 18, 0xFF3F76E4));

		leftColumn.add(sectionTitle("RectangleOutline"));
		leftColumn.add(new RectangleOutline(64, 18, 0xFFFFFFFF));

		leftColumn.add(sectionTitle("GradientRectangle"));
		leftColumn.add(new GradientRectangle(64, 18, 0xFF3F76E4, 0xFF8A44CC));

		leftColumn.add(sectionTitle("TextureRectangle - Source"));
		leftColumn.add(new TextureRectangle(
				RenderPipelines.GUI_TEXTURED,
				Gridium.id("icon.png"),
				24, 24,
				0, 0,
				128, 128,
				128, 128
		));

		leftColumn.add(sectionTitle("TextureRectangle - UV"));
		leftColumn.add(new TextureRectangle(
				Gridium.id("icon.png"),
				24, 24,
				0.0F, 0.5F,
				0.0F, 0.5F
		));

		rightColumn.add(sectionTitle("SpriteRectangle"));
		rightColumn.add(new SpriteRectangle(
				RenderPipelines.GUI_TEXTURED,
				Identifier.fromNamespaceAndPath("minecraft", "widget/button"),
				80, 20
		));

		rightColumn.add(sectionTitle("HorizontalLine"));
		rightColumn.add(new HorizontalLine(80, 0xFFFF5555));

		rightColumn.add(sectionTitle("VerticalLine"));
		rightColumn.add(new VerticalLine(24, 0xFF55FF55));

		rightColumn.add(sectionTitle("TextHighlight"));
		rightColumn.add(new TextHighlight(80, 18, false));
		rightColumn.add(new Text(font, Component.literal("Highlight text"), 0xFFFFFFFF, false));
	}

	private Text sectionTitle(String title) {
		return new Text(font, Component.literal("§l" + title), 0xFFFFFFFF, false)
				.setMargin(new Thickness(0, 5, 0, 0));
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

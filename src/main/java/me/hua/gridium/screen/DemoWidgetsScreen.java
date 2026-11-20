package me.hua.gridium.screen;

import me.hua.gridium.controls.GridTooltip;
import me.hua.gridium.controls.ItemDecorations;
import me.hua.gridium.controls.ItemIcon;
import me.hua.gridium.controls.ProfilerChart;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import me.hua.gridium.layout.Grid;
import me.hua.gridium.layout.StackOrientation;
import me.hua.gridium.layout.StackPanel;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * 展示布局、变换、原生 Widget、物品控件和 Tooltip。
 * 复杂 Minecraft 模型控件需要真实运行时状态，因此这里只展示可安全创建的控件。
 */
public class DemoWidgetsScreen extends Screen {
	private final Screen parent;
	private Grid grid;

	public DemoWidgetsScreen(Screen parent) {
		super(Component.literal("Gridium Demo - Widgets"));
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

		grid.add(new Text(font, Component.literal("§lLayout & Widget"), 0xFFFFFFFF, true)
				.setLayoutScale(2, 2)
				.setMargin(new Thickness(44, 10, 10, 0))
		);

		var leftColumn = new StackPanel().setSpacing(8);
		var rightColumn = new StackPanel().setSpacing(8);
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

		leftColumn.add(new Text(font, Component.literal("§lHorizontal StackPanel"), 0xFFFFFFFF, false));
		var horizontalRow = new StackPanel(StackOrientation.HORIZONTAL).setSpacing(8);
		horizontalRow.add(new Rectangle(24, 24, 0xFFEF5350));
		horizontalRow.add(new Rectangle(24, 24, 0xFF66BB6A));
		horizontalRow.add(new Rectangle(24, 24, 0xFF42A5F5));
		leftColumn.add(horizontalRow);

		leftColumn.add(new Text(font, Component.literal("§lAlignment"), 0xFFFFFFFF, false));
		leftColumn.add(new Rectangle(60, 18, 0xFF9C27B0)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT));
		leftColumn.add(new Rectangle(60, 18, 0xFFFF9800)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));

		leftColumn.add(new Text(font, Component.literal("§lRender transform"), 0xFFFFFFFF, false));
		leftColumn.add(new Rectangle(48, 18, 0xFF00ACC1)
				.setRenderTransform(new RenderTransform(4, -2, 1.1, 1.1, 8))
				.setRenderTransformOrigin(0.5, 0.5)
		);

		leftColumn.add(new Text(font, Component.literal("§lTooltip"), 0xFFFFFFFF, false)
				.setTooltip(new GridTooltip(font, Component.literal("Tooltip"))));

		rightColumn.add(new Text(font, Component.literal("§lNative Button"), 0xFFFFFFFF, false));
		var nativeButton = Button.builder(
				Component.literal("Click me"),
				clicked -> clicked.setMessage(Component.literal("Clicked!"))
		).size(96, 20).build();
		addWidget(nativeButton);
		rightColumn.add(new MinecraftWidget(nativeButton));

		if (minecraft.level != null) {
			rightColumn.add(new Text(font, Component.literal("§lItem View"), 0xFFFFFFFF, false));
			var itemStack = new ItemStack(Items.DIAMOND);
			var itemRow = new StackPanel(StackOrientation.HORIZONTAL).setSpacing(6);
			itemRow.add(new ItemIcon(itemStack, 1234));
			itemRow.add(new ItemDecorations(font, itemStack, "64"));
			rightColumn.add(itemRow);

			rightColumn.add(new Text(font, Component.literal("§lProfilerChart"), 0xFFFFFFFF, false));
			rightColumn.add(new ProfilerChart(List.<ResultField>of(), 120, 60));
		} else {
			rightColumn.add(new Text(font, Component.literal("Please load a world to check this control."), 0xFFFFFFFF, false));
		}
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

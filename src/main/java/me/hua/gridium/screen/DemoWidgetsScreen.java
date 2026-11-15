package me.hua.gridium.screen;

import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import me.hua.gridium.layout.Grid;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DemoWidgetsScreen extends Screen {
	public final Screen parent;
	public Grid grid;

	public DemoWidgetsScreen(Screen parent) {
		super(Component.literal("Gridium Demo - Widgets"));
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

		grid.add(new Text(font, Component.literal("§lWidgets"), 0xFFFFFFFF, true)
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

		int columnWidth = Math.max(160, width - 20);

		addLabel(grid2, "Interactive widgets", 10, 4);

		int[] clickCount = {0};
		addWidget(grid2, Button.builder(Component.literal("Button: click me"), button -> {
			clickCount[0]++;
			button.setMessage(Component.literal("Button clicked " + clickCount[0] + " times"));
		}).size(columnWidth, 20).build(), 10, 24);

		Checkbox checkbox = Checkbox.builder(Component.literal("Checkbox (selected)"), font)
				.pos(10, 54)
				.selected(true)
				.maxWidth(columnWidth)
				.build();
		addWidget(grid2, checkbox, 10, 54);

		CycleButton<String> cycleButton = CycleButton.<String>builder(
						value -> Component.literal("Cycle: " + value),
						"One"
				)
				.withValues("One", "Two", "Three")
				.create(Component.literal("Cycle button"), (button, value) -> {
				});
		cycleButton.setSize(columnWidth, 20);
		addWidget(grid2, cycleButton, 10, 82);

		AbstractSliderButton slider = new AbstractSliderButton(
				0,
				0,
				columnWidth,
				20,
				Component.empty(),
				0.35
		) {
			{
				updateMessage();
			}

			@Override
			protected void updateMessage() {
				setMessage(Component.literal("Slider: " + Math.round(value * 100) + "%"));
			}

			@Override
			protected void applyValue() {
			}
		};
		addWidget(grid2, slider, 10, 110);

		EditBox editBox = new EditBox(font, columnWidth, 20, Component.literal("Single-line input"));
		editBox.setHint(Component.literal("Type here..."));
		editBox.setValue("Gridium");
		addWidget(grid2, editBox, 10, 138);

		MultiLineEditBox multiLineEditBox = MultiLineEditBox.builder()
				.setPlaceholder(Component.literal("Multi-line input"))
				.build(font, columnWidth, 58, Component.literal("Multi-line input"));
		multiLineEditBox.setValue("Edit multiple lines here.");
		addWidget(grid2, multiLineEditBox, 10, 166);

		//GRID2 END
		//GRID END
	}

	private void addLabel(Grid target, String text, double x, double y) {
		target.add(new Text(font, Component.literal(text), 0xFFFFFFFF, false)
				.setMargin(new Thickness(x, y, 0, 0)));
	}

	private void addWidget(Grid target, AbstractWidget widget, double x, double y) {
		addWidget(widget);
		target.add(new MinecraftWidget(widget)
				.setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.TOP)
				.setMargin(new Thickness(x, y, 0, 0)));
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

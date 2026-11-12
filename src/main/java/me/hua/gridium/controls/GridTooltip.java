package me.hua.gridium.controls;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class GridTooltip {
	private final Font font;
	private final ContentType contentType;
	private final List<Component> components;
	private final List<FormattedCharSequence> formattedLines;
	private final Optional<TooltipComponent> image;
	private final @Nullable ItemStack itemStack;
	private final ClientTooltipPositioner positioner;
	private final @Nullable Identifier style;
	private final boolean replaceExisting;
	private final boolean extraSpaceAfterFirstLine;

	public GridTooltip(Font font, Component component) {
		this(font, List.of(Objects.requireNonNull(component, "component")));
	}

	public GridTooltip(Font font, List<Component> components) {
		this(font, components, Optional.empty(), null, false);
	}

	public GridTooltip(
			Font font,
			List<Component> components,
			Optional<TooltipComponent> image,
			@Nullable Identifier style,
			boolean extraSpaceAfterFirstLine
	) {
		this.font = Objects.requireNonNull(font, "font");
		this.contentType = ContentType.COMPONENTS;
		this.components = List.copyOf(Objects.requireNonNull(components, "components"));
		this.formattedLines = List.of();
		this.image = Objects.requireNonNull(image, "image");
		this.itemStack = null;
		this.positioner = DefaultTooltipPositioner.INSTANCE;
		this.style = style;
		this.replaceExisting = false;
		this.extraSpaceAfterFirstLine = extraSpaceAfterFirstLine;
	}

	private GridTooltip(
			Font font,
			List<FormattedCharSequence> formattedLines,
			Optional<TooltipComponent> image,
			ClientTooltipPositioner positioner,
			boolean replaceExisting,
			@Nullable Identifier style
	) {
		this.font = Objects.requireNonNull(font, "font");
		this.contentType = ContentType.FORMATTED_LINES;
		this.components = List.of();
		this.formattedLines = List.copyOf(Objects.requireNonNull(formattedLines, "formattedLines"));
		this.image = Objects.requireNonNull(image, "image");
		this.itemStack = null;
		this.positioner = Objects.requireNonNull(positioner, "positioner");
		this.style = style;
		this.replaceExisting = replaceExisting;
		this.extraSpaceAfterFirstLine = false;
	}

	public GridTooltip(Font font, ItemStack itemStack) {
		this.font = Objects.requireNonNull(font, "font");
		this.contentType = ContentType.ITEM_STACK;
		this.components = List.of();
		this.formattedLines = List.of();
		this.image = Optional.empty();
		this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
		this.positioner = DefaultTooltipPositioner.INSTANCE;
		this.style = null;
		this.replaceExisting = false;
		this.extraSpaceAfterFirstLine = false;
	}

	public static GridTooltip formatted(
			Font font,
			List<FormattedCharSequence> lines,
			Optional<TooltipComponent> image,
			ClientTooltipPositioner positioner,
			boolean replaceExisting,
			@Nullable Identifier style
	) {
		return new GridTooltip(font, lines, image, positioner, replaceExisting, style);
	}

	public void show(GuiGraphicsExtractor graphics, int x, int y) {
		Objects.requireNonNull(graphics, "graphics");
		switch (contentType) {
			case COMPONENTS -> graphics.setTooltipForNextFrame(
					font,
					components,
					image,
					x,
					y,
					style,
					extraSpaceAfterFirstLine
			);
			case FORMATTED_LINES -> graphics.setTooltipForNextFrame(
					font,
					formattedLines,
					image,
					positioner,
					x,
					y,
					replaceExisting,
					style
			);
			case ITEM_STACK -> graphics.setTooltipForNextFrame(font, itemStack, x, y);
		}
	}

	private enum ContentType {
		COMPONENTS,
		FORMATTED_LINES,
		ITEM_STACK
	}
}

package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Draws the native item bar, cooldown overlay, and count text for an item stack. Create the stack
 * only after Minecraft has bound item components, typically after a world has loaded.
 */
public class ItemDecorations extends FrameworkElement<ItemDecorations> {
	private static final double ICON_SIZE = 16.0;

	private final Font font;
	private final ItemStack itemStack;
	private final @Nullable String countText;

	public ItemDecorations(Font font, ItemStack itemStack) {
		this(font, itemStack, null);
	}

	public ItemDecorations(Font font, ItemStack itemStack, String countText) {
		this.font = Objects.requireNonNull(font, "font");
		this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
		this.countText = Objects.requireNonNull(countText, "countText");
	}

	public Font getFont() {
		return font;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public @Nullable String getCountText() {
		return countText;
	}

	@Override
	protected ItemDecorations self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return new Size(ICON_SIZE, ICON_SIZE);
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

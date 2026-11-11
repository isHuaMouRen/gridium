package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Displays an item stack without a player or entity owner. Create the stack only after Minecraft
 * has bound item components, typically after a world has loaded.
 */
public class FakeItemIcon extends FrameworkElement<FakeItemIcon> {
	private static final double ICON_SIZE = 16.0;

	private final ItemStack itemStack;
	private final int seed;

	public FakeItemIcon(ItemStack itemStack) {
		this(itemStack, 0);
	}

	public FakeItemIcon(ItemStack itemStack, int seed) {
		this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
		this.seed = seed;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getSeed() {
		return seed;
	}

	@Override
	protected FakeItemIcon self() {
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

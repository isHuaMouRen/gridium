package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Displays an item stack. Create the stack only after Minecraft has bound item components,
 * typically after a world has loaded.
 */
public class ItemIcon extends FrameworkElement<ItemIcon> {
	private static final double ICON_SIZE = 16.0;

	private final ItemStack itemStack;
	private final int seed;
	private final @Nullable LivingEntity owner;

	public ItemIcon(ItemStack itemStack) {
		this(itemStack, 0);
	}

	public ItemIcon(ItemStack itemStack, int seed) {
		this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
		this.seed = seed;
		this.owner = null;
	}

	public ItemIcon(LivingEntity owner, ItemStack itemStack, int seed) {
		this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
		this.seed = seed;
		this.owner = Objects.requireNonNull(owner, "owner");
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getSeed() {
		return seed;
	}

	public @Nullable LivingEntity getOwner() {
		return owner;
	}

	@Override
	protected ItemIcon self() {
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

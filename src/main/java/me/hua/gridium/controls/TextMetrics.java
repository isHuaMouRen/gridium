package me.hua.gridium.controls;

import me.hua.gridium.core.Size;

@FunctionalInterface
public interface TextMetrics {
	Size measureText(String text);
}

package me.hua.gridium.controls;

import me.hua.gridium.core.FrameworkElement;
import me.hua.gridium.core.Size;
import me.hua.gridium.rendering.GridRenderer;
import net.minecraft.util.profiling.ResultField;

import java.util.List;
import java.util.Objects;

public class ProfilerChart extends FrameworkElement<ProfilerChart> {
	private final List<ResultField> chartData;
	private final Size viewportSize;

	public ProfilerChart(List<ResultField> chartData, double width, double height) {
		this.chartData = List.copyOf(Objects.requireNonNull(chartData, "chartData"));
		this.viewportSize = new Size(width, height);
	}

	public List<ResultField> getChartData() {
		return chartData;
	}

	@Override
	protected ProfilerChart self() {
		return this;
	}

	@Override
	protected Size measureContent(Size availableSize) {
		return viewportSize;
	}

	@Override
	protected void renderOverride(GridRenderer renderer) {
		renderer.render(this);
	}
}

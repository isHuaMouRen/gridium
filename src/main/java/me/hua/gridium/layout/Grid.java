package me.hua.gridium.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.UIContainer;
import me.hua.gridium.core.UIElement;
import me.hua.gridium.core.VerticalAlignment;

public class Grid extends UIContainer {
	private final List<Double> rowHeights = new ArrayList<>();
	private final List<Double> columnWidths = new ArrayList<>();
	private final Map<UIElement, GridCell> cells = new IdentityHashMap<>();
	private int rowCount = 1;
	private int columnCount = 1;

	public Grid() {
		resetGridDefinitions();
	}

	public Grid(int rows, int columns) {
		this.rowCount = Math.max(1, rows);
		this.columnCount = Math.max(1, columns);
		resetGridDefinitions();
	}

	public void setRowCount(int rowCount) {
		this.rowCount = Math.max(1, rowCount);
		resetGridDefinitions();
		invalidateMeasure();
		invalidateArrange();
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = Math.max(1, columnCount);
		resetGridDefinitions();
		invalidateMeasure();
		invalidateArrange();
	}

	public void add(UIElement child, int row, int column) {
		addCell(child, row, column);
		super.add(child);
	}

	@Override
	public void add(UIElement child) {
		addCell(child, defaultRowIndexFor(child), defaultColumnIndexFor(child));
		super.add(child);
	}

	public List<Double> getRowHeights() {
		return Collections.unmodifiableList(rowHeights);
	}

	public List<Double> getColumnWidths() {
		return Collections.unmodifiableList(columnWidths);
	}

	private void addCell(UIElement child, int row, int column) {
		if (child == null) {
			return;
		}
		cells.put(child, new GridCell(Math.max(0, row), Math.max(0, column)));
	}

	private int defaultRowIndexFor(UIElement child) {
		int index = 0;
		for (UIElement existing : getChildren()) {
			GridCell cell = cells.get(existing);
			if (cell != null) {
				index = Math.max(index, cell.row + 1);
			}
		}
		return index;
	}

	private int defaultColumnIndexFor(UIElement child) {
		int count = 0;
		for (UIElement existing : getChildren()) {
			if (existing == child) {
				break;
			}
			count++;
		}
		return count % columnCount;
	}

	private void resetGridDefinitions() {
		rowHeights.clear();
		columnWidths.clear();
		for (int i = 0; i < rowCount; i++) {
			rowHeights.add(0.0);
		}
		for (int i = 0; i < columnCount; i++) {
			columnWidths.add(0.0);
		}
	}

	@Override
	public void measure(Size availableSize) {
		super.measure(availableSize);
		for (int i = 0; i < rowHeights.size(); i++) {
			rowHeights.set(i, 0.0);
		}
		for (int i = 0; i < columnWidths.size(); i++) {
			columnWidths.set(i, 0.0);
		}

		for (UIElement child : getChildren()) {
			child.measure(availableSize);
			GridCell cell = resolveCell(child);
			int row = cell.row;
			int column = cell.column;
			if (row >= rowHeights.size()) {
				row = rowHeights.size() - 1;
			}
			if (column >= columnWidths.size()) {
				column = columnWidths.size() - 1;
			}
			rowHeights.set(row, Math.max(rowHeights.get(row), child.getDesiredSize().getHeight()));
			columnWidths.set(column, Math.max(columnWidths.get(column), child.getDesiredSize().getWidth()));
		}

		double totalWidth = 0.0;
		double totalHeight = 0.0;
		for (double width : columnWidths) {
			totalWidth += width;
		}
		for (double height : rowHeights) {
			totalHeight += height;
		}
		setDesiredSize(new Size(totalWidth, totalHeight));
	}

	@Override
	public void arrange(Rect finalRect) {
		super.arrange(finalRect);
		if (finalRect == null) {
			return;
		}

		double[] rowStarts = new double[rowHeights.size()];
		double[] columnStarts = new double[columnWidths.size()];
		double currentY = finalRect.getY();
		for (int row = 0; row < rowHeights.size(); row++) {
			rowStarts[row] = currentY;
			currentY += rowHeights.get(row);
		}
		double currentX = finalRect.getX();
		for (int column = 0; column < columnWidths.size(); column++) {
			columnStarts[column] = currentX;
			currentX += columnWidths.get(column);
		}

		for (UIElement child : getChildren()) {
			GridCell cell = resolveCell(child);
			int row = Math.min(Math.max(0, cell.row), rowHeights.size() - 1);
			int column = Math.min(Math.max(0, cell.column), columnWidths.size() - 1);

			double occupyWidth = columnWidths.get(column);
			double occupyHeight = rowHeights.get(row);
			double childWidth = child.getDesiredSize().getWidth();
			double childHeight = child.getDesiredSize().getHeight();

			if (child.getHorizontalAlignment() == HorizontalAlignment.STRETCH) {
				childWidth = Math.max(0.0, occupyWidth - child.getMargin().getHorizontal());
			}
			if (child.getVerticalAlignment() == VerticalAlignment.STRETCH) {
				childHeight = Math.max(0.0, occupyHeight - child.getMargin().getVertical());
			}

			double x = columnStarts[column] + child.getMargin().getLeft();
			double y = rowStarts[row] + child.getMargin().getTop();

			switch (child.getHorizontalAlignment()) {
				case CENTER -> x += Math.max(0.0, (occupyWidth - childWidth - child.getMargin().getHorizontal()) / 2.0);
				case RIGHT -> x += Math.max(0.0, occupyWidth - childWidth - child.getMargin().getHorizontal());
				case LEFT, STRETCH -> { }
			}
			switch (child.getVerticalAlignment()) {
				case CENTER -> y += Math.max(0.0, (occupyHeight - childHeight - child.getMargin().getVertical()) / 2.0);
				case BOTTOM -> y += Math.max(0.0, occupyHeight - childHeight - child.getMargin().getVertical());
				case TOP, STRETCH -> { }
			}

			child.arrange(new Rect(x, y, childWidth, childHeight));
		}
	}

	private GridCell resolveCell(UIElement child) {
		GridCell cell = cells.get(child);
		if (cell != null) {
			return cell;
		}
		return new GridCell(defaultRowIndexFor(child), defaultColumnIndexFor(child));
	}

	private static final class GridCell {
		private final int row;
		private final int column;

		private GridCell(int row, int column) {
			this.row = row;
			this.column = column;
		}
	}
}

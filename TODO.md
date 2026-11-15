# TODO

Only list work that is not implemented yet. Keep tasks aligned with Gridium's goal of combining layout with Minecraft's native rendering and interaction behavior. Verify APIs against the configured Minecraft version before implementation.

## 1. Add stack layout

- [ ] Add a `StackPanel` layout container with horizontal and vertical orientation, non-negative spacing, and child ordering.
- [ ] Measure and arrange stacked children while preserving existing margin, alignment, stretch, layout-scale, and visibility behavior.
- [ ] Support nested `Grid` and `StackPanel` containers without allowing cycles, and keep tooltips discoverable through nested containers.
- [ ] Add layout tests for both orientations, spacing, margins, alignment, hidden children, and nested containers; defer main-axis space distribution, wrapping, and scrolling.

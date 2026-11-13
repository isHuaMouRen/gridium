# TODO

Only list work that is not implemented yet. Keep tasks aligned with Gridium's goal of combining layout with Minecraft's native rendering and interaction behavior. Verify APIs against the configured Minecraft version before implementation.

## 1. Establish regression coverage

- [x] Add tests for core geometry and layout: input validation, measurement, margins, alignment, stretch, and layout scaling.
- [x] Test nested-grid measurement and arrangement, including how a nested grid's margin offsets its content as a group within the parent grid.
- [x] Test visual transforms and inverse hit testing, including transform origins and zero-scale transforms.
- [x] Test render dispatch with a recording renderer. Verify layout changes destination geometry without changing drawing options such as texture UVs, colors, and text properties.

## 2. Integrate native Minecraft widgets with Grid layout

- [ ] Preserve the ordinary Minecraft widget construction API (`Button.builder(...)`, `Checkbox.builder(...)`, `EditBox`, and similar) and use the native widget as the source of truth for its value, input, focus, tooltip, and narration behavior.
- [ ] Add a small Gridium adapter that measures a native widget from its preferred/current size, applies Grid-arranged position and size to it, and extracts its native render state from the Grid drawing pass.
- [ ] Provide a Screen integration path that registers adapted widgets through Minecraft's input/narration registration without also registering them for Screen rendering; Grid should draw each adapted widget exactly once.
- [ ] Keep registration and layout ownership clear in the public API, with a concise example that creates a normal native widget, registers it with the Screen, adds its adapter to a root Grid, and draws only that root.
- [ ] Synchronize arranged widget bounds after screen initialization and resize, and define how visibility and active/enabled state stay consistent between the adapter and the native widget.
- [ ] Initially support layout positioning and sizing only. Do not claim Gridium layout scale, visual transforms, clipping, or custom hit regions work for native widgets until rendering and native input hit testing are verified to agree.
- [ ] Verify representative native widgets end to end, including a button, slider, checkbox or cycle button, and text input; confirm mouse/keyboard behavior, focus traversal, tooltips, narration, and no duplicate rendering.

## 3. Define custom interaction boundaries

- [ ] Prefer native-widget adaptation when it preserves Minecraft behavior and the desired layout experience. Add Gridium-owned interactive controls only for capabilities that cannot be provided cleanly by native widgets.
- [ ] If custom interactive controls are needed, keep their interaction state and input behavior separate from render-only controls, sharing only common layout, geometry, and drawing capabilities.
- [ ] Define how the Screen host routes input to any Gridium-owned interactive controls without making the core layout model depend on global Screen state; define ordering relative to native widgets.
- [ ] Add custom focus, enabled/disabled, keyboard navigation, pointer capture, and cursor behavior only for the Gridium-owned interaction path, avoiding duplicate implementations when Minecraft already provides the behavior.

## 4. Complete layout and rendering integration

- [ ] Add scoped scissor clipping with guaranteed state restoration, and make hit testing respect active clipping regions.
- [ ] Add stratum ordering and blur controls only where needed to expose corresponding native rendering behavior without requiring callers to manage raw rendering state.
- [ ] Inspect the target version's tooltip extraction paths; add direct `tooltip` support only if it provides distinct functionality beyond the existing `GridTooltip` APIs.
- [ ] Collect interactive text regions from `textRenderer` and `textRendererForWidget`, preserving native hover effects and style callbacks while connecting them to Gridium layout and hit testing.

## 5. Verify the framework end to end

- [ ] Add tests for event routing, hit testing, focus, keyboard activation, pointer capture, and visual-transform-aware interaction where those behaviors are owned by Gridium.
- [ ] Add focused client-side tests or a repeatable manual verification checklist for native widget integration, narration, tooltip timing, resize behavior, and Screen lifecycle behavior.

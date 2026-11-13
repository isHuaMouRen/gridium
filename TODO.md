# TODO

Only list work that is not implemented yet. Keep tasks aligned with Gridium's goal of combining layout with Minecraft's native rendering and interaction behavior. Verify APIs against the configured Minecraft version before implementation.

## 1. Establish regression coverage

- [ ] Add tests for core geometry and layout: input validation, measurement, margins, alignment, stretch, layout scaling, and nested grids.
- [ ] Test visual transforms and inverse hit testing, including transform origins and zero-scale transforms.
- [ ] Test rendering adapters with a recording renderer or equivalent seam. Verify layout changes destination geometry without changing drawing options such as texture source regions, UVs, colors, and text properties.

## 2. Separate drawable and interactive element bases

- [ ] Clarify the common element responsibilities, then introduce a drawable base for elements that render content and an interactive base that **inherits from the drawable base** and adds input behavior.
- [ ] Keep focus, enabled state, pointer/keyboard handlers, and other interaction-only behavior out of render-only elements. Share only the layout and geometry capabilities that both branches need.
- [ ] Keep containers such as `Grid` focused on layout and orchestration; do not force containers or render-only elements into the interactive branch.
- [ ] Decide where tooltip state and hit testing belong so existing tooltips remain usable without adding unnecessary interaction state to every drawable element.

## 3. Add interaction routing

- [ ] Define how the screen host forwards pointer, keyboard, and focus events to interactive elements without making the core layout model depend on global screen state.
- [ ] Implement topmost-first hit testing and event dispatch for nested elements, respecting visibility, visual transforms, and clipping.
- [ ] Add focus, enabled/disabled, activation, and keyboard-navigation behavior, including deterministic focus traversal.
- [ ] Add pointer capture and release so drag interactions continue after the pointer leaves an element; define deterministic cursor request/application behavior.

## 4. Add initial interactive controls

- [ ] Add a button with visual states, activation callbacks, keyboard activation, focus indication, and native-compatible narration/accessibility behavior.
- [ ] Add a slider with a bounded value, change callbacks, pointer dragging, keyboard adjustment, and visible track/thumb states.
- [ ] Verify controls participate in measure, arrange, and render like other elements. Reuse Minecraft's native widget behavior where it preserves expected input, narration, and rendering semantics.

## 5. Complete rendering and native interaction integration

- [ ] Add scoped scissor clipping with guaranteed state restoration, and make hit testing respect active clipping regions.
- [ ] Add stratum ordering and blur controls only where needed to expose corresponding native rendering behavior without requiring callers to manage raw rendering state.
- [ ] Inspect the target version's tooltip extraction paths; add direct `tooltip` support only if it provides distinct functionality beyond the existing `GridTooltip` APIs.
- [ ] Collect interactive text regions from `textRenderer` and `textRendererForWidget`, preserving native hover effects and style callbacks while connecting them to Gridium layout and hit testing.

## 6. Verify interaction end to end

- [ ] Add tests for event routing, hit testing, focus, keyboard activation, pointer capture, and visual-transform-aware interaction.
- [ ] Add focused client-side tests or a repeatable manual verification checklist for native widget integration, narration, tooltip timing, and screen lifecycle behavior.

# TODO

Only list work that is not implemented yet. Remove an item from this file when its implementation is complete.

## Drawing elements

- [x] Add line elements for `GuiGraphicsExtractor.horizontalLine` and `verticalLine`.
- [x] Add gradient fill support for `fillGradient`, preserving both endpoint colors.
- [x] Add simple `fill` overloads for default pure color, `RenderPipeline` plus pure color, and `RenderPipeline` plus `TextureSetup`.
- [x] Add rectangle outline support for `outline`.
- [ ] Extend `Text` to cover the remaining text inputs and options:
  - [x] Add simple `String`, `FormattedCharSequence`, and `Component` content overloads with default or explicit shadow options.
  - [x] Centered text placement (`centeredText`).
  - [x] Word wrapping (`textWithWordWrap`), including configurable width and shadow.
  - [x] Backdrop rendering (`textWithBackdrop`).
- [x] Add fake item drawing support for `fakeItem` and its seed option.
- [ ] Add item decoration support for `itemDecorations`, including optional count text.
- [ ] Add map rendering support for `map`.
- [ ] Add entity preview support for `entity`, preserving scale, translation, rotation, camera override, and bounds.
- [ ] Add player skin rendering support for `skin`.
- [ ] Add book preview support for `book`, preserving open and flip state.
- [ ] Add banner pattern rendering support for `bannerPattern`.
- [ ] Add profiler chart rendering support for `profilerChart`.

## Tooltips and interactive text

- [ ] Add deferred tooltip support for the `setTooltipForNextFrame` and `setComponentTooltipForNextFrame` overloads, preserving font, optional image, positioner, style, replacement, and extra-spacing options.
- [ ] Add direct tooltip extraction support for `tooltip`.
- [ ] Add interactive text collection support for `textRenderer` and `textRendererForWidget`, including hover effects, cursor changes, and additional style callbacks.
- [ ] Integrate deferred elements through `extractDeferredElements`, including tooltip and preedit overlay handling.

## Rendering state

- [ ] Add a rendering-state API for scissor clipping (`enableScissor`, `disableScissor`, `containsPointInScissor`) with safe scoped use.
- [x] Add a layout transform system supporting scale only:
  - [x] Apply scale during measure and arrange so the transformed desired size participates in parent layout and alignment.
  - [x] Apply the same scale to drawing so visual size matches the space reserved by layout.
  - [x] Keep layout transforms independent of Minecraft rendering APIs.
- [x] Add a render transform system applied after layout:
  - [x] Support translation, scale, and rotation, with a defined transform origin and composition order.
  - [x] Do not change measure, arrange, or the space reserved by the element.
  - [x] Apply transforms through the rendering adapter and restore the graphics pose after each element.
  - [x] Defer transformed hit testing and pointer interaction until the UI interaction model exists.
- [ ] Add explicit stratum and blur controls (`nextStratum`, `blurBeforeThisStratum`).
- [ ] Add cursor request/application support (`requestCursor`, `applyCursor`) where the UI interaction layer needs it.

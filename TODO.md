# TODO

Only list work that is not implemented yet. Remove an item from this file when its implementation is complete.

## Drawing elements

- [ ] Add line elements for `GuiGraphicsExtractor.horizontalLine` and `verticalLine`.
- [ ] Add gradient fill support for `fillGradient`, preserving both endpoint colors.
- [x] Add simple `fill` overloads for default pure color, `RenderPipeline` plus pure color, and `RenderPipeline` plus `TextureSetup`.
- [ ] Add rectangle outline support for `outline`.
- [ ] Add text highlight support for `textHighlight`, including its inversion option.
- [ ] Extend `Text` to cover the remaining text inputs and options:
  - [x] Add simple `String`, `FormattedCharSequence`, and `Component` content overloads with default or explicit shadow options.
  - Centered text placement (`centeredText`).
  - Word wrapping (`textWithWordWrap`), including configurable width and shadow.
  - Backdrop rendering (`textWithBackdrop`).
- [ ] Add textured blit elements for all `blit` forms:
  - `Identifier` textures and `GpuTextureView` plus `GpuSampler`.
  - Explicit render pipeline and tint color where supported.
  - Distinguish destination bounds from source dimensions, UV coordinates, and texture dimensions.
- [ ] Add sprite elements for all `blitSprite` forms:
  - `Identifier` and `TextureAtlasSprite` sources.
  - Alpha/color options and source-region variants.
  - Preserve the native stretch, tile, and nine-slice scaling behavior.
- [ ] Add item drawing support for `item`, seeded variants, and owner-specific rendering.
- [ ] Add fake item drawing support for `fakeItem` and its seed option.
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
- [ ] Add a rendering-state API for pose transforms (`pose`) without coupling layout geometry to Minecraft rendering state.
- [ ] Add explicit stratum and blur controls (`nextStratum`, `blurBeforeThisStratum`).
- [ ] Add cursor request/application support (`requestCursor`, `applyCursor`) where the UI interaction layer needs it.

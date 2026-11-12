# TODO

Only list work that is not implemented yet. Remove an item from this file when its implementation is complete.

## Tooltips and interactive text

- [ ] Add deferred tooltip support for all `setTooltipForNextFrame` and `setComponentTooltipForNextFrame` overloads, including item-stack tooltips, optional images, positioners, styles, replacement behavior, and extra spacing.
- [ ] Add direct tooltip extraction support for `tooltip`.
- [ ] Add interactive text collection support for `textRenderer` and `textRendererForWidget`, including hover effects, cursor changes, and additional style callbacks.
- [ ] Integrate deferred elements through `extractDeferredElements`, including tooltip and preedit overlay handling.

## Rendering state

- [ ] Add a rendering-state API for scissor clipping (`enableScissor`, `disableScissor`, `containsPointInScissor`) with safe scoped use.
- [ ] Add explicit stratum and blur controls (`nextStratum`, `blurBeforeThisStratum`).
- [ ] Add cursor request/application support (`requestCursor`, `applyCursor`) where the UI interaction layer needs it.

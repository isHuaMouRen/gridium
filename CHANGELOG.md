# Change Logs

## 1.0.0+26.3

* Release the first stable version!

### Change

* `UIElement.setParent()` is no longer public

## 0.1.3+26.3

### Updates

* `Panel.add()` now supports method chaining.
* Added `Panel.clear()`; elements can now be detached from a panel using `remove()`.
* Implemented a restriction: an element cannot be added to multiple containers.
* Expanded public API Javadoc to clarify layout semantics and native widget limitations.
* Added minimum and maximum size properties to `FrameworkElement`: `minWidth` `maxWidth` `minHeight` `maxHeight`.

### Changes

* **Namespace moved: `me.hua.gridium.layout.StackOrientation` -> `me.hua.gridium.core.StackOrientation`**
* Removed the `centered` property from `Text`, along with its associated methods.

## 0.1.2+26.3

### Updates

* Added two overloads for `Thickness`: `Thickness(double all)` and `Thickness(double horizontal, double vertical)`
* Clarified the adjustment strategy when containers overflow
* Clarified the layout boundary constraints for `MinecraftWidget`

### Changes

* Rewrote the demo screen

### Fixes

* Fixed an issue where the default constructor of `ItemDecorations` would throw an exception because `countText` was `null`
* Unified the rounding rules when converting floating-point layout coordinates to native integer coordinates to avoid deviations between drawing boundaries and control hit areas due to rounding positions and sizes separately.

## 0.1.1+26.3

### Updates

* Package the changelog in the `jar` and stop renaming the license file
* Add drawing examples to the demo screen, accessible through the Mod Menu configuration button
* Add the `StackPanel` stack layout

## 0.1.0+26.3

*no changelog*

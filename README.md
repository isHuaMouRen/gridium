# Gridium

**English** | [简体中文](/README.zh.md)

---

![Gridium](src/main/resources/assets/gridium/icon.png)

Gridium is a UI layout framework for Minecraft Java Edition Fabric mods. It brings layout support to Minecraft's native rendering capabilities: describe what to draw and how to align it, and let the framework measure, arrange, and calculate the rendering positions, reducing the need to manually calculate screen coordinates.

The layout design is inspired by WPF (Windows Presentation Foundation), but the project focuses on UI in Fabric and Minecraft and does not aim to replace the native rendering API.

> This project is developed with AI assistance.

## Features

* Use `Size`, `Rect`, and `Thickness` to represent dimensions, layout bounds, and margins.
* Arrange elements using horizontal and vertical alignment options.
* `Grid` measures, arranges, and draws all its children within the available screen area.
* Pass layout results to Minecraft's native rendering API through `GuiGraphicsExtractor`.
* Currently supported drawing elements:
  + Text: supports `String`, `FormattedCharSequence`, and `Component`, with configurable color and shadow.
  + Rectangles: supports solid-color fills, as well as fills using `RenderPipeline` and `TextureSetup`.
  + Gradient rectangles: supports setting the colors at both ends of the gradient.
  + Horizontal and vertical lines.
* Provides a test screen accessible through Mod Menu (an optional, recommended dependency).

`Grid` currently arranges its children within the entire screen area; it is not a table layout with row and column definitions. Multiple children can be aligned within the same area.

## Usage Example

Create elements in a `Screen` and add them to a `Grid`. Specify the drawing content and its properties when constructing an element, then use chained methods to declare alignment and margins. `Grid.draw(...)` lays out and draws the children using the current screen dimensions.

```java
private Grid grid;

@Override
protected void init() {
    grid = new Grid();

    grid.add(new Text(font, Component.literal("Gridium layout"), 0xFFFFFFFF)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setVerticalAlignment(VerticalAlignment.TOP)
        .setMargin(new Thickness(0.0, 24.0, 0.0, 0.0)));

    grid.add(new GradientRectangle(180.0, 48.0, 0xFF202020, 0xFF405080)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setVerticalAlignment(VerticalAlignment.CENTER));
}

@Override
public void extractRenderState(
    GuiGraphicsExtractor graphics,
    int mouseX,
    int mouseY,
    float partialTick
) {
    super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    grid.draw(graphics);
}
```

In this example, `font` is the font provided by `Screen`. To use `Component`, import `net.minecraft.network.chat.Component`. Alignment and margin types are in the `me.hua.gridium.core` package, `Grid` is in `me.hua.gridium.layout`, and drawing elements are in `me.hua.gridium.controls`.

Horizontal alignment options: `LEFT`, `CENTER`, `RIGHT`, and `STRETCH`. Vertical alignment options: `TOP`, `CENTER`, `BOTTOM`, and `STRETCH`.

## Build and Run

After cloning the repository, run the following command from the project root:

```bash
./gradlew build
```

Build artifacts are placed in `build/libs/`. To launch the development client, run:

```bash
./gradlew runClient
```

Install Mod Menu in the development client to view the current basic elements and layout behavior through Gridium's test screen.

## License

This project is licensed under GPL-3.0. See [LICENSE.md](LICENSE.md) for details.

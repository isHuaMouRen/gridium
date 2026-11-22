# Gridium Writing Guide

Using Minecraft 26.3 and Gridium 0.1.x as examples, this article provides a complete demonstration of how to implement a `Screen` , covering layout, lifecycle, and the bridging of native widgets. Gridium does not replace Minecraft’s native rendering API; instead, element constructors define the content, while layout properties dictate how those elements are arranged.

## Add dependencies

Add the Modrinth Maven repository and Gridium dependencies to the project using Fabric Loom:

```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:gridium:<version>"
}
```

Replace `<version>` with the target Gridium version. The current project targets Minecraft `~26.3` , Fabric Loader `>=0.20.1` , and Java `>=25` ; Fabric API is a required dependency.

## Complete Screen Example

The following classes can be put into your own Mod source code. Like other `Screen` , it is opened by the host Mod at the appropriate interaction entrance.

```java
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import me.hua.gridium.layout.Grid;
import me.hua.gridium.layout.StackPanel;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class GridiumExampleScreen extends Screen {
    private Grid grid;

    public GridiumExampleScreen() {
        super(Component.literal("Gridium example"));
    }

    @Override
    protected void init() {
        grid = new Grid();

        grid.add(new Text(font, Component.literal("Gridium"), 0xFFFFFFFF)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMargin(new Thickness(0, 24, 0, 0)));

        var content = new StackPanel()
                .setSpacing(8)
                .setHorizontalAlignment(HorizontalAlignment.STRETCH)
                .setMargin(new Thickness(16, 56, 16, 16));

        content.add(new Rectangle(120, 24, 0xFF3F76E4));
        content.add(new Text(font, "Laid out without manual coordinates",
                0xFFFFFFFF, false));

        var button = Button.builder(Component.literal("Click me"), clicked -> {
            clicked.setMessage(Component.literal("Clicked"));
        }).size(120, 20).build();

        addWidget(button);
        content.add(new MinecraftWidget(button)
                .setHorizontalAlignment(HorizontalAlignment.CENTER));

        grid.add(content);
    }

    @Override
    public void extractRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        grid.draw(graphics, mouseX, mouseY, partialTick);
    }
}
```

### Key points in the example

* `new Text(...)` and `new Rectangle(...)` define the drawing content and its natural size; the constructor does not receive screen coordinates.
* `setHorizontalAlignment(...)`,  `setMargin(...)` and `setSpacing(...)` describe layout intentions.
* `Grid` places child elements in the same layout area;  `StackPanel` arranges children in order vertically or horizontally.
* `Grid.draw(...)` remeasures, arranges and draws the layout every time the Screen rendering state is retrieved. And pass in `graphics` according to the calculated layout.
* Use ARGB integers for colors, such as `0xFF3F76E4`.

## Dynamic addition, deletion and visibility

After the container adds or deletes child elements, the next time `Grid.draw(...)` will be re-layout according to the new set of child elements. An element can only belong to one container at a time; when moving an element, remove it from the original container first:

```java
var badge = new Rectangle(12, 12, 0xFFFFCC00);

grid.add(badge);
grid.remove(badge);
content.add(badge);
```

The `grid` and `content` mentioned above refer to references to containers stored within a `Screen` . If you need to add or remove elements during subsequent events—such as within a button callback—you should store the relevant containers and elements as fields of the `Screen` class, then invoke the appropriate `add(...)` or `remove(...)` methods when the event occurs.

`Panel.add(...)` returns the panel itself, allowing for method chaining; `remove(...)` returns a boolean indicating whether the removal was successful; and `clear()` removes all children from the container.

```java
grid.add(new Rectangle(16, 16, 0xFF55AA55))
    .add(new Text(font, "Added fluently", 0xFFFFFFFF, false));

grid.clear();
```

`clear()` only clears the elements in the Gridium container and does not unregister native Widgets registered to the Screen input system through `addWidget(...)` .

## Hidden elements

It is also possible to temporarily hide elements. Hidden elements are not drawn and do not occupy stacking layout space; they will be arranged in the next layout after being redisplayed:

```java
badge.setVisible(false);
badge.setVisible(true);
```

## Size, alignment and overflow

Alignment determines the position of an element within the area allocated by the parent container. `STRETCH` fills the available area, subject to the maximum size; if the available area is smaller than the minimum size, the actual size is the size of the available area, which may be smaller than the minimum. Min/max size constraints are measured to the expected size:

```java
var panel = new StackPanel()
        .setSpacing(6)
        .setMinSize(120, 40)
        .setMaxSize(320, 240)
        .setHorizontalAlignment(HorizontalAlignment.STRETCH);
```

`Grid` and `StackPanel` do not automatically compress or crop overflowing child elements. When cropping is required, Screen or the caller must use Minecraft's native drawing capabilities; the layout will not automatically change drawing parameters such as the texture source area.

## Bridging Minecraft native widgets

The native Widget needs to join the Screen input system and the Grid layout at the same time:

```java
var button = Button.builder(Component.literal("Native button"), clicked -> {
    clicked.setMessage(Component.literal("Pressed"));
}).size(96, 20).build();

addWidget(button);
grid.add(new MinecraftWidget(button)
        .setHorizontalAlignment(HorizontalAlignment.CENTER));
```

`MinecraftWidget` is only responsible for layout adaptation, and the content of the Widget is still controlled by the native Button. Since native hitboxes do not follow Gridium's visual transformations, `MinecraftWidget` does not support layout scaling and rendering transformations, nor does it automatically crop drawing or input areas. `addWidget(...)` is used to register input. Do not use `addRenderableWidget(...)` to register native drawing at the same time; the Gridium bridge is responsible for extracting and drawing the Widget.

## More examples

The warehouse demo screen shows a variety of controls, layouts and drawing adaptations: [DemoScreen.java](./src/main/java/me/hua/gridium/screen/DemoScreen.java), [DemoWidgetsScreen.java](./src/main/java/me/hua/gridium/screen/DemoWidgetsScreen.java).

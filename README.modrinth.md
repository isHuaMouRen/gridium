# Gridium

**Gridium** is a UI layout framework for Minecraft Java Edition Fabric mods. It provides layout support for Minecraft's native graphics drawing capabilities: developers describe drawing content and layout intent, while Gridium handles measurement, arrangement, and drawing-position calculation, reducing the need for manual screen-coordinate calculations.

Gridium's design is inspired by [WPF](https://github.com/dotnet/wpf) (Windows Presentation Foundation), but focuses on Fabric and Minecraft UI scenarios and does not attempt to replace Minecraft's native drawing API.

> *This project is developed with AI assistance.*

[**>>Full README<<**](https://github.com/isHuaMouRen/gridium)

## Adding the Dependency

In a project using Fabric Loom, add the Modrinth Maven repository to the repository list and declare Gridium in the dependencies:

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

Replace `<version>` with the version of Gridium published on Modrinth.

## Quick Start

The following example shows how to create a Grid in a `Screen` , add text and a vertical `StackPanel` , and draw the layout during the Screen's render-state extraction phase.

```java
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Thickness;
import me.hua.gridium.layout.Grid;
import me.hua.gridium.layout.StackPanel;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ExampleScreen extends Screen {
    private Grid grid;

    public ExampleScreen(Screen parent) {
        super(Component.literal("Example screen"));
    }

    @Override
    protected void init() {
        grid = new Grid();

        grid.add(new Text(font, Component.literal("Gridium"), 0xFFFFFFFF)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMargin(new Thickness(0, 24, 0, 0)));

        var content = new StackPanel()
                .setSpacing(8);
        content.add(new Rectangle(120, 24, 0xFF3F76E4));
        content.add(new Text(font, "Laid out without manual coordinates", 0xFFFFFFFF, false));
        grid.add(content
                .setHorizontalAlignment(HorizontalAlignment.STRETCH)
                .setMargin(new Thickness(16, 56, 16, 16)));
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

By default, `Grid` arranges child elements from the top-left of its layout area. In the example above, the title is horizontally centered, and a vertically arranged content area is placed below the title.

## License

This project is licensed under GPL-3.0. See [LICENSE.md](LICENSE.md) for details.

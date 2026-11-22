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

In a `Screen` , create a `Grid` , add an element with layout intent, then draw it during render-state extraction:

```java
Grid grid = new Grid();

grid.add(new Text(font, Component.literal("Gridium"), 0xFFFFFFFF)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setMargin(new Thickness(10))
);

//in extractRenderState:
grid.draw(graphics, mouseX, mouseY, partialTick);
```

A minimal implementation example; More informations in [Github](https://github.com/isHuaMouRen/gridium)

## License

This project is licensed under GPL-3.0. See [LICENSE.md](LICENSE.md) for details.

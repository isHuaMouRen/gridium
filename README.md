# Gridium

**English** | [简体中文](/README.zh.md)

---

<img src="src/main/resources/assets/gridium/icon.png" alt="Gridium" width="128">

**Gridium** is a UI layout framework for Minecraft Java Edition Fabric mods. It provides layout support for Minecraft's native graphics drawing capabilities: developers describe drawing content and layout intent, while Gridium handles measurement, arrangement, and drawing-position calculation, reducing the need for manual screen-coordinate calculations.

Gridium's design is inspired by [WPF](https://github.com/dotnet/wpf) (Windows Presentation Foundation), but focuses on Fabric and Minecraft UI scenarios and does not attempt to replace Minecraft's native drawing API.

> *This project is developed with AI assistance.*

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

In a `Screen`, create a `Grid`, add an element with layout intent, then draw it during render-state extraction:

```java
Grid grid = new Grid();

grid.add(new Text(font, Component.literal("Gridium"), 0xFFFFFFFF)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setMargin(new Thickness(10))
);

//in extractRenderState:
grid.draw(graphics, mouseX, mouseY, partialTick);
```

A minimal implementation example; `font`, `graphics`, `mouseX/Y`, and `partialTick` are derived from the `Screen` lifecycle. For a complete, reproducible example—covering `Screen` implementation, layout behavior, dynamic addition/removal of elements, and bridging to native widgets—please refer to the [detailed guide](./GUIDE.md). The repository also includes a [demo screen](./src/main/java/me/hua/gridium/screen/DemoScreen.java) for reference.

## License

This project is licensed under GPL-3.0. See [LICENSE.md](LICENSE.md) for details.

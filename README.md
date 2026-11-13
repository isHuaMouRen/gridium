# Gridium

**English** | [简体中文](/README.zh.md)

---

![Gridium](src/main/resources/assets/gridium/icon.png)

**Gridium** is a UI layout framework for Minecraft Java Edition Fabric mods. It adds layout support to Minecraft's native rendering capabilities: developers describe what to draw and how to align it, while the framework measures, arranges, and calculates rendering positions—reducing the need to manually calculate screen coordinates.

The layout design is inspired by WPF (Windows Presentation Foundation), but this project focuses on UI scenarios for Fabric and Minecraft and does not aim to replace the native rendering API.

> This project is developed with the assistance of AI.

## Build and Run

After cloning the repository, run the following command from the project root:

```bash
./gradlew build
```

Build artifacts are located in `build/libs/` . To launch the development client, run:

```bash
./gradlew runClient
```

With Mod Menu installed in the development client, you can use Gridium's test screen to view the current basic elements and layout behavior.

## License

This project is licensed under GPL-3.0. See [LICENSE.md](LICENSE.md) for details.

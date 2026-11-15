# Gridium

[English](/README.md) | **简体中文**

---

<img src="src/main/resources/assets/gridium/icon.png" alt="Gridium" width="128">

**Gridium** 是一个面向 Minecraft Java Edition Fabric Mod 的 UI 布局框架。它为 Minecraft 原生图形绘制能力提供布局支持，让开发者描述绘制内容及其对齐方式，由Gridium负责测量、排列和计算绘制位置，减少手工计算屏幕坐标的需要。

设计与布局方式受到 [WPF](https://github.com/dotnet/wpf)（Windows Presentation Foundation）启发，但项目专注于 Fabric 和 Minecraft 的 UI 场景，不试图取代原生绘制 API。

> 此项目使用AI进行辅助开发。

## 在项目中添加依赖

在使用 Fabric Loom 的项目中，将 Modrinth Maven 仓库添加到仓库列表，并在依赖中声明 Gridium：

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

请将 `<version>` 替换为 Gridium 在 Modrinth 上发布的版本号。

## 许可证

本项目使用 GPL-3.0 许可证，详见 [LICENSE.md](LICENSE.md)。

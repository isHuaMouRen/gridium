# Gridium

[English](/README.md) | **简体中文**

---

<img src="src/main/resources/assets/gridium/icon.png" alt="Gridium" width="128">

**Gridium** 是一个面向 Minecraft Java Edition Fabric Mod 的 UI 布局框架。它为 Minecraft 原生图形绘制能力提供布局支持：开发者描述绘制内容和布局意图，Gridium 负责测量、排列并计算绘制位置，减少手工计算屏幕坐标的需要。

Gridium 的设计受到 [WPF](https://github.com/dotnet/wpf)（Windows Presentation Foundation）启发，但专注于 Fabric 和 Minecraft 的 UI 场景，不试图取代 Minecraft 原生绘制 API。

> *此项目使用 AI 辅助开发。*

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

## 快速开始

在 `Screen` 中创建 `Grid` ，添加带布局意图的元素，并在渲染状态提取阶段绘制：

```java
Grid grid = new Grid();

grid.add(new Text(font, Component.literal("Gridium"), 0xFFFFFFFF)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setMargin(new Thickness(10))
);

//在extractRenderState内：
grid.draw(graphics, mouseX, mouseY, partialTick);
```

一个最小的实现示例； `font` 、 `graphics` 、 `mouseX/Y` 和 `partialTick` 来自 Screen 生命周期。完整可复制的 Screen、布局行为、动态增删和原生 Widget 桥接示例见[详细编写指南](./GUIDE.zh.md)。仓库还提供了[演示屏幕](./src/main/java/me/hua/gridium/screen/DemoScreen.java) 以供参考。

## 许可证

本项目使用 GPL-3.0 许可证，详见 [LICENSE.md](LICENSE.md)。

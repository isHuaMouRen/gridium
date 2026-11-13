# Gridium

[English](/README.md) | **简体中文**

---

![Gridium](src/main/resources/assets/gridium/icon.png)

**Gridium** 是一个面向 Fabric Mod 的 Minecraft Java Edition UI 布局框架。它为 Minecraft 原生图形绘制能力提供布局支持，让开发者描述绘制内容及其对齐方式，由框架负责测量、排列和计算绘制位置，减少手工计算屏幕坐标的需要。

布局设计受到 WPF（Windows Presentation Foundation）启发，但项目专注于 Fabric 和 Minecraft 的 UI 场景，不试图取代原生绘制 API。

> 此项目使用AI进行辅助开发。

## 构建与运行

克隆仓库后，在项目根目录执行：

```bash
./gradlew build
```

构建产物位于 `build/libs/` 。如需启动开发客户端，可执行：

```bash
./gradlew runClient
```

开发客户端中安装 Mod Menu 后，可以通过 Gridium 的测试界面查看当前基础元素和布局效果。

## 许可证

本项目使用 GPL-3.0 许可证，详见 [LICENSE.md](LICENSE.md)。

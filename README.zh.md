# Gridium

[English](/README.md) | **简体中文**

---

![Gridium](src/main/resources/assets/gridium/icon.png)

Gridium 是一个面向 Fabric Mod 的 Minecraft Java Edition UI 布局框架。它为 Minecraft 原生图形绘制能力提供布局支持，让开发者描述绘制内容及其对齐方式，由框架负责测量、排列和计算绘制位置，减少手工计算屏幕坐标的需要。

布局设计受到 WPF（Windows Presentation Foundation）启发，但项目专注于 Fabric 和 Minecraft 的 UI 场景，不试图取代原生绘制 API。

> 此项目使用AI进行辅助开发。

## 功能

* 使用 `Size`、`Rect` 和 `Thickness` 表达尺寸、布局边界和外边距。
* 使用水平和垂直对齐方式对元素进行布局。
* 使用 `getActualWidth()` 和 `getActualHeight()` 获取元素排列后的内容尺寸；包含布局缩放、不含外边距，且不受绘制变换影响。
* 支持布局后的平移、缩放和旋转，并可设置归一化变换原点；绘制变换不影响布局尺寸。
* `Grid` 根据屏幕可用区域测量、排列并绘制所有子元素。
* 通过 `GuiGraphicsExtractor` 将布局结果交给 Minecraft 原生绘制 API。
* 当前绘制元素：
  + 文本：支持 `String`、`FormattedCharSequence` 和 `Component`，并可设置颜色及阴影；`FormattedText` 支持按指定宽度换行，`Component` 支持 Minecraft 原生文字背景。
  + 文本高亮：支持原生高亮矩形及可选的文字反色。
  + 矩形：支持纯色填充，以及 `RenderPipeline` 和 `TextureSetup` 形式的填充。
  + 纹理矩形：支持 `Identifier` 纹理的归一化 UV 边界，或使用渲染管线、像素源区域、纹理尺寸及可选 tint 颜色；也支持 `GpuTextureView` 和 `GpuSampler`。
  + 精灵矩形：支持 GUI 精灵标识符及原生缩放元数据、图集精灵、tint、alpha 和标识符精灵源区域。
  + 渐变矩形：支持设置渐变两端的颜色。
  + 水平线和垂直线。
  + 物品图标：支持物品栈、确定性渲染 seed 及指定实体 owner 的渲染。
* 提供可从 Mod Menu 打开的测试界面（Mod Menu 为可选推荐依赖）。

`Grid` 当前将子元素排列在整个屏幕区域中，并非具有行列定义的表格布局；多个子元素可以通过对齐方式安排在相同区域内。

## 使用示例

在 `Screen` 中创建元素并添加到 `Grid` 。构造绘制元素时提供绘制内容和内容属性；随后通过链式方法声明对齐和外边距。 `Grid.draw(...)` 会根据当前界面尺寸完成布局并绘制子元素。

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

    grid.add(new TextureRectangle(Gridium.id("icon.png"), 64.0, 64.0)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setVerticalAlignment(VerticalAlignment.TOP)
        .setMargin(new Thickness(0.0, 48.0, 0.0, 0.0)));

    grid.add(new RectangleOutline(180.0, 48.0, 0xFFFFFFFF)
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .setVerticalAlignment(VerticalAlignment.CENTER)
        .setRenderTransform(new RenderTransform(0.0, 0.0, 1.0, 1.0, 8.0))
        .setRenderTransformOrigin(0.5, 0.5));
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

示例中的 `font` 是 `Screen` 提供的字体；使用 `Component` 时需要导入 `net.minecraft.network.chat.Component` 。对齐类型和边距类型分别位于 `me.hua.gridium.core` 包， `Grid` 位于 `me.hua.gridium.layout` 包，绘制元素位于 `me.hua.gridium.controls` 包。

使用 `setHorizontalAlignment(HorizontalAlignment.CENTER)` 可将文本元素居中排列在可用布局区域中。`Text.isCentered()` 和 `Text.setCentered(...)` 已弃用，请改用布局对齐方式。

需要对格式化文本换行时，可用 `FormattedText`、换行宽度、颜色及可选阴影参数创建 `Text`。换行宽度同时作为元素期望布局宽度；元素期望高度根据换行后的行数测量，绘制时调用 Minecraft 原生 `textWithWordWrap`。

需要绘制 Minecraft 配置的文字背景时，用 `Component`、文本宽度和文字颜色创建 `Text`。元素会预留文本宽度加背景左右各 2 像素的留白，及原生 13 像素背景高度；背景颜色由 Minecraft 当前选项提供，原生方法会绘制带阴影文本。

`SpriteRectangle` 使用布局边界作为目标绘制区域。GUI 精灵标识符保留原生拉伸、平铺和九宫格行为；`TextureAtlasSprite` 使用原生直接精灵重载。标识符精灵还支持原生 alpha 重载和源区域形式，源区域尺寸与目标布局尺寸分别处理。

`ItemIcon` 在 Minecraft 原生 16×16 GUI 物品区域绘制 `ItemStack`，支持默认或显式渲染 seed，以及接收 `LivingEntity` owner 的原生重载。

`FakeItemIcon` 使用 Minecraft 原生 `fakeItem` 绘制方式，不指定玩家或实体 owner，并支持默认或显式渲染 seed。

`ItemDecorations` 在物品区域绘制 Minecraft 原生耐久条、冷却遮罩和数量文字。将它添加在物品元素之后并设置相同布局位置即可叠加显示；可选数量文字会覆盖实际堆叠数量。

`MapImage` 使用 `MapRenderState` 绘制 128×128 地图。绘制前需调用 `Minecraft.getInstance().getMapRenderer().extractRenderState(mapId, mapData, state)` 填充状态；地图标记和标签由 Minecraft 原生 `map` 绘制方法一并处理。

`EntityPreview` 将 `EntityRenderState` 绘制到自身布局边界内。构造参数保留原生缩放、平移、实体旋转和可选相机角度覆盖；实体状态变化时可用 `Minecraft.getInstance().getEntityRenderDispatcher().extractEntity(entity, partialTick)` 更新。

`SkinPreview` 将玩家模型和皮肤纹理绘制到自身布局边界内。传入 `Model.Simple`、皮肤 `Identifier`、缩放、X/Y 旋转角度及枢轴 Y；模型可从 `Minecraft.getInstance().getEntityModels()` 和玩家的 `PlayerSkin` 构造。缩放使用原生模型单位而非像素；常规玩家预览可使用 `0.97f * viewportHeight / 2.125f`，枢轴 Y 使用 `-1.0625f`。

`BookPreview` 在布局边界内绘制 Minecraft 书本模型。传入 `BookModel`、书本纹理、缩放、打开程度和翻页值；模型通过烘焙 `ModelLayers.BOOK` 创建。

`TextureRectangle` 使用布局边界作为目标绘制区域。默认 `Identifier` 构造器使用归一化 UV 采样整张纹理；UV 重载允许指定归一化边界。`RenderPipeline` 重载则使用像素坐标描述源区域，并接收纹理完整尺寸及可选 tint 颜色。源区域尺寸与目标尺寸相互独立，因此对齐或布局缩放不会改变采样区域。

绘制 GPU 纹理时，传入 `GpuTextureView` 和 `GpuSampler`；对应原生重载使用归一化 UV、默认 GUI 纹理管线和白色 tint。

绘制变换参数依次为 X/Y 平移、X/Y 缩放和角度（度）。归一化原点默认为 `(0.0, 0.0)`（左上角）；`(0.5, 0.5)` 表示围绕中心旋转和缩放。变换顺序为先缩放、再旋转、最后平移。绘制变换不影响布局或实际宽高。

水平对齐方式： `LEFT` 、 `CENTER` 、 `RIGHT` 、 `STRETCH` 。垂直对齐方式： `TOP` 、 `CENTER` 、 `BOTTOM` 、 `STRETCH` 。

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

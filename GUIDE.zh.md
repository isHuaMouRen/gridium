# Gridium 编写指南

本文以 Minecraft 26.3 和 Gridium 0.1.x 为例，提供一个完整的 `Screen` 编写示例，并说明布局、生命周期和原生 Widget 桥接。Gridium 不取代 Minecraft 绘制 API：元素构造器描述内容，布局属性描述元素如何排列。

## 添加依赖

在使用 Fabric Loom 的项目中添加 Modrinth Maven 仓库和 Gridium 依赖：

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

将 `<version>` 替换为目标 Gridium 版本。当前项目以 Minecraft `~26.3` 、Fabric Loader `>=0.20.1` 和 Java `>=25` 为目标；Fabric API 为必需依赖。

## 完整 Screen 示例

以下类可以放入自己的 Mod 源码中。像其他 `Screen` 一样，由宿主 Mod 在合适的交互入口打开它。

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

### 示例中的关键点

* `new Text(...)` 和 `new Rectangle(...)` 定义绘制内容及其自然尺寸；构造器不接收屏幕坐标。
* `setHorizontalAlignment(...)`、`setMargin(...)` 和 `setSpacing(...)` 描述布局意图。
* `Grid` 将子元素放在同一布局区域内；`StackPanel` 将孩子按顺序沿垂直或水平方向排列。
* `Grid.draw(...)` 在每次 Screen 渲染状态提取时重新测量、排列并绘制布局。并根据算好的布局传入 `graphics`。
* 颜色使用 ARGB 整数，例如 `0xFF3F76E4`。

## 动态增删与可见性

容器增删子元素后，下次 `Grid.draw(...)` 会按新的子元素集合重新布局。一个元素一次只能属于一个容器；移动元素时先从原容器移除：

```java
var badge = new Rectangle(12, 12, 0xFFFFCC00);

grid.add(badge);
grid.remove(badge);
content.add(badge);
```

上面的 `grid` 和 `content` 指 Screen 中保存的容器引用。若要在按钮回调等后续事件中增删元素，将需要访问的容器和元素保存为 Screen 字段，并在事件发生时执行相应的 `add(...)` 或 `remove(...)` 。

`Panel.add(...)` 返回面板本身，可链式添加； `remove(...)` 返回是否移除成功， `clear()` 移除该容器的全部孩子：

```java
grid.add(new Rectangle(16, 16, 0xFF55AA55))
    .add(new Text(font, "Added fluently", 0xFFFFFFFF, false));

grid.clear();
```

`clear()` 只清除 Gridium 容器中的元素，不会注销通过 `addWidget(...)` 注册到 Screen 输入系统的原生 Widget。

## 隐藏元素

也可以暂时隐藏元素。隐藏的元素不绘制，也不占用堆叠布局空间；重新显示后会在下一次布局中参与排列：

```java
badge.setVisible(false);
badge.setVisible(true);
```

## 尺寸、对齐和溢出

对齐方式决定元素在父容器分配区域中的位置。 `STRETCH` 会填充可用区域，但受最大尺寸限制；如果可用区域小于最小尺寸，那么实际尺寸就为可用区域大小，可能小于最小值。最小/最大尺寸约束的是测量得到的期望尺寸：

```java
var panel = new StackPanel()
        .setSpacing(6)
        .setMinSize(120, 40)
        .setMaxSize(320, 240)
        .setHorizontalAlignment(HorizontalAlignment.STRETCH);
```

`Grid` 和 `StackPanel` 不会自动压缩或裁剪溢出的子元素。需要裁剪时，须由 Screen 或调用者使用 Minecraft 原生绘制能力实现；布局不会自动改变纹理源区域等绘制参数。

## 桥接 Minecraft 原生 Widget

原生 Widget 需要同时加入 Screen 的输入系统和 Grid 的布局：

```java
var button = Button.builder(Component.literal("Native button"), clicked -> {
    clicked.setMessage(Component.literal("Pressed"));
}).size(96, 20).build();

addWidget(button);
grid.add(new MinecraftWidget(button)
        .setHorizontalAlignment(HorizontalAlignment.CENTER));
```

`MinecraftWidget` 只负责布局适配，Widget 的内容仍由原生 Button 控制。由于原生 hitbox 不跟随 Gridium 的视觉变换， `MinecraftWidget` 不支持布局缩放和渲染变换，也不会自动裁剪绘制或输入区域。 `addWidget(...)` 用于注册输入，不要用 `addRenderableWidget(...)` 同时注册原生绘制；由 Gridium 桥接负责提取并绘制该 Widget。

## 更多示例

仓库演示屏幕展示了多种控件、布局和绘制适配：[DemoScreen.java](./src/main/java/me/hua/gridium/screen/DemoScreen.java)、[DemoWidgetsScreen.java](./src/main/java/me/hua/gridium/screen/DemoWidgetsScreen.java)。

# Change Logs

## 1.0.1+26.3

### 修复

* 修复 `Layout & Widgets` 页里缺失的标题下划线

## 1.0.0+26.3

* 发布第一个正式版！

### 更改

* `UIElement.setParent()` 不再公开

## 0.1.3+26.3

### 更新

* `Panel.add()` 支持链式调用
* 新增 `Panel.clear()`，并支持通过 `remove()` 解除元素与面板的附属关系
* 限制一个元素不能被添加进多个容器
* 补充公开 API Javadoc，说明布局语义和原生 Widget 限制
* 为 `FrameworkElement` 添加最大/最小尺寸属性：`minWidth` `maxWidth` `minHeight` `maxHeight`

### 更改

* **移动命名空间: `me.hua.gridium.layout.StackOrientation` -> `me.hua.gridium.core.StackOrientation`**
* 移除 `Text` 的 `centered` 属性及其配套的方法

## 0.1.2+26.3

### 更新

* 为 `Thickness` 添加两个重载 `Thickness(double all)` 和 `Thickness(double horizontal, double vertical)`
* 明确容器溢出后的调整策略
* 明确 `MinecraftWidget` 布局限制边界

### 更改

* 重写演示屏幕

### 修复

* 修复 `ItemDecorations` 默认构造器因 `countText` 为 `null` 而抛出异常的问题
* 统一浮点布局坐标转换为原生整数坐标时的取整规则，避免绘制边界与控件命中区域因位置和尺寸分别取整而产生偏差。

## 0.1.1+26.3

### 更新

* 将更改日志打包进 `jar` 内，并取消许可证的重命名
* 在演示屏幕添加绘制示例，可通过ModMenu集成配置按钮进入
* 添加堆叠布局 `StackPanel`

## 0.1.0+26.3

*no changelog*

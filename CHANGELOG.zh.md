# Change Logs

## 0.1.3+26.3

### 更新

* `Panel.add()` 支持链式调用
* 现在一个元素不能被添加进多个容器
* 补充Javadoc

### 更改

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

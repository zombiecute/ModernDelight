# 工业发酵机（发酵系统）
## 发酵配方
目前发酵配方所涉及的变量
```java
    private final Identifier id; // 配方 ID
    private final ItemStack output_item; // 配方输出物品
    private final DefaultedList<Ingredient> ingredients; // 配方输入物品
    private final Fluid output_fluid; // 配方输出流体
    private final long min_fluid_amount; // 最小流体量（让配方生效所需的最少输入流体量）
    private final Fluid input_fluid; // 输入流体
    private final int temperature; // 发酵最适温度
    private final int luminosity; // 发酵最适光照
    private final int time; // 发酵所需时间
    private final String bacteria_input; // 发酵所需的菌种类型
    private final String bacteria_output; // 发酵产出的菌种类型
    private final int bacteria_output_amount; // 发酵产出的菌种数量
```
现有的发酵配方示例
```json
// 酒精（流体在菌种的作用转换，A 流体 -> B 流体）
{
  "type": "moderndelight:fermenting",
  "ingredients": [
    {
      "item": "minecraft:barrier" // 屏障表示空物品
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    }
  ],
  "output": {
    "item": "minecraft:barrier"
  },
  "fluid_input": "moderndelight:still_sweetened_water", // 输入糖水
  "fluid_output": "moderndelight:still_alcohol", // 输出酒精
  "temperature": 28,
  "luminosity": 2,
  "time": 120,
  "bacteria_input": "yeast", // 需要酵母菌
  "bacteria_output": "null"
}
```

```json
// 奶酪（消耗流体并在菌种的作用下产出物品，消耗 A 流体 -> 产出 B 物品）
{
  "type": "moderndelight:fermenting",
  "ingredients": [
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    }
  ],
  "output": {
    "item": "moderndelight:cheese" // 输出奶酪
  },
  "fluid_input": "moderndelight:still_milk", // 输入奶
  "min_fluid_amount": 16200, // 最少需要 16200 drops（200 mB） 的奶
  "fluid_output": "empty", // 无输出流体表示输出时会消耗流体
  "temperature": 35,
  "luminosity": 2,
  "time": 120,
  "bacteria_input": "lactobacillus", // 需要乳酸菌
  "bacteria_output": "null"
}
```

```json
// 乳酸菌（消耗流体产出菌种，消耗 A 流体 -> 产出 B 菌种）
{
  "type": "moderndelight:fermenting",
  "ingredients": [
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    },
    {
      "item": "minecraft:barrier"
    }
  ],
  "output": {
    "item": "minecraft:barrier"
  },
  "fluid_input": "moderndelight:still_milk", // 输入奶
  "min_fluid_amount": 810, // 最少需要 810 drops （10 mB）的奶
  "fluid_output": "empty",
  "temperature": 32,
  "luminosity": 4,
  "time": 15,
  "bacteria_input": "null", // 无需菌种
  "bacteria_output": "lactobacillus", // 产出乳酸菌
  "bacteria_output_amount": 50 // 产出乳酸菌的 CFU
}
```

```json
// 毛霉（消耗物品产出菌种，消耗 A 物品 -> 产出 B 菌种）
{
  "type": "moderndelight:fermenting",
  "ingredients": [
    {
      "item": "minecraft:mycelium"
    },
    {
      "item": "minecraft:mycelium"
    },
    {
      "item": "minecraft:mycelium"
    },
    {
      "item": "minecraft:mycelium"
    }
  ],
  "output": {
    "item": "minecraft:dirt",
    "count": 4
  },
  "fluid_input": "empty",
  "fluid_output": "empty",
  "temperature": 25,
  "luminosity": 0,
  "time": 15,
  "bacteria_input": "null",
  "bacteria_output": "mucor",
  "bacteria_output_amount": 300
}
```
## 工业发酵机工作逻辑

`tick()` 方法每游戏刻在服务端执行，主要流程如下：
## 动画更新
若 `closingAnimationTick > 0`，递减并在归零时清除 `IS_CLOSING` 状态。
## 电力与温控
`cachedPower > 0`：消耗 1 tick 电力，重置 `noPowerTicker = 10`。
否则：若 `noPowerTicker > 0` 递减；当两者均为零且 `temperatureFix != 0`，温度修正缓慢归零（每 20 tick 调整 1）。
## 环境温度更新
每 100 tick 重新计算一次环境温度：`temperature = TemperatureUtil.getTemperature(pos, world)`。
## 流体装桶/倒桶（待优化）
装桶：若桶输入槽有桶、流体 ≥ 1 桶且桶输出槽为空，则将流体装桶，并写入品质 NBT。

倒桶：支持普通流体桶和奶桶（特殊处理），倒入时合并品质 NBT，并返还空桶。
## 发酵主逻辑（每 20 tick）
菌种自然涨落：调用 `updateBacteriaGrowth(world)`。

配方匹配：查找所有满足 `FermentingRecipe` 类型且匹配物品输入的配方。

筛选最优配方：根据流体、菌种、环境评分选出最佳配方。

效率计算：调用 `calculateEfficiency(world, recipe, dominant)`。

进度推进：`progress += 1`，若 `progress >= maxProgress` 则完成发酵。

重置：若无有效配方，重置进度并关闭运行状态。
## 菌种培育
每个菌种包含：

`optimalTemp`：最适温度

`toleranc`：温度容忍范围（±）

`optimalMinCFU` / optimalMaxCFU：最佳 CFU 区间

`maxLight`：最大光照容忍值

`rawID`：用于数组索引
```java
public enum BacteriaType {
    LACTOBACILLUS("lactobacillus", 35, 15, 5000, 20000, 7, 0),
    ACETOBACTER("acetobacter", 30, 10, 3000, 327680, 12, 1),
    YEAST("yeast", 28, 12, 2000, 30000, 7, 2),
    MUCOR("mucor", 25, 5, 4000, 10000, 4, 3),
    ESCHERICHIA("escherichia", 37, 3, 8500, 9500, 4, 4);
}
```
## CFU 动态变化
`updateBacteriaGrowth()` 每 20 tick 执行：

适宜环境（温度、光照、流体非空）：

指数增长：`cfu + cfu * 0.02 * growthFactor`

当 `cfu > 50000` 时，采用逻辑增长，增长率受 `(1 - cfu / MAX_CFU)` 限制，并加入随机波动。

不适宜环境：衰减 `cfu = cfu - cfu * 0.01`，最低为 0。

杂菌引入：若随机数小于 `spawnMiscBacteriaRate`，在高温高光下引入醋酸菌或大肠杆菌。
## 主导菌与辅助菌
主导菌：`CFU ≥ 1000` 且 CFU 最高的菌种。

辅助菌：`CFU ≥ 100` 且 < 主导菌 CFU 的 50%，且未达到主导阈值。不同组合提供不同加成（如醋酸菌辅助酵母菌提升品质）。
## 环境因素与效率计算
## 温度
实际温度 = temperature + temperatureFix。

效率因子：30.0 / (30.0 + |实际温度 - 最适温度|)。
## 光照
使用 `world.getLightLevel(pos)`。

效率因子：9.0 / (9.0 + |实际光照 - 最适光照|)。
## 菌种增益
主导菌在最佳 CFU 范围内时，根据菌种提供额外效率：

醋酸菌 + 醋类产物：×2.0

酵母菌 + 酒精类产物：×1.5

毛霉：×1.3

乳酸菌 + 酵母辅助：×1.3
## 竞争与侵袭
拮抗：当第二菌种 CFU > 主导菌一半时，效率 ×0.5。

大肠杆菌侵袭：若大肠杆菌 CFU ≥ 300 且 ≥ 主导菌 30%，效率 ×0.5。
## 品质判定
品质等级：完美（2）、正常（1）、劣质（0）、有毒（-1）。

`determineQuality()` 逻辑：

基础品质 = 1。

杂菌惩罚：

大肠杆菌 ≥ 300 且非主导：-2

毛霉 ≥ 500 且非主导：-1

醋酸菌 ≥ 1000 且非主导：-1

主导菌特殊情况：

酵母菌：醋酸菌辅助 +1，CFU > 30000 -1，高温/高光 -1

乳酸菌：酵母菌辅助 +1，CFU > 20000 -1，高温/高光 -1

大肠杆菌：最佳 CFU 时 +2，否则 -2

毛霉：CFU > 10000 -1

若主导菌 CFU 不在最佳范围：-1。

最终 clamp 到 [-1, 2]。

品质值会存入输出物品的 NBT 标签 ferment_quality，影响食用效果。

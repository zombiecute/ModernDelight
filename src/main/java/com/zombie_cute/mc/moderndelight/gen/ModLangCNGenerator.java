package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.ModernDelightClient;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.food.pizza.PizzaWIPBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.*;
import com.zombie_cute.mc.moderndelight.block.kitchenware.decor.CabinetBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.batteries.BatteryBlockItem;
import com.zombie_cute.mc.moderndelight.compat.rei.baking_tray.BakingTrayCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.biogas_fermentation.BiogasFermentationCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.glass_bowl.GlassBowlMixWithWaterCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.glass_bowl.GlassBowlWhiskingCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.pizza.PizzaMakingCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.transform.OvenTransformCategory;
import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.moderndelight.entity.ModEntities;
import com.zombie_cute.mc.moderndelight.item.ModItemGroups;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.screen.custom.*;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import com.zombie_cute.mc.moderndelight.util.enums.BacteriaType;
import com.zombie_cute.mc.moderndelight.util.enums.CreamFlavor;
import com.zombie_cute.mc.moderndelight.util.enums.SpecialIngredient;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.jetbrains.annotations.NotNull;

public class ModLangCNGenerator extends FabricLanguageProvider {
    public ModLangCNGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(@NotNull TranslationBuilder translationBuilder) {
        translationBuilder.add(TextUtil.HOLD_A_KEY, "按住 ");
        translationBuilder.add(TextUtil.SHIFT_END, " 以查看概要");
        translationBuilder.add(TextUtil.CTRL_END, " 以查看流体信息");
        translationBuilder.add(TextUtil.WHISK, "一款搅拌器可以用于搅拌或打发食材，或者...？用它来搅匀怪物的脑浆？");
        translationBuilder.add(TextUtil.BUTTER, "黏糊糊的质感...或许除了用于制作食物另有其用？");
        translationBuilder.add(TextUtil.TRUFFLE, "可以在灰化土中找到");
        translationBuilder.add(TextUtil.CUTTLEBONE, "被紫水晶工具击杀时掉落");
        translationBuilder.add(TextUtil.FILTER, "一款由线织成的过滤器，可以放在木盆中用于过滤油和渣");
        translationBuilder.add(TextUtil.KNEADING_STICK, "打人与擀面兼备");
        translationBuilder.add(TextUtil.SPATULA, "可以搭配受热的烤盘一起使用来炒菜");
        translationBuilder.add(TextUtil.BDC,
                "它是构成沼气池的核心部件，当它下有一个平整的完全密封的长方体区域时，" +
                "这块区域就会被自动的识别为沼气池的一部分。");
        translationBuilder.add(TextUtil.BDI,
                "当其下方存在一个工作的沼气池控制器时，它将被激活，" +
                        "这时，你只需将任意的食物装入其中，他就会开始发酵，并产生沼气。" +
                        "当它的侧面存在一个燃气罐时，就可以为燃气罐充气。");
        translationBuilder.add(TextUtil.GAS_COOKING_STOVE,
                "当它的侧面有一个装有气体的燃气罐时，右键即可有几率激活它。激活后，它可以加热其上方的方块，烤盘，烤炉，高级熔炉均可兼容。");
        translationBuilder.add(TextUtil.CROWBAR, "物理学圣剑");
        translationBuilder.add(TextUtil.INGREDIENTS, "成分：");
        translationBuilder.add(TextUtil.BAKING_TRAY,
                "一款简易的小铁盘，可以用于升级高级熔炉，也可以用于炒菜；" +
                        "在其下方有燃气灶为其加热的条件下，将食物放在烤盘的上方，使用锅铲进行翻炒5次即可炒熟食物。");
        translationBuilder.add(TextUtil.DEEP_FRYER,
                "油炸锅可以用于油炸各种食物。倒入食用油，在其后方放置一个装有燃气的燃气罐时，再按下它前方的按钮，" +
                        "它就会开始消耗燃气并加热，接着将食物加入其中就会开始油炸了，对其按住Shift加右键可以查看其详细状态。");
        translationBuilder.add(TextUtil.WOODEN_BASIN,
                "一个可以用于榨取植物油的木盆，放入油料作物（如炒葵花籽）和过滤网，" +
                        "然后站在它上面跳跃就可以榨油了你可以使用玻璃瓶或是桶来取出油。");
        translationBuilder.add(TextUtil.HOLDER,
                "可以用于安全无伤的取出油炸锅内的物品，也可以按潜行加右键来转移不同容器之间的物品。" +
                        "手持右键可以把夹着的物品取下。未夹物品时右键可以把另一只手握着的物品夹住。");
        translationBuilder.add(TextUtil.ALT_END, " 以查看用电概况");
        translationBuilder.add(TextUtil.ACDCC,
                "一款可以自由转换直流电和交流电的装置，“AC->DC”模式下，" +
                        "与任意的交流电发电装置连接时可将交流电转化为直流电并缓存，" +
                        "可以在里面接入电池并为电池充电，“DC->AC”模式下，可以将缓存的直流电或接入电池的直流电转化为交流电并输出，" +
                        "可以在GUI中调节它的工作效率。此外还可以用于于FE/RF/AE的转化。");
        translationBuilder.add(TextUtil.ALT_ACGen, "[交流电发电量]");
        translationBuilder.add(TextUtil.ALT_ACCom, "[交流电耗电量]");
        translationBuilder.add(TextUtil.ALT_DCSto, "[最大直流电存储量]");
        translationBuilder.add(TextUtil.ALT_DCGen, "[直流电发电量]");
        translationBuilder.add(TextUtil.ALT_DCCom, "[直流电耗电量]");
        translationBuilder.add(TextUtil.BAMBOO_STEAMER,
                "一款简易的竹制蒸笼，需要搭配笼盖，笼屉，一个被燃气灶加热且装有水的炼药锅来使用，" +
                        "向其中放入食材即可开始加工，蒸笼最高可以叠3个方块高，每个方块可以放4层。");
        translationBuilder.add(TextUtil.CUISINE_TABLE,
                "料理台可以深度加工“食材”，搭配一个工具来使用，将“食材”分解成更多的部分");
        translationBuilder.add(TextUtil.ELECTRICIANS_DESK,
                "电工台允许你合成加工一些复杂的电路元件或电器，" +
                        "将材料正确的放入后点击GUI中的成品按钮来开始合成，共有3种不同类型的小游戏：" +
                        "1. 2048（通过按下WASD来合成出指定的数字后即可）；" +
                        "2. 电路板拼接（将“？”界面中的图案复刻一遍即可，螺丝刀可以削除图案）；" +
                        "3. 汉诺塔（将左边的塔移动到右侧即可，窄块只能放在宽块上）；" +
                        "完成小游戏后再按下成品按钮便可将成品取出。");
        translationBuilder.add(TextUtil.ELECTRIC_STEAMER,
                "与普通蒸笼一样，不过它需要消耗交流电加热水，并且只能同时加工12种食材，" +
                        "不过它的优点就是占用空间小，不需要额外的东西来给它加热。");
        translationBuilder.add(TextUtil.FAN_BLADE,
                "一块用于风力发电的扇叶，可以安装在风力发电机控制器上，" +
                        "也可以放在其他地方做装饰用，它的旋转速度取决于当前高度和天气状况。");
        translationBuilder.add(TextUtil.FARADAY_GENERATOR,
                "当被正确的安装在斯特林引擎的飞轮后时，则可进行发电，" +
                        "注意一定要将斯特林引擎的飞轮和本发电机拼的刚好能够衔接上时才能正常发电。");
        translationBuilder.add(TextUtil.FREEZER,
                "一款简易的冰箱，可以用于存储物品，也可以冷加工一些食材，" +
                        "可以通过消耗一些制冷剂，如冰块等，来制冷，或者是直接消耗交流电来制冷。");
        translationBuilder.add(TextUtil.GLASS_BOWL,
                "一款用于加工或搅拌食材的容器，需要配合搅拌器使用，也可以往其中倒入水来进行与水混合的操作。");
        translationBuilder.add(TextUtil.PGEN,
                "光伏发电机可以将光能转化为直流电能并将其存储起来，可以在里面接入电池并为其充电，" +
                        "发电量取决与当前的高度，时间，天气，方块光和天空光等因素。同时也会尝试为其后方的直流用电器供能。");
        translationBuilder.add(TextUtil.STERLING_ENGINE,
                "斯特林引擎需要安装在燃烧中的熔炉，高炉，烟熏炉，高级熔炉或烤炉上才能工作，" +
                        "它需要与法拉第发电机相连接来产生交流电。使用海绵可以让它减小噪音。");
        translationBuilder.add(TextUtil.TESLA_COIL,
                "特斯拉线圈可以远程传输交流电，将其放置在一个可以产生交流电的装置上时便可将电力扩散至其东南西北上下各8格的区域，" +
                        "可以打开GUI中的可视化按钮来可视化这48格的区域，区域内的交流电用电器和其他特斯拉线圈也会被通电，" +
                        "距离被通电的特斯拉线圈太近的生物会收到电击伤害。");
        translationBuilder.add(TextUtil.WTC,
                "当风力发电机的叶片被正确的安装后，本控制器将会开始工作并产生交流电，产生的电量取决于当前高度和天气状况。");
        translationBuilder.add(TextUtil.ICE_CREAM_MAKER,
                "按住潜行和右键可以打开GUI界面，在其中放入任意口味的奶油，糖和鸡蛋并提供交流电后就会开始工作，" +
                        "制成的不同口味的冰淇淋会存储在它里面，在勾选想要混合的口味后，手持冰淇淋脆筒右键便可以将冰淇淋取出。");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK_MSG, "电量不足！");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK_NEED_BOWL, "此物品只能通过配合玻璃碗来加工！");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK,
                "和普通的搅拌器一样，都可以用于搭配玻璃碗来实现搅拌食物，" +
                        "不过它会对直流电的进行消耗，此外它还对地上的掉落物也能够生效。");
        translationBuilder.add(TextUtil.CHARGING_POST,
                "一款可以给直流用电器充电的装置，右键打开GUI后，左边的两格为电池放置槽，" +
                        "你可以在这里放置至少一个电池，右边的槽为待充电的直流用电器。");
        translationBuilder.add(TextUtil.JUICE_EXTRACTOR,
                "一款可以用于榨取果汁的榨汁机，通入交流电便可使用，手持待物品右键榨汁机即可将其放入其中，" +
                        "放入两种物品后再次右键即可启动机器。");
        translationBuilder.add(TextUtil.POT_HAS_QUICKLIME, "包含生石灰");
        translationBuilder.add(TextUtil.POT_MISS_QUICKLIME, "缺少生石灰");
        translationBuilder.add(TextUtil.POT_HAS_WATER, "包含水");
        translationBuilder.add(TextUtil.POT_MISS_WATER, "缺少水");
        translationBuilder.add(TextUtil.NOODLE_UNHEALTHY, "奇怪的方便面");
        translationBuilder.add(TextUtil.ANYTHING, "任何物品");
        translationBuilder.add(TextUtil.NEED_FOOD, "你需要在另一只手上持有任意一种食物");
        translationBuilder.add(TextUtil.SEASONING_TIP, "调味品，一只手拿着它，另一只手拿着任何食物，即可为食物调味");
        translationBuilder.add(TextUtil.FAILED_SEASONING, "你不能向这个食物添加更多调味品了");
        translationBuilder.add(TextUtil.SEASONING_ADDED, "调味品：");
        translationBuilder.add(TextUtil.PUN, "禁止无限套娃！");
        translationBuilder.add(TextUtil.WOODEN_PLATE,
                "一个可以用于展示物品的盘子，右键可以将物品放在上面，潜行加右键旋转上面的物品。");
        translationBuilder.add(TextUtil.INDUSTRIAL_FERMENTER,
                "利用微生物的力量，将普通食材转化为美酒、香醋与更多奇妙产物。");
        translationBuilder.add(TextUtil.FERMENTING_QUALITY,"品质：");
        translationBuilder.add(TextUtil.QUALITY_NEG_1,"有毒");
        translationBuilder.add(TextUtil.QUALITY_0,"劣质");
        translationBuilder.add(TextUtil.QUALITY_1,"正常");
        translationBuilder.add(TextUtil.QUALITY_2,"优质");
        translationBuilder.add(TextUtil.FERMENTATION_SHIFT,"SHIFT 键已经按下，右键此处将会删除当前的所有流体");
        translationBuilder.add(TextUtil.FERMENTATION_TIME,"在发酵机100%效率的情况下，完成此发酵需要");
        translationBuilder.add(TextUtil.FERMENTATION_LIGHT,"此发酵的最适光照强度为");
        translationBuilder.add(TextUtil.FERMENTATION_TEMP,"此发酵的最适温度为");
        translationBuilder.add(TextUtil.NO_BACTRIA,"无");
        translationBuilder.add(TextUtil.FERMENTATION_TANK,"一个容量为 5 B 的流体储罐，可以用于转移工业发酵机中的发酵液以及发酵菌。");
        translationBuilder.add(TextUtil.TRAWLER,
                "一款可以用于打捞水产品的设备，只能放置在水面上，右键可以升起或放下拖网，拖网位于水面上时，按下 Shift 加右键可以打开 GUI。鱼饵为可选项。");
        translationBuilder.add(TextUtil.TRAWLER_BAIT,"此处放置鱼饵（可选）");
        translationBuilder.add(TextUtil.GET_EXP,"点击以获取经验值");
        translationBuilder.add(MiniGame2Screen.TOOLTIP,"消除工具");
        translationBuilder.add(TextUtil.STONE_MORTAR,"一手持待碾磨的物品，另一只手持石臼，按住交互键开始碾磨。");

        translationBuilder.add(GlassBowlBlockEntity.WHISK_FAIL, "这东西似乎不是搅拌器能处理的");
        translationBuilder.add(GlassBowlBlockEntity.NEED_PACKAGE, "里面的东西需要特殊的物品取出，需要");
        translationBuilder.add(PizzaWIPBlockEntity.NEED_INGREDIENT, "你需要在这里放一个适当的食材");
        translationBuilder.add(PizzaWIPBlockEntity.NEED_CHEESE, "你需要在这里放一个奶酪");
        translationBuilder.add(FreezerBlock.FAIL_TO_OPEN, "冰箱的门被意外的阻挡了");
        translationBuilder.add(JuiceExtractorBlockEntity.NO_POWER, "没有足够的能量来启动榨汁机");
        translationBuilder.add(JuiceExtractorBlockEntity.WRONG_RECIPE, "放入的物品无法进行榨汁操作");
        translationBuilder.add(JuiceExtractorBlockEntity.IS_FULL, "请先取出榨汁机内的果汁，需要");

        translationBuilder.add(ModItems.IRON_WHISK, "搅拌器");

        translationBuilder.add(ModItems.COPPER_KNIFE, "铜刀");
        translationBuilder.add(ModItems.AMETHYST_KNIFE, "紫水晶刀");
        translationBuilder.add(ModItems.KNEADING_STICK, "擀面杖");
        translationBuilder.add(ModItems.CROWBAR, "撬棍");
        translationBuilder.add(ModItems.SPATULA, "锅铲");
        translationBuilder.add(ModItems.FILTER, "过滤网");

        translationBuilder.add(ModItems.EGG_TART, "蛋挞");
        translationBuilder.add(ModItems.APPLE_PUDDING, "焦糖苹果布丁");
        translationBuilder.add(ModItems.BRAISED_SHRIMP_BALL, "金丝虾球");
        translationBuilder.add(ModItems.BRAISED_SHRIMP_BALL_WIP, "金丝虾球（半成品）");
        translationBuilder.add(ModItems.MATCHA_PUDDING, "抹茶布丁");
        translationBuilder.add(ModItems.SUNFLOWER_SEED, "葵花籽");
        translationBuilder.add(ModItems.SUNFLOWER_SEED_PEEL, "葵花籽皮");
        translationBuilder.add(ModItems.SUNFLOWER_SEED_PULP, "葵花籽肉");
        translationBuilder.add(ModItems.ROASTED_SUNFLOWER_SEED, "炒葵花籽");
        translationBuilder.add(ModItems.BIOMASS, "生物质");
        translationBuilder.add(ModItems.VEGETABLE_OIL_BOTTLE, "罐装植物油");
        translationBuilder.add(ModItems.VEGETABLE_OIL_BUCKET, "植物油桶");

        translationBuilder.add(ModBlocks.CLAY_POT, "砂锅");
        translationBuilder.add(ModBlocks.BRITISH_BREAKFAST, "英式早餐");
        translationBuilder.add(ModBlocks.MARSHALA_CHEESE_BALL, "玛莎拉芝士球");
        translationBuilder.add(ModBlocks.CHICKEN_RICE, "砂锅香菇滑鸡饭");
        translationBuilder.add(ModBlocks.CHILI_FISH, "水煮鱼");
        translationBuilder.add(ModBlocks.CRAB_SAUSAGE_VEGETABLE, "蟹黄火腿炒时蔬");
        translationBuilder.add(ModBlocks.PORK_NOODLE, "砂锅叉烧面");
        translationBuilder.add(ModBlocks.PEPPER_BEEF_RICE, "砂锅黑椒牛柳饭");
        translationBuilder.add(ModBlocks.VEGETABLE_NOODLE, "砂锅素汤面");

        translationBuilder.add(ModItems.CARAMEL_TOAST, "焦糖吐司");

        translationBuilder.add(ModItems.TRUFFLE_EGG_TART, "松露蛋挞");
        translationBuilder.add(ModItems.PUDDING_WIP_1, "布丁（半成品1）");
        translationBuilder.add(ModItems.PUDDING_WIP_2, "布丁（半成品2）");

        translationBuilder.add(ModItems.POTATO_STARCH, "马铃薯淀粉");
        translationBuilder.add(ModItems.MIXED_DOUGH, "混合面团");
        translationBuilder.add(ModItems.WHEAT_FLOUR, "小麦粉");
        translationBuilder.add(ModBlocks.WHEAT_DOUGH, "小麦面团");

        translationBuilder.add(ModItems.BLACK_PEPPER_CORN, "黑胡椒粒");
        translationBuilder.add(ModItems.BLACK_PEPPER_DUST, "黑胡椒粉");
        translationBuilder.add(ModItems.BLACK_PEPPER_SEASONING, "黑胡椒调味罐");

        translationBuilder.add(ModItems.MASHED_POTATO, "土豆泥");
        translationBuilder.add(ModItems.SAUCE_MASHED_POTATO, "酱汁土豆泥");
        translationBuilder.add(ModBlocks.MASHED_POTATO_BLOCK, "土豆泥块");

        translationBuilder.add(ModItems.APPLE_PETAL, "苹果片");
        translationBuilder.add(ModItems.CHERRY, "樱桃");
        translationBuilder.add(ModItems.CHERRY_BOMB, "樱桃");
        translationBuilder.add(ModItems.BUTTER, "黄油");
        translationBuilder.add(ModItems.CHEESE, "奶酪");

        translationBuilder.add(ModItems.PINECONE, "松果");
        translationBuilder.add(ModItems.ORANGE, "橘子");
        translationBuilder.add(ModItems.PEAR, "梨子");
        translationBuilder.add(ModItems.LEMON, "柠檬");
        translationBuilder.add(ModItems.MANGO, "芒果");

        translationBuilder.add(ModItems.BREAD_SLICE, "面包片");
        translationBuilder.add(ModItems.BUTTER_BREAD_SLICE, "黄油面包片");

        translationBuilder.add(ModItems.CREAM_BUCKET, "奶油桶");
        translationBuilder.add(ModItems.CREAM, "奶油");
        translationBuilder.add(ModItems.APPLE_CREAM, "苹果奶油");
        translationBuilder.add(ModItems.CHERRY_CREAM, "樱桃奶油");
        translationBuilder.add(ModItems.CHOCOLATE_CREAM, "巧克力奶油");
        translationBuilder.add(ModItems.PRAWN, "对虾");
        translationBuilder.add(ModItems.GOLDEN_APPLE_CREAM, "金苹果奶油");
        translationBuilder.add(ModItems.MATCHA_CREAM, "抹茶奶油");
        translationBuilder.add(ModItems.PUMPKIN_CREAM, "南瓜奶油");
        translationBuilder.add(ModItems.WHIPPING_CREAM, "淡奶油");

        translationBuilder.add(ModItems.COOKED_PRAWN, "熟对虾");
        translationBuilder.add(ModItems.BUTTER_COOKIE, "黄油曲奇");
        translationBuilder.add(ModItems.CHEESE_POTATO_BOAT, "芝士土豆船");
        translationBuilder.add(ModItems.OPERA_CAKE, "歌剧院蛋糕");
        translationBuilder.add(ModItems.CRYSTAL_DUMPLING_WIP, "水晶虾饺（半成品）");
        translationBuilder.add(ModItems.DOUBLE_MEAT_BURGER, "双层肉霸堡");
        translationBuilder.add(ModItems.CRISPY_BARBECUE, "香脆烤肉");
        translationBuilder.add(ModItems.MIXED_JAM, "混合果酱");
        translationBuilder.add(ModItems.SEASONING_BOTTLE, "调料瓶");
        translationBuilder.add(ModItems.CRAB, "螃蟹");
        translationBuilder.add(ModItems.CRAB_ROE, "蟹黄");
        translationBuilder.add(ModItems.COOKED_CRAB, "烤螃蟹");
        translationBuilder.add(ModItems.GOLDEN_DOUBLE_CRISP_WIP, "黄金双环脆（半成品）");
        translationBuilder.add(ModItems.GOLDEN_DOUBLE_CRISP, "黄金双环脆");
        translationBuilder.add(ModItems.SAKURA_YOGURT_SAUCE, "樱花酸奶沙司");
        translationBuilder.add(ModItems.SCOTCH_EGG_WIP, "苏格兰蛋（半成品）");
        translationBuilder.add(ModItems.SCOTCH_EGG, "苏格兰蛋");
        translationBuilder.add(ModItems.CHOW_MEIN, "什锦炒面");
        translationBuilder.add(ModItems.STUFFED_MASHED_POTATO, "夹心土豆泥");
        translationBuilder.add(ModItems.STEAMED_EGG_CUSTARD, "蒸蛋羹");
        translationBuilder.add(ModItems.CHILI_SEEDS, "辣椒种子");
        translationBuilder.add(ModItems.CHILI_PEPPER, "辣椒");
        translationBuilder.add(ModItems.SOYBEAN, "大豆");
        translationBuilder.add(ModItems.CHILI_SAUCE, "辣椒酱");

        translationBuilder.add(ModItems.MOUSSE_WIP, "慕斯（半成品）");
        translationBuilder.add(ModItems.CREAM_MOUSSE, "奶油慕斯");
        translationBuilder.add(ModItems.CHERRY_MOUSSE, "樱桃慕斯");
        translationBuilder.add(ModItems.CHOCOLATE_MOUSSE, "巧克力慕斯");
        translationBuilder.add(ModItems.PUMPKIN_MOUSSE, "南瓜慕斯");
        translationBuilder.add(ModItems.GOLDEN_APPLE_MOUSSE, "金苹果慕斯");
        translationBuilder.add(ModItems.MATCHA_MOUSSE, "抹茶慕斯");

        translationBuilder.add(ModItems.CRYSTAL_DUMPLING, "水晶饺子");

        translationBuilder.add(ModItems.SQUID, "鱿鱼");
        translationBuilder.add(ModItems.ROASTED_SQUID, "烤鱿鱼");
        translationBuilder.add(ModItems.CUTTLEBONE, "鱿鱼骨");
        translationBuilder.add(ModItems.GLOW_SQUID, "发光鱿鱼");
        translationBuilder.add(ModItems.ROASTED_GLOW_SQUID, "烤发光鱿鱼");
        translationBuilder.add(ModItems.GLOW_CUTTLEBONE, "发光鱿鱼骨");

        translationBuilder.add(ModItems.SAUSAGE, "香肠");
        translationBuilder.add(ModItems.SECTIONED_SAUSAGE, "切片香肠");

        translationBuilder.add(ModItems.BLACK_TRUFFLE, "黑松露");
        translationBuilder.add(ModItems.WHITE_TRUFFLE, "白松露");
        translationBuilder.add(ModItems.BLACK_TRUFFLE_SEASONING, "黑松露调味罐");
        translationBuilder.add(ModItems.WHITE_TRUFFLE_SEASONING, "白松露调味罐");

        translationBuilder.add(ModItems.SILICON_INGOT, "硅锭");
        translationBuilder.add(ModBlocks.SILICON_BLOCK, "硅块");
        translationBuilder.add(ModItems.SILICON_COMPONENT, "硅构件");
        translationBuilder.add(ModItems.REDSTONE_COMPONENT, "红石构件");
        translationBuilder.add(ModItems.DIAMOND_COMPONENT, "钻石构件");

        translationBuilder.add(ModItems.EMPTY_CAKE, "饼皮");
        translationBuilder.add(ModItems.BLUE_ORCHID_FLOWER_CAKE, "兰花饼");
        translationBuilder.add(ModItems.CHERRY_CAKE, "樱桃饼");
        translationBuilder.add(ModItems.LILAC_CAKE, "丁香饼");
        translationBuilder.add(ModItems.ORANGE_TULIP_CAKE, "橙色郁金香饼");
        translationBuilder.add(ModItems.OXEYE_DAISY_CAKE, "滨菊饼");
        translationBuilder.add(ModItems.PINK_TULIP_CAKE, "粉色郁金香饼");
        translationBuilder.add(ModItems.ROSE_CAKE, "玫瑰饼");
        translationBuilder.add(ModItems.SUNFLOWER_CAKE, "向日葵饼");
        translationBuilder.add(ModItems.WHITE_TULIP_CAKE, "白色郁金香饼");
        translationBuilder.add(ModItems.WITHER_ROSE_CAKE, "凋灵玫瑰饼");

        translationBuilder.add(ModItems.FRIED_COD, "油炸鳕鱼");
        translationBuilder.add(ModItems.FRIED_SALMON, "油炸鲑鱼");
        translationBuilder.add(ModItems.FRIED_MILK_WIP, "油炸牛奶（半成品）");
        translationBuilder.add(ModItems.FRIED_MILK, "油炸牛奶");
        translationBuilder.add(ModItems.FRIED_APPLE, "油炸苹果");
        translationBuilder.add(ModItems.RAW_POTATO_CHIP, "生土豆片");
        translationBuilder.add(ModItems.POTATO_CHIP, "薯片");
        translationBuilder.add(ModItems.FRIED_DOUGH_STICK, "油条");
        translationBuilder.add(ModItems.RAW_CHICKEN_FILLET, "生鸡柳");
        translationBuilder.add(ModItems.CHICKEN_FILLET, "炸鸡柳");
        translationBuilder.add(ModItems.STEAMED_BUN, "馒头");
        translationBuilder.add(ModItems.DEEP_FRIED_BUN, "黄金馒头");
        translationBuilder.add(ModItems.DEEP_FRIED_BUN_WIP, "黄金馒头（半成品）");

        translationBuilder.add(ModItems.ICE_CREAM_CONE, "冰淇淋脆筒");
        translationBuilder.add(ModItems.RAW_ICE_CREAM_CONE, "冰淇淋脆筒（半成品）");
        translationBuilder.add(ModItems.ICE_CREAM, "冰淇淋");

        translationBuilder.add(ModItems.EGG_BOWL, "碗装鸡蛋");
        translationBuilder.add(ModItems.CHERRY_EGG, "樱桃鸡蛋");
        translationBuilder.add(ModItems.FISH_EGG, "海鲜鸡蛋");
        translationBuilder.add(ModItems.STEAMED_EGG, "蒸鸡蛋");
        translationBuilder.add(ModItems.STEAMED_CHERRY_EGG, "樱桃蒸蛋");
        translationBuilder.add(ModItems.STEAMED_FISH_EGG, "海鲜蒸蛋");
        translationBuilder.add(ModItems.BEEF_TOMATO_CUP, "牛肉番茄盅");
        translationBuilder.add(ModItems.BUTTERFLY_CRISP, "蝴蝶酥");
        translationBuilder.add(ModItems.JAR, "玻璃罐");
        translationBuilder.add(ModItems.CARAMEL, "焦糖");
        translationBuilder.add(ModItems.CARAMEL_PUDDING, "焦糖布丁");
        translationBuilder.add(TextUtil.CAN_PLACE, "可放置");
        translationBuilder.add(ModItems.CHEESE_BAKED_POTATO, "芝士焗土豆");
        translationBuilder.add(ModItems.CHEESE_RICE_BALL, "芝士饭团");
        translationBuilder.add(ModItems.CHOCOLATE_SAUCE, "巧克力酱");
        translationBuilder.add(ModItems.DEEP_FRIED_SHRIMP_CAKE, "油炸虾饼");
        translationBuilder.add(ModItems.SEAWEED_FRIED_SHRIMP_CAKE, "海苔炸虾饼");
        translationBuilder.add(ModItems.DONUT, "甜甜圈");
        translationBuilder.add(ModItems.CHERRY_PUDDING, "樱桃布丁");
        translationBuilder.add(ModItems.ICE_LOLLY, "冰棍");
        translationBuilder.add(ModItems.PACKAGING_BAG, "包装袋");
        translationBuilder.add(ModItems.DIRTY_PACKAGING_BAG, "脏的包装袋");
        translationBuilder.add(ModItems.POTATO_CHIPS, "土豆条");
        translationBuilder.add(ModItems.DEEP_FRIED_POTATO_CHIPS, "炸土豆条");
        translationBuilder.add(ModItems.FRENCH_FRIES, "薯条");
        translationBuilder.add(ModItems.FRIED_CHICKEN, "炸鸡");
        translationBuilder.add(ModItems.FRIED_RED_MUSHROOM, "油炸红蘑菇");
        translationBuilder.add(ModItems.FRIED_BROWN_MUSHROOM, "油炸棕蘑菇");
        translationBuilder.add(ModBlocks.CREAM_OF_MUSHROOM_SOUP, "奶油蘑菇汤");
        translationBuilder.add(ModBlocks.FRENCH_ONION_SOUP, "法式洋葱汤");
        translationBuilder.add(ModItems.CHERRY_ICE_LOLLY, "樱桃冰棍");
        translationBuilder.add(ModItems.MATCHA_ICE_LOLLY, "抹茶冰棍");
        translationBuilder.add(ModItems.WITHER_ICE_LOLLY, "凋零冰棍");
        translationBuilder.add(ModItems.GLOW_SQUID_TENTACLE, "发光鱿鱼须");
        translationBuilder.add(ModItems.GLOW_SQUID_TENTACLE_KEBABS, "发光鱿鱼须串");
        translationBuilder.add(ModItems.RAW_GLOW_SQUID_TENTACLE_KEBABS, "生发光鱿鱼须串");
        translationBuilder.add(ModItems.SQUID_TENTACLE, "鱿鱼须");
        translationBuilder.add(ModItems.RAW_SQUID_TENTACLE_KEBABS, "生鱿鱼须串");
        translationBuilder.add(ModItems.SQUID_TENTACLE_KEBABS, "鱿鱼须串");
        translationBuilder.add(ModItems.TURNIP, "芜菁甘蓝");
        translationBuilder.add(TextUtil.TURNIP, "来自作者的微微怨念");
        translationBuilder.add(ModItems.RAW_DONUT, "生甜甜圈");
        translationBuilder.add(ModItems.CHOCOLATE_DONUT, "巧克力甜甜圈");
        translationBuilder.add(ModItems.CHEESE_BURGER, "芝士汉堡");
        translationBuilder.add(ModItems.ROAST_STREAKY_PORK, "香烤五花肉");
        translationBuilder.add(ModItems.PORK_CHOP_BURGER, "芝士猪扒堡");
        translationBuilder.add(ModItems.FRIED_COD_NUGGET, "油炸鳕鱼块");
        translationBuilder.add(ModBlocks.FISH_AND_CHIPS_ITEM, "炸鱼薯条");
        translationBuilder.add(ModBlocks.WILD_PEPPER_CROP, "野生黑胡椒");
        translationBuilder.add(ModItems.GARLIC, "大蒜");
        translationBuilder.add(ModItems.GARLIC_PETAL, "蒜瓣");
        translationBuilder.add(ModBlocks.WILD_GARLIC, "野生大蒜");
        translationBuilder.add(ModItems.ELECTRIC_WHISK, "电动搅拌器");
        translationBuilder.add(ModItems.RAW_NOODLES, "生面条");
        translationBuilder.add(ModBlocks.WILD_CHILI_CROP, "野生辣椒");
        translationBuilder.add(ModBlocks.WILD_SOYBEAN, "野生大豆");
        translationBuilder.add(ModItems.CHILI_POWDER_SEASONING, "辣椒粉调味罐");
        translationBuilder.add(ModItems.SOY_MILK_BUCKET, "豆浆桶");

        translationBuilder.add(ModEntities.BUTTER,"黄油");
        translationBuilder.add(ModEntities.CHERRY_BOMB,"樱桃");

        translationBuilder.add(ModBlocks.PIZZA, "披萨");
        translationBuilder.add(ModBlocks.RAW_PIZZA, "生披萨");
        translationBuilder.add(ModBlocks.PIZZA_WIP, "披萨（半成品）");
        translationBuilder.add(ModItems.ANCIENT_SCRAP, "远古残片");

        translationBuilder.add(CreamFlavor.TRANSLATION_KEY, "口味");
        translationBuilder.add(CreamFlavor.NULL.getTranslationKey(), "未知");
        translationBuilder.add(CreamFlavor.PLAIN.getTranslationKey(), "原味");
        translationBuilder.add(CreamFlavor.APPLE.getTranslationKey(), "苹果味");
        translationBuilder.add(CreamFlavor.CHERRY.getTranslationKey(), "樱桃味");
        translationBuilder.add(CreamFlavor.CHOCOLATE.getTranslationKey(), "巧克力味");
        translationBuilder.add(CreamFlavor.GOLDEN_APPLE.getTranslationKey(), "金苹果味");
        translationBuilder.add(CreamFlavor.MATCHA.getTranslationKey(), "抹茶味");
        translationBuilder.add(CreamFlavor.PUMPKIN.getTranslationKey(), "南瓜味");

        translationBuilder.add(ModBlocks.GLASS_BOWL, "玻璃碗");
        translationBuilder.add(GlassBowlWhiskingCategory.GLASS_BOWL_NAME, "搅拌");
        translationBuilder.add(GlassBowlMixWithWaterCategory.WATER_GLASS_BOWL_NAME,"与水混合");
        translationBuilder.add(ModBlocks.OVEN, "烤炉");
        translationBuilder.add(ModBlocks.ADVANCE_FURNACE, "高级熔炉");
        translationBuilder.add(AdvanceFurnaceBlockEntity.ADVANCE_FURNACE_NAME, "高级熔炉");
        translationBuilder.add(ModBlocks.BAKING_TRAY, "烤盘");
        translationBuilder.add(OvenBlockEntity.OVEN_NAME, "烘焙");
        translationBuilder.add(ModBlocks.FREEZER, "冰柜");
        translationBuilder.add(FreezerBlockEntity.FREEZER_NAME, "冷藏");
        translationBuilder.add(PizzaMakingCategory.PIZZA_TITLE, "制作披萨");
        translationBuilder.add(OvenTransformCategory.TRANSFORM_TITLE, "世界交互");
        translationBuilder.add(ModBlocks.DEEP_FRYER, "油炸锅");
        translationBuilder.add(BakingTrayCategory.BAKING_TRAY_NAME, "炒菜");
        translationBuilder.add(ModBlocks.WOODEN_BASIN, "木盆");
        translationBuilder.add(WoodenBasinBlockEntity.WOODEN_BASIN_NAME, "榨油");
        translationBuilder.add(ModBlocks.GAS_CANISTER, "燃气罐");
        translationBuilder.add(GasCanisterBlockEntity.GAS_CANISTER_NAME, "容量");
        translationBuilder.add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER, "沼气池控制器");
        translationBuilder.add(BiogasDigesterControllerScreen.UNAVAILABLE, "控制器下方的空间不完整、不封闭或者过大");
        translationBuilder.add(BiogasDigesterControllerScreen.SIZE, "控制器下方的空间的大小");
        translationBuilder.add(BiogasDigesterControllerScreen.GAS_VALUE, "当前的储气量");
        translationBuilder.add(BiogasDigesterControllerScreen.MAX_GAS_VALUE, "沼气池的最大储气量");
        translationBuilder.add(ModBlocks.BIOGAS_DIGESTER_IO, "沼气池输入输出接口");
        translationBuilder.add(BiogasDigesterIOScreen.UNAVAILABLE, "此接口下方的方块不是控制器，或者控制器不可用");
        translationBuilder.add(BiogasFermentationCategory.BIOGAS_FERMENTATION_NAME, "沼气发酵");
        translationBuilder.add(BiogasFermentationCategory.FOOD, "任意食物");
        translationBuilder.add(ModBlocks.BURNING_GAS_COOKING_STOVE, "燃烧中的燃气灶");
        translationBuilder.add(ModBlocks.GAS_COOKING_STOVE, "燃气灶");
        translationBuilder.add(ModBlocks.DEEP_FRY_BASKET, "油炸篮");
        translationBuilder.add(DeepFryerBlockEntity.DEEP_FRYER_NAME, "油炸");
        translationBuilder.add(DeepFryerBlockEntity.ADD_OIL, "你需要向其中加入食用油");
        translationBuilder.add(DeepFryerBlockEntity.TOO_HOT, "太烫了！请用夹子来取物");
        translationBuilder.add(ModBlocks.KITCHEN_UTENSIL_HOLDER, "厨具架");
        translationBuilder.add(ModBlocks.CUISINE_TABLE, "料理台");
        translationBuilder.add(CuisineTableBlockEntity.CUISINE_TABLE_NAME, "烹饪");
        translationBuilder.add(ModBlocks.ANDESITE_CABINET, "安山岩橱柜");
        translationBuilder.add(ModBlocks.DIORITE_CABINET, "闪长岩橱柜");
        translationBuilder.add(ModBlocks.GRANITE_CABINET, "花岗岩橱柜");
        translationBuilder.add(ModBlocks.BLACKSTONE_CABINET, "黑石橱柜");
        translationBuilder.add(ModBlocks.BASALT_CABINET, "玄武岩橱柜");
        translationBuilder.add(ModBlocks.DEEPSLATE_CABINET, "深板岩橱柜");
        translationBuilder.add(ModBlocks.OBSIDIAN_CABINET, "黑曜石橱柜");
        translationBuilder.add(CabinetBlockEntity.CABINET_NAME, "橱柜");
        translationBuilder.add(PhotovoltaicGeneratorBlockEntity.PHOTOVOLTAIC_GENERATOR_NAME, "光伏发电");
        translationBuilder.add(ModBlocks.PHOTOVOLTAIC_GENERATOR, "光伏发电机");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_1, "高效率模式：");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_2, "仅限白天且露天时启动");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_3, " ");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_1, "低效率模式：");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_2, "附近有足够的光照时启动");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_3, " ");
        translationBuilder.add(ModBlocks.AC_DC_CONVERTER, "交流/直流转换器");
        translationBuilder.add(ModBlocks.FAN_BLADE_ITEM, "风力发电机叶片");
        translationBuilder.add(ModBlocks.WIND_TURBINE_CONTROLLER, "风力发电机控制器");
        translationBuilder.add(ModBlocks.SIMPLE_BATTERY, "简易电池");
        translationBuilder.add(ModBlocks.INTERMEDIATE_BATTERY, "中级电池");
        translationBuilder.add(ModBlocks.ADVANCE_BATTERY, "高级电池");
        translationBuilder.add(ModBlocks.DIMENSION_BATTERY, "维度电池");
        translationBuilder.add(BatteryBlockItem.TOOLTIP_TEXT, "电量：");
        translationBuilder.add(ModBlocks.STERLING_ENGINE_ITEM, "斯特林引擎");
        translationBuilder.add(ModBlocks.FARADAY_GENERATOR, "法拉第发电机");
        translationBuilder.add(ACDCConverterScreen.SPEED_TIP, "工作速度（按住Shift以快速调节）：");
        translationBuilder.add(ACDCConverterScreen.PUT_BATTERY_TIP, "此处需要放置一个电池");
        translationBuilder.add(ACDCConverterScreen.AC2DC, "交流转直流模式");
        translationBuilder.add(ACDCConverterScreen.DC2AC, "直流转交流模式");
        translationBuilder.add(ModBlocks.TESLA_COIL, "特斯拉线圈");
        translationBuilder.add(ModBlocks.ELECTRICIANS_DESK, "电工台");
        translationBuilder.add(ModBlocks.BOXED_CHERRIES, "箱装樱桃");
        translationBuilder.add(ModBlocks.BAMBOO_COVER, "竹蒸笼盖");
        translationBuilder.add(ModBlocks.BAMBOO_GRATE, "竹蒸笼屉");
        translationBuilder.add(BambooGrateBlockEntity.NAME, "蒸笼");
        translationBuilder.add(ModBlocks.ELECTRIC_STEAMER, "电蒸笼");
        translationBuilder.add(ModBlocks.ICE_CREAM_MAKER, "冰淇淋机");
        translationBuilder.add(IceCreamMakerBlockEntity.NEED_CONE, "请使用冰淇淋脆筒来接取冰淇淋");
        translationBuilder.add(IceCreamMakerBlockEntity.NEED_SELECT, "请在GUI界面中选择一种口味或剩余冰淇淋量不足");
        translationBuilder.add(CuisineTableBlock.CANT_OPEN, "料理台已被其他玩家占用，无法打开");
        translationBuilder.add(ModBlocks.CHARGING_POST, "充电桩");
        translationBuilder.add(ModBlocks.JUICE_EXTRACTOR, "榨汁机");
        translationBuilder.add(ModItems.SWEET_BERRIES_JUICE, "甜浆果汁");
        translationBuilder.add(ModItems.TOMATO_JUICE, "番茄汁");
        translationBuilder.add(ModBlocks.GLASS_CUP, "玻璃杯");
        translationBuilder.add(ModBlocks.CHERRY_MILK_TEA, "樱花奶茶");
        translationBuilder.add(ModBlocks.ROSE_ICE_TEA, "玫瑰冰茶");
        translationBuilder.add(ModItems.LIQUEFIED_BIOGAS_BUCKET, "液化沼气桶");
        translationBuilder.add(ModBlocks.WOODEN_PLATE, "木盘");
        translationBuilder.add(ModItems.FRIED_NOODLES, "油炸面饼");
        translationBuilder.add(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER, "多功能包装纸");
        translationBuilder.add(ModItems.QUICKLIME, "生石灰");
        translationBuilder.add(ModItems.PACKAGED_INSTANT_NOODLES, "包装方便面");
        translationBuilder.add(SpecialIngredient.BRAISED_BEEF_NOODLE_SOUP.toTranslationKey(), "红烧牛肉面");
        translationBuilder.add(SpecialIngredient.STEW_CHICKEN_NOODLE_WITH_MUSHROOM.toTranslationKey(), "香菇炖鸡面");
        translationBuilder.add(SpecialIngredient.TONKOTSU_RAMEN.toTranslationKey(), "豚骨拉面");
        translationBuilder.add(ModItems.PORTABLE_POT, "便携锅");
        translationBuilder.add(ModItems.COOKED_PORTABLE_POT, "煮熟的方便面");
        translationBuilder.add(ModItems.DIRTY_WRAPPING_PAPER, "脏包装纸");
        translationBuilder.add(ModItems.HONEY_CRYSTALLIZATION, "蜂蜜结晶");
        translationBuilder.add(ModItems.SEA_SALT, "海盐");
        translationBuilder.add(ModItems.GARLIC_PUREE, "蒜蓉");
        translationBuilder.add(ModItems.MATCHA, "抹茶");
        translationBuilder.add(ModItems.GRINDED_LEAVES, "研磨后的树叶");
        translationBuilder.add(ModItems.STONE_MORTAR, "石臼");
        translationBuilder.add(ModItems.MANGO_MILK_TEA, "芒果鲜奶茶");
        translationBuilder.add(ModItems.SEA_SALT_LEMON, "海盐柠檬");
        translationBuilder.add(ModItems.STEAMED_PUMPKIN_IN_BOWL, "碗装蒸南瓜");
        translationBuilder.add(ModBlocks.STEAMED_PUMPKIN, "蒸南瓜");
        translationBuilder.add(ModItems.STEAMED_PUMPKIN_WIP, "蒸南瓜（半成品）");
        translationBuilder.add(ModItems.CHOCOLATE_CRUNCH_ICE_LOLLY, "巧克力脆皮冰棍");
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN_WIP, "小笼包（半成品）");
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN, "小笼包");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN_WIP, "碧玉白菜包（半成品）");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN, "碧玉白菜包");
        translationBuilder.add(ModItems.SHAOMAI_WIP, "烧卖（半成品）");
        translationBuilder.add(ModItems.SHAOMAI, "烧卖");
        translationBuilder.add(ModItems.BLACK_PEPPER_STEAK, "黑椒牛排");
        translationBuilder.add(ModItems.HOLDER, "夹子");
        translationBuilder.add(ModItems.HOLDER_UP, "半截夹子");
        translationBuilder.add(ModItems.SWEETENED_WATER_BUCKET, "糖水桶");
        translationBuilder.add(ModItems.ALCOHOL_BUCKET, "酒精桶");
        translationBuilder.add(ModItems.VINEGAR_BUCKET, "醋桶");
        translationBuilder.add(ModItems.FERMENTATION_TANK, "发酵液储罐");
        translationBuilder.add(ModBlocks.TRAWLER, "拖网");

        translationBuilder.add(ModBlocks.INDUSTRIAL_FERMENTER, "工业发酵机");
        translationBuilder.add(BacteriaType.ACETOBACTER.getTranslationKey(), "醋酸菌");
        translationBuilder.add(BacteriaType.ESCHERICHIA.getTranslationKey(), "大肠杆菌");
        translationBuilder.add(BacteriaType.YEAST.getTranslationKey(), "酵母菌");
        translationBuilder.add(BacteriaType.MUCOR.getTranslationKey(), "毛霉");
        translationBuilder.add(BacteriaType.LACTOBACILLUS.getTranslationKey(), "乳酸菌");

        translationBuilder.add("death.attack.moderndelight_electroshock","%1$s 触电身亡！");
        translationBuilder.add("death.attack.moderndelight_turned_to_ashes","%1$s 化为了灰烬");
        translationBuilder.add("death.attack.moderndelight_turned_to_ashes.player","%1$s 化为了灰烬");
        translationBuilder.add("death.attack.moderndelight_scalded","%1$s 被烫死了");

        translationBuilder.add(ModBlocks.CREAM_FLUID_BLOCK, "奶油");
        translationBuilder.add(ModBlocks.VEGETABLE_OIL_FLUID_BLOCK, "植物油");
        translationBuilder.add(ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK, "液化沼气");
        translationBuilder.add(ModBlocks.SWEENTENED_WATER_FLUID_BLOCK, "糖水");
        translationBuilder.add(ModBlocks.ALCOHOL_FLUID_BLOCK, "酒精");
        translationBuilder.add(ModBlocks.VINEGAR_FLUID_BLOCK, "醋");
        translationBuilder.add(ModBlocks.MILK_FLUID_BLOCK, "奶 （发酵中）");
        translationBuilder.add(ModBlocks.SOY_MILK_FLUID_BLOCK, "豆浆");

        translationBuilder.add(ModEnchantments.FINE_GRINDING,"精碾");
        translationBuilder.add(ModEnchantments.FINE_GRINDING.getTranslationKey()+".desc","石臼在碾磨时会有额外的产出");

        translationBuilder.add(ModEffectsAndPotions.STICKY,"黏糊糊");
        translationBuilder.add(ModEffectsAndPotions.STICKY.getTranslationKey() + ".description","大幅度降低移动速度，同时无法跳跃。");

        translationBuilder.add("item.minecraft.potion.effect.sticky_potion","黏脚药水");
        translationBuilder.add("item.minecraft.potion.effect.sticky_long_potion","黏脚药水");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_potion","鱿鱼药水");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_long_potion","鱿鱼药水");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_strong_potion","鱿鱼药水");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_potion","发光鱿鱼药水");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_long_potion","发光鱿鱼药水");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_strong_potion","发光鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.sticky_potion","喷溅型黏脚药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.sticky_long_potion","喷溅型黏脚药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_potion","喷溅型鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_long_potion","喷溅型鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_strong_potion","喷溅型鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_potion","喷溅型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_long_potion","喷溅型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_strong_potion","喷溅型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.sticky_potion","滞留型黏脚药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.sticky_long_potion","滞留型黏脚药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_potion","滞留型鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_long_potion","滞留型鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_strong_potion","滞留型鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_potion","滞留型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_long_potion","滞留型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_strong_potion","滞留型发光鱿鱼药水");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.sticky_potion","黏脚之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.sticky_long_potion","黏脚之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_potion","鱿鱼之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_long_potion","鱿鱼之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_strong_potion","鱿鱼之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_potion","发光鱿鱼之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_long_potion","发光鱿鱼之箭");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_strong_potion","发光鱿鱼之箭");

        translationBuilder.add(ModItemGroups.GROUPS_TAB_NAME, "现代乐事");

        translationBuilder.add("emi.category.moderndelight.mix_with_water","与水混合");
        translationBuilder.add("emi.category.moderndelight.whisking","搅拌");
        translationBuilder.add("emi.category.moderndelight.assembly","电工台");
        translationBuilder.add("emi.category.moderndelight.stir_frying","炒菜");
        translationBuilder.add("emi.category.moderndelight.biogas_fermentation","沼气发酵");
        translationBuilder.add("emi.category.moderndelight.cuisine","烹饪");
        translationBuilder.add("emi.category.moderndelight.deep_frying","油炸");
        translationBuilder.add("emi.category.moderndelight.freezing","冷藏");
        translationBuilder.add("emi.category.moderndelight.baking","烘焙");
        translationBuilder.add("emi.category.moderndelight.pizza_making","披萨制作");
        translationBuilder.add("emi.category.moderndelight.steaming","蒸笼");
        translationBuilder.add("emi.category.moderndelight.steaming_electric","电蒸笼");
        translationBuilder.add("emi.category.moderndelight.advance_furnace_transforming","世界交互");
        translationBuilder.add("emi.category.moderndelight.oven_transforming","世界交互");
        translationBuilder.add("emi.category.moderndelight.oil_extraction","榨油");
        translationBuilder.add("emi.category.moderndelight.ice_cream_making","制作冰淇淋");
        translationBuilder.add("emi.category.moderndelight.juice_extracting","榨汁");
        translationBuilder.add("emi.category.moderndelight.instant_noodles_making","制作方便面");
        translationBuilder.add("emi.category.moderndelight.grinding","碾磨");
        translationBuilder.add("emi.category.moderndelight.fermenting","发酵");

        translationBuilder.add("config.jade.moderndelight.machine_details", "机器信息");
        translationBuilder.add("moderndelight.jade.tooltip.trawler.trawler_interval","剩余收获时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.has_sponge","已降噪");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.no_sponge","未降噪");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.working","运行中");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.not_working","待机");
        translationBuilder.add("moderndelight.jade.tooltip.ac_generator.power","当前交流电的发电量：%d EP/s");
        translationBuilder.add("moderndelight.jade.tooltip.juice_extractor.time","剩余榨汁时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.ac_device.powered","已通电");
        translationBuilder.add("moderndelight.jade.tooltip.ac_device.not_powered","未通电");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.temperature", "温度：%d °C");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.dominate", "当前发酵的主导菌：%s");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.progress", "剩余发酵时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.electric_steamer.steam", "蒸汽：%d / %d mB");
        translationBuilder.add("moderndelight.jade.tooltip.electric_steamer.active_slots", "正在工作的蒸制槽：%d");
        translationBuilder.add("moderndelight.jade.tooltip.ice_cream_maker.progress", "冰淇淋制作进度：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.ice_cream_maker.flavor", "已选口味：%s（%d mB）");
        translationBuilder.add("moderndelight.jade.tooltip.freezer.cooling_time", "剩余制冷时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.freezer.progress", "冷冻进度：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.oven.fuel_time", "剩余燃料时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.oven.progress", "剩余烘焙时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.advance_furnace.active_slots", "正在工作的烹饪槽：%d");
        translationBuilder.add("moderndelight.jade.tooltip.advance_furnace.fuel_time", "剩余燃料时间：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.heated", "加热器：%s");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.running", "已开启");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.stopped", "已关闭");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.active_slots", "正在工作的油炸槽：%d");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.stir_fry", "翻炒次数：%d / %d 次");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.heat_source", "热源：%s");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.heated", "正在加热");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.cold", "未加热");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.heat", "热度：%s");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.cover", "顶盖：%s");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.layer", "蒸笼层数：%d / %d");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.active_slots", "正在工作的蒸制槽：%d");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.heated", "已加热");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.cold", "未加热");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.covered", "已加盖");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.uncovered", "未加盖");
        translationBuilder.add("moderndelight.jade.tooltip.gas_canister.gas", "燃气已填充");
        translationBuilder.add("moderndelight.jade.tooltip.gas_canister.not_gas", "燃气未填充");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.gas", "沼气：%d / %d mB");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.volume", "发酵室容积：%d 格");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.structure", "结构：%s");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.valid", "有效");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.invalid", "无效");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_io.gas", "沼气：%d mB");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_io.progress", "发酵进度：%d 秒");
        translationBuilder.add("moderndelight.jade.tooltip.dc_generator.power", "当前直流电的发电量：%d EP/s");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode", "输出模式：%s");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode_ac", "交流电");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode_dc", "直流电");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.speed", "工作速度：%d / %d");
        translationBuilder.add("moderndelight.jade.tooltip.tesla_coil.efficiency", "当前负载：%d EP");
        translationBuilder.add("moderndelight.jade.tooltip.tesla_coil.devices", "激活的设备: %d");

        translationBuilder.add("sounds.moderndelight.entity_butter_hit", "黄油：击中");
        translationBuilder.add("sounds.moderndelight.entity_butter_shoot", "黄油：发射");
        translationBuilder.add("sounds.moderndelight.entity_cherry_bomb_explosion", "樱桃：爆炸");
        translationBuilder.add("sounds.moderndelight.entity_cherry_bomb_shoot", "樱桃：发射");
        translationBuilder.add("sounds.moderndelight.block_freezer_running", "冰箱：运行");
        translationBuilder.add("sounds.moderndelight.block_freezer_open", "冰箱：打开");
        translationBuilder.add("sounds.moderndelight.block_freezer_close", "冰箱：关闭");
        translationBuilder.add("sounds.moderndelight.block_glass_bowl_whisking", "物品: 被搅拌");
        translationBuilder.add("sounds.moderndelight.block_food_frying", "食物: 煎炸");
        translationBuilder.add("sounds.moderndelight.item_crowbar_hit", "撬棍: 击打");
        translationBuilder.add("sounds.moderndelight.item_crowbar_attack", "撬棍: 击中生物");
        translationBuilder.add("sounds.moderndelight.block_gas_canister_filling", "燃气罐: 填充");
        translationBuilder.add("sounds.moderndelight.block_gas_cooking_stove_ignite", "燃气灶：尝试点燃");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine", "斯特林引擎：运行");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine_start", "斯特林引擎：开始运行");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine_stop", "斯特林引擎：停止运行");
        translationBuilder.add("sounds.moderndelight.block_tesla_coil", "特斯拉线圈：电击");
        translationBuilder.add("sounds.moderndelight.item_electric_whisk_working", "电动搅拌器：工作");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_working", "榨汁机：运行");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_scream", "榨汁机：惨叫");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_stop", "榨汁机：停止运行");
        translationBuilder.add("sounds.moderndelight.item_stone_mortar_working", "石臼：打磨");

        translationBuilder.add(ModernDelightClient.ORE_UI_DARK, "Ore UI 风格资源包（深色）");
        translationBuilder.add(ModernDelightClient.ORE_UI_BRIGHT, "Ore UI 风格资源包（浅色）");

        translationBuilder.add("modmenu.nameTranslation.moderndelight","现代乐事");
        translationBuilder.add("modmenu.descriptionTranslation.moderndelight","让Minecraft拥有更丰富的食材处理方式。");
        translationBuilder.add("modmenu.summaryTranslation.moderndelight","更现代的食材处理方式");

        translationBuilder.add("config.moderndelight.title","现代乐事配置界面");
        translationBuilder.add("config.moderndelight.biogasSystem.title","沼气系统配置");
        translationBuilder.add("config.moderndelight.option.digestate","沼渣成分");
        translationBuilder.add("config.moderndelight.option.digestate.desc","修改沼气池输入输出接口所产生沼渣的成分。此处需要填写物品正确的的命名空间ID。");
        translationBuilder.add("config.moderndelight.powerSystem.title","能源系统配置");
        translationBuilder.add("config.moderndelight.option.acdcConverterMaxWorkSpeed","交流/直流转换器的最大工作速率");
        translationBuilder.add("config.moderndelight.option.acdcConverterMaxWorkSpeed.desc","修改交流/直流转换器的最大工作速率。");
        translationBuilder.add("config.moderndelight.option.energyGeneratedByFaradayGenerator","法拉第发电机的发电量");
        translationBuilder.add("config.moderndelight.option.energyGeneratedByFaradayGenerator.desc","修改法拉第发电机的发电量。");
        translationBuilder.add("config.moderndelight.option.photovoltaicGeneratorMultiplier","光伏发电机产生能源的乘数");
        translationBuilder.add("config.moderndelight.option.photovoltaicGeneratorMultiplier.desc","修改光伏发电机产生能源的乘数。");
        translationBuilder.add("config.moderndelight.option.windTurbineMultiplier","风力发电机产生能源的乘数");
        translationBuilder.add("config.moderndelight.option.windTurbineMultiplier.desc","修改风力发电机产生能源的乘数。");
        translationBuilder.add("config.moderndelight.option.teslaCoilConversionEfficiency","特斯拉线圈的电能转化效率");
        translationBuilder.add("config.moderndelight.option.teslaCoilConversionEfficiency.desc","修改特斯拉线圈的电能转化效率。");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterExplode","允许燃气罐爆炸");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterExplode.desc","当该选项启用时，若燃气罐旁边存在“危险的方块”时，它将有可能爆炸，此外燃气罐还会因充入过量的气体而发生爆炸。");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterInNether","允许燃气罐在下界使用");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterInNether.desc","当该选项启用时，燃气罐将被允许在下界正常使用而不会爆炸。");
        translationBuilder.add("config.moderndelight.option.gasCanisterVolume","燃气罐的容量");
        translationBuilder.add("config.moderndelight.option.gasCanisterVolume.desc","修改燃气罐的容量");
        translationBuilder.add("config.moderndelight.seasoning.title","调味系统配置");
        translationBuilder.add("config.moderndelight.option.maxSeasonings.title","最大调味品量");
        translationBuilder.add("config.moderndelight.option.maxSeasonings.desc","设置一个食物最多可以添加多少调味料。");
        translationBuilder.add("config.moderndelight.option.chargingPostEfficiency.title","充电桩充电效率");
        translationBuilder.add("config.moderndelight.option.chargingPostEfficiency.desc","设置充电桩的充电效率。");
        translationBuilder.add("config.moderndelight.trawler.title","拖网配置");
        translationBuilder.add("config.moderndelight.option.base_fishing_intervals.title","拖网的默认捕鱼时间（秒）");
        translationBuilder.add("config.moderndelight.option.base_fishing_intervals.desc","配置拖网的默认捕鱼时长。");

        translationBuilder.add(ModItems.GUIDE_BOOK,"现代乐事指南");
        translationBuilder.add("patchouli.moderndelight.book.name","现代乐事指南");
        translationBuilder.add("patchouli.moderndelight.book.landing_text","向你介绍：现代乐事。这本书将会告诉你如何更好的游玩这个模组。");

        translationBuilder.add(ModAdvancementGenerator.GET_WHISK_TITLE, "真相就是搅拌器");
        translationBuilder.add(ModAdvancementGenerator.GET_WHISK_DESC, "合成一把搅拌器来开始你新的厨房之旅");
        translationBuilder.add(ModAdvancementGenerator.GET_AMETHYST_TOOL_TITLE, "亮晶晶!");
        translationBuilder.add(ModAdvancementGenerator.GET_AMETHYST_TOOL_DESC, "合成一把紫水晶工具");
        translationBuilder.add(ModAdvancementGenerator.GET_CUTTLEBONE_TITLE, "比牛奶更好！");
        translationBuilder.add(ModAdvancementGenerator.GET_CUTTLEBONE_DESC, "获得鱿鱼骨");
        translationBuilder.add(ModAdvancementGenerator.GET_TRUFFLE_TITLE, "掘地三尺");
        translationBuilder.add(ModAdvancementGenerator.GET_TRUFFLE_DESC, "找到松露");
        translationBuilder.add(ModAdvancementGenerator.GET_CHERRY_BOMB_TITLE, "轰！");
        translationBuilder.add(ModAdvancementGenerator.GET_CHERRY_BOMB_DESC, "✧(≖ ◡ ≖✿)");
        translationBuilder.add(ModAdvancementGenerator.GET_GLASS_BOWL_TITLE, "轻拿轻放");
        translationBuilder.add(ModAdvancementGenerator.GET_GLASS_BOWL_DESC, "小心别把这碗弄碎了");
        translationBuilder.add(ModAdvancementGenerator.GET_MASHED_POTATO_TITLE, "粉嫩质感");
        translationBuilder.add(ModAdvancementGenerator.GET_MASHED_POTATO_DESC, "将马铃薯放入玻璃碗，再用搅拌器搅拌即可获得土豆泥");
        translationBuilder.add(ModAdvancementGenerator.GET_POTATO_STARCH_TITLE, "搅得稀碎");
        translationBuilder.add(ModAdvancementGenerator.GET_POTATO_STARCH_DESC, "将土豆泥放入玻璃碗，再用搅拌器搅拌即可获得马铃薯淀粉");
        translationBuilder.add(ModAdvancementGenerator.GET_FREEZER_TITLE, "冷静！");
        translationBuilder.add(ModAdvancementGenerator.GET_FREEZER_DESC, "是该让食材冷静一下了，这冰箱可以冷加工不少东西呢！");
        translationBuilder.add(ModAdvancementGenerator.GET_OVEN_TITLE, "“热”门话题");
        translationBuilder.add(ModAdvancementGenerator.GET_OVEN_DESC, "烤炉会帮你烘焙更棒的美食");
        translationBuilder.add(ModAdvancementGenerator.GET_EGG_TART_TITLE, "甜蜜蜜！");
        translationBuilder.add(ModAdvancementGenerator.GET_EGG_TART_DESC, "依次将奶油、混合面团、鸡蛋和糖放入烤炉来制作蛋挞");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_PUDDING_TITLE, "Q弹！");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_PUDDING_DESC, "收集所有的布丁");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_MOUSSE_TITLE, "好松软！");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_MOUSSE_DESC, "收集所有的慕斯");
        translationBuilder.add(ModAdvancementGenerator.GET_CREAM_BUCKET_TITLE, "黏糊糊的奶");
        translationBuilder.add(ModAdvancementGenerator.GET_CREAM_BUCKET_DESC, "将奶桶放入玻璃碗，再用搅拌器搅拌即可获得奶油桶");
        translationBuilder.add(ModAdvancementGenerator.GET_BUTTER_TITLE, "甜而粘口");
        translationBuilder.add(ModAdvancementGenerator.GET_BUTTER_DESC, "将奶油放入玻璃碗，再用搅拌器搅拌即可获得黄油");
        translationBuilder.add("advancement.moderndelight.get_start_desc", "一份简单的模组教程将在此展开。");
        translationBuilder.add("advancement.moderndelight.get_kneading_stick.title", "李奶奶的擀面杖");
        translationBuilder.add("advancement.moderndelight.get_kneading_stick.desc", "其实只是一根普通的木头棒子罢");
        translationBuilder.add("advancement.moderndelight.get_wheat_flour.title", "做更好的面包");
        translationBuilder.add("advancement.moderndelight.get_wheat_flour.desc", "搅拌小麦以获得小麦粉");
        translationBuilder.add("advancement.moderndelight.get_wheat_dough.title", "一大坨");
        translationBuilder.add("advancement.moderndelight.get_wheat_dough.desc", "将水倒入玻璃碗中，加入小麦粉，揉成面团！你可以用揉面棒将它擀平，也可以去烤面包");
        translationBuilder.add("advancement.moderndelight.get_raw_pizza.title", "准备开业！");
        translationBuilder.add("advancement.moderndelight.get_raw_pizza.desc", "首先，在擀平的面团中加入奶酪，然后加入5种你喜欢的食材，最后加入一个奶酪");
        translationBuilder.add("advancement.moderndelight.get_pizza.title", "披萨！");
        translationBuilder.add("advancement.moderndelight.get_pizza.desc", "按照黑胡椒粉、糖、黑胡椒粉、生披萨的顺序放入烤炉");
        translationBuilder.add("advancement.moderndelight.get_black_pepper.title", "热带品种");
        translationBuilder.add("advancement.moderndelight.get_black_pepper.desc", "在村民家中或热带雨林里发现新作物");
        translationBuilder.add("advancement.moderndelight.get_cheese.title", "一点都不酸");
        translationBuilder.add("advancement.moderndelight.get_cheese.desc", "将奶放入烤炉中去煮");
        translationBuilder.add("advancement.moderndelight.get_advance_furnace.title", "四倍速");
        translationBuilder.add("advancement.moderndelight.get_advance_furnace.desc", "合成一个高级熔炉，它比普通熔炉快四倍！");
        translationBuilder.add("advancement.moderndelight.get_baking_tray.title", "不只是一个“烤”盘");
        translationBuilder.add("advancement.moderndelight.get_baking_tray.desc", "它可以被放在高级熔炉里面来升级你的炉子");
        translationBuilder.add("advancement.moderndelight.get_crowbar.title", "统治物理学");
        translationBuilder.add("advancement.moderndelight.get_crowbar.desc", "一个超棒的工具好吗？既可以右键降级烤炉，还可以右键快速破坏门窗。记住千万不要拿他打人");
        translationBuilder.add("advancement.moderndelight.wooden_basin.title", "不是洗脸盆！");
        translationBuilder.add("advancement.moderndelight.wooden_basin.desc", "显然它是用来榨油的工具");
        translationBuilder.add("advancement.moderndelight.filter.title", "开始劳苦的一生");
        translationBuilder.add("advancement.moderndelight.filter.desc", "把过滤器和炒制的油料作物（例如葵花籽）放入木盆，并用脚踩木盆来榨油");
        translationBuilder.add("advancement.moderndelight.vegetable_oil.title", "第一桶油");
        translationBuilder.add("advancement.moderndelight.vegetable_oil.desc", "恭喜你你离油炸食物已经不远啦");
        translationBuilder.add("advancement.moderndelight.deep_fryer.title", "煎熬（对食物而言）");
        translationBuilder.add("advancement.moderndelight.deep_fryer.desc", "右键倒入油或存入或取出物品，潜行加右键可查看其状态。它必须在有油和有燃气罐连接的情况下才能工作");
        translationBuilder.add("advancement.moderndelight.deep_fry_basket.title", "当心热油烫手");
        translationBuilder.add("advancement.moderndelight.deep_fry_basket.desc", "记得用夹子来从油炸锅中取物，当心油锅杀人事件");
        translationBuilder.add("advancement.moderndelight.all_fried.title", "完全煎炸");
        translationBuilder.add("advancement.moderndelight.all_fried.desc", "品尝所有的油炸食品！");
        translationBuilder.add("advancement.moderndelight.wither_rose_cake.title", "绽放与终结");
        translationBuilder.add("advancement.moderndelight.wither_rose_cake.desc", "吃下凋灵玫瑰饼，然后好好思考你的人生。（隐藏进度）");
        translationBuilder.add("advancement.moderndelight.bdc_bdi.title", "沼气工业");
        translationBuilder.add("advancement.moderndelight.bdc_bdi.desc", "合成一个沼气池控制器和沼气池输入输出接口。记得看清它们的使用说明，然后建造你的沼气发酵池吧");
        translationBuilder.add("advancement.moderndelight.gas_canister.title", "再靠近一点就会爆炸！");
        translationBuilder.add("advancement.moderndelight.gas_canister.desc", "它可以从沼气池输入输出接口那里装载气体，也可以为燃气灶供气。不过记住一定要注意安全！");
        translationBuilder.add("advancement.moderndelight.gas_cooking_stove.title", "另起炉灶");
        translationBuilder.add("advancement.moderndelight.gas_cooking_stove.desc", "燃气灶可以加热很多东西，包括你自己");
        translationBuilder.add("advancement.moderndelight.spatula.title", "炒菜时间到！");
        translationBuilder.add("advancement.moderndelight.spatula.desc", "对一个烤盘用燃气灶加热，这时往它上面放入食材再拿锅铲翻炒即可炒菜");
        translationBuilder.add("advancement.moderndelight.kuh.title", "美化厨房");
        translationBuilder.add("advancement.moderndelight.kuh.desc", "合成一个厨具架");
        translationBuilder.add("advancement.moderndelight.cuisine_table.title", "分解一切");
        translationBuilder.add("advancement.moderndelight.cuisine_table.desc", "合成一个料理台, 上面的槽位放工具，下面的槽放物品，你能分解很多东西！");
        translationBuilder.add("advancement.moderndelight.electricians_desk.title", "向现代化出发");
        translationBuilder.add("advancement.moderndelight.electricians_desk.desc", "合成一个电工台, 它将会在今后伴你很长一段时间");
        translationBuilder.add("advancement.moderndelight.tesla_coil.title", "新的开始");
        translationBuilder.add("advancement.moderndelight.tesla_coil.desc", "合成一个特斯拉线圈，它可以让你摆脱电线，解决输电的烦恼");
        translationBuilder.add("advancement.moderndelight.wind.title", "好风凭借力，送我上青云");
        translationBuilder.add("advancement.moderndelight.wind.desc", "合成一个风力发电机叶片和一个风力发电机控制器，开始感受来自大自然的力量吧！");
        translationBuilder.add("advancement.moderndelight.bamboo_steamer.title", "水汽氤氲");
        translationBuilder.add("advancement.moderndelight.bamboo_steamer.desc", "准备好竹蒸笼屉，竹蒸笼盖，炼药锅来搭建一台传统蒸笼吧！");
        translationBuilder.add("advancement.moderndelight.electric_steamer.title", "更快更强！");
        translationBuilder.add("advancement.moderndelight.electric_steamer.desc", "电蒸笼可以让你摆脱建造复杂的传统蒸笼");
        translationBuilder.add("advancement.moderndelight.steamed_bun.title", "是妈妈的味道");
        translationBuilder.add("advancement.moderndelight.steamed_bun.desc", "蒸一个馒头");
        translationBuilder.add("advancement.moderndelight.silicon_ingot.title", "进阶材料");
        translationBuilder.add("advancement.moderndelight.silicon_ingot.desc", "用高炉冶炼石英以获取硅锭");
        translationBuilder.add("advancement.moderndelight.acdcc.title", "交流和直流");
        translationBuilder.add("advancement.moderndelight.acdcc.desc", "合成一个交流/直流转化器，它会有损耗的转换这两种电能。");
        translationBuilder.add("advancement.moderndelight.battery1.title", "储电入门");
        translationBuilder.add("advancement.moderndelight.battery1.desc", "制作一个简易电池，这是一切电力存储的开始");
        translationBuilder.add("advancement.moderndelight.thermal_power_generation.title", "工业革命");
        translationBuilder.add("advancement.moderndelight.thermal_power_generation.desc", "合成一个斯特林引擎和法拉第发电机，将斯特林引擎安装在任意工作中的炉子上，斯特林引擎上面的飞轮接上法拉第发电机，开始大规模产电吧！");
        translationBuilder.add("advancement.moderndelight.battery2.title", "进阶储电");
        translationBuilder.add("advancement.moderndelight.battery2.desc", "制作一个中级电池，你又可以储更多的电了");
        translationBuilder.add("advancement.moderndelight.battery3.title", "我需要更多的电！");
        translationBuilder.add("advancement.moderndelight.battery3.desc", "制作一个高级电池，它真的能存很多很多的电能！");
        translationBuilder.add("advancement.moderndelight.battery4.title", "还有高手？！");
        translationBuilder.add("advancement.moderndelight.battery4.desc", "制作一个维度电池，这下再也不用担心存电问题了");
        translationBuilder.add("advancement.moderndelight.pg.title", "环境友好");
        translationBuilder.add("advancement.moderndelight.pg.desc", "制作一个光伏发电机，便携又环保");
        translationBuilder.add("advancement.moderndelight.ice_cream_maker.title", "有谁能拒绝冰淇淋呢？");
        translationBuilder.add("advancement.moderndelight.ice_cream_maker.desc", "制作一个冰淇淋机，取出冰淇淋的那一刻，你绝对可以让你倍感兴奋！");
        translationBuilder.add("advancement.moderndelight.ice_cream.title", "完全胜利！");
        translationBuilder.add("advancement.moderndelight.ice_cream.desc", "目睹一个冰淇淋的制作！");
        translationBuilder.add("advancement.moderndelight.garlic.title", "吸血鬼的最恨");
        translationBuilder.add("advancement.moderndelight.garlic.desc", "在村民家或者森林中找到大蒜");
        translationBuilder.add("advancement.moderndelight.kebabs.title", "来一串");
        translationBuilder.add("advancement.moderndelight.kebabs.desc", "烤一串鱿鱼须，好吃又健康");
        translationBuilder.add("advancement.moderndelight.fish_and_chips.title", "最方便的外带食品");
        translationBuilder.add("advancement.moderndelight.fish_and_chips.desc", "制作一份炸鱼薯条，绝佳的美味");
        translationBuilder.add("advancement.moderndelight.all_ice_lolly.title", "冰棍专家");
        translationBuilder.add("advancement.moderndelight.all_ice_lolly.desc", "做出所有的冰棍");
        translationBuilder.add("advancement.moderndelight.charging_post.title", "最伟大的发明");
        translationBuilder.add("advancement.moderndelight.charging_post.desc", "合成一个充电桩，它可以为一些直流用电器充电！");
        translationBuilder.add("advancement.moderndelight.electric_whisk.title", "懒人厨房");
        translationBuilder.add("advancement.moderndelight.electric_whisk.desc", "普通的搅拌器搭配玻璃碗手动搅拌的速度太慢？试试电动搅拌器！它可以直接对地上的掉落物生效！");
        translationBuilder.add("advancement.moderndelight.stone_mortar.title", "碾碎它！");
        translationBuilder.add("advancement.moderndelight.stone_mortar.desc", "制作一个石臼，它能帮你碾碎一些东西。");
        translationBuilder.add("advancement.moderndelight.juice_extractor.title", "均匀混合");
        translationBuilder.add("advancement.moderndelight.juice_extractor.desc", "制作一个榨汁机，用它制作一些饮料吧！");
        translationBuilder.add("advancement.moderndelight.matcha.title", "一抹清香");
        translationBuilder.add("advancement.moderndelight.matcha.desc", "碾碎树叶以获得抹茶。");
        translationBuilder.add("advancement.moderndelight.fried_noodles.title", "方便面时刻");
        translationBuilder.add("advancement.moderndelight.fried_noodles.desc", "油炸一些面条，用蜜啤和纸合成包装袋，尝试制作一些方便面。");
        translationBuilder.add("advancement.moderndelight.cooked_portable_pot.title", "也许并不方便");
        translationBuilder.add("advancement.moderndelight.cooked_portable_pot.desc", "向便携锅中放入包装方便面，生石灰和水，快速的煮一些方便面吧。");
        translationBuilder.add("advancement.moderndelight.click_gas_cooking_stove.title", "燃气灶音乐家");
        translationBuilder.add("advancement.moderndelight.click_gas_cooking_stove.desc", "在 3 秒内快速右键燃气灶 10 次。（隐藏进度）");
    }
}

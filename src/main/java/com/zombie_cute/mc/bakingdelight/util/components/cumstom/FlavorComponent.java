package com.zombie_cute.mc.bakingdelight.util.components.cumstom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public record FlavorComponent(int id,int color, String name, int nutrition){
    public static final FlavorComponent NULL = new FlavorComponent(-1,0xffffff,"null", 0);
    public static final FlavorComponent PLAIN = new FlavorComponent(1,0xefece1,"plain", 2);
    public static final FlavorComponent APPLE = new FlavorComponent(2,0xf7d888,"apple", 3);
    public static final FlavorComponent CHERRY = new FlavorComponent(3,0xf97aa8,"cherry", 3);
    public static final FlavorComponent CHOCOLATE = new FlavorComponent(4,0x8b674a,"chocolate", 5);
    public static final FlavorComponent GOLDEN_APPLE = new FlavorComponent(5,0xf7ee36,"golden_apple", 6);
    public static final FlavorComponent MATCHA = new FlavorComponent(6,0x6da42e,"matcha", 3);
    public static final FlavorComponent PUMPKIN = new FlavorComponent(7,0xd09b39,"pumpkin", 4);
    public static final Codec<FlavorComponent> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.INT.fieldOf("id").forGetter(FlavorComponent::id),
                    Codec.INT.fieldOf("color").forGetter(FlavorComponent::color),
                    Codec.STRING.fieldOf("name").forGetter(FlavorComponent::name),
                    Codec.INT.fieldOf("nutrition").forGetter(FlavorComponent::nutrition)
            ).apply(builder, FlavorComponent::new));
    public static final String TRANSLATION_KEY = "bakingdelight.flavor";
    public boolean isNull(){
        return this.getID() < 0;
    }
    public int getID(){
        return id;
    }
    public int getNutrition() {
        return nutrition;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
    public static FlavorComponent getFlavorByID(int id){
        return switch (id){
            case 1 -> FlavorComponent.PLAIN;
            case 2 -> FlavorComponent.APPLE;
            case 3 -> FlavorComponent.CHERRY;
            case 4 -> FlavorComponent.CHOCOLATE;
            case 5 -> FlavorComponent.GOLDEN_APPLE;
            case 6 -> FlavorComponent.MATCHA;
            case 7 -> FlavorComponent.PUMPKIN;
            default -> FlavorComponent.NULL;
        };
    }
    public static Item getCream (FlavorComponent flavorComponent){
        return switch (flavorComponent.name){
            case "plain" -> ModItems.CREAM;
            case "apple" -> ModItems.APPLE_CREAM;
            case "cherry" -> ModItems.CHERRY_CREAM;
            case "chocolate" -> ModItems.CHOCOLATE_CREAM;
            case "golden_apple" -> ModItems.GOLDEN_APPLE_CREAM;
            case "matcha" -> ModItems.MATCHA_CREAM;
            case "pumpkin" -> ModItems.PUMPKIN_CREAM;
            default -> Items.AIR;
        };
    }
    public String getTranslationKey(){
        return "bakingdelight.flavor."+name;
    }
}

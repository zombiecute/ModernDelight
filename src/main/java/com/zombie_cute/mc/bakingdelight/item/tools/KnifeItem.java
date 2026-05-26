package com.zombie_cute.mc.bakingdelight.item.tools;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class KnifeItem extends ToolItem {

    public static final Set<RegistryKey<Enchantment>> ALLOWED_ENCHANTMENTS = Set.of(Enchantments.SHARPNESS,
            Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
            Enchantments.UNBREAKING, Enchantments.MENDING);
    public KnifeItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, new Settings().attributeModifiers(new AttributeModifiersComponent(List.of(
                new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + toolMaterial.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND),
                new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,  attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
        ),true)));
    }
    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        for (RegistryKey<Enchantment> key : ALLOWED_ENCHANTMENTS){
            if (enchantment.matchesKey(key))
                return true;
        }
        return super.canBeEnchantedWith(stack, enchantment, context);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner,EquipmentSlot.MAINHAND);
        }
        return true;
    }
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }
}

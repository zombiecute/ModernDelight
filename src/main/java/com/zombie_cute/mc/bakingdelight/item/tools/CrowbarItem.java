package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class CrowbarItem extends ToolItem {
    public static final Set<RegistryKey<Enchantment>> ALLOWED_ENCHANTMENTS = Set.of(Enchantments.SHARPNESS,
            Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
            Enchantments.UNBREAKING, Enchantments.MENDING);
    public CrowbarItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed) {
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.CROWBAR)));
        }else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        BlockPos blockPos = context.getBlockPos();
        Block target = world.getBlockState(blockPos).getBlock();
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            context.getStack().damage(1, player,player.getActiveHand()== Hand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);
        }
        for (RegistryEntry<Block> needStone : Registries.BLOCK.iterateEntries(BlockTags.NEEDS_IRON_TOOL)){
            if (needStone.value() == target){
                world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                        ModSounds.ITEM_CROWBAR_HIT,
                        SoundCategory.BLOCKS,
                        1.0f, world.random.nextFloat()+1.0f);
                return ActionResult.CONSUME;
            }
        }
        for (RegistryEntry<Block> needStone : Registries.BLOCK.iterateEntries(BlockTags.NEEDS_DIAMOND_TOOL)){
            if (needStone.value() == target){
                world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                        ModSounds.ITEM_CROWBAR_HIT,
                        SoundCategory.BLOCKS,
                        1.0f, world.random.nextFloat()+1.0f);
                return ActionResult.CONSUME;
            }
        }
        for (RegistryEntry<Block> registryEntry : Registries.BLOCK.iterateEntries(TagKeys.CROWBAR_DESTROYABLE)){
            if (registryEntry.value() == target){
                if (world.random.nextDouble() < 0.35){
                    world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                            ModSounds.ITEM_CROWBAR_HIT,
                            SoundCategory.BLOCKS,
                            0.3f, world.random.nextFloat()+2.0f);
                    world.breakBlock(blockPos, true, context.getPlayer());
                } else {
                    world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                            ModSounds.ITEM_CROWBAR_HIT,
                            SoundCategory.BLOCKS,
                            0.6f, world.random.nextFloat()+1.0f);
                }
                break;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(3, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner,EquipmentSlot.MAINHAND);
        }
        return true;
    }
}

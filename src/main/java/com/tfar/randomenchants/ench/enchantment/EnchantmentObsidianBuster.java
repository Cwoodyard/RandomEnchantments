package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.OBSIDIAN_BUSTER;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentObsidianBuster extends Enchantment {
    public EnchantmentObsidianBuster() {

        super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("obsidian_buster");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack){
        return tools.enableObsidianBuster != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableObsidianBuster == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return tools.enableObsidianBuster != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return tools.enableObsidianBuster == NORMAL;
    }

    @SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        PlayerEntity p = e.getEntityPlayer();
        BlockState state = e.getState();
        if (EnchantmentHelper.getMaxEnchantmentLevel(OBSIDIAN_BUSTER, p) > 0 && state.getBlock() == Blocks.OBSIDIAN) {
            float oldSpeed = e.getOriginalSpeed();
                e.setNewSpeed(oldSpeed + 100F);
        }
    }
}


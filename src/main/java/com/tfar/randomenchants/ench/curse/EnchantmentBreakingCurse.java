package com.tfar.randomenchants.ench.curse;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.curses;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.BREAKING;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentBreakingCurse extends Enchantment {
    public EnchantmentBreakingCurse() {

        super(Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("breaking");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    @Override
    public boolean canApply(ItemStack stack)
    {
        return curses.enableBreaking != DISABLED && stack.isDamageable() || super.canApply(stack);
    }

    @Override
    public boolean isCurse()
    {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return curses.enableBreaking == ANVIL;
    }

    @SubscribeEvent
public static void amplifyDamage(BlockEvent.BreakEvent e) {
        PlayerEntity p = e.getPlayer();
        ItemStack stack = p.getHeldItemMainhand();
        stack.damageItem(EnchantmentHelper.getEnchantmentLevel(BREAKING,stack),p, player -> player.sendBreakAnimation(p.getActiveHand()));
}
}


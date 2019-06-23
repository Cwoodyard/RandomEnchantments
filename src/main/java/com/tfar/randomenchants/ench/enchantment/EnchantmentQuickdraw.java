package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.QUICKDRAW;
import static net.minecraft.enchantment.EnchantmentHelper.*;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentQuickdraw extends Enchantment {
  public EnchantmentQuickdraw() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    setRegistryName("quickdraw");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableQuickdraw != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableQuickdraw == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableQuickdraw != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableQuickdraw == NORMAL;
  }

  @SubscribeEvent
  public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getEntity();
      ItemStack heldItem = player.getHeldItem(Hand.MAIN_HAND);
      if (!(heldItem.getItem() instanceof BowItem)) heldItem = player.getHeldItem(Hand.OFF_HAND);
      if (!(heldItem.getItem() instanceof BowItem) || getMaxEnchantmentLevel(QUICKDRAW, player) <= 0)
        return;
      if (player.isHandActive()) {
        for (int i = 0; i < getMaxEnchantmentLevel(QUICKDRAW, player); i++) {
          tickHeldBow(player);
        }
      }
    }
  }
  //borrowed from cyclic
  //player.updateActiveHand();//BUT its protected bahhhh
  private static void tickHeldBow(PlayerEntity player) {
    try {
      Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class,"func_184608_ct");
      m.invoke(player);
    }
    catch (Exception e) {
      RandomEnchants.logger.error("Reflection error ", e);
    }
  }
}



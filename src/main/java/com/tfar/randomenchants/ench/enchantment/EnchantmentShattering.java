package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.SHATTERING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentShattering extends Enchantment {
  public EnchantmentShattering() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("shattering");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 25;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableShattering != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableShattering == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableShattering != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableShattering == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    RayTraceResult result = event.getRayTraceResult();
    if (!(result instanceof BlockRayTraceResult))return;
    Entity arrow = event.getEntity();
    PlayerEntity player = (PlayerEntity)((AbstractArrowEntity)arrow).getShooter();
    if (player == null)return;
    if (!EnchantUtils.hasEnch(player.getHeldItemMainhand(),SHATTERING))return;
    BlockPos pos = ((BlockRayTraceResult) result).getPos();
    Block glass = arrow.world.getBlockState(pos).getBlock();
    if (!(glass instanceof GlassBlock))return;
    if (player.canHarvestBlock(arrow.world.getBlockState(pos)))
      arrow.world.destroyBlock(pos,true);
      event.setCanceled(true);
    }
  }



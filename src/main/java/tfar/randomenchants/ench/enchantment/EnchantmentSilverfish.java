package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentSilverfish extends Enchantment {
  public EnchantmentSilverfish() {

    super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("silverfish");
  }

  @Override
  public boolean isCurse() {
    return true;
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
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.disarm.get() == Config.Restriction.NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.disarm.get() == Config.Restriction.ANVIL;
  }

  public static void repair(PlayerEntity player, List<ItemStack> drops) {
    List<ItemStack> remove = new ArrayList<>();
    ItemStack tool = player.getHeldItemMainhand();
    for (ItemStack stack : drops) {
      int fuelvalue = (int)Math.ceil(ForgeHooks.getBurnTime(stack)/50d);
      if (fuelvalue == 0) continue;
      int toRepair = tool.getDamage();
      if (toRepair >= fuelvalue) {
        tool.setDamage(toRepair - fuelvalue);
        remove.add(stack);
      }
    }
    drops.removeAll(remove);
  }
}

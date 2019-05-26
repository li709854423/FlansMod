package com.li709.item;

import com.flansmod.common.FlansMod;
import com.li709.PotionConfine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * 医疗包
 * 去除减速效果
 * 回血
 * 去除流血效果
 */
public class ItemMedkit extends Item {

    public ItemMedkit(){
        setMaxStackSize(1);
        setHasSubtypes(false);
        setCreativeTab(FlansMod.tabFlanMechas);
        setRegistryName("medkit");
        setTranslationKey("medkit");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        playerIn.removePotionEffect(MobEffects.SLOWNESS);
        playerIn.removePotionEffect(PotionConfine.instance);
        //回20%的血量
        float v = playerIn.getMaxHealth() * 0.2f;
        playerIn.heal(v);

        //扣除一
        playerIn.getHeldItem(handIn).shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

    }
}

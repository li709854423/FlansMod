package com.li709.item;

import com.flansmod.common.FlansMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * 跌打药
 * 去除减速效果
 */
public class ItemDieDaYao extends Item {

    public ItemDieDaYao(){
        setMaxStackSize(1);
        setHasSubtypes(false);
        setCreativeTab(FlansMod.tabFlanMechas);
        setRegistryName("diedayao");
		setTranslationKey("diedayao");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        playerIn.removePotionEffect(MobEffects.SLOWNESS);
        //扣除一
        playerIn.getHeldItem(handIn).shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

    }
}

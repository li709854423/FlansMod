package com.li709.item;

import com.flansmod.common.FlansMod;
import com.li709.PotionConfine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * 创口贴
 */
public class ItemBandaid extends Item {

    public  ItemBandaid(){
        setMaxStackSize(1);
        setHasSubtypes(false);
        setCreativeTab(FlansMod.tabFlanMechas);
        setRegistryName("bandaid");
		setUnlocalizedName("bandaid");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		//删除出血效果
        playerIn.removePotionEffect(PotionConfine.instance);
        //扣除一
        playerIn.getHeldItem(handIn).shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

    }
}

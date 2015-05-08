package com.flansmod.common.teams;

import com.flansmod.common.CraftingInstance;
import com.flansmod.common.FlansMod;
import com.flansmod.common.teams.ArmourBoxType.ArmourBoxEntry;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockArmourBox extends Block 
{
	public ArmourBoxType type;
	
	public BlockArmourBox(ArmourBoxType t) 
	{
		super(Material.wood);
		type = t;

		setUnlocalizedName(type.shortName);
		setHardness(2F);
	    setResistance(4F);
	    GameRegistry.registerBlock(this, "armorBox." + type.shortName);
		setCreativeTab(FlansMod.tabFlanTeams);
		type.block = this;
		type.item = Item.getItemFromBlock(this);
	}
	
	public void buyArmour(String shortName, int piece, InventoryPlayer inventory)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			FlansMod.proxy.buyArmour(shortName, piece, type);
		} 
		ArmourBoxEntry entryPicked = null;
		for(ArmourBoxEntry page : type.pages)
		{
			if(page.shortName.equals(shortName))
				entryPicked = page;
		}
		
		ItemStack resultStack = new ItemStack(entryPicked.armours[piece].item);
		
		CraftingInstance crafting = new CraftingInstance(inventory, entryPicked.requiredStacks[piece], resultStack);
		if(crafting.canCraft())
		{
			crafting.craft(inventory.player);
		}
	}
	
	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{		
		if (type == null)
			return null;
		
		if (side == 1)
		{
			return type.top;
		}
		if (side == 0)
		{
			return type.bottom;
		}
		return type.side;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
		type.top = register.registerIcon("FlansMod:" + type.topTexturePath);
		type.side = register.registerIcon("FlansMod:" + type.sideTexturePath);
		type.bottom = register.registerIcon("FlansMod:" + type.bottomTexturePath);
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumFacing side, float par7, float par8, float par9)
	{
		if(entityplayer.isSneaking())
			return false;
		entityplayer.openGui(FlansMod.INSTANCE, 11, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}

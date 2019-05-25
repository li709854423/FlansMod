package com.li709;

import com.flansmod.common.FlansMod;
import com.li709.item.ItemBandaid;
import com.li709.item.ItemDieDaYao;
import com.li709.item.ItemMedkit;
import com.li709.listener.TickListener;
import com.li709.lootable.LootableBodies;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * 自定义flan初始化
 */
public class CustomInitFlan {

	public static String ID= FlansMod.MODID+"custom";

	public static Item bandaid;
	//跌打药
	public static Item diedayao;
	//医疗包
	public static Item medkit;


	public static void preInit(){
		//创口贴注册
		bandaid=new ItemBandaid();
		diedayao=new ItemDieDaYao();
		medkit=new ItemMedkit();
	}

	public static void init(FMLInitializationEvent event){
		//注册药水效果
		RegisterHolder.registerPotion();
		//每tick操作
		new TickListener();
		//初始化尸体
		LootableBodies.init(event);
	}
}

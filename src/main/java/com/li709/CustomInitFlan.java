package com.li709;

import com.li709.listener.TickListener;
import com.li709.lootable.LootableBodies;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * 自定义flan初始化
 */
public class CustomInitFlan {

	public static void init(FMLInitializationEvent event){
		//注册药水效果
		RegisterHolder.registerPotion();
		//每tick操作
		new TickListener();
		//初始化尸体
		LootableBodies.init(event);
	}
}

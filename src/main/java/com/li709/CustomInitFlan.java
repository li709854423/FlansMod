package com.li709;

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
	}
}

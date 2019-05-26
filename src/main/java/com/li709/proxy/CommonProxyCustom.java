package com.li709.proxy;

import com.li709.entity.EntityLootableBody;
import com.li709.listener.TickListener;
import com.li709.lootable.LootableBodies;
import com.li709.lootable.render.CorpseContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxyCustom {

	public static void preInit() {
		//初始化服务端尸体MOD
		LootableBodies.preInit(FMLCommonHandler.instance().getMinecraftServerInstance().getDataDirectory());
	}

	public static void init(){
		//每tick操作
		new TickListener();
	}

	public static Container getServerGui(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//初始化服务端尸体的GUI
		switch (ID) {
			case 14:
				Entity e = world.getEntityByID(x);
				if (e instanceof EntityLootableBody) return new CorpseContainer(player.inventory, (IInventory) e);
		}
		return null;
	}
}

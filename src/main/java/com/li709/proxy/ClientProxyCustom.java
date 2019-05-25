package com.li709.proxy;

import com.li709.entity.EntityLootableBody;
import com.li709.lootable.LootableBodies;
import com.li709.lootable.render.CorpseGUIContainer;
import com.li709.lootable.render.CorpseRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxyCustom {

	public static void preInit() {
		//加载 LootableBodies
		LootableBodies.preInit(Minecraft.getMinecraft().mcDataDir);
		//注册尸体
		RenderingRegistry.registerEntityRenderingHandler(EntityLootableBody.class, rm -> (new CorpseRenderer(rm)));
	}

	public static  Object getClientGui(int ID, EntityPlayer player, World world, int x, int y, int z){
		switch (ID){
			case 14:
				//打开尸体界面
				Entity entity = world.getEntityByID(x);
				if (entity instanceof EntityLootableBody)
					return new CorpseGUIContainer(player.inventory, (IInventory) entity);
		}
		return null;
	}
}

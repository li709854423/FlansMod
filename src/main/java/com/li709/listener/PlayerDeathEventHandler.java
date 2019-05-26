package com.li709.listener;

import com.flansmod.common.guns.EntityDamageSourceGun;
import com.li709.PotionConfine;
import com.li709.entity.EntityLootableBody;
import com.li709.lootable.LootableBodies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class PlayerDeathEventHandler {

	private static final Map<EntityPlayer, Map<ItemStack, EntityEquipmentSlot>> equipmentCache = new HashMap<>();


	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onEntityHurt(LivingAttackEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (event.getSource() instanceof EntityDamageSourceGun) {
			if (Math.random()<=0.1){
				entity.addPotionEffect(new PotionEffect(PotionConfine.instance,20*60*60));
			}
		}
	}

	/**
	 * 死亡时去除掉血效果
	 */
//    @Mod.EventHandler
	@SubscribeEvent
	public void PlayerDied(LivingDeathEvent event) {
		EntityLivingBase entityLiving = event.getEntityLiving();
		PotionEffect potionEffect = entityLiving.getActivePotionMap().get(PotionConfine.instance);
		if (potionEffect != null) {
			entityLiving.removePotionEffect(PotionConfine.instance);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void livingKnockBackEvent(LivingKnockBackEvent e) {

		if (e.getEntity() instanceof EntityLootableBody) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void entityDeathEvent(LivingDeathEvent e) {
		if (e.getEntity() instanceof EntityPlayer
				&& e.getResult() != Event.Result.DENY
				&& !e.getEntity().getEntityWorld().isRemote) {
			final EntityPlayer player = (EntityPlayer) e.getEntity();
			if (player.isSpectator()) return;
			Map<ItemStack, EntityEquipmentSlot> cache = equipmentCache.computeIfAbsent(player, (EntityPlayer p) -> new HashMap<>());
			for (EntityEquipmentSlot slot : EntityLootableBody.EQUIPMENT_SLOTS) {
				cache.put(player.getItemStackFromSlot(slot), slot);
			}

			if (player.getPrimaryHand() == EnumHandSide.LEFT) {
				// swap main and off hand items (easier than messing with the rendering code)
				cache.put(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), EntityEquipmentSlot.OFFHAND);
				cache.put(player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND), EntityEquipmentSlot.MAINHAND);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void entityDropEvent(LivingDropsEvent e) {
		if (e.getEntity() instanceof EntityPlayer
				&& e.getResult() != Event.Result.DENY
				&& !e.getEntity().getEntityWorld().isRemote) {
			final EntityPlayer player = (EntityPlayer) e.getEntity();
			if (player.isSpectator()) return;
			final World w = player.getEntityWorld();
			Map<ItemStack, EntityEquipmentSlot> cache = equipmentCache.computeIfAbsent(player, (EntityPlayer p) -> new HashMap<>());

			EntityLootableBody corpse = new EntityLootableBody(player);
			corpse.forceSpawn = true;
			corpse.setUserName(player.getName());
			corpse.setRotation(player.rotationYaw);

			NonNullList<ItemStack> items = NonNullList.create();
			for (EntityItem itemEntity : e.getDrops()) {
				ItemStack item = itemEntity.getItem();
				if (!item.isEmpty() && cache.containsKey(item)) {
					corpse.setItemStackToSlot(cache.get(item), item);
				} else {
					items.add(item);
				}
			}
			corpse.initializeItems(items);


			w.spawnEntity(corpse);
			w.updateEntityWithOptionalForce(corpse, false);

			e.getDrops().clear();

			equipmentCache.remove(e.getEntity());

		}
	}

	@SuppressWarnings("unused")
	private static void log(String s, Object... o) {
		FMLLog.info("%s: %s", LootableBodies.MODID, String.format(s, o));
	}

	@SuppressWarnings("unused")
	private static void log(Object o) {
		FMLLog.info("%s: %s", LootableBodies.MODID, String.valueOf(o));
	}
}

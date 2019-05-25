package com.flansmod.common.eventhandlers;

import com.flansmod.common.manage.BleedingManager;
import com.flansmod.common.potion.PotionConfine;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickListener {
    public TickListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * 显示自定义的title消息
     */
    @SubscribeEvent
    public void tickEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            BleedingManager.onUpdate();
        }
    }
    /**
     * 死亡时去除掉血效果
     */
    @Mod.EventHandler
    @SubscribeEvent
    public void PlayerDied(LivingDeathEvent event) {
        EntityLivingBase entityLiving = event.getEntityLiving();
        PotionEffect potionEffect = entityLiving.getActivePotionMap().get(PotionConfine.instance);
        if (potionEffect!=null){
            entityLiving.removePotionEffect(PotionConfine.instance);
        }
    }
}

package com.li709.listener;

import com.li709.manage.BleedingManager;
import net.minecraftforge.common.MinecraftForge;
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
}

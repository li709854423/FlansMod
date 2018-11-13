package com.flansmod.common.manage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.*;

/**
 * 出血管理
 */
public class BleedingManager {

    /**
     * 更新药水效果
     */
    private static Map<UUID,PotionEffect> map=new HashMap<>();

    private static int i=0;

    /**
     * 更新出血
     */
    public static void onUpdate(){
        i++;
        if (i==60){
            i=0;
            MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
            EntityLivingBase entityLivingBase;
            Double num;
            for (UUID uuid : map.keySet()) {
                Entity entity = minecraftServer.getEntityFromUuid(uuid);
                if (entity instanceof EntityLivingBase){
                    entityLivingBase= (EntityLivingBase) entity;
                    double maxValue = entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
                    num=maxValue*0.01>1?maxValue*0.01:1;
                    entityLivingBase.attackEntityFrom(DamageSource.FIREWORKS, num.floatValue());
                }
            }
        }
    }

    /**
     * 进入流血效果
     */
    public static void addBleeding(UUID uuid,PotionEffect potionEffect){
        map.put(uuid,potionEffect);
    }
    /**
     * 删除流血效果
     */
    public static void removeBleeding(UUID uuid){
        map.remove(uuid);
    }
}

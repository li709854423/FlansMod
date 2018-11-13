package com.flansmod.common.potion;

import com.flansmod.common.manage.BleedingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

/**
 * 流血
 * /effect @a flansmod:potion_confine
 */
public class PotionConfine extends Potion {
    public static ResourceLocation tex = new ResourceLocation("flansmod", "textures/potion/confine.png");
    public static PotionConfine instance;
    public PotionConfine() {
        super(false, 0xFFFAF0);
        setPotionName("流血");
        setRegistryName("flansmod", "potion_confine");
        instance=this;
    }
    /**
     * 添加药水时
     */
    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        PotionEffect potionEffect = entityLivingBaseIn.getActivePotionMap().get(this);
        System.out.println("添加uuid"+entityLivingBaseIn.getUniqueID());
        BleedingManager.addBleeding(entityLivingBaseIn.getUniqueID(),potionEffect);
    }

    /**
     * 删除药水时
     */
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        System.out.println("删除uuid"+entityLivingBaseIn.getUniqueID());
        BleedingManager.removeBleeding(entityLivingBaseIn.getUniqueID());
    }

    /**
     * 渲染在背包界面
     */
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(tex);
        GlStateManager.color(1f, 1f, 1.0f);
        Gui.drawScaledCustomSizeModalRect(x + 6, y + 6, 0, 0, 63, 63, 18, 19, 63, 63);
    }

    /**
     * 渲染在游戏界面
     */
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(tex);
        GlStateManager.color(1f, 1f, 1.0f, alpha);
        Gui.drawScaledCustomSizeModalRect(x + 1, y + 1, 0, 0, 63, 63, 9, 9, 63, 63);
    }
}
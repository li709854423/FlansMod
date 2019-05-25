package com.li709;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder("flansmod")
public class RegisterHolder {

    public static void registerPotion(){
        IForgeRegistry<Potion> registry = GameRegistry.findRegistry(Potion.class);
        registry.register(new PotionConfine());
    }

}

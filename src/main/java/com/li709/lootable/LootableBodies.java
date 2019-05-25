package com.li709.lootable;


import com.flansmod.common.FlansMod;
import com.li709.entity.EntityLootableBody;
import com.li709.listener.PlayerDeathEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.io.File;

public class LootableBodies {
    public static final String MODID = FlansMod.MODID+"custom";

    public static boolean displayNameTag = true;
    public static long ticksPerItemDecay = 10 * 60 * 20; // -1 to disable
    public static boolean hurtByEnvironment = false;
    public static boolean hurtByAttacks = false;
    public static boolean completelyInvulnerable = false;
    public static float corpseHP = 20;

    public static boolean allowCorpseDecay = true;
    public static boolean decayOnlyWhenEmpty = false;
    public static long corpseDecayTime = 3600 * 20; // in game ticks

    private static File lootableConfig;


    // Mark this method for receiving an FMLEvent (in this case, it's the FMLPreInitializationEvent)
    public static void preInit(File file) {
        // Do stuff in pre-init phase (read config, create blocks and items, register them)
        // load config
        lootableConfig = new File(file, "/config/lootableBodies.cfg");
        Configuration config = new Configuration(lootableConfig);
        config.load();

        corpseHP = config.getFloat("corpse_HP", "options", corpseHP, 1, Short.MAX_VALUE,
                "The amount of damage a corpse can suffer before being \n"
                        + "destroyed and releasing its items. \n"
                        + "Note that 10 hearts = 20 HP.");
        displayNameTag = config.getBoolean("display_nametag", "options", displayNameTag,
                "If true, corpses will show their owner's name");

        hurtByEnvironment = config.getBoolean("hurt_by_environment", "corpse damage", hurtByEnvironment,
                "If true, corpses will be damaged by fire, lava, falling, and other such hazards.");
        hurtByAttacks = config.getBoolean("hurt_by_weapons", "corpse damage", hurtByAttacks,
                "If true, corpses can be damaged by attacking them.");


        completelyInvulnerable = !or(hurtByEnvironment, hurtByAttacks);

        ticksPerItemDecay = (int) (60 * 20 * config.getFloat("item_decay_rate", "options", 0, -1F, 1e9F,
                "All damageable items on the corpse will suffer 1 durability damage \n"
                        + "for every X number of minutes (default is 5 minutes) that they are. \n"
                        + "Items damaged in this way will never be completely destroyed. \n"
                        + "Set to 0 (or negative) to disable."));

        allowCorpseDecay = config.getBoolean("enable_corpse_decay", "corpse decay", allowCorpseDecay,
                "If true, corpses will self-destruct after a period of time.");
        decayOnlyWhenEmpty = config.getBoolean("empty_only_decay", "corpse decay", decayOnlyWhenEmpty,
                "If true and enable_corpse_decay is also true, corpses will \n"
                        + "self-destruct after being empty for a period of time (will \n"
                        + "not decay if there are any items on the corpse). If using this \n"
                        + "option, you will probably want to also disable the \n"
                        + "add_bones_to_corpse option.");
        String decayTime = config.getString("corpse_decay_time", "corpse decay", "00:05:00",
                "Time after death before a corpse will self-destruct (if the \n"
                        + "enable_corpse_decay option is set to true). \n"
                        + "The format is hours:minutes:seconds or just hours:minutes");
        corpseDecayTime = Math.max(parseTimeInSeconds(decayTime), 2) * 20; // 2 second minimum

        config.save();
    }


    private static int parseTimeInSeconds(String time) {
        String[] component = time.split(":");
        int hr = 0, min = 0, sec = 0;
        if (component.length > 0) hr = Integer.parseInt(component[0].trim());
        if (component.length > 1) min = Integer.parseInt(component[1].trim());
        if (component.length > 2) sec = Integer.parseInt(component[2].trim());
        return 3600 * hr + 60 * min + sec;
    }


    public static void init(FMLInitializationEvent event) {

        registerEntity(EntityLootableBody.class);
        MinecraftForge.EVENT_BUS.register(new PlayerDeathEventHandler());
//		NetworkRegistry.INSTANCE.registerGuiHandler(FlansMod.INSTANCE, GUIHandler.getInstance());
    }

    private static void registerEntity(Class<? extends Entity> entityClass) {
        String idName = "Corpse";
        EntityRegistry.registerModEntity(new ResourceLocation(FlansMod.MODID, idName), entityClass, idName, 200/*mod-specific entity id*/, FlansMod.INSTANCE, 32/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);

    }

    private static boolean or(boolean... bools) {
        for (int i = 0; i < bools.length; i++) {
            if (bools[i] == true) return true;
        }
        return false;
    }

    private static boolean and(boolean... bools) {
        for (int i = 0; i < bools.length; i++) {
            if (bools[i] == false) return false;
        }
        return true;
    }
}

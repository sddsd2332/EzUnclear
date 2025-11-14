package me.kall.ezunclear;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = EzUnclear.MOD_ID, useMetadata = true)
public class EzUnclear {

    public static final String MOD_ID = Tags.MOD_ID;

    @Mod.Instance(EzUnclear.MOD_ID)
    public static EzUnclear instance;

    public static final String LOG_TAG = "[" + MOD_ID + "]";
    public static Logger logger = LogManager.getLogger(MOD_ID);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}


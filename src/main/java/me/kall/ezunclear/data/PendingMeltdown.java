package me.kall.ezunclear.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.kall.ezunclear.EzUnclear;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EzUnclear.MOD_ID)
public class PendingMeltdown {
    public static final List<Runnable> MELT_DOWNS = new ObjectArrayList<>();
    public static List<BlockPos> posList = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(PendingMeltdown.class);

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        if (event.getMessage().equals(I18n.translateToLocal("info.ezunclear.interact"))) {
            synchronized (MELT_DOWNS) {
                MELT_DOWNS.forEach(task -> {
                    try {
                        task.run();

                    } catch (Exception exception) {
                        LOGGER.error("Error running meltdown task", exception);
                    }
                });
                MELT_DOWNS.clear();
                posList.clear();
            }
        }
    }
}


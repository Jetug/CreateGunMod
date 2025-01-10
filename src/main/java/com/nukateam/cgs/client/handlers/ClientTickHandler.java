package com.nukateam.cgs.client.handlers;

import com.nukateam.geo.render.ItemAnimator;
import com.nukateam.cgs.Gunsmithing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, value = Dist.CLIENT)
public class ClientTickHandler {
    private static final Map<ItemAnimator, Runnable> tickingAnimators = new HashMap<>();

    public static void addTicker(ItemAnimator animator, Runnable onTick){
        tickingAnimators.put(animator, onTick);
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            tickingAnimators.forEach((k, v) -> {
                v.run();
            });
        }
    }
}

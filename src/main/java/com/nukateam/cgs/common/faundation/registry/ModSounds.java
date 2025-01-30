package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.Ntgl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Gunsmithing.MOD_ID);

    public static final RegistryObject<SoundEvent> GATLING_FIRE = register("gun.gatling.fire");
    public static final RegistryObject<SoundEvent> GATLING_MAG_IN = register("gun.gatling.reload_mag_in");
    public static final RegistryObject<SoundEvent> GATLING_MAG_OUT = register("gun.gatling.reload_mag_out");

    public static final RegistryObject<SoundEvent> REVOLVER_FIRE = register("gun.revolver.fire");

    private static RegistryObject<SoundEvent> register(String key) {
        return REGISTER.register(key, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Gunsmithing.MOD_ID, key)));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

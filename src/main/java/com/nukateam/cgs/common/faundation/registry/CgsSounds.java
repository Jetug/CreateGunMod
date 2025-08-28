package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CgsSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Gunsmithing.MOD_ID);

    public static final RegistryObject<SoundEvent> GATLING_FIRE     = register("gun.gatling.fire");
    public static final RegistryObject<SoundEvent> GATLING_MAG_IN   = register("gun.gatling.reload_mag_in");
    public static final RegistryObject<SoundEvent> GATLING_MAG_OUT  = register("gun.gatling.reload_mag_out");

    public static final RegistryObject<SoundEvent> REVOLVER_FIRE            = register("gun.revolver.fire"         );
    public static final RegistryObject<SoundEvent> REVOLVER_CHAMBER_OPEN    = register("gun.revolver.chamber_open" );
    public static final RegistryObject<SoundEvent> REVOLVER_CHAMBER_CLOSE   = register("gun.revolver.chamber_close");
    public static final RegistryObject<SoundEvent> REVOLVER_ROUNDS_IN       = register("gun.revolver.rounds_in"    );
    public static final RegistryObject<SoundEvent> REVOLVER_ROUNDS_OUT      = register("gun.revolver.rounds_out"   );

    public static final RegistryObject<SoundEvent> SHOTGUN_FIRE     = register("gun.shotgun.fire"    );
    public static final RegistryObject<SoundEvent> SHOTGUN_ROUND_IN = register("gun.shotgun.round_in");
    public static final RegistryObject<SoundEvent> SHOTGUN_CLOSE    = register("gun.shotgun.close"   );
    public static final RegistryObject<SoundEvent> SHOTGUN_OPEN     = register("gun.shotgun.open"    );
    public static final RegistryObject<SoundEvent> SHOTGUN_LEVEL    = register("gun.shotgun.lever"   );

    public static final RegistryObject<SoundEvent> NAILGUN_FIRE        = register("gun.nailgun.fire"       );
    public static final RegistryObject<SoundEvent> NAILGUN_FIRE_STEAM  = register("gun.nailgun.fire_steam");
    public static final RegistryObject<SoundEvent> NAILGUN_MAG_IN      = register("gun.nailgun.mag_in"     );
    public static final RegistryObject<SoundEvent> NAILGUN_MAG_OUT     = register("gun.nailgun.mag_out"    );

    public static final RegistryObject<SoundEvent> FLINTLOCK_FIRE    = register("gun.flintlock.fire"    );
    public static final RegistryObject<SoundEvent> FLINTLOCK_AMMO_IN = register("gun.flintlock.ammo_in" );
    public static final RegistryObject<SoundEvent> FLINTLOCK_COCK    = register("gun.flintlock.cock"    );
    public static final RegistryObject<SoundEvent> FLINTLOCK_RAMROD  = register("gun.flintlock.ramrod"  );

    public static final RegistryObject<SoundEvent> LAUNCHER_FIRE  = register("gun.launcher.fire"  );
    public static final RegistryObject<SoundEvent> BALLISTA_FIRE  = register("gun.launcher.ballista_fire"  );
    public static final RegistryObject<SoundEvent> LAUNCHER_BOLT_OPEN = register("gun.launcher.boltopen");
    public static final RegistryObject<SoundEvent> LAUNCHER_EQUIP = register("gun.launcher.equip");
    public static final RegistryObject<SoundEvent> LAUNCHER_MAG_IN = register("gun.launcher.magin");
    public static final RegistryObject<SoundEvent> LAUNCHER_MAG_IN_MINI = register("gun.launcher.magin_mini");
    public static final RegistryObject<SoundEvent> LAUNCHER_MAG_OUT = register("gun.launcher.magout");
    public static final RegistryObject<SoundEvent> LAUNCHER_MAG_OUT_MINI = register("gun.launcher.magout_mini");

    public static final RegistryObject<SoundEvent> GRENADE_PIN = register("gun.grenade.pin");
    public static final RegistryObject<SoundEvent> GRENADE_THROW = register("gun.grenade.throw");

    public static final RegistryObject<SoundEvent> HAMMER_BOLT_CLOSE = register("gun.hammer.boltclose");
    public static final RegistryObject<SoundEvent> HAMMER_BOLT_OPEN = register("gun.hammer.boltopen");
    public static final RegistryObject<SoundEvent> HAMMER_MAG_IN = register("gun.hammer.magin");
    public static final RegistryObject<SoundEvent> HAMMER_MAG_OUT = register("gun.hammer.magout");
    public static final RegistryObject<SoundEvent> HAMMER_RELOAD = register("gun.hammer.reload");
    public static final RegistryObject<SoundEvent> HAMMER_SHOT = register("gun.hammer.shot");
    public static final RegistryObject<SoundEvent> HAMMER_EQUIP = register("gun.hammer.equip");

    private static RegistryObject<SoundEvent> register(String key) {
        return REGISTER.register(key, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Gunsmithing.MOD_ID, key)));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

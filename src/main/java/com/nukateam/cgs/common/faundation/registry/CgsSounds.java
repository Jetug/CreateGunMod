package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CgsSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, Gunsmithing.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> GATLING_FIRE     = register("gun.gatling.fire");
    public static final DeferredHolder<SoundEvent, SoundEvent> GATLING_MAG_IN   = register("gun.gatling.reload_mag_in");
    public static final DeferredHolder<SoundEvent, SoundEvent> GATLING_MAG_OUT  = register("gun.gatling.reload_mag_out");

    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_FIRE            = register("gun.revolver.fire"         );
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_CHAMBER_OPEN    = register("gun.revolver.chamber_open" );
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_CHAMBER_CLOSE   = register("gun.revolver.chamber_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_ROUNDS_IN       = register("gun.revolver.rounds_in"    );
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_ROUNDS_OUT      = register("gun.revolver.rounds_out"   );

    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_FIRE     = register("gun.shotgun.fire"    );
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_ROUND_IN = register("gun.shotgun.round_in");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_CLOSE    = register("gun.shotgun.close"   );
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_OPEN     = register("gun.shotgun.open"    );
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_LEVEL    = register("gun.shotgun.lever"   );

    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_FIRE        = register("gun.nailgun.fire"       );
    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_FIRE_STEAM  = register("gun.nailgun.fire_steam");
    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_MAG_IN      = register("gun.nailgun.mag_in"     );
    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_MAG_OUT     = register("gun.nailgun.mag_out"    );

    public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_FIRE    = register("gun.flintlock.fire"    );
    public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_AMMO_IN = register("gun.flintlock.ammo_in" );
    public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_COCK    = register("gun.flintlock.cock"    );
    public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_RAMROD  = register("gun.flintlock.ramrod"  );
    public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_RELOAD  = register("gun.flintlock.reload"  );

    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_FIRE  = register("gun.launcher.fire"  );
    public static final DeferredHolder<SoundEvent, SoundEvent> BALLISTA_FIRE  = register("gun.launcher.ballista_fire"  );
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_BOLT_OPEN = register("gun.launcher.boltopen");
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_EQUIP = register("gun.launcher.equip");
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_MAG_IN = register("gun.launcher.magin");
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_MAG_IN_MINI = register("gun.launcher.magin_mini");
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_MAG_OUT = register("gun.launcher.magout");
    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_MAG_OUT_MINI = register("gun.launcher.magout_mini");

    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_PIN = register("gun.grenade.pin");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_THROW = register("gun.grenade.throw");

    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_BOLT_CLOSE = register("gun.hammer.boltclose");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_BOLT_OPEN = register("gun.hammer.boltopen");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_MAG_IN = register("gun.hammer.magin");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_MAG_OUT = register("gun.hammer.magout");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_RELOAD = register("gun.hammer.reload");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_SHOT = register("gun.hammer.shot");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_EQUIP = register("gun.hammer.equip");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String key) {
        return REGISTER.register(key, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Gunsmithing.MOD_ID, key)));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}

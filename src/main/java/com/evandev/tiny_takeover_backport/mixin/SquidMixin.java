package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import com.evandev.tiny_takeover_backport.entity.ModEntityData;
import com.evandev.tiny_takeover_backport.entity.ModifiableBaby;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Squid.class)
public abstract class SquidMixin extends WaterAnimal implements ModifiableBaby {

    @Unique
    private static final EntityDataAccessor<Boolean> tiny_takeover_backport$BABY_ID =
            ModEntityData.SQUID_BABY_ID = SynchedEntityData.defineId(Squid.class, EntityDataSerializers.BOOLEAN);

    protected SquidMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(ModEntityData.SQUID_BABY_ID);
    }

    @Unique
    @Override
    public void tiny_takeover_backport$setBaby(boolean baby) {
        this.entityData.set(ModEntityData.SQUID_BABY_ID, baby);
        if (baby) {
            ((AgeLockable) this).tiny_takeover_backport$setCustomAge(-24000);
        } else {
            ((AgeLockable) this).tiny_takeover_backport$setCustomAge(0);
        }
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (ModEntityData.SQUID_BABY_ID.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }
}

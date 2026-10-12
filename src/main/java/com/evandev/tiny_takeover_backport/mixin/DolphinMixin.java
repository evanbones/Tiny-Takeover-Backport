package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.ModEntityData;
import com.evandev.tiny_takeover_backport.entity.ModifiableBaby;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundEvents;
import com.evandev.tiny_takeover_backport.entity.AgeLockable;

@Mixin(Dolphin.class)
public abstract class DolphinMixin extends WaterAnimal implements ModifiableBaby {

    @Unique
    private static final EntityDataAccessor<Boolean> tiny_takeover_backport$BABY_ID =
            ModEntityData.DOLPHIN_BABY_ID = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);

    protected DolphinMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(ModEntityData.DOLPHIN_BABY_ID);
    }

    @Unique
    @Override
    public void tiny_takeover_backport$setBaby(boolean baby) {
        this.entityData.set(ModEntityData.DOLPHIN_BABY_ID, baby);
        if (baby) {
            ((AgeLockable) this).tiny_takeover_backport$setCustomAge(-24000);
        } else {
            ((AgeLockable) this).tiny_takeover_backport$setCustomAge(0);
        }
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (ModEntityData.DOLPHIN_BABY_ID.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void tiny_takeover_backport$addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("IsBaby", this.isBaby());
        compound.putInt("Age", ((AgeLockable) this).tiny_takeover_backport$getCustomAge());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void tiny_takeover_backport$readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.tiny_takeover_backport$setBaby(compound.getBoolean("IsBaby"));
        if (compound.contains("Age")) {
            ((AgeLockable) this).tiny_takeover_backport$setCustomAge(compound.getInt("Age"));
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tiny_takeover_backport$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Dolphin dolphin = (Dolphin) (Object) this;
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.is(ItemTags.FISHES) && this.isBaby()) {
            if (((AgeLockable) this).tiny_takeover_backport$isAgeLocked()) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }

            if (!dolphin.level().isClientSide()) {
                this.playSound(SoundEvents.DOLPHIN_EAT, 1.0F, 1.0F);
                dolphin.level().broadcastEntityEvent(dolphin, (byte) 38);
                int age = ((AgeLockable) this).tiny_takeover_backport$getCustomAge();
                int ageReduction = (int)((float)(-age) * 0.1F);
                ((AgeLockable) this).tiny_takeover_backport$setCustomAge(age + ageReduction);
                if (((AgeLockable) this).tiny_takeover_backport$getCustomAge() >= 0) {
                    this.tiny_takeover_backport$setBaby(false);
                }
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            cir.setReturnValue(InteractionResult.sidedSuccess(dolphin.level().isClientSide()));
        }
    }
}
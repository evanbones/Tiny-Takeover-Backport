package com.evandev.tiny_takeover_backport.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dolphin.class)
public abstract class DolphinMixin extends WaterAnimal {

    @Unique
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);

    protected DolphinMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_BABY_ID, false);
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(DATA_BABY_ID);
    }

    @Unique
    public void setBaby(boolean baby) {
        this.entityData.set(DATA_BABY_ID, baby);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Dolphin dolphin = (Dolphin) (Object) this;
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() instanceof SpawnEggItem egg && egg.spawnsEntity(itemstack, dolphin.getType())) {
            if (!dolphin.level().isClientSide) {
                Dolphin babyDolphin = EntityType.DOLPHIN.create(dolphin.level());
                if (babyDolphin != null) {
                    ((DolphinMixin) (Object) babyDolphin).setBaby(true);
                    babyDolphin.moveTo(dolphin.getX(), dolphin.getY(), dolphin.getZ(), 0.0F, 0.0F);
                    dolphin.level().addFreshEntity(babyDolphin);
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            }
            cir.setReturnValue(InteractionResult.sidedSuccess(dolphin.level().isClientSide));
        }
    }
}
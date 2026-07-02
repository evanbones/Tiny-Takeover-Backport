package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
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
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dolphin.class)
public abstract class DolphinMixin extends WaterAnimal implements ModifiableBaby {

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
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void tiny_takeover_backport$addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("IsBaby", this.isBaby());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void tiny_takeover_backport$readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.tiny_takeover_backport$setBaby(compound.getBoolean("IsBaby"));
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tiny_takeover_backport$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!ModConfig.get().spawnBabyDolphin) return;
        Dolphin dolphin = (Dolphin) (Object) this;
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() instanceof SpawnEggItem egg && egg.spawnsEntity(itemstack.getTag(), dolphin.getType())) {
            if (!dolphin.level().isClientSide()) {
                Dolphin babyDolphin = EntityType.DOLPHIN.create(dolphin.level());
                if (babyDolphin != null) {
                    ((ModifiableBaby) babyDolphin).tiny_takeover_backport$setBaby(true);
                    babyDolphin.moveTo(dolphin.getX(), dolphin.getY(), dolphin.getZ(), 0.0F, 0.0F);
                    dolphin.level().addFreshEntity(babyDolphin);
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            }
            cir.setReturnValue(InteractionResult.sidedSuccess(dolphin.level().isClientSide()));
        }
    }
}
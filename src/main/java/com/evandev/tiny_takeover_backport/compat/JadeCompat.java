package com.evandev.tiny_takeover_backport.compat;

import com.evandev.tiny_takeover_backport.Constants;
import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Squid;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.TooltipPosition;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

//? if >=1.21 {
import snownee.jade.api.JadeIds;
//?} else
//import snownee.jade.api.Identifiers;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
    //? if >=1.21 {
    private static final ResourceLocation MOB_GROWTH = JadeIds.MC_MOB_GROWTH;
    //?} else
    //private static final ResourceLocation MOB_GROWTH = Identifiers.MC_MOB_GROWTH;

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(PausedGrowthProvider.INSTANCE, AgeableMob.class);
        registration.registerEntityComponent(PausedGrowthProvider.INSTANCE, Dolphin.class);
        registration.registerEntityComponent(PausedGrowthProvider.INSTANCE, Squid.class);
    }

    public enum PausedGrowthProvider implements IEntityComponentProvider {
        INSTANCE;

        private static final ResourceLocation UID = Constants.location(Constants.MOD_ID, "paused_growth");

        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
            Entity entity = accessor.getEntity();
            if (!config.get(MOB_GROWTH) || !(entity instanceof LivingEntity living) || !living.isBaby()
                    || !(entity instanceof AgeLockable lockable) || !lockable.tiny_takeover_backport$isAgeLocked()) {
                return;
            }
            tooltip.remove(MOB_GROWTH);
            tooltip.add(Component.translatable("jade.mobgrowth.time",
                    IThemeHelper.get().info(Component.translatable("tiny_takeover_backport.jade.mobgrowth.paused"))));
        }

        @Override
        public ResourceLocation getUid() {
            return UID;
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.BODY + 1;
        }

        @Override
        public boolean isRequired() {
            return true;
        }
    }
}

package com.evandev.tiny_takeover_backport.registry;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModRegistry {
    public static final Map<ResourceLocation, Block> BLOCKS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, SimpleParticleType> PARTICLES = new LinkedHashMap<>();

    // Blocks
    public static final Block GOLDEN_DANDELION = registerBlock("golden_dandelion",
            new FlowerBlock(MobEffects.SATURATION, 7, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
            )
    );


    public static final Block POTTED_GOLDEN_DANDELION = registerBlock("potted_golden_dandelion",
            new FlowerPotBlock(GOLDEN_DANDELION, BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)
            )
    );

    // Items
    public static final Item GOLDEN_DANDELION_ITEM = registerItem("golden_dandelion",
            new BlockItem(GOLDEN_DANDELION, new Item.Properties())
    );

    // Sound Events
    public static final SoundEvent GOLDEN_DANDELION_USE = registerSound("item.golden_dandelion.use");
    public static final SoundEvent GOLDEN_DANDELION_UNUSE = registerSound("item.golden_dandelion.unuse");

    // Baby Sound Events
    public static final SoundEvent CHICKEN_AMBIENT_BABY = registerSound("entity.baby_chicken.ambient");
    public static final SoundEvent CHICKEN_HURT_BABY = registerSound("entity.baby_chicken.hurt");
    public static final SoundEvent CHICKEN_DEATH_BABY = registerSound("entity.baby_chicken.death");
    public static final SoundEvent CHICKEN_STEP_BABY = registerSound("entity.baby_chicken.step");

    public static final SoundEvent PIG_AMBIENT_BABY = registerSound("entity.baby_pig.ambient");
    public static final SoundEvent PIG_HURT_BABY = registerSound("entity.baby_pig.hurt");
    public static final SoundEvent PIG_DEATH_BABY = registerSound("entity.baby_pig.death");
    public static final SoundEvent PIG_STEP_BABY = registerSound("entity.baby_pig.step");

    public static final SoundEvent CAT_AMBIENT_BABY = registerSound("entity.baby_cat.ambient");
    public static final SoundEvent CAT_STRAY_AMBIENT_BABY = registerSound("entity.baby_cat.stray_ambient");
    public static final SoundEvent CAT_DEATH_BABY = registerSound("entity.baby_cat.death");
    public static final SoundEvent CAT_EAT_BABY = registerSound("entity.baby_cat.eat");
    public static final SoundEvent CAT_HISS_BABY = registerSound("entity.baby_cat.hiss");
    public static final SoundEvent CAT_BEG_FOR_FOOD_BABY = registerSound("entity.baby_cat.beg_for_food");
    public static final SoundEvent CAT_HURT_BABY = registerSound("entity.baby_cat.hurt");
    public static final SoundEvent CAT_PURR_BABY = registerSound("entity.baby_cat.purr");
    public static final SoundEvent CAT_PURREOW_BABY = registerSound("entity.baby_cat.purreow");

    public static final SoundEvent HORSE_AMBIENT_BABY = registerSound("entity.baby_horse.ambient");
    public static final SoundEvent HORSE_ANGRY_BABY = registerSound("entity.baby_horse.angry");
    public static final SoundEvent HORSE_BREATHE_BABY = registerSound("entity.baby_horse.breathe");
    public static final SoundEvent HORSE_DEATH_BABY = registerSound("entity.baby_horse.death");
    public static final SoundEvent HORSE_EAT_BABY = registerSound("entity.baby_horse.eat");
    public static final SoundEvent HORSE_HURT_BABY = registerSound("entity.baby_horse.hurt");
    public static final SoundEvent HORSE_LAND_BABY = registerSound("entity.baby_horse.land");
    public static final SoundEvent HORSE_STEP_BABY = registerSound("entity.baby_horse.step");

    public static final SoundEvent WOLF_AMBIENT_BABY = registerSound("entity.baby_wolf.ambient");
    public static final SoundEvent WOLF_DEATH_BABY = registerSound("entity.baby_wolf.death");
    public static final SoundEvent WOLF_GROWL_BABY = registerSound("entity.baby_wolf.growl");
    public static final SoundEvent WOLF_HURT_BABY = registerSound("entity.baby_wolf.hurt");
    public static final SoundEvent WOLF_PANT_BABY = registerSound("entity.baby_wolf.pant");
    public static final SoundEvent WOLF_STEP_BABY = registerSound("entity.baby_wolf.step");
    public static final SoundEvent WOLF_WHINE_BABY = registerSound("entity.baby_wolf.whine");

    // Particles
    public static final SimpleParticleType PAUSE_MOB_GROWTH = registerParticle("pause_mob_growth", false);
    public static final SimpleParticleType RESET_MOB_GROWTH = registerParticle("reset_mob_growth", false);

    private static SoundEvent registerSound(String name) {
        ResourceLocation id = new ResourceLocation("minecraft", name);
        SoundEvent event = SoundEvent.createVariableRangeEvent(id);
        SOUND_EVENTS.put(id, event);
        return event;
    }

    private static SimpleParticleType registerParticle(String name, boolean overrideLimiter) {
        ResourceLocation id = new ResourceLocation("minecraft", name);
        SimpleParticleType type = new SimpleParticleType(overrideLimiter) {
        };
        PARTICLES.put(id, type);
        return type;
    }

    private static Block registerBlock(String name, Block block) {
        BLOCKS.put(new ResourceLocation("minecraft", name), block);
        return block;
    }

    private static Item registerItem(String name, Item item) {
        ITEMS.put(new ResourceLocation("minecraft", name), item);
        return item;
    }

    public static void init() {
    }
}

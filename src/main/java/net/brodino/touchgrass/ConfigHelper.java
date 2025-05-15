package net.brodino.touchgrass;

import io.wispforest.owo.config.annotation.Config;

import java.util.List;

@Config(name = "touchgrass", wrapperName = "Config")
public class ConfigHelper {

    /**
     * This is a list of every block that count as "grass"
     */
    public List<String> touchableBlocks = List.of(
            "minecraft:grass", "minecraft:tall_grass", "minecraft:seagrass", "minecraft:tall_seagrass",
            "minecraft:fern", "minecraft:large_fern", "minecraft:glow_lichen", "minecraft:lily_pad",
            "minecraft:spore_blossom", "minecraft:crimson_roots", "minecraft:kelp", "minecraft:moss_carpet",
            "minecraft:moss_block", "minecraft:big_dripleaf", "minecraft:small_dripleaf", "minecraft:vine"
            // Leaving some stuff that I don't fully recognize as grass but somebody could
            /*
            "minecraft:dandelion", "minecraft:poppy", "minecraft:blue_orchid", "minecraft:allium",
            "minecraft:azure_bluet", "minecraft:red_tulip", "minecraft:orange_tulip", "minecraft:peony",
            "minecraft:white_tulip", "minecraft:pink_tulip", "minecraft:oxeye_daisy", "minecraft:cornflower",
            "minecraft:lily_of_the_valley", "minecraft:wither_rose", "minecraft:lilac", "minecraft:rose_bush",
             */
    );

    /**
     * Effect options
     */
    public Effects effects = new Effects();
    /**
     * Feedback options
     */
    public Feedback feedback = new Feedback();

    public static class Effects {
        /**
         * If the mod should give an effect
         */
        public boolean enabled = true;
        /**
         * The list of every effect that touching grass can grant
         */
        public List<String> effects = List.of(
                "minecraft:wither", "minecraft:regeneration", "minecraft:speed",
                "minecraft:poison", "minecraft:hunger", "minecraft:slowness", "minecraft:blindness"
        );
        /**
         * The duration of the effect given when touching grass
         */
        public int duration = 30;
        /**
         * The % chance that something will actually happen when touching grass
         */
        public int chance = 50;
        /**
         * The cooldown between touching grass
         */
        public int cooldown = 60;
    }

    public static class Feedback {
        /**
         * If the mod should give any feedback when stuff happens
         */
        public boolean enabled = true;
        /**
         * Feedback when touching grass
         */
        public String grassTouched = "You have finally touched some grass!";
        /**
         * Feedback when touching grass while on cooldown
         */
        public String inCooldown = "You've become accustomed to touching grass, wait %s seconds";
        /**
         * If the feedback should appear on the action bar or in chat
         */
        public boolean overlay = true;
    }

    public ConfigHelper() {}
}
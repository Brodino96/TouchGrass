package net.brodino.touchgrass;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;

@Config(name = "assets/touchgrass", wrapperName = "Config")
public class ConfigHelper {

    public ArrayList<String> touchableBlocks;
    public boolean shouldGiveEffect;
    public String effectName;
    public int effectDuration;

    public boolean shouldDisplayMessage;
    public String messageText;
    public boolean messageOverlay;

    public ConfigHelper() {
        this.touchableBlocks = new ArrayList<>();
        this.touchableBlocks.add("minecraft:grass");
        this.touchableBlocks.add("minecraft:tall_grass");
        this.touchableBlocks.add("minecraft:seagrass");
        this.touchableBlocks.add("minecraft:tall_seagrass");

        this.shouldGiveEffect = true;
        this.effectName = "minecraft:luck";
        this.effectDuration = 10;

        this.shouldDisplayMessage = true;
        this.messageText = "You have finally touched some grass!";
        this.messageOverlay = true;
    }
}
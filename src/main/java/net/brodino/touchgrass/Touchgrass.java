package net.brodino.touchgrass;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Touchgrass implements ModInitializer {

    public static final String MOD_ID = "assets/touchgrass";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Config CONFIG = Config.createAndLoad();

    @Override
    public void onInitialize() {

        LOGGER.info("Initializing Touch Grass");

        EventHandler.register();
    }
}

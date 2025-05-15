package net.brodino.touchgrass;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class EventHandler {

    public static void register() {
        UseBlockCallback.EVENT.register(Touchgrass::blockTouched);

        ServerTickEvents.END_SERVER_TICK.register(Touchgrass::onTick);
    }
}

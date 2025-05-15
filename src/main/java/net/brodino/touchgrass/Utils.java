package net.brodino.touchgrass;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    private static final Random RANDOM = new Random();

    private static final int chance = Touchgrass.CONFIG.effects().chance;

    public static final ArrayList<StatusEffect> EFFECTS = Touchgrass.CONFIG.effects().effects
            .stream()
            .map(id -> Registry.STATUS_EFFECT.get(new Identifier(id)))
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

    public static StatusEffect getRandomEffect() {
        int index = RANDOM.nextInt(EFFECTS.size());
        return EFFECTS.get(index);
    }

    public static boolean chance() {
        if (chance <= 0) return false;
        if (chance >= 100) return true;
        return RANDOM.nextDouble() * 100 < chance;
    }
}

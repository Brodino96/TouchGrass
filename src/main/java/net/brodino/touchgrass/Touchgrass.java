package net.brodino.touchgrass;

import net.brodino.touchgrass.Config;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Touchgrass implements ModInitializer {

    public static final String MOD_ID = "touchgrass";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Config CONFIG = Config.createAndLoad();
    public static final Map<PlayerEntity, Integer> COOLDOWN = new HashMap<>();

    private static final ArrayList<Block> ALLOWED_BLOCKS = CONFIG.touchableBlocks()
            .stream()
            .map(id -> Registry.BLOCK.get(new Identifier(id)))
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Touch Grass");
        EventHandler.register();
        LOGGER.info("Cooldown has be set to: {} seconds", CONFIG.effects().cooldown);
        LOGGER.info("Allowed blocks are: {}", ALLOWED_BLOCKS.stream().map(Block::getName));
        LOGGER.info("Allowed effects are: {}", Utils.EFFECTS.stream().map(StatusEffect::getName));
    }

    public static ActionResult blockTouched(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {

        if (world.isClient()) {
            return ActionResult.PASS;
        }

        if (player.isSpectator()) {
            return ActionResult.PASS;
        }

        if (!hand.equals(Hand.MAIN_HAND)) {
            return ActionResult.PASS;
        }

        if (!player.getStackInHand(hand).equals(ItemStack.EMPTY)) {
            return ActionResult.PASS;
        }

        BlockPos pos = blockHitResult.getBlockPos();
        Block targetBlock = world.getBlockState(pos).getBlock();

        if (!ALLOWED_BLOCKS.contains(targetBlock)) {
            return ActionResult.PASS;
        }

        if (COOLDOWN.containsKey(player)) {
            if (CONFIG.feedback().enabled) {
                player.sendMessage(Text.literal(String.format(CONFIG.feedback().inCooldown, COOLDOWN.get(player) / 20)), CONFIG.feedback().overlay);
            }
            return ActionResult.SUCCESS;
        }

        COOLDOWN.put(player, CONFIG.effects().cooldown * 20);

        if (CONFIG.feedback().enabled) {
            player.sendMessage(Text.literal(CONFIG.feedback().grassTouched), CONFIG.feedback().overlay);
        }

        if (CONFIG.effects().enabled && Utils.chance()) {
            player.addStatusEffect(new StatusEffectInstance(Utils.getRandomEffect(), CONFIG.effects().duration * 20), player);
        }

        return ActionResult.SUCCESS;
    }

    public static void onTick(MinecraftServer minecraftServer) {
        Map<PlayerEntity, Integer> cooldownsCopy = new HashMap<>(COOLDOWN);

        for (Map.Entry<PlayerEntity, Integer> entry : cooldownsCopy.entrySet()) {

            PlayerEntity player = entry.getKey();
            Integer timer = entry.getValue();

            if (timer <= 0) {
                COOLDOWN.remove(player);
            } else {
                timer--;
                COOLDOWN.put(player, timer);
            }
        }
    }
}

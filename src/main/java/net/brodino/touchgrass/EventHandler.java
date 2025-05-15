package net.brodino.touchgrass;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventHandler {

    private static final StatusEffect EFFECT = Registry.STATUS_EFFECT.get(new Identifier(Touchgrass.CONFIG.effectName()));
    private static final ArrayList<Block> ALLOWED_BLOCKS = Touchgrass.CONFIG.touchableBlocks()
            .stream()
            .map(id -> Registry.BLOCK.get(new Identifier(id)))
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

    public static ActionResult blockTouched(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {

        if (world.isClient()) {
            return ActionResult.PASS;
        }

        if (!hand.equals(Hand.MAIN_HAND)) {
            return ActionResult.PASS;
        }

        BlockPos pos = blockHitResult.getBlockPos();
        Block targetBlock = world.getBlockState(pos).getBlock();

        if (!ALLOWED_BLOCKS.contains(targetBlock)) {
            return ActionResult.PASS;
        }

        if (!player.getStackInHand(hand).equals(ItemStack.EMPTY)) {
            return ActionResult.PASS;
        }

        if (EFFECT == null) {
            Touchgrass.LOGGER.error("The effect to give to the player is somehow nu");
            return ActionResult.PASS;
        }

        if (Touchgrass.CONFIG.shouldDisplayMessage()) {
            player.sendMessage(Text.literal(Touchgrass.CONFIG.messageText()), Touchgrass.CONFIG.messageOverlay());
        }

        if (Touchgrass.CONFIG.shouldGiveEffect()) {
            player.addStatusEffect(new StatusEffectInstance(EFFECT, Touchgrass.CONFIG.effectDuration() * 20), player);
        }


        return ActionResult.SUCCESS;
    }

    public static void register() {
        UseBlockCallback.EVENT.register(EventHandler::blockTouched);
    }
}

package net.dewteereeum.aquaticaspirations.item.custom.fishingrods;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

import javax.annotation.Nonnull;

public class VoidFishingRod extends FishingRodItem {
    public VoidFishingRod(Properties properties) {
        super(properties);
    }
    /*
    public VoidFishingRod(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand){
        ItemStack heldStack = player.getItemInHand(hand);

        if(player instanceof FakePlayer) return InteractionResult.FAIL;

        if(player.fishing != null){
            player.swing(hand);
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        }
        return InteractionResult.SUCCESS;
    }

     */

}

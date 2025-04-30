package net.dewteereeum.functionalfish.block.custom;

import com.mojang.serialization.MapCodec;
import net.dewteereeum.functionalfish.block.entity.ModBlockEntities;
import net.dewteereeum.functionalfish.block.entity.custom.FishbowlBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Fishbowl extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 15, 15);
    public static final MapCodec<Fishbowl> CODEC = simpleCodec(Fishbowl::new);

    //FACING
    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    //END OF FACING

    public Fishbowl(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;


    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FishbowlBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level plevel, BlockPos pPos, BlockState newState, boolean movedByPiston) {
        if(pState.getBlock() != newState.getBlock()) {
            if(plevel.getBlockEntity(pPos) instanceof FishbowlBlockEntity fishbowlBlockEntity) {
                fishbowlBlockEntity.drops();
                plevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, plevel, pPos, newState, movedByPiston);
    }
 /*
    @Override
   protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(level.getBlockEntity(pos) instanceof FishbowlBlockEntity fishbowlBlockEntity) {
            if(player.isCrouching() && !level.isClientSide()){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(fishbowlBlockEntity, Component.literal("Fishbowl")), pos);
                return ItemInteractionResult.SUCCESS;
            }
           if (fishbowlBlockEntity.itemHandler.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                fishbowlBlockEntity.itemHandler.insertItem(0,stack.copy(), false);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);

            } else if (stack.isEmpty() && !fishbowlBlockEntity.itemHandler.getStackInSlot(0).isEmpty() && !player.isCrouching()) {
                ItemStack stackInBowl = fishbowlBlockEntity.itemHandler.extractItem(0, 1, false);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackInBowl);
                fishbowlBlockEntity.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

     */

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof FishbowlBlockEntity fishbowlBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(fishbowlBlockEntity, Component.literal("Fishbowl")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
       if(pLevel.isClientSide()) {
           return null;
       }
       return createTickerHelper(pBlockEntityType, ModBlockEntities.FISHBOWL_BE.get(),
               (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
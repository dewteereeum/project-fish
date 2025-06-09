package net.dewteereeum.aquaticaspirations.block.custom;

import com.mojang.serialization.MapCodec;
import net.dewteereeum.aquaticaspirations.block.ModBlockProperties;
import net.dewteereeum.aquaticaspirations.block.entity.ModBlockEntities;
import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.sound.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

public class Fishtank extends BaseEntityBlock {


    public static final EnumProperty<ModBlockProperties.ContainedFluid> FLUID = ModBlockProperties.CONTAINED_FLUID;

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final MapCodec<Fishtank> CODEC = simpleCodec(Fishtank::new);


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
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(FLUID, ModBlockProperties.ContainedFluid.EMPTY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FLUID);
    }
    //END OF FACING


    public Fishtank(Properties properties) {
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
        return new FishtankBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level plevel, BlockPos pPos, BlockState newState, boolean movedByPiston) {
        if (pState.getBlock() != newState.getBlock()) {
            if (plevel.getBlockEntity(pPos) instanceof FishtankBlockEntity fishtankBlockEntity) {
                fishtankBlockEntity.drops();
                plevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, plevel, pPos, newState, movedByPiston);
    }


    //Credit to klikli_dev for help with fluid handling, from Theurgy Mod
    //https://github.com/klikli-dev/theurgy/blob/version/1.21.1/src/main/java/com/klikli_dev/theurgy/content/behaviour/fluidhandler/OneTankFluidHandlerBehaviour.java
    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            ItemStack stackInHand = pPlayer.getItemInHand(pHand);
            boolean holdingBucketItem =
                    stackInHand.getCapability(Capabilities.FluidHandler.ITEM) != null;


            if (entity instanceof FishtankBlockEntity fishtankBlockEntity) {
                if (holdingBucketItem) {
                    if (pHand != InteractionHand.MAIN_HAND) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }

                    ItemStack fillStack = stackInHand.copyWithCount(1);
                    IFluidHandler blockFluidhandler = pLevel.getCapability(Capabilities.FluidHandler.BLOCK, pPos, null);

                    if (blockFluidhandler == null) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }

                    IFluidHandlerItem itemFluidHandler = fillStack.getCapability(Capabilities.FluidHandler.ITEM);

                    if (itemFluidHandler == null) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }
                    //try to insert
                    FluidStack transferredFluid = FluidUtil.tryFluidTransfer(blockFluidhandler, itemFluidHandler, Integer.MAX_VALUE, true);
                    if (this.updateFluidContainerInHand(pPlayer, pHand, stackInHand, itemFluidHandler, transferredFluid)) {
                        pLevel.playSound(pPlayer, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS);
                        return ItemInteractionResult.SUCCESS;
                    }
                    //try to extract
                    transferredFluid = FluidUtil.tryFluidTransfer(itemFluidHandler, blockFluidhandler, Integer.MAX_VALUE, true);
                    if (this.updateFluidContainerInHand(pPlayer, pHand, stackInHand, itemFluidHandler, transferredFluid)) {
                        pLevel.playSound(pPlayer, pPos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS);
                        return ItemInteractionResult.SUCCESS;
                    }
                } else {
                    ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(fishtankBlockEntity, Component.literal("Fishtank")), pPos);
                }
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private boolean updateFluidContainerInHand(Player pPlayer, InteractionHand pHand, ItemStack stackInHand, IFluidHandlerItem itemFluidHandler, FluidStack transferredFluid) {
        if (!transferredFluid.isEmpty()) {
            //handle bucket stacking correctly
            stackInHand.shrink(1);
            if (stackInHand.isEmpty()) {
                pPlayer.setItemInHand(pHand, itemFluidHandler.getContainer()); //always set to container to handle e.g. empty bucket correctly
            } else {
                pPlayer.setItemInHand(pHand, stackInHand);
                pPlayer.getInventory().placeItemBackInInventory(itemFluidHandler.getContainer());
            }

            return true;
        }
        return false;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.FISHTANK_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
package net.dewteereeum.functionalfish.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.dewteereeum.functionalfish.Config;
import net.dewteereeum.functionalfish.block.custom.Fishbowl;
import net.dewteereeum.functionalfish.block.entity.custom.FishbowlBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;

public class FishbowlBlockEntityRenderer implements BlockEntityRenderer<FishbowlBlockEntity> {
    public FishbowlBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }



    @Override
    public void render(FishbowlBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        if (Config.RENDER_BOWLS.get()) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = pBlockEntity.itemHandler.getStackInSlot(0);
        Direction facing = pBlockEntity.getBlockState().getValue(Fishbowl.FACING);


            pPoseStack.pushPose();

            pPoseStack.translate(0.5f, 0.5f, 0.5f);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);

            //setting facing direction
            if (facing == Direction.NORTH) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            } else if (facing == Direction.EAST) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            } else if (facing == Direction.SOUTH) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            } else if (facing == Direction.WEST) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }


            pPoseStack.mulPose(Axis.ZP.rotationDegrees(pBlockEntity.getRenderingRotation()));

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
            pPoseStack.popPose();

        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}

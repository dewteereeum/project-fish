package net.dewteereeum.aquaticaspirations.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.dewteereeum.aquaticaspirations.Config;
import net.dewteereeum.aquaticaspirations.block.custom.Fishtank;
import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
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

public class FishtankBlockEntityRenderer implements BlockEntityRenderer<FishtankBlockEntity> {
    public FishtankBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }



    @Override
    public void render(FishtankBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        if (Config.RENDER_TANKS.get()) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = pBlockEntity.itemHandler.getStackInSlot(0);
        Direction facing = pBlockEntity.getBlockState().getValue(Fishtank.FACING);


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

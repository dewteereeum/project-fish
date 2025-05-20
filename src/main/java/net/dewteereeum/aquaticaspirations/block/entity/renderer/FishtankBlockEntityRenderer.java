package net.dewteereeum.aquaticaspirations.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.dewteereeum.aquaticaspirations.Config;
import net.dewteereeum.aquaticaspirations.block.custom.Fishtank;
import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class FishtankBlockEntityRenderer implements BlockEntityRenderer<FishtankBlockEntity> {
    public FishtankBlockEntityRenderer(BlockEntityRendererProvider.Context context){
    }



    @Override
    public void render(FishtankBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {



        if (Config.RENDER_TANKS.get()) {



            //FLUID RENDERER
            // Credits to TurtyWurty
            // Under MIT-License: https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod?tab=MIT-1-ov-file#readme



            FluidStack fluidStack = pBlockEntity.getFluid();

            if (!fluidStack.isEmpty()){


                Level level = pBlockEntity.getLevel();
                if (level == null)
                    return;

                BlockPos pos = pBlockEntity.getBlockPos();

                IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
                ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);


                FluidState state = fluidStack.getFluid().defaultFluidState();

                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
                int tintColor = fluidTypeExtensions.getTintColor(state, level, pos);



                float height = (((float) pBlockEntity.getTank(null).getFluidInTank(0).getAmount() / pBlockEntity.getTank(null).getTankCapacity(0)) * 0.625f) + 0.25f;


                VertexConsumer builder = pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));
                //VertexConsumer builder = pBufferSource.getBuffer();




                // Top Texture
                drawQuad(builder, pPoseStack, 0.1f, height, 0.1f, 0.9f, height, 0.9f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
                drawQuad(builder, pPoseStack, 0.1f, 0.15f, 0.1f, 0.9f, height, 0.1f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);




                pPoseStack.pushPose();
                pPoseStack.mulPose(Axis.XP.rotationDegrees(180));
                pPoseStack.translate(0, -0.9f, -1f);
                drawQuad(builder, pPoseStack, 0.1f, height, 0.1f, 0.9f, height, 0.9f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
                pPoseStack.popPose();




                pPoseStack.pushPose();
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                pPoseStack.translate(-1f, 0, -1.8f);
                drawQuad(builder, pPoseStack, 0.1f, 0.15f, 0.9f, 0.9f, height, 0.9f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
                pPoseStack.popPose();

                pPoseStack.pushPose();
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.translate(-1f, 0, 0);
                drawQuad(builder, pPoseStack, 0.1f, 0.15f, 0.1f, 0.9f, height, 0.1f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
                pPoseStack.popPose();

                pPoseStack.pushPose();
                pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
                pPoseStack.translate(0, 0, -1f);
                drawQuad(builder, pPoseStack, 0.1f, 0.15f, 0.1f, 0.9f, height, 0.1f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
                pPoseStack.popPose();

            }








            //System.out.println("FluidRenderer Pass");

            //ITEM RENDERER

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack stack = pBlockEntity.itemHandler.getStackInSlot(0);
            Direction facing = pBlockEntity.getBlockState().getValue(Fishtank.FACING);



            pPoseStack.pushPose();

            pPoseStack.translate(0.5f, 0.5f, 0.5f);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);

            //setting facing direction
            if (facing == Direction.NORTH) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            } else if (facing == Direction.EAST) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(270));
            } else if (facing == Direction.SOUTH) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            } else if (facing == Direction.WEST) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            }


            pPoseStack.mulPose(Axis.ZP.rotationDegrees(pBlockEntity.getRenderingRotation()));



            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);


            pPoseStack.popPose();



        }
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1, 0, 0);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}

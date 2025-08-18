package net.dewteereeum.aquaticaspirations.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.dewteereeum.aquaticaspirations.Config;
import net.dewteereeum.aquaticaspirations.block.custom.Fishtank;
import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class FishtankBlockEntityRenderer implements BlockEntityRenderer<FishtankBlockEntity> {
    public FishtankBlockEntityRenderer(BlockEntityRendererProvider.Context context){
    }



    @Override
    public void render(FishtankBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {



        if (Config.RENDER_TANKS.get()) {



            //Credit to thedarkcolour
            //Under GNU-License: https://github.com/thedarkcolour/ExDeorum/tree/1.21.1?tab=readme-ov-file

            FluidStack fluidStack = pBlockEntity.getFluid();
            ItemStack substrateStack = pBlockEntity.itemHandler.getStackInSlot(1);
            boolean hasFluid = (!pBlockEntity.getFluid().isEmpty());
            boolean hasFish = (!pBlockEntity.itemHandler.getStackInSlot(0).isEmpty());
            boolean hasSubstrate = (!pBlockEntity.itemHandler.getStackInSlot(1).isEmpty());
            boolean hasAccessory = (!pBlockEntity.itemHandler.getStackInSlot(2).isEmpty());

            int r = -1;
            int g = -1;
            int b = -1;
            float percentage = fluidStack.getAmount() / 1000.0f;

            if (hasFluid){
                Fluid fluid = fluidStack.getFluid();
                Level level = pBlockEntity.getLevel();
                BlockPos pos = pBlockEntity.getBlockPos();
                float tankFloor = hasSubstrate ? 4/16f : 2/16f;
                float tankCeiling = 15.01f/16f;
                float y = Mth.lerp(percentage, tankFloor, tankCeiling);
                int inputFluidColour = TankRenderUtil.getFluidColor(fluid, level, pos);

                r = (inputFluidColour >> 16) & 0xff;
                g = (inputFluidColour >> 8) & 0xff;
                b = inputFluidColour & 0xff;

                //TOP FACE
                //TankRenderUtil.renderTopFace(pBufferSource.getBuffer(RenderType.TRANSLUCENT), pPoseStack, y, r, g, b, fluid, pPackedLight, 2.0f);





                TankRenderUtil.renderFluidCube(pBufferSource, pPoseStack, level, pos, tankFloor, y, 2.0f, pPackedLight, r, g, b, fluid);

            }


            //SUBSTRATE RENDERER
            if(hasSubstrate){
                //System.out.println("r = " + r);
                //System.out.println("g = " + g);
                //System.out.println("b = " + b);

               if(hasFluid){
                   r = Math.min(255, r + 60);
                   g = Math.min(255, g + 60);
                   b = Math.min(255, b + 60);
               }
               TankRenderUtil.renderCuboid(pBufferSource.getBuffer(RenderType.CUTOUT), pPoseStack, 2/16f, 4/16f, r, g, b, substrateStack, pPackedLight, 2.0f);
            }








            //System.out.println(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor());

            //ITEM RENDERER


            if(hasFish) {

                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

                ItemStack stack = pBlockEntity.itemHandler.getStackInSlot(0);
                Direction facing = pBlockEntity.getBlockState().getValue(Fishtank.FACING);
                float fishY = Math.max(4/16f, percentage * 0.6f);
                int fishZRot = hasFluid ? 0 : -90;


                pPoseStack.pushPose();

                pPoseStack.translate(0.5f, fishY, 0.5f);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.ZN.rotationDegrees(fishZRot));

                //setting facing direction
                int fishYRot = switch (facing){
                    case NORTH -> 180;
                    case EAST -> -90;
                    case SOUTH -> 0;
                    case WEST -> 90;
                    default -> 0;
                };

                pPoseStack.mulPose(Axis.YP.rotationDegrees(fishYRot));



                if(hasFluid) {
                    pPoseStack.mulPose(Axis.ZP.rotationDegrees(pBlockEntity.getRenderingRotation()));
                }

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                        OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);


                pPoseStack.popPose();
            }

            if(hasAccessory) {

                ItemRenderer itemRenderer1 = Minecraft.getInstance().getItemRenderer();
                //ACCESSORY
                ItemStack stack = pBlockEntity.itemHandler.getStackInSlot(2);
                Direction facing = pBlockEntity.getBlockState().getValue(Fishtank.FACING);


                pPoseStack.pushPose();

                float x;
                float y;
                float z;
                int accYRot;
                x = z = 8/16f;
                y = 4/16f;

                switch(facing){
                    case NORTH -> {
                        x -= 3/16f;
                        z -= 1/16f;
                    }
                    case EAST -> {
                        x += 1/16f;
                        z -= 3/16f;
                    }
                    case SOUTH -> {
                        x += 3/16f;
                        z += 1/16f;
                    }
                    case WEST -> {
                        x -= 1/16f;
                        z += 3/16f;
                    }
                }
                

                pPoseStack.translate(x, y, z);
                pPoseStack.scale(1.0f, 1.0f, 1.0f);

                //setting facing direction
                int fishYRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> -90;
                    case SOUTH -> 180;
                    case WEST -> 90;
                    default -> 0;
                };

                pPoseStack.mulPose(Axis.YP.rotationDegrees(fishYRot));

                itemRenderer1.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                        OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();

            }



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

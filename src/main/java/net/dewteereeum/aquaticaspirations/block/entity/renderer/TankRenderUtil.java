package net.dewteereeum.aquaticaspirations.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Vector3f;

import java.util.Map;

public class TankRenderUtil {


    //Credit to thedarkcolour
    //Under GNU-License: https://github.com/thedarkcolour/ExDeorum/tree/1.21.1?tab=readme-ov-file

    public static TextureAtlas blockAtlas = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);

    public static Map topTextureGetter = Map.of(
            Fluids.WATER, ResourceLocation.parse("block/water"),
            Fluids.LAVA, ResourceLocation.parse("block/lava"),
            ModFluids.SOURCE_TANK_FLUID, ResourceLocation.parse("entity/end_portal")
    );



    public static void renderFluidCube(MultiBufferSource buffers, PoseStack stack, Level level, BlockPos pos, float minY, float maxY, float edge, int light, int r, int g, int b, Fluid fluid) {
        var extensions = IClientFluidTypeExtensions.of(fluid);
        var extensionsWater = IClientFluidTypeExtensions.of(ModFluids.SOURCE_TANK_FLUID.get());
        var state = fluid.defaultFluidState();
        var builder = buffers.getBuffer(Sheets.translucentCullBlockSheet());
        var pose = stack.last().pose();
        var poseNormal = stack.last().normal();

        Vector3f normal;
        TextureAtlasSprite tankFluidSprite = TankRenderUtil.blockAtlas.getSprite(extensionsWater.getStillTexture(state, level, pos));
        TextureAtlasSprite topFluidSprite = TankRenderUtil.blockAtlas.getSprite(extensions.getStillTexture(state, level, pos));
        float uMin = tankFluidSprite.getU0();
        float uMax = tankFluidSprite.getU1();
        float vMin = tankFluidSprite.getV0();
        float vMax = tankFluidSprite.getV1();

        float edgeMin = edge / 16f;
        float edgeMax = 1f - edge / 16f;

        // Top face

        normal = poseNormal.transform(new Vector3f(0, 1, 0));
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        // Bottom face

        /*
        normal = poseNormal.transform(new Vector3f(0, -1, 0));
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);

         */

        // Flowing texture coordinates
        //sprite = RenderUtil.blockAtlas.getSprite(extensions.getFlowingTexture(state, level, pos));
        //uMin = sprite.getU0();
        //uMax = sprite.getU(8);
        //vMin = sprite.getV0();
        //vMax = sprite.getV(8);

        // South face
        normal = poseNormal.transform(new Vector3f(0, 0, 1));
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        // North face
        normal = poseNormal.transform(new Vector3f(0, 0, -1));
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        // East face
        normal = poseNormal.transform(new Vector3f(1, 0, 0));
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        // West face
        normal = poseNormal.transform(new Vector3f(-1, 0, 0));
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);

        //System.out.println(extensions.getStillTexture(state, level, pos).toString());
    }

    public static TextureAtlasSprite topFaceTexture(Fluid fluid){
        if(fluid.isSame(ModFluids.SOURCE_TANK_FLUID.get())){
            return TankRenderUtil.blockAtlas.getSprite(ResourceLocation.withDefaultNamespace("entity/end_portal"));
        }
        var extensions = IClientFluidTypeExtensions.of(fluid);
        return TankRenderUtil.blockAtlas.getSprite(extensions.getStillTexture());

    }

    public static void renderTopFace(VertexConsumer builder, PoseStack stack, float y, int r, int g, int b, Fluid fluid, int light, float edge) {
        var pose = stack.last().pose();
        var normal = stack.last().normal().transform(new Vector3f(0, 1, 0));
        var sprite = topFaceTexture(fluid);
        // Position coordinates
        float edgeMin = edge / 16.0f;
        float edgeMax = (16.0f - edge) / 16.0f;

        // Texture coordinates
        float uMin = sprite.getU0();
        float uMax = sprite.getU1();
        float vMin = sprite.getV0();
        float vMax = sprite.getV1();

        // overlayCoords(0, 10) is NO_OVERLAY (0xA0000)
        builder.addVertex(pose, edgeMin, y, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, y, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, y, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, y, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
    }

    public static void renderFlatSprite(VertexConsumer builder, PoseStack stack, float y, int r, int g, int b, TextureAtlasSprite sprite, int light, float edge) {
        var pose = stack.last().pose();
        var normal = stack.last().normal().transform(new Vector3f(0, 1, 0));

        // Position coordinates
        float edgeMin = edge / 16.0f;
        float edgeMax = (16.0f - edge) / 16.0f;

        // Texture coordinates
        float uMin = sprite.getU0();
        float uMax = sprite.getU1();
        float vMin = sprite.getV0();
        float vMax = sprite.getV1();

        // overlayCoords(0, 10) is NO_OVERLAY (0xA0000)
        builder.addVertex(pose, edgeMin, y, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, y, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, y, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, y, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setLight(light).setNormal(normal.x, normal.y, normal.z);
    }

    public static void renderFlatFluidSprite(MultiBufferSource buffers, PoseStack stack, Level level, BlockPos pos, float y, float edge, int light, int r, int g, int b, Fluid fluid) {
        var extensions = IClientFluidTypeExtensions.of(fluid);
        var state = fluid.defaultFluidState();
        var builder = buffers.getBuffer(Sheets.translucentCullBlockSheet());

        TankRenderUtil.renderFlatSprite(builder, stack, y, r, g, b, TankRenderUtil.blockAtlas.getSprite(extensions.getStillTexture(state, level, pos)), light, edge);
    }

    public static int getFluidColor(Fluid fluid, Level level, BlockPos pos) {
        return IClientFluidTypeExtensions.of(fluid).getTintColor(fluid.defaultFluidState(), level, pos);
    }

    public static void renderCuboid(VertexConsumer builder, PoseStack stack, float minY, float maxY, int r, int g, int b, ItemStack subStack, int light, float edge) {
        var pose = stack.last().pose();
        var poseNormal = stack.last().normal();

        var sprite = getSubstrateSprite(subStack);

        Vector3f normal;
        float uMin = sprite.getU0();
        float uMax = sprite.getU1();
        float vMin = sprite.getV0();
        float vMax = sprite.getV1();

        float edgeMin = edge / 16f;
        float edgeMax = 1f - edge / 16f;

        int lightU = light & '\uffff';
        int lightV = light >> 16 & '\uffff';

        // Top face
        normal = poseNormal.transform(new Vector3f(0, 1, 0));
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        // Bottom face
        normal = poseNormal.transform(new Vector3f(0, -1, 0));
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);

        // Adjust UV based on height of cuboid, rendering from the top down to the bottom of the texture
        float f = sprite.getV1() - sprite.getV0();
        vMax = sprite.getV0() + f * (maxY - minY);

        // South face
        normal = poseNormal.transform(new Vector3f(0, 0, -1));
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        // North face
        normal = poseNormal.transform(new Vector3f(0, 0, -1));
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        // East face
        normal = poseNormal.transform(new Vector3f(1, 0, 0));
        builder.addVertex(pose, edgeMax, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMax, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        // West face
        normal = poseNormal.transform(new Vector3f(-1, 0, 0));
        builder.addVertex(pose, edgeMin, maxY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, maxY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMin).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMin).setColor(r, g, b, 255).setUv(uMin, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
        builder.addVertex(pose, edgeMin, minY, edgeMax).setColor(r, g, b, 255).setUv(uMax, vMax).setUv1(0, 10).setUv2(lightU, lightV).setNormal(normal.x, normal.y, normal.z);
    }

    public static TextureAtlasSprite getSubstrateSprite(ItemStack stack) {
        Block sub = (Block.byItem(stack.getItem()) == null) ? Blocks.SAND : Block.byItem(stack.getItem());
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(sub);


        String[] tokens = key.toString().split(":");
        String substrateName = tokens[1];
        ResourceLocation SUBSTRATE_MODEL =
                ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "block/" + substrateName);



        return TankRenderUtil.blockAtlas.getSprite(SUBSTRATE_MODEL);
    }
}
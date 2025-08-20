package net.dewteereeum.aquaticaspirations.fluid.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class TankFluid {

    private Fluid fluid;
    private int fluidColor;
    private String fluidName;
    private final TextureAtlas blockAtlas = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
    private TextureAtlasSprite sprite;

    TankFluid(Fluid fluid){
        this.fluid = fluid;
        this.fluidName = fluidName(fluid);
        this.sprite = sprite(fluid);
    }

    private String fluidName(Fluid fluid){
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        String[] tokens = key.toString().split(":");
        return tokens[1];
    }

    private TextureAtlasSprite sprite(Fluid fluid){
        var extensions = IClientFluidTypeExtensions.of(fluid);
        return blockAtlas.getSprite(extensions.getStillTexture());

    }


}

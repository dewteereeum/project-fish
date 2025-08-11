package net.dewteereeum.aquaticaspirations.fluid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class TankFluid {

    private String name;
    private ResourceLocation resourceLocation;
    private int color;
    private Fluid fluid;

    public TankFluid(ResourceKey<Fluid> fluidKey){
        fluid = BuiltInRegistries.FLUID.get(fluidKey);
        resourceLocation = fluidKey.location();
        name = fluidKey.toString().split(":")[1];
    }

    public TankFluid(Fluid fluid){
        this.fluid = fluid;
        this.resourceLocation = BuiltInRegistries.FLUID.getKey(fluid);
    }

    public boolean findColour(){
       int color = IClientFluidTypeExtensions.of(this.fluid).getTintColor();
       if(color == -1){
           return false;
       } else {
           this.color = color;
           return true;
       }
    }

    public void giveColor(){
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS)
                .getTextures().get(resourceLocation);
        int sum = 0;
        int count = 0;

        for(int x = 0; x < 16; x++){
            for(int y = 0; y < 16; y++){
                sum += sprite.getPixelRGBA(0, x, y);
                count++;
            }
        }
        this.color = sum / count;

    }

    @Override
    public String toString() {
        return "{name = %s, color = %d, location = %s}".formatted(this.name, this.color, this.resourceLocation);
    }
}

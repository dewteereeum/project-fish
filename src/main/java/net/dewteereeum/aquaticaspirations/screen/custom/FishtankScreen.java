package net.dewteereeum.aquaticaspirations.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.screen.renderer.FluidTankRenderer;
import net.dewteereeum.aquaticaspirations.util.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

public class FishtankScreen extends AbstractContainerScreen<FishtankMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AquaticAspirationsMod.MOD_ID, "textures/gui/fishtank/fishtank_gui.png");


    private FluidTankRenderer fluidRenderer;

    public FishtankScreen(FishtankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    @Override
    protected void init() {
        super.init();

        //this.inventoryLabelY = 10000;
        //this.titleLabelY = 10000;

        assignFluidRenderer();
    }

    private void assignFluidRenderer() {

        fluidRenderer = new FluidTankRenderer(1000, false, 43, 44);
    }


    private void renderFluidTooltipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                        FluidStack stack, int dirtLevel, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer) && this.getSlotUnderMouse() == null) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, dirtLevel, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidTooltipArea(guiGraphics, pMouseX, pMouseY, x, y, menu.blockEntity.getFluid(), menu.getDirtLevel(), 56, 22, fluidRenderer);
    }

    //private int substrateAdjustment = (menu.blockEntity.itemHandler.getStackInSlot(1).isEmpty()) ? 0 : 4;


    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(pGuiGraphics, x, y);
        renderSubstrateLayer(pGuiGraphics, x, y);

        fluidRenderer.hasSubstrate = !menu.blockEntity.itemHandler.getStackInSlot(1).isEmpty();
        fluidRenderer.render(pGuiGraphics, x + 56, y + 22, menu.blockEntity.getFluid(), menu.getDirtLevel());

    }




    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(GUI_TEXTURE, x + 113, y + 56 - menu.getScaledProgress(), 176, 32 - menu.getScaledProgress(), 10, 32);
        }
    }



    private void renderSubstrateLayer(GuiGraphics guiGraphics, int x, int y){

        ItemStack substrate = menu.getSubstrateStack();
        Block substrateBlock = Block.byItem(substrate.getItem());


        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(substrateBlock);
        String[] tokens = blockKey.toString().split(":");
        String substrateName = tokens[1];
        ResourceLocation SUBSTRATE_TEXTURE =
                ResourceLocation.fromNamespaceAndPath(blockKey.getNamespace(), "textures/block/" + substrateName + ".png");




        if(!substrateName.equals("air")) {
            guiGraphics.blit(SUBSTRATE_TEXTURE, x + 56, y + 60, 0, 0, 43, 6, 16, 16);
            //System.out.println(blockKeyTest);
        }

    }




    @Override
    public void render(GuiGraphics guiGraphics, int MouseX, int MouseY, float delta) {
        renderBackground(guiGraphics, MouseX, MouseY, delta);
        super.render(guiGraphics, MouseX, MouseY, delta);
        renderTooltip(guiGraphics, MouseX, MouseY);
    }
    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }


}

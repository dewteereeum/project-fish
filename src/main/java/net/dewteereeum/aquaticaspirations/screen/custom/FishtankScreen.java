package net.dewteereeum.aquaticaspirations.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.screen.renderer.FluidTankRenderer;
import net.dewteereeum.aquaticaspirations.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
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
        fluidRenderer = new FluidTankRenderer(1000, true, 28, 31);
    }

    private void renderFluidTooltipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                        FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidTooltipArea(guiGraphics, pMouseX, pMouseY, x, y, menu.blockEntity.getFluid(), 80, 8, fluidRenderer);
    }


    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(pGuiGraphics, x, y);
        fluidRenderer.render(pGuiGraphics, x + 63, y + 24, menu.blockEntity.getFluid());
    }



    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(GUI_TEXTURE, x + 110, y + 56 - menu.getScaledProgress(), 176, 32 - menu.getScaledProgress(), 10, 32);
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

package net.dewteereeum.functionalfish.item.custom;

import net.dewteereeum.functionalfish.block.SubstrateTier;
import net.dewteereeum.functionalfish.block.SubstrateType;
import net.dewteereeum.functionalfish.component.ModDataComponentTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProducingFish extends Item {

    protected Item resource;
    private SubstrateTier tier;
    private FishQuality quality;
    private int reqSubTier;
    private SubstrateType reqSubType;

    public ProducingFish(Properties properties, Item resource, SubstrateType type, SubstrateTier tier) {
        super(properties);
        this.resource=resource;
        this.reqSubType=type;
        this.tier = tier;
    }

    String resourceTooltip(){
        return "tooltip.functionalfish.producingfish.tooltip." + resource.toString();
    }

    String typeTooltip(){
        return "tooltip.functionalfish.fishtype.tooltip." + reqSubType.toString();
    }

    String qualityTooltip(){
        return "tooltip.functionalfish.quality.tooltip." + quality.toString();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable(typeTooltip()));
            tooltipComponents.add(Component.translatable(qualityTooltip()));
        }
        else{
            tooltipComponents.add(Component.translatable(resourceTooltip()));
        }


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }


    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public void upgradeQuality(){
    }



}

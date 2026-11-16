package tech.kardyn.may.common.compat.JEI;

import flaxbeard.immersivepetroleum.common.util.compat.jei.IPRecipeCategory;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.kardyn.may.common.crafting.HeatExchangerRecipe;
import tech.kardyn.may.common.multiblock.MAYMultiblocks;
import tech.kardyn.may.common.util.MAYResLocManager;

import java.util.Arrays;

public class HeatExchangerRecipeCategory extends IPRecipeCategory<HeatExchangerRecipe> {
    public HeatExchangerRecipeCategory(IGuiHelper guiHelper) {
        super(HeatExchangerRecipe.class, guiHelper, MAYResLocManager.mayResLoc("heat_exchanger"), "block.may.heat_exchanger");
        setIcon(new ItemStack(MAYMultiblocks.HEAT_EXCHANGER.block().get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HeatExchangerRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(3, 56)
                .setFluidRenderer(1, false, 16, 16)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(recipe.getTubeInputFluid().getFluids()));

        builder.addOutputSlot(25, 3)
                .setFluidRenderer(1, false, 16, 16)
                .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.getShellOutput());


        builder.addInputSlot(72, 3)
                .setFluidRenderer(1, false, 16, 16)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(recipe.getShellInputFluid().getFluids()));


        builder.addOutputSlot(94, 56)
                .setFluidRenderer(1, false, 16, 16)
                .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.getTubeOutput());

    }

    @Override
    public int getWidth() {
        return 113;
    }

    @Override
    public int getHeight() {
        return 75;
    }
}

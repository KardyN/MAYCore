package tech.kardyn.may.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import flaxbeard.immersivepetroleum.api.crafting.IPMultiblockRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import tech.kardyn.may.MAYCore;

import javax.annotation.Nonnull;
import java.util.*;

public class HeatExchangerRecipe extends IPMultiblockRecipe {
    public static Map<ResourceLocation, RecipeHolder<HeatExchangerRecipe>> recipes = new HashMap<>();
    private final SizedFluidIngredient tubeInput;
    private final FluidStack tubeOutput;
    private final SizedFluidIngredient shellInput;
    private final FluidStack shellOutput;

    public static RecipeHolder<HeatExchangerRecipe> findRecipe(@Nonnull FluidStack tube_in, @Nonnull FluidStack shell_in) {
        Objects.requireNonNull(tube_in);
        Objects.requireNonNull(shell_in);

        for (RecipeHolder<HeatExchangerRecipe> holder : recipes.values()) {
            HeatExchangerRecipe recipe = holder.value();
            if (recipe.tubeInput != null && recipe.shellInput != null && recipe.tubeInput.test(tube_in) && recipe.shellInput.test(shell_in)) {
                return holder;
            }
        }

        return null;
    }

    public HeatExchangerRecipe(SizedFluidIngredient tubeInput, FluidStack tubeOutput, SizedFluidIngredient shellInput, FluidStack shellOutput) {
        super(MAYRecipeTypes.HEAT_EXCHANGER, 1, 0);
        this.tubeInput = tubeInput;
        this.tubeOutput = tubeOutput;
        this.shellInput = shellInput;
        this.shellOutput = shellOutput;
        this.fluidInputList = new ArrayList<>(List.of(tubeInput, shellInput));
        this.fluidOutputList = new ArrayList<>(List.of(tubeOutput, shellOutput));
    }

    public SizedFluidIngredient getTubeInputFluid() {
        return this.tubeInput;
    }

    public FluidStack getTubeOutput() {
        return this.tubeOutput.copy();
    }

    public SizedFluidIngredient getShellInputFluid() {
        return this.shellInput;
    }

    public FluidStack getShellOutput() {
        return this.shellOutput.copy();
    }

    public int getMultipleProcessTicks() {
        return 0;
    }

    @Override
    protected IERecipeSerializer<HeatExchangerRecipe> getIESerializer() {
        return MAYRecipeSerializers.HEAT_EXCHANGER_SERIALIZER.get();
    }
}

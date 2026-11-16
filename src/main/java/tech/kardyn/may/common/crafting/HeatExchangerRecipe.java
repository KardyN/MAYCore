/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class HeatExchangerRecipe extends MultiblockRecipe {
    private static final Supplier<RecipeMultiplier> NONE = () -> null;
    public static Map<ResourceLocation, RecipeHolder<HeatExchangerRecipe>> recipes = new HashMap<>();
    private final SizedFluidIngredient tubeInput;
    private final FluidStack tubeOutput;
    private final SizedFluidIngredient shellInput;
    private final FluidStack shellOutput;

    public HeatExchangerRecipe(SizedFluidIngredient tubeInput, FluidStack tubeOutput, SizedFluidIngredient shellInput, FluidStack shellOutput) {
        super(TagOutput.EMPTY, MAYRecipeTypes.HEAT_EXCHANGER, 1, 0, NONE);
        this.tubeInput = tubeInput;
        this.tubeOutput = tubeOutput;
        this.shellInput = shellInput;
        this.shellOutput = shellOutput;
        this.fluidInputList = new ArrayList<>(List.of(tubeInput, shellInput));
        this.fluidOutputList = new ArrayList<>(List.of(tubeOutput, shellOutput));
    }

    @Override
    public int getTotalProcessTime() {
        return 1;
    }

    @Override
    public int getTotalProcessEnergy() {
        return 1;
    }

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

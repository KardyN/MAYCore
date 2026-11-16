/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.utils.codec.IEDualCodecs;
import malte0811.dualcodecs.DualCompositeMapCodecs;
import malte0811.dualcodecs.DualMapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import tech.kardyn.may.common.multiblock.MAYMultiblocks;

public class HeatExchangerRecipeSerializer extends IERecipeSerializer<HeatExchangerRecipe> {
    public static final DualMapCodec<RegistryFriendlyByteBuf, HeatExchangerRecipe> CODEC = DualCompositeMapCodecs.composite(
            IEDualCodecs.SIZED_FLUID_INGREDIENT.fieldOf("tubeInput"), HeatExchangerRecipe::getTubeInputFluid,
            IEDualCodecs.FLUID_STACK.fieldOf("tubeOutput"), HeatExchangerRecipe::getTubeOutput,
            IEDualCodecs.SIZED_FLUID_INGREDIENT.fieldOf("shellInput"), HeatExchangerRecipe::getShellInputFluid,
            IEDualCodecs.FLUID_STACK.fieldOf("shellOutput"), HeatExchangerRecipe::getShellOutput,
            HeatExchangerRecipe::new);

    protected DualMapCodec<RegistryFriendlyByteBuf, HeatExchangerRecipe> codecs() {
        return CODEC;
    }

    public ItemStack getIcon() {
        return MAYMultiblocks.HEAT_EXCHANGER.iconStack();
    }
}

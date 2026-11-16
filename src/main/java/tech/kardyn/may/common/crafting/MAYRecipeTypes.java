/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MAYRecipeTypes {
    private static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, "may");
    public static final IERecipeTypes.TypeWithClass<HeatExchangerRecipe> HEAT_EXCHANGER = new IERecipeTypes.TypeWithClass<>(
            REGISTER.register("heat_exchanger", () -> new RecipeType<>() {}),
            HeatExchangerRecipe.class);

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

}

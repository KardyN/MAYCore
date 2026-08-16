/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.component.food;

import net.dries007.tfc.common.component.food.FoodTrait;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FoodTraits {
    public static final ResourceKey<Registry<FoodTrait>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("tfc", "food_trait"));
    public static final DeferredRegister<FoodTrait> TRAITS;
    public static final DeferredHolder<FoodTrait, FoodTrait> NEVER_EXPIRES;

    static {
        TRAITS = DeferredRegister.create(KEY, "tfc");
        NEVER_EXPIRES = TRAITS.register("never_expires", () -> new FoodTrait(() -> 0.0, "tfc.tooltip.food_trait.never_expires"));
    }
}

package tech.kardyn.may.common.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.kardyn.may.MAYCore;

public class MAYRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MAYCore.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, HeatExchangerRecipeSerializer> HEAT_EXCHANGER_SERIALIZER = REGISTER.register("heat_exchanger", HeatExchangerRecipeSerializer::new);

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}

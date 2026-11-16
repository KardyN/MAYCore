package tech.kardyn.may.common.multiblock.logic.heat_exchanger;

import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcessInMachine;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import tech.kardyn.may.common.crafting.HeatExchangerRecipe;
import net.minecraft.world.level.Level;


import java.util.function.BiFunction;

public class HeatExchangerProcess extends MultiblockProcessInMachine<HeatExchangerRecipe> {
    static final int[] SLOTS_NONE = new int[0];

    public HeatExchangerProcess(RecipeHolder<HeatExchangerRecipe> recipe, int[] inputTanks, int[] inputAmounts) {
        super(recipe, SLOTS_NONE);
        setInputTanks(inputTanks);
        setInputAmounts(inputAmounts);
    }

    public HeatExchangerProcess(BiFunction<Level, ResourceLocation, HeatExchangerRecipe> recipe, CompoundTag data, HolderLookup.Provider provider) {
        super(recipe, data);
    }
}

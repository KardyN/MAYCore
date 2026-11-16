package tech.kardyn.may.common.compat.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import tech.kardyn.may.common.crafting.HeatExchangerRecipe;
import tech.kardyn.may.common.multiblock.MAYMultiblocks;
import tech.kardyn.may.common.util.MAYResLocManager;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {
    private RecipeType<HeatExchangerRecipe> HEAT_EXCHANGER_TYPE;

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return MAYResLocManager.mayResLoc("main");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        HeatExchangerRecipeCategory heat_exchanger =  new HeatExchangerRecipeCategory(guiHelper);
        this.HEAT_EXCHANGER_TYPE = heat_exchanger.getRecipeType();
        registration.addRecipeCategories(heat_exchanger);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(this.HEAT_EXCHANGER_TYPE, HeatExchangerRecipe.recipes.values().stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(MAYMultiblocks.HEAT_EXCHANGER.block().get()), this.HEAT_EXCHANGER_TYPE);
    }
}

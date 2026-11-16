/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import org.jetbrains.annotations.NotNull;
import tech.kardyn.may.MAYCore;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class MAYResourceManagerReloadListener implements ResourceManagerReloadListener{
    private final ReloadableServerResources dataPackRegistries;

    public MAYResourceManagerReloadListener(ReloadableServerResources dataPackRegistries) {
        this.dataPackRegistries = dataPackRegistries;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void recipesUpdated(RecipesUpdatedEvent event){
        if(!Minecraft.getInstance().hasSingleplayerServer()){
            lists(event.getRecipeManager());
        }
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        if (dataPackRegistries != null) {
            lists(dataPackRegistries.getRecipeManager());
        }
    }

    public void lists(RecipeManager recipeManager) {
        Collection<RecipeHolder<?>> recipes = recipeManager.getRecipes();

        if(recipes.isEmpty())
            return;

        MAYCore.LOGGER.info("Loading Heat Exchanger recipes.");
        HeatExchangerRecipe.recipes = filterRecipes(recipes, MAYRecipeTypes.HEAT_EXCHANGER);
    }

    @SuppressWarnings("unchecked")
    static <R extends Recipe<?>, H extends RecipeHolder<R>> Map<ResourceLocation, H> filterRecipes(Collection<RecipeHolder<?>> recipes, IERecipeTypes.TypeWithClass<R> recipeType) {
        return (Map<ResourceLocation, H>) recipes.stream()
                .filter(holder -> holder.value().getType() == recipeType.get())
                .map(holder -> new Test(holder.id(), holder))
                .collect(Collectors.toMap(Test::id, Test::holder));
    }

    private record Test(ResourceLocation id, RecipeHolder<?> holder){
    }
}

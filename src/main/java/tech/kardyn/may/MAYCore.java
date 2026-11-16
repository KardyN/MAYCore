/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;
import tech.kardyn.may.common.MAYMenuTypes;
import tech.kardyn.may.common.block.MAYBlocks;
import tech.kardyn.may.common.component.food.MAYFoodTraits;
import tech.kardyn.may.common.crafting.MAYRecipeTypes;
import tech.kardyn.may.common.crafting.MAYRecipeSerializers;
import tech.kardyn.may.common.crafting.MAYResourceManagerReloadListener;
import tech.kardyn.may.common.item.MAYItems;
import tech.kardyn.may.common.multiblock.MAYMultiblocks;
import tech.kardyn.may.common.util.MAYInteractionManager;

@Mod(MAYCore.MODID)
public class MAYCore {
    public static final String MODID = "may";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MAYCore(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        MAYBlocks.init(modEventBus);
        MAYItems.init(modEventBus);
        MAYFoodTraits.init(modEventBus);
        MAYMultiblocks.init(modEventBus);
        MAYRecipeTypes.init(modEventBus);
        MAYRecipeSerializers.init(modEventBus);
        MAYMenuTypes.forceClassLoad();
        NeoForge.EVENT_BUS.addListener(this::addReloadListeners);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        MAYMultiblocks.setup(event);
        event.enqueueWork(MAYInteractionManager::init);
    }

    @SubscribeEvent
    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new MAYResourceManagerReloadListener(event.getServerResources()));
    }
}

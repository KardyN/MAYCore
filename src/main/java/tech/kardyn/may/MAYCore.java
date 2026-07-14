/**
 *     MAYCore  Copyright (C) 2026 KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import tech.kardyn.may.common.blocks.MAYBlocks;
import tech.kardyn.may.common.component.food.FoodTraits;
import tech.kardyn.may.common.items.MAYItems;
import tech.kardyn.may.common.utils.MAYInteractionManager;

@Mod(MAYCore.MODID)
public class MAYCore {
    public static final String MODID = "may";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MAYCore(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        MAYBlocks.BLOCKS.register(modEventBus);
        FoodTraits.TRAITS.register(modEventBus);
        MAYItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Hello from MAYCore setup!");
        event.enqueueWork(MAYInteractionManager::init);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello from server starting MAYCore");
    }
}

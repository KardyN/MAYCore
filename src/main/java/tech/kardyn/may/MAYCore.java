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
import tech.kardyn.may.common.items.MAYItems;

@Mod(MAYCore.MODID)
public class MAYCore {
    public static final String MODID = "may";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MAYCore(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        MAYBlocks.BLOCKS.register(modEventBus);
        MAYItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Hello from MAYCore setup!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello from server starting MAYCore");
    }
}

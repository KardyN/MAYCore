/**
 *     MAYCore  Copyright (C) 2026  KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tech.kardyn.may.common.blocks.MAYBlocks;

@Mod(MAYCore.MODID)
public class MAYCore
{
    public static final String MODID = "may";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MAYCore(FMLJavaModLoadingContext context)
    {
        LOGGER.info("Initializing MAYCoreMod");
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MAYBlocks.BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Hello From MAYCore Common Setup!\nIt doesn't do anything.");
    }
}

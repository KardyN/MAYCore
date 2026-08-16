/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.register;

import blusunrize.immersiveengineering.api.multiblocks.blocks.MultiblockRegistration;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockLogic;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.IEMultiblockBuilder;
import blusunrize.immersiveengineering.common.register.IEBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.kardyn.may.common.block.multiblock.MAYMultiblocks;
import tech.kardyn.may.common.block.multiblock.logic.RocketAssemblerLogic;

import static tech.kardyn.may.MAYCore.MODID;

public class MAYMultiblockLogic {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);

    public static final MultiblockRegistration<RocketAssemblerLogic.State> ROCKET_ASSEMBLER = metal(new RocketAssemblerLogic(), "rocket_assembler")
            .notMirrored()
            .structure(() -> MAYMultiblocks.ROCKET_ASSEMBLER)
            .build();

//    public static final MultiblockRegistration<HighPressureTankLogic.State> HIGH_PRESSURE_TANK = metal(new HighPressureTankLogic(), "high_pressure_tank")
//            .notMirrored()
//            .structure(() -> MAYMultiblocks.HIGH_PRESSURE_TANK)
//            .build();

    private static <S extends IMultiblockState>
    IEMultiblockBuilder<S> metal(IMultiblockLogic<S> logic, String name)
    {
        return new IEMultiblockBuilder<>(logic, name)
                .defaultBEs(BLOCK_ENTITIES)
                .defaultBlock(BLOCKS, ITEMS, IEBlocks.METAL_PROPERTIES_NO_OCCLUSION.get());
    }

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        IEMultiblockBuilder.handleModBusRegistrations(modEventBus);
    }
}

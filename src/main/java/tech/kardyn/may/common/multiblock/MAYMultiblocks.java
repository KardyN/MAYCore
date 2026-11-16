/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.multiblock;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import blusunrize.immersiveengineering.api.multiblocks.TemplateMultiblock;
import blusunrize.immersiveengineering.api.multiblocks.blocks.MultiblockRegistration;
import blusunrize.immersiveengineering.api.multiblocks.blocks.MultiblockRegistrationBuilder;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.ComparatorManager;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.IMultiblockComponent;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.RedstoneControl;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockLogic;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.api.multiblocks.blocks.registry.MultiblockItem;
import blusunrize.immersiveengineering.common.blocks.multiblocks.component.MultiblockGui;
import blusunrize.immersiveengineering.common.register.IEMenuTypes;
import flaxbeard.immersivepetroleum.common.blocks.IPMultiblockBase;
import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.kardyn.may.common.MAYMenuTypes;
import tech.kardyn.may.common.block.MAYBlocks;
import tech.kardyn.may.common.item.MAYItems;
import tech.kardyn.may.common.multiblock.instance.HeatExchangerMultiblock;
import tech.kardyn.may.common.multiblock.logic.HeatExchangerLogic;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MAYMultiblocks {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "may");
    private static final List<Consumer<IEventBus>> MOD_BUS_CALLBACKS = new ArrayList<>();

    public static void init(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPE.register(modEventBus);
        MOD_BUS_CALLBACKS.forEach(e -> {e.accept(modEventBus);});
    }

    public static void setup(FMLCommonSetupEvent event) {
        MultiblockHandler.registerMultiblock(HeatExchangerMultiblock.INSTANCE);
    }

    public static final MultiblockRegistration<HeatExchangerLogic.State> HEAT_EXCHANGER = registerMultiblock("heat_exchanger",
            new HeatExchangerLogic(),
            () -> HeatExchangerMultiblock.INSTANCE,
            (builder) -> builder
                    .redstone((st) -> st.rsState, HeatExchangerLogic.REDSTONE_IN)
                    .gui(MAYMenuTypes.HEAT_EXCHANGER),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .sound(SoundType.METAL)
                    .strength(3.0F, 15.0F)
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()
                    .isViewBlocking((state, blockReader, pos) -> false)
                    .noOcclusion()
                    .dynamicShape()
                    .pushReaction(PushReaction.BLOCK));

    public static <S extends IMultiblockState> MultiblockRegistration<S> registerMultiblock(String name, IMultiblockLogic<S> logic, Supplier<TemplateMultiblock> structure, @Nullable Consumer<MultiblockBuilder<S>> extras, BlockBehaviour.Properties prop) {
        MultiblockBuilder<S> builder = (((new MultiblockBuilder<>(logic, name)).structure(structure)).defaultBEs(MAYMultiblocks.BLOCK_ENTITY_TYPE)).customBlock(MAYBlocks.BLOCKS, MAYItems.ITEMS, (mb) -> new IPMultiblockBase<>(prop, mb), MultiblockItem::new);
        if (extras != null) {
            extras.accept(builder);
        }
        return builder.build(MOD_BUS_CALLBACKS::add);
    }

    public static class MultiblockBuilder<S extends IMultiblockState> extends MultiblockRegistrationBuilder<S, MAYMultiblocks.MultiblockBuilder<S>> {
        public MultiblockBuilder(IMultiblockLogic<S> logic, String name) {
            super(logic, ResourceUtils.ip(name));
        }

        public MultiblockBuilder<S> redstone(IMultiblockComponent.StateWrapper<S, RedstoneControl.RSState> getState, BlockPos... positions) {
            this.redstoneAware();
            return this.selfWrappingComponent(new RedstoneControl<>(getState, positions));
        }

        public MultiblockBuilder<S> comparator(ComparatorManager<S> comparator) {
            this.withComparator();
            return super.selfWrappingComponent(comparator);
        }

        public MultiblockBuilder<S> gui(IEMenuTypes.MultiblockContainer<S, ?> menu) {
            return this.component(new MultiblockGui<>(menu));
        }

        protected MAYMultiblocks.MultiblockBuilder<S> self() {
            return this;
        }
    }
}

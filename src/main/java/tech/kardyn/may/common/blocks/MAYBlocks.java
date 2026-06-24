/**
 *     MAYCore  Copyright (C) 2026 KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.dries007.tfc.common.blocks.GroundcoverBlock.SMALL;

public class MAYBlocks {
    public static final DeferredRegister<Block> BLOCKS;
    public static final Map<Indicator, MAYBlocks.Id<Block>> INDICATORS;

    static {
        BLOCKS = DeferredRegister.create(Registries.BLOCK, "may");
        INDICATORS = Helpers.mapOf(Indicator.class, (indicator) -> registerNoItem("indicator/" + indicator.name(), () -> getGroundcoverBlockSupplier(indicator)));
    }

    private static @NotNull GroundcoverBlock getGroundcoverBlockSupplier(Indicator indicator) {
        return new GroundcoverBlock(ExtendedProperties.of(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.05F, 0.0F).sound(SoundType.NETHER_ORE).noCollission().pushReaction(PushReaction.DESTROY)).cloneItem(indicator.getPickItem()), SMALL);
    }

    private static <T extends Block> MAYBlocks.Id<T> registerNoItem(String name, Supplier<T> blockSupplier) {
        return new Id<>(RegistrationHelpers.registerBlock(BLOCKS, TFCItems.ITEMS, name, blockSupplier, null));
    }

    private static <T extends Block> MAYBlocks.Id<T> register(String name, Supplier<T> blockSupplier) {
        return new Id<>(RegistrationHelpers.registerBlock(BLOCKS, TFCItems.ITEMS, name, blockSupplier, (Function<T, ? extends BlockItem>)((block) -> new BlockItem(block, new Item.Properties()))));
    }

    public record Id<T extends Block>(DeferredHolder<Block, T> holder) implements RegistryHolder<Block, T>, ItemLike {
        public @NotNull Item asItem() {
            return this.get().asItem();
        }
    }
}
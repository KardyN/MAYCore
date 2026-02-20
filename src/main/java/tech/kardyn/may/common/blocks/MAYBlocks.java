/**
 *     MAYCore  Copyright (C) 2026  KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tech.kardyn.may.common.blocks.rock.Indicator;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static net.dries007.tfc.common.blocks.GroundcoverBlock.PIXEL_HIGH;

public class MAYBlocks {
    public static final DeferredRegister<Block> BLOCKS;
    public static final Map<Indicator, RegistryObject<Block>> INDICATORS;

    static {
        BLOCKS = DeferredRegister.create(Registries.BLOCK, "may");
        INDICATORS = Helpers.mapOfKeys(
                Indicator.class,
                Indicator::hasIndicator,
                (indicator) -> registerBlock(
                        MAYBlocks.BLOCKS,
                        "ore/" + indicator.name(),
                        () -> new GroundcoverBlock(ExtendedProperties.of(BlockBehaviour.Properties.of()
                                .mapColor(MapColor.GRASS).strength(0.05F, 0.0F)
                                .sound(SoundType.NETHER_ORE).noCollission().pushReaction(PushReaction.DESTROY)),
                                PIXEL_HIGH,
                                indicator.getPickItem())));
    }

    public static <T extends Block> RegistryObject<T> registerBlock(DeferredRegister<Block> blocks, String name, Supplier<T> blockSupplier) {
        return blocks.register(name.toLowerCase(Locale.ROOT), blockSupplier);
    }
}

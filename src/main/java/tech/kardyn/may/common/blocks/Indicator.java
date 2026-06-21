/**
 *     MAYCore  Copyright (C) 2026  KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.blocks;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;

public enum Indicator {
    BITUMINOUS_COAL(true, TFCItems.ORES.get(Ore.BITUMINOUS_COAL)),
    LIGNITE(true, TFCItems.ORES.get(Ore.LIGNITE)),
    GYPSUM(true, TFCItems.ORES.get(Ore.GYPSUM)),
    GRAPHITE(true, TFCItems.ORES.get(Ore.GRAPHITE)),
    SULFUR(true, TFCItems.ORES.get(Ore.SULFUR)),
    CINNABAR(true, TFCItems.ORES.get(Ore.CINNABAR)),
    CRYOLITE(true, TFCItems.ORES.get(Ore.CRYOLITE)),
    SALTPETER(true, TFCItems.ORES.get(Ore.SALTPETER)),
    SYLVITE(true, TFCItems.ORES.get(Ore.SYLVITE)),
    BORAX(true, TFCItems.ORES.get(Ore.BORAX)),
    HALITE(true, TFCItems.ORES.get(Ore.HALITE)),
    AMETHYST(false),
    DIAMOND(false),
    EMERALD(false),
    LAPIS_LAZULI(false),
    OPAL(false),
    PYRITE(false),
    RUBY(false),
    SAPPHIRE(false),
    TOPAZ(false);

    private final boolean hasIndicator;
    private final @Nullable ItemLike pickItem;

    Indicator(boolean hasIndicator) {
        this(hasIndicator, null);
    }

    Indicator(boolean hasIndicator, @Nullable ItemLike pickItem) {
        this.hasIndicator = hasIndicator;
        this.pickItem = pickItem;
    }

    public boolean hasIndicator() {
        return this.hasIndicator;
    }

    public @Nullable ItemLike getPickItem(){
        return this.pickItem;
    }

}

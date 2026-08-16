/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.block;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.level.ItemLike;

public enum Indicator {
    BITUMINOUS_COAL(TFCItems.ORES.get(Ore.BITUMINOUS_COAL)),
    LIGNITE(TFCItems.ORES.get(Ore.LIGNITE)),
    GYPSUM(TFCItems.ORES.get(Ore.GYPSUM)),
    GRAPHITE(TFCItems.ORES.get(Ore.GRAPHITE)),
    SULFUR(TFCItems.ORES.get(Ore.SULFUR)),
    CINNABAR(TFCItems.ORES.get(Ore.CINNABAR)),
    CRYOLITE(TFCItems.ORES.get(Ore.CRYOLITE)),
    SALTPETER(TFCItems.ORES.get(Ore.SALTPETER)),
    SYLVITE(TFCItems.ORES.get(Ore.SYLVITE)),
    BORAX(TFCItems.ORES.get(Ore.BORAX)),
    HALITE(TFCItems.ORES.get(Ore.HALITE));

    private final ItemLike pickItem;

    Indicator(ItemLike pickItem) {
        this.pickItem = pickItem;
    }

    public ItemLike getPickItem(){
        return this.pickItem;
    }

}

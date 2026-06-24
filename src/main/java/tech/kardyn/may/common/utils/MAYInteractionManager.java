/**
 *     MAYCore  Copyright (C) 2026 KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.utils;

import net.dries007.tfc.util.BlockItemPlacement;
import net.dries007.tfc.util.InteractionManager;
import tech.kardyn.may.common.blocks.Indicator;
import tech.kardyn.may.common.blocks.MAYBlocks;

public class MAYInteractionManager {
    public static void init(){
        for (Indicator indicator : Indicator.values()) {
            InteractionManager.registerBlock(new BlockItemPlacement(indicator.getPickItem(), MAYBlocks.INDICATORS.get(indicator)));
        }
    }
}

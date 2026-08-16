/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.block.multiblock;


import net.minecraft.core.BlockPos;
import tech.kardyn.may.common.block.multiblock.logic.RocketAssemblerLogic;
import tech.kardyn.may.common.register.MAYMultiblockLogic;

import static tech.kardyn.may.common.util.ResourceLocationManager.ieResLoc;

public class RocketAssemblerMultiblock extends MAYTemplateMultiblock {
    public RocketAssemblerMultiblock()
    {
        super(ieResLoc("multiblocks/lightning_rod"),
                RocketAssemblerLogic.MASTER_OFFSET, new BlockPos(1, 1, 2), new BlockPos(3, 3, 3),
                MAYMultiblockLogic.ROCKET_ASSEMBLER);
    }

    @Override
    public float getManualScale()
    {
        return 12;
    }
}

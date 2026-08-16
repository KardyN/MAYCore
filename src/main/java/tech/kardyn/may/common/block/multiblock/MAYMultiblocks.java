/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.block.multiblock;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;

import java.util.ArrayList;
import java.util.List;

public class MAYMultiblocks {
    public static final List<MultiblockHandler.IMultiblock> MAY_MULTIBLOCKS = new ArrayList<>();

    public static MAYTemplateMultiblock ROCKET_ASSEMBLER;

    public static void init() {
        ROCKET_ASSEMBLER = register(new RocketAssemblerMultiblock());
    }

    private static <T extends MultiblockHandler.IMultiblock>
    T register(T multiblock)
    {
        MAY_MULTIBLOCKS.add(multiblock);
        MultiblockHandler.registerMultiblock(multiblock);
        return multiblock;
    }
}

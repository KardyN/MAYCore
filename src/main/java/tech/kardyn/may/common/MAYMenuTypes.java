/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common;

import blusunrize.immersiveengineering.common.register.IEMenuTypes;
import tech.kardyn.may.common.gui.HeatExchangerContainer;
import tech.kardyn.may.common.multiblock.logic.HeatExchangerLogic;

public class MAYMenuTypes {
    public static final IEMenuTypes.MultiblockContainer<HeatExchangerLogic.State, HeatExchangerContainer> HEAT_EXCHANGER = IEMenuTypes.registerMultiblock("heat_exchanger", HeatExchangerContainer::makeServer, HeatExchangerContainer::makeClient);

    public static void forceClassLoad() {
    }
}

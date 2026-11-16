package tech.kardyn.may.common;

import blusunrize.immersiveengineering.common.register.IEMenuTypes;
import tech.kardyn.may.common.gui.HeatExchangerContainer;
import tech.kardyn.may.common.multiblock.logic.heat_exchanger.HeatExchangerLogic;

public class MAYMenuTypes {
    public static final IEMenuTypes.MultiblockContainer<HeatExchangerLogic.State, HeatExchangerContainer> HEAT_EXCHANGER = IEMenuTypes.registerMultiblock("heat_exchanger", HeatExchangerContainer::makeServer, HeatExchangerContainer::makeClient);

    public static void forceClassLoad() {
    }
}

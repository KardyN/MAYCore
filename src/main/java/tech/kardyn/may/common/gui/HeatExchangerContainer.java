package tech.kardyn.may.common.gui;

import blusunrize.immersiveengineering.common.gui.IEContainerMenu;
import flaxbeard.immersivepetroleum.common.gui.MultiblockAwareGuiContainer;
import flaxbeard.immersivepetroleum.common.util.inventory.FluidTankFiltered;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import tech.kardyn.may.common.multiblock.instance.HeatExchangerMultiblock;
import tech.kardyn.may.common.multiblock.logic.heat_exchanger.HeatExchangerLogic;

public class HeatExchangerContainer extends MultiblockAwareGuiContainer {
    public final FluidTankFiltered tubeInput;
    public final FluidTankFiltered tubeOutput;
    public final FluidTankFiltered shellInput;
    public final FluidTankFiltered shellOutput;

    private HeatExchangerContainer(IEContainerMenu.MenuContext ctx, HeatExchangerLogic.Tanks tanks) {
        super(ctx, HeatExchangerMultiblock.INSTANCE);
        this.tubeInput = tanks.tubeInput();
        this.tubeOutput = tanks.tubeOutput();
        this.shellInput = tanks.shellInput();
        this.shellOutput = tanks.shellOutput();
        this.addGenericData(this.tubeInput.getContainerData());
        this.addGenericData(this.tubeOutput.getContainerData());
        this.addGenericData(this.shellInput.getContainerData());
        this.addGenericData(this.shellOutput.getContainerData());
    }

    public static HeatExchangerContainer makeServer(MenuType<?> type, int id, Inventory player, IEContainerMenu.MultiblockMenuContext<HeatExchangerLogic.State> ctx) {
        HeatExchangerLogic.State state = ctx.mbContext().getState();
        HeatExchangerLogic.Tanks tanks = state.tanks;
        return new HeatExchangerContainer(multiblockCtx(type, id, ctx), tanks);
    }

    public static HeatExchangerContainer makeClient(MenuType<?> type, int id, Inventory player) {
        HeatExchangerLogic.Tanks tanks = HeatExchangerLogic.Tanks.client();
        return new HeatExchangerContainer(clientCtx(type, id), tanks);
    }
}

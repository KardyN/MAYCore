package tech.kardyn.may.common.multiblock.instance;

import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.IPMultiblockProperties;
import net.minecraft.core.BlockPos;
import tech.kardyn.may.common.multiblock.MAYMultiblocks;
import tech.kardyn.may.common.multiblock.MAYTemplateMultiblock;
import tech.kardyn.may.common.util.MAYResLocManager;

import java.util.function.Consumer;

public class HeatExchangerMultiblock extends MAYTemplateMultiblock {
    public static final HeatExchangerMultiblock INSTANCE = new HeatExchangerMultiblock();

    public HeatExchangerMultiblock() {
        super(MAYResLocManager.mayResLoc("multiblocks/heat_exchanger"), new BlockPos(2, 0, 0), new BlockPos(2, 0, 0), new BlockPos(5, 1, 1), MAYMultiblocks.HEAT_EXCHANGER);
    }

    public float getManualScale() {
        return 1.0F;
    }

    public void initializeClient(Consumer<ClientMultiblocks.MultiblockManualData> consumer) {
        consumer.accept(new IPMultiblockProperties(this, 1.5F, 0.5F, 2.5F));
    }
}

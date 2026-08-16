/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.block.multiblock.logic;

import blusunrize.immersiveengineering.api.energy.MutableEnergyStorage;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.IServerTickableComponent;
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IInitialMultiblockContext;
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockLevel;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockLogic;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.api.multiblocks.blocks.util.RelativeBlockFace;
import blusunrize.immersiveengineering.api.multiblocks.blocks.util.ShapeType;
import blusunrize.immersiveengineering.common.blocks.multiblocks.shapes.LightningRodShapes;
import blusunrize.immersiveengineering.common.config.IEServerConfig;
import blusunrize.immersiveengineering.common.util.EnergyHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class RocketAssemblerLogic implements IMultiblockLogic<RocketAssemblerLogic.State>, IServerTickableComponent<RocketAssemblerLogic.State> {
    public static final BlockPos MASTER_OFFSET = new BlockPos(1, 1, 1);

    @Override
    public void tickServer(IMultiblockContext<RocketAssemblerLogic.State> context)
    {
        final RocketAssemblerLogic.State state = context.getState();
        final IMultiblockLevel level = context.getLevel();
        if(state.energy.getEnergyStored() > 0)
            for(final Supplier<@Nullable IEnergyStorage> outputRef : state.energyOutputs)
            {
                final IEnergyStorage output = outputRef.get();
                if(output!=null)
                {
                    final int accepted = output.receiveEnergy(state.energy.getEnergyStored(), false);
                    state.energy.modifyEnergyStored(-accepted);
                }
            }
    }

    @Override
    public RocketAssemblerLogic.State createInitialState(IInitialMultiblockContext<RocketAssemblerLogic.State> capabilitySource)
    {
        return new RocketAssemblerLogic.State(capabilitySource);
    }


    @Override
    public void registerCapabilities(CapabilityRegistrar<RocketAssemblerLogic.State> register)
    {
        register.register(Capabilities.EnergyStorage.BLOCK, (state, position) -> {
            final BlockPos posInMultiblock = position.posInMultiblock();
            if(position.side()==null||(posInMultiblock.getY()==1&&(posInMultiblock.getX()+posInMultiblock.getZ())%2==1))
                return state.energy;
            else
                return null;
        });
    }


    @Override
    public Function<BlockPos, VoxelShape> shapeGetter(ShapeType forType)
    {
        return LightningRodShapes.SHAPE_GETTER;
    }

    public static class State implements IMultiblockState
    {
        private final MutableEnergyStorage energy = new MutableEnergyStorage(
                IEServerConfig.MACHINES.lightning_output.get(), 0, IEServerConfig.MACHINES.lightning_output.get()
        );
        private final ImmutableList<Supplier<@Nullable IEnergyStorage>> energyOutputs;

        public State(IInitialMultiblockContext<RocketAssemblerLogic.State> capabilitySource)
        {
            ImmutableList.Builder<Supplier<@Nullable IEnergyStorage>> builder = ImmutableList.builder();
            for(RelativeBlockFace face : RelativeBlockFace.HORIZONTAL)
                builder.add(capabilitySource.getCapabilityAt(
                        Capabilities.EnergyStorage.BLOCK,
                        face.offsetRelative(MASTER_OFFSET, 2),
                        face.getOpposite()
                ));
            this.energyOutputs = builder.build();
        }

        @Override
        public void writeSaveNBT(CompoundTag nbt, HolderLookup.Provider provider)
        {
            EnergyHelper.serializeTo(energy, nbt, provider);
        }

        @Override
        public void readSaveNBT(CompoundTag nbt, HolderLookup.Provider provider)
        {
            EnergyHelper.deserializeFrom(energy, nbt, provider);
        }
    }
}

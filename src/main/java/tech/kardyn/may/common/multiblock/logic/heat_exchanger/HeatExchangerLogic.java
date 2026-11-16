package tech.kardyn.may.common.multiblock.logic.heat_exchanger;

import blusunrize.immersiveengineering.api.energy.AveragingEnergyStorage;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.IClientTickableComponent;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.IServerTickableComponent;
import blusunrize.immersiveengineering.api.multiblocks.blocks.component.RedstoneControl;
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IInitialMultiblockContext;
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockLogic;
import blusunrize.immersiveengineering.api.multiblocks.blocks.logic.IMultiblockState;
import blusunrize.immersiveengineering.api.multiblocks.blocks.util.CapabilityPosition;
import blusunrize.immersiveengineering.api.multiblocks.blocks.util.RelativeBlockFace;
import blusunrize.immersiveengineering.api.multiblocks.blocks.util.ShapeType;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcessor;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext;
import blusunrize.immersiveengineering.common.fluids.ArrayFluidHandler;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.logic.IReadWriteNBT;
import flaxbeard.immersivepetroleum.common.util.FluidHelper;
import flaxbeard.immersivepetroleum.common.util.inventory.FluidTankFiltered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import tech.kardyn.may.MAYCore;
import tech.kardyn.may.common.crafting.HeatExchangerRecipe;
import tech.kardyn.may.common.multiblock.shape.HeatExchangerShape;

import java.util.function.Function;

public class HeatExchangerLogic implements IMultiblockLogic<HeatExchangerLogic.State>, IServerTickableComponent<HeatExchangerLogic.State>, IClientTickableComponent<HeatExchangerLogic.State> {
    public static final int TANK_TUBE_IN = 0;
    public static final int TANK_TUBE_OUT = 1;
    public static final int TANK_SHELL_IN = 2;
    public static final int TANK_SHELL_OUT = 3;
    public static final CapabilityPosition TUBE_FLUID_IN = new CapabilityPosition(0, 0, 0, RelativeBlockFace.DOWN);
    public static final CapabilityPosition TUBE_FLUID_OUT = new CapabilityPosition(4, 0, 0, RelativeBlockFace.DOWN);
    public static final CapabilityPosition SHELL_FLUID_IN = new CapabilityPosition(3, 0, 0, RelativeBlockFace.UP);
    public static final CapabilityPosition SHELL_FLUID_OUT = new CapabilityPosition(1, 0, 0, RelativeBlockFace.UP);
    public static final BlockPos REDSTONE_IN = new BlockPos(2, 0, 0);

    @Override
    public State createInitialState(IInitialMultiblockContext<State> capabilitySource) {
        return new State(capabilitySource);
    }

    @Override
    public void tickClient(IMultiblockContext<State> context) {
        final State state = context.getState();
        if (state.cooldownTicks > 0) {
            state.cooldownTicks--;
        }

        if (state.wasActive) {
            state.cooldownTicks = 20;
        }
    }

    @Override
    public void tickServer(IMultiblockContext<State> context) {
        boolean update = false;

        State state = context.getState();
        Level level = context.getLevel().getRawLevel();
        if (state.wasActive) {
            state.wasActive = false;
            update = true;
        }

        if (state.rsState.isEnabled(context))
            if (state.processor.getQueueSize() < state.processor.getMaxQueueSize())
                if (state.tanks.tubeInput().getFluidAmount() > 0 && state.tanks.shellInput().getFluidAmount() > 0) {
                    RecipeHolder<HeatExchangerRecipe> holder = HeatExchangerRecipe.findRecipe(state.tanks.tubeInput().getFluid().copyWithAmount(1000), state.tanks.shellInput().getFluid().copyWithAmount(1000));
                    if (holder != null) {
                        int[] inputTanks = new int[]{TANK_TUBE_IN, TANK_SHELL_IN};
                        int[] inputAmounts = new int[]{1000, 1000};
                        HeatExchangerProcess process = new HeatExchangerProcess(holder, inputTanks, inputAmounts);
                        if (state.processor.addProcessToQueue(process, level, true)) {
                            state.processor.addProcessToQueue(process, level, false);
                            update = true;
                        }
                    }
                }

        if (state.processor.tickServer(state, context.getLevel(), !state.processor.getQueue().isEmpty())) {
            update = true;
            state.wasActive = true;
        }

        if (state.tanks.tubeOutput().getFluidAmount() > 0) {
            BlockPos tubeOutPos = context.getLevel().toAbsolute(TUBE_FLUID_OUT.posInMultiblock()).above();
            update |= FluidUtil.getFluidHandler(level, tubeOutPos, Direction.DOWN).map(output -> {
                boolean ret = false;
                FluidStack target = state.tanks.tubeOutput().getFluid();
                target = FluidHelper.copyFluid(target, Math.min(target.getAmount(), 1000));
                int accepted = output.fill(target, IFluidHandler.FluidAction.SIMULATE);
                if (accepted > 0) {
                    int drained = output.fill(FluidHelper.copyFluid(target, Math.min(target.getAmount(), accepted)), IFluidHandler.FluidAction.EXECUTE);
                    state.tanks.tubeOutput().drain(new FluidStack(target.getFluid(), drained), IFluidHandler.FluidAction.EXECUTE);
                    ret = true;
                }

                return ret;
            }).orElse(false);
        }

        if (state.tanks.shellOutput().getFluidAmount() > 0) {
            BlockPos shellOutPos = context.getLevel().toAbsolute(SHELL_FLUID_OUT.posInMultiblock()).below();
            update |= FluidUtil.getFluidHandler(level, shellOutPos, Direction.UP).map(output -> {
                boolean ret = false;
                FluidStack target = state.tanks.shellOutput().getFluid();
                target = FluidHelper.copyFluid(target, Math.min(target.getAmount(), 1000));
                int accepted = output.fill(target, IFluidHandler.FluidAction.SIMULATE);
                if (accepted > 0) {
                    int drained = output.fill(FluidHelper.copyFluid(target, Math.min(target.getAmount(), accepted)), IFluidHandler.FluidAction.EXECUTE);
                    state.tanks.shellOutput().drain(new FluidStack(target.getFluid(), drained), IFluidHandler.FluidAction.EXECUTE);
                    ret = true;
                }

                return ret;
            }).orElse(false);
        }

        if (update) {
            context.markDirtyAndSync();
        }
    }

    @Override
    public void registerCapabilities(CapabilityRegistrar<State> register) {
        register.register(Capabilities.FluidHandler.BLOCK, (state, pos) -> {
            if (TUBE_FLUID_IN.equalsOrNullFace(pos)) {
                return state.tubeFluidInput;
            } else if (TUBE_FLUID_OUT.equalsOrNullFace(pos)) {
                return state.tubeFluidOutput;
            } else if (SHELL_FLUID_IN.equalsOrNullFace(pos)) {
                return state.shellFluidInput;
            } else if (SHELL_FLUID_OUT.equalsOrNullFace(pos)){
                return state.shellFluidOutput;
            }
            return null;
        });
    }

    @Override
    public Function<BlockPos, VoxelShape> shapeGetter(ShapeType forType) {
        return HeatExchangerShape.GETTER;
    }

    public static class State implements IMultiblockState, ProcessContext.ProcessContextInMachine<HeatExchangerRecipe> {
        public final AveragingEnergyStorage energy = new AveragingEnergyStorage(1);
        public final RedstoneControl.RSState rsState = RedstoneControl.RSState.enabledByDefault();
        public final HeatExchangerLogic.Tanks tanks = HeatExchangerLogic.Tanks.server();
        public final MultiblockProcessor.InMachineProcessor<HeatExchangerRecipe> processor;
        private final IFluidHandler tubeFluidInput;
        private final IFluidHandler tubeFluidOutput;
        private final IFluidHandler shellFluidInput;
        private final IFluidHandler shellFluidOutput;

        /** Flickering avoidance for the "Active" Texture overlay (info from IP)*/
        public int cooldownTicks = 0;
        public boolean wasActive = false;

        public State(IInitialMultiblockContext<State> context) {
            this.processor = new MultiblockProcessor.InMachineProcessor<>(1, 0.0F, 1, context.getMarkDirtyRunnable(), State::getRecipeForId);
            this.tubeFluidInput = ArrayFluidHandler.fillOnly(this.tanks.tubeInput(), context.getMarkDirtyRunnable());
            this.tubeFluidOutput = ArrayFluidHandler.drainOnly(this.tanks.tubeOutput(), context.getMarkDirtyRunnable());
            this.shellFluidInput = ArrayFluidHandler.fillOnly(this.tanks.shellInput(), context.getMarkDirtyRunnable());
            this.shellFluidOutput = ArrayFluidHandler.drainOnly(this.tanks.shellOutput(), context.getMarkDirtyRunnable());
        }

        private static HeatExchangerRecipe getRecipeForId(Level level, ResourceLocation id) {
            return HeatExchangerRecipe.recipes.get(id).value();
        }

        public void writeSaveNBT(CompoundTag nbt, HolderLookup.Provider provider) {
            this.rsState.writeSaveNBT(nbt, provider);
            nbt.put("tanks", this.tanks.writeNBT(provider));
            nbt.put("energy", this.energy.serializeNBT(provider));
            nbt.put("processor", this.processor.toNBT(provider));
            nbt.putBoolean("wasActive", this.wasActive);
        }

        public void readSaveNBT(CompoundTag nbt, HolderLookup.Provider provider) {
            this.rsState.readSaveNBT(nbt, provider);
            this.tanks.readNBT(nbt.getCompound("tanks"), provider);
            this.energy.deserializeNBT(provider, nbt.getCompound("energy"));
            this.processor.fromNBT(nbt.get("processor"), HeatExchangerProcess::new, provider);
            this.wasActive = nbt.getBoolean("wasActive");
        }

        public void writeSyncNBT(CompoundTag nbt, HolderLookup.Provider provider) {
            this.writeSaveNBT(nbt, provider);
        }

        public void readSyncNBT(CompoundTag nbt, HolderLookup.Provider provider) {
            this.readSaveNBT(nbt, provider);
        }

        @Override
        public AveragingEnergyStorage getEnergy() {
            return this.energy;
        }

        public IFluidTank[] getInternalTanks() {
            return this.tanks.array();
        }

        public int[] getOutputTanks() {
            return new int[]{TANK_TUBE_OUT, TANK_SHELL_OUT};
        }

        public boolean additionalCanProcessCheck(MultiblockProcess<HeatExchangerRecipe, ?> process, Level level) {
            // Assuming all recipes are 1:1 mB
            return true;
//            return (this.tanks.tubeOutput().getCapacity() > this.tanks.tubeOutput().getFluidAmount())
//                    && (this.tanks.shellOutput().getCapacity() > this.tanks.shellOutput().getFluidAmount());
        }
    }

    public static class Tanks implements IReadWriteNBT {
        public static final int CAPACITY = 12000;
        private final FluidTankFiltered tubeInput;
        private final FluidTankFiltered tubeOutput;
        private final FluidTankFiltered shellInput;
        private final FluidTankFiltered shellOutput;
        private final IFluidTank[] array;

        public static Tanks server() {
            return new Tanks(
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY));
        }

        public static Tanks client() {
            return new Tanks(
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY),
                    new FluidTankFiltered(CAPACITY));
        }

        private Tanks(FluidTankFiltered tubeInput, FluidTankFiltered tubeOutput, FluidTankFiltered shellInput, FluidTankFiltered shellOutput) {
            this.tubeInput = tubeInput;
            this.tubeOutput = tubeOutput;
            this.shellInput = shellInput;
            this.shellOutput = shellOutput;
            this.array = new IFluidTank[]{tubeInput, tubeOutput, shellInput, shellOutput};
        }

        public void readNBT(CompoundTag nbt, HolderLookup.Provider provider) {
            this.tubeInput.readFromNBT(nbt.getCompound("tubeInput"), provider);
            this.tubeOutput.readFromNBT(nbt.getCompound("tubeOutput"), provider);
            this.shellInput.readFromNBT(nbt.getCompound("shellInput"), provider);
            this.shellOutput.readFromNBT(nbt.getCompound("shellOutput"), provider);
        }

        public CompoundTag writeNBT(HolderLookup.Provider provider) {
            CompoundTag nbt = new CompoundTag();
            nbt.put("tubeInput", this.tubeInput.writeToNBT(new CompoundTag(), provider));
            nbt.put("tubeOutput", this.tubeOutput.writeToNBT(new CompoundTag(), provider));
            nbt.put("shellInput", this.shellInput.writeToNBT(new CompoundTag(), provider));
            nbt.put("shellOutput", this.shellOutput.writeToNBT(new CompoundTag(), provider));
            return nbt;
        }

        public FluidTankFiltered tubeInput() {
            return this.tubeInput;
        }

        public FluidTankFiltered tubeOutput() {
            return this.tubeOutput;
        }

        public FluidTankFiltered shellInput() {
            return this.shellInput;
        }

        public FluidTankFiltered shellOutput() {
            return this.shellOutput;
        }

        public IFluidTank[] array() {
            return this.array;
        }
    }
}

package com.github.mochi_753.mekanism_replicate.block;

import com.github.mochi_753.mekanism_replicate.register.ModBlocks;
import com.github.mochi_753.mekanism_replicate.register.ModChemicals;
import mekanism.api.*;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableBoolean;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// FIXME: 物流トランスポーターとユニバーサルケーブルが繋がらない
// REVIEW: インベントリなどがちゃんと保存できているか
// TODO: GUIを実装する
public class TileEntityReplicator extends TileEntityConfigurableMachine {
    public static final int MAX_CHEMICAL = FluidType.BUCKET_VOLUME;
    public static final int CHEMICAL_NEEDED = FluidType.BUCKET_VOLUME / 100;
    private static final int BASE_TICKS_REQUIRED = 120;
    public int ticksRequired = BASE_TICKS_REQUIRED;
    public int operatingTicks;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class,
            methodNames = {"getInput", "getInputCapacity", "getInputNeeded", "getInputFilledPercentage"}, docPlaceholder = "input tank")
    public BasicChemicalTank chemicalTank;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class,
            methodNames = "getInputItem", docPlaceholder = "output slot")
    InputInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class,
            methodNames = "getOutputItem", docPlaceholder = "output slot")
    OutputInventorySlot outputSlot;
    private boolean usedEnergy = false;
    private MachineEnergyContainer<TileEntityReplicator> energyContainer;

    public TileEntityReplicator(BlockPos pos, BlockState state) {
        super(ModBlocks.REPLICATOR, pos, state);
        this.ejectorComponent = new TileComponentEjector(this);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSide(facingSupplier);
        builder.addTank(chemicalTank = (BasicChemicalTank) BasicChemicalTank.inputModern(MAX_CHEMICAL, chemical -> chemical.is(ModChemicals.MIRACLE_SUBSTANCE),
                chemical -> chemical.is(ModChemicals.MIRACLE_SUBSTANCE), listener));
        return builder.build();
    }

    @Override
    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(facingSupplier);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, listener), RelativeSide.BACK);
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(facingSupplier);
        builder.addSlot(inputSlot = InputInventorySlot.at(listener, 80, 20));
        builder.addSlot(outputSlot = OutputInventorySlot.at(listener, 116, 50));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        long clientEnergyUsed = 0L;

        if (canFunction() && replicate()) {
            long energyPerTick = energyContainer.getEnergyPerTick();
            clientEnergyUsed = energyContainer.extract(energyPerTick, Action.EXECUTE, AutomationType.INTERNAL);
        }
        usedEnergy = clientEnergyUsed > 0L;

        return sendUpdatePacket || usedEnergy;
    }

    private boolean replicate() {
        ItemStack inputStack = inputSlot.getStack();
        ItemStack outputStack = outputSlot.getStack();

        boolean canReplicate = !inputSlot.isEmpty() &&
                chemicalTank.getStored() >= CHEMICAL_NEEDED &&
                (outputSlot.isEmpty() || (ItemStack.isSameItemSameComponents(inputStack, outputStack)) && outputStack.getCount() < outputStack.getMaxStackSize());

        if (canReplicate) {
            operatingTicks++;
            if (ticksRequired >= operatingTicks) {
                if (outputSlot.isEmpty()) {
                    outputSlot.setStack(inputStack.copyWithCount(1));
                } else {
                    outputStack.grow(1);
                    outputSlot.setStack(outputStack);
                }

                chemicalTank.extract(CHEMICAL_NEEDED, Action.EXECUTE, AutomationType.INTERNAL);
                operatingTicks = 0;
            }
        } else {
            operatingTicks = 0;
        }

        return canReplicate;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt(SerializationConstants.PROGRESS, operatingTicks);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        operatingTicks = tag.getInt(SerializationConstants.PROGRESS);
    }

    public MachineEnergyContainer<TileEntityReplicator> getEnergyContainer() {
        return energyContainer;
    }

    public boolean usedEnergy() {
        return usedEnergy;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableBoolean.create(this::usedEnergy, value -> usedEnergy = value));
    }
}

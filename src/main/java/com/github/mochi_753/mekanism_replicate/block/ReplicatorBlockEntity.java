package com.github.mochi_753.mekanism_replicate.block;

import com.github.mochi_753.mekanism_replicate.gui.ReplicatorMenu;
import com.github.mochi_753.mekanism_replicate.register.ModBlockEntities;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReplicatorBlockEntity extends BlockEntity implements MenuProvider {
    private static final int MAX_PROGRESS = 120;
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private final ItemStackHandler handler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot != OUTPUT_SLOT;
        }
    };
    // protected final ContainerData data;
    private int PROGRESS;

    public ReplicatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.REPLICATOR.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        tag.put("inventory", handler.serializeNBT(registries));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        handler.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            inventory.setItem(i, handler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void tick() {
        if (level == null || level.isClientSide) {
        }


    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.mekanism_replicate.replicator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new ReplicatorMenu(containerId, inventory, this);
    }

    private void replicate() {
        ItemStack inputStack = handler.getStackInSlot(INPUT_SLOT).copy();
        ItemStack outputStack = handler.getStackInSlot(OUTPUT_SLOT).copy();

        if (inputStack.isEmpty()) {
            resetProgress();
            return;
        }

        if (ItemStack.isSameItemSameComponents(inputStack, outputStack)) {
            resetProgress();
            return;
        }

        setProgress(getProgress() + 1);

        if (MAX_PROGRESS <= PROGRESS) {
            if (outputStack.isEmpty()) {
                handler.setStackInSlot(OUTPUT_SLOT, inputStack.copyWithCount(1));
            } else if (ItemStack.isSameItemSameComponents(inputStack, outputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
                outputStack.grow(1);
                handler.setStackInSlot(OUTPUT_SLOT, outputStack);
            }

            // Check Antimatter Replicate
            if (inputStack.is(MekanismItems.ANTIMATTER_PELLET) || outputStack.is(MekanismItems.ANTIMATTER_PELLET)) {
                level.explode(null,
                        this.getBlockPos().getX() + 0.5,
                        this.getBlockPos().getY() + 0.5,
                        this.getBlockPos().getZ() + 0.5,
                        32.0F,
                        Level.ExplosionInteraction.BLOCK
                );

                handler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                level.removeBlock(this.getBlockPos(), false);
            }

            resetProgress();
        }
        if (!ItemStack.isSameItemSameComponents(inputStack, outputStack)) {
            resetProgress();
        }


    }

    private void resetProgress() {
        setProgress(0);
    }

    public int getProgress() {
        return PROGRESS;
    }

    public void setProgress(int progress) {
        this.PROGRESS = progress;
    }

    public int getMaxProgress() {
        return MAX_PROGRESS;
    }
}

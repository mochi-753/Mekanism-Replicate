package com.github.mochi_753.mekanism_replicate.block;

import com.github.mochi_753.mekanism_replicate.gui.ReplicatorMenu;
import com.github.mochi_753.mekanism_replicate.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ReplicatorBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    // protected final ContainerData data;

    private static final ItemStackHandler handler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
        }
    };

    public ReplicatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.REPLICATOR.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", handler.serializeNBT(registries));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        handler.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public void tick() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.mekanism_replicate.replicator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ReplicatorMenu(containerId, inventory, this);
    }
}

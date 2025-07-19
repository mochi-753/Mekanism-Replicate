package com.github.mochi_753.mekanism_replicate.gui;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.block.ReplicatorBlockEntity;
import com.github.mochi_753.mekanism_replicate.register.ModBlocks;
import com.github.mochi_753.mekanism_replicate.register.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ReplicatorMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int BE_INVENTORY_SLOT_COUNT = 2;
    public final ReplicatorBlockEntity replicatorBlockEntity;
    private final Level level;

    public ReplicatorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ReplicatorMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.REPLICATOR_MENU.get(), containerId);
        this.replicatorBlockEntity = ((ReplicatorBlockEntity) blockEntity);
        this.level = inventory.player.level();

        addPlayerInventory(inventory);

        this.addSlot(new SlotItemHandler(replicatorBlockEntity.getHandler(), 0, 80, 20));
        this.addSlot(new SlotItemHandler(replicatorBlockEntity.getHandler(), 1, 116, 50));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copyStack = stack.copy();

        // スロットがバニラのものかどうか
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(stack, BE_INVENTORY_FIRST_SLOT_INDEX,
                    BE_INVENTORY_FIRST_SLOT_INDEX + BE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < BE_INVENTORY_FIRST_SLOT_INDEX + BE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(stack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            MekanismReplicate.LOGGER.error("Invalid slotIndex:{}", index);
            return ItemStack.EMPTY;
        }

        if (stack.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(player, stack);
        return copyStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, replicatorBlockEntity.getBlockPos()), player, ModBlocks.REPLICATOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}

package retr0.globalenderchest.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static retr0.globalenderchest.GlobalEnderChest.MOD_ID;

public class ContainerMigrationScreenHandler extends GenericContainerScreenHandler {
    private static final Text MIGRATION_SUCCESS_MESSAGE =
            Text.literal("(" + MOD_ID + ") ").append(
            Text.translatable(MOD_ID + ".migration.success").formatted(Formatting.AQUA, Formatting.ITALIC));

    private boolean shouldClose = false;

    private ContainerMigrationScreenHandler(
            ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows)
    {
        super(type, syncId, playerInventory, inventory, rows);

        for (var i = 0; i < inventory.size(); i++) {
            var slot = slots.get(i);
            slots.set(i, new Slot(inventory, slot.getIndex(), slot.x, slot.y) {
                public boolean canInsert(ItemStack stack) {
                    return false;
                }
            });
        }
    }



    public static ContainerMigrationScreenHandler createGeneric9x3(
            int syncId, PlayerInventory playerInventory, Inventory inventory)
    {
        return new ContainerMigrationScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }



    @Override
    public boolean canUse(PlayerEntity player) {
        return !shouldClose && super.canUse(player);
    }



    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);

        // Force screen to close if all slots have been cleared (+ cursor stack) is empty.
        if (!getCursorStack().isEmpty()) return;
        for (Slot slot : slots)
            if (!(slot.inventory instanceof PlayerInventory) && !slot.getStack().isEmpty()) return;

        shouldClose = true;
    }



    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        if (!shouldClose) return;

        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 0.4f, 0.572f);
        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 0.6f, 1.144f);
        player.playSound(SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.MASTER, 1.0f, 1.2f);
        player.sendMessage(MIGRATION_SUCCESS_MESSAGE);
    }
}

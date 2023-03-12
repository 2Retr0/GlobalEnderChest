package retr0.globalenderchest;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class TestScreenHandler extends GenericContainerScreenHandler {
    private TestScreenHandler(
        ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows)
    {
        super(type, syncId, playerInventory, inventory, rows);

        // TODO: WHERE ARE THE SCREENHANDLERS OPENED ON THE CLIENT?!?!?!
        for (var i = 0; i < inventory.size(); ++i) {
            var slot = slots.get(i);
            slots.set(i, new Slot(inventory, slot.getIndex(), slot.x, slot.y) {
                public boolean canInsert(ItemStack stack) {
                    return false;
                }
            });
        }
    }

    public static TestScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new TestScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }
}

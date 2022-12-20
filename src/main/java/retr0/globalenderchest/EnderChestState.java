package retr0.globalenderchest;

import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

public class EnderChestState extends PersistentState {
    public static final String INVENTORY_KEY = "inventory";
    private final EnderChestInventory inventory;

    private EnderChestState(@NotNull EnderChestInventory inventory) {
        this.inventory = inventory;
    }

    public EnderChestState readNbt(NbtCompound nbt) {
        inventory.readNbtList(nbt.getList(INVENTORY_KEY, NbtElement.COMPOUND_TYPE));
        return this;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put(INVENTORY_KEY, inventory.toNbtList());
        return nbt;
    }

    public static EnderChestState create() {
        return new EnderChestState(new EnderChestInventory());
    }

    public EnderChestInventory getInventory() {
        return inventory;
    }
}

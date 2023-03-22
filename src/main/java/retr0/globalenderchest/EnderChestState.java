package retr0.globalenderchest;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static retr0.globalenderchest.GlobalEnderChest.MOD_ID;

public final class EnderChestState extends PersistentState {
    public static final String INVENTORY_KEY = "inventory";

    private static EnderChestState instance;

    private final EnderChestInventory inventory = new EnderChestInventory();

    public static void register() {
        ServerWorldEvents.LOAD.register(((server, world) -> {
            if (world.getRegistryKey() != World.OVERWORLD) return;

            instance = server.getOverworld().getPersistentStateManager().getOrCreate(
                    new EnderChestState()::readNbt,
                    EnderChestState::new,
                    MOD_ID);
            instance.inventory.addListener(inventory -> instance.markDirty());
        }));
    }



    public static EnderChestInventory getInventory() {
        Objects.requireNonNull(instance.inventory, "Global ender chest inventory should be initialized!");
        return instance.inventory;
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

    private EnderChestState() { }
}

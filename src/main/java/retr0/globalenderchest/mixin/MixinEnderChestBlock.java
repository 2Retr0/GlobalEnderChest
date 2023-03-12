package retr0.globalenderchest.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import retr0.globalenderchest.GlobalEnderChest;
import retr0.globalenderchest.TestScreenHandler;

@Mixin(EnderChestBlock.class)
public abstract class MixinEnderChestBlock {
    @Shadow @Final private static Text CONTAINER_NAME;

    @Unique private PlayerEntity player;

    @ModifyVariable(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/EnderChestInventory;setActiveBlockEntity(Lnet/minecraft/block/entity/EnderChestBlockEntity;)V"))
    private EnderChestInventory useGlobalInventory(
        EnderChestInventory original, BlockState state, World world, BlockPos pos, PlayerEntity player)
    {
        this.player = player;
        var inventoryHasItem = false;
        var inventory = player.getEnderChestInventory();
        for (var slot = 0; slot < inventory.size(); ++slot) {
            var itemStack = inventory.getStack(slot);

            GlobalEnderChest.LOGGER.info("checking slot " + slot + "!2");
            if (!itemStack.equals(ItemStack.EMPTY)) {
                inventoryHasItem = true;
                break;
            }
        }

        if (inventoryHasItem) {
            GlobalEnderChest.LOGGER.info("HAS ITEM2!");
            return original;
        }

        return GlobalEnderChest.getInventory();
    }


    @ModifyArg(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"))
    private NamedScreenHandlerFactory useScreen(@Nullable NamedScreenHandlerFactory original) {
        var enderChestInventory = player.getEnderChestInventory();

        ServerPlayNetworking.send((ServerPlayerEntity) player, new Identifier("testtest"), PacketByteBufs.empty());

        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player1) ->
            TestScreenHandler.createGeneric9x3(syncId, inventory, enderChestInventory), CONTAINER_NAME);
    }


}

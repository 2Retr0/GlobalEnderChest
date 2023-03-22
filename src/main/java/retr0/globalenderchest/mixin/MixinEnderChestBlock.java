package retr0.globalenderchest.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import retr0.globalenderchest.EnderChestState;
import retr0.globalenderchest.GlobalEnderChest;
import retr0.globalenderchest.network.MigrationWarningS2CPacket;
import retr0.globalenderchest.screen.ContainerMigrationScreenHandler;

@Mixin(EnderChestBlock.class)
public abstract class MixinEnderChestBlock {
    @Shadow @Final private static Text CONTAINER_NAME;

    @Unique private PlayerEntity player;
    @Unique private boolean canUseGlobalInventory;

    @Inject(method = "onUse", at = @At("HEAD"))
    private void checkPlayerEnderChestInventory(
            BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit,
            CallbackInfoReturnable<ActionResult> cir)
    {
        this.player = player;
        this.canUseGlobalInventory = true;

        var enderChestInventory = player.getEnderChestInventory();
        for (var slot = 0; slot < enderChestInventory.size(); slot++) {
            if (enderChestInventory.getStack(slot).equals(ItemStack.EMPTY)) continue;

            GlobalEnderChest.LOGGER.info("Found item in player's inventory!");
            canUseGlobalInventory = false;
            return;
        }
    }



    @ModifyVariable(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/EnderChestInventory;setActiveBlockEntity(Lnet/minecraft/block/entity/EnderChestBlockEntity;)V"))
    private EnderChestInventory useGlobalInventory(
        EnderChestInventory original, BlockState state, World world, BlockPos pos, PlayerEntity player)
    {
        if (canUseGlobalInventory) return EnderChestState.getInventory();

        return original;
    }



    @ModifyArg(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"))
    private NamedScreenHandlerFactory useScreen(@Nullable NamedScreenHandlerFactory original) {
        var enderChestInventory = player.getEnderChestInventory();
        if (canUseGlobalInventory) return original;

        // If the player still has items in their inventory
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player1) -> {
                MigrationWarningS2CPacket.send((ServerPlayerEntity) player, syncId);

                return ContainerMigrationScreenHandler.createGeneric9x3(syncId, inventory, enderChestInventory);
            }, CONTAINER_NAME);
    }
}

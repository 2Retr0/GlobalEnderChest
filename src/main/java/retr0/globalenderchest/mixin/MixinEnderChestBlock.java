package retr0.globalenderchest.mixin;

import retr0.globalenderchest.EnderChestState;
import retr0.globalenderchest.GlobalEnderChest;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.screen.GenericContainerScreenHandler.createGeneric9x3;

@Mixin(EnderChestBlock.class)
public abstract class MixinEnderChestBlock {
    private static EnderChestInventory globalInventory;

    @Inject(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/EnderChestInventory;setActiveBlockEntity(Lnet/minecraft/block/entity/EnderChestBlockEntity;)V",
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void onUse (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, EnderChestInventory enderChestInventory, BlockEntity blockEntity, BlockPos blockPos, EnderChestBlockEntity enderChestBlockEntity)
    {
        var server = player.getServer();
        if (server != null) {
            var stateManager = server.getOverworld().getPersistentStateManager();
            var enderChestState = stateManager.get(EnderChestState.createState()::readNbt, "inventory_test");

            // GlobalEnderChest.LOGGER.info(((AccessorSimpleInventory) GlobalEnderChest.enderChestState.getInventory()).getListeners().size() + "");
            GlobalEnderChest.LOGGER.info(GlobalEnderChest.enderChestState.getInventory().toString());
            globalInventory = GlobalEnderChest.enderChestState.getInventory();
            // globalInventory.addStack(DIRT_PATH.getDefaultStack());
        } else {
            GlobalEnderChest.LOGGER.info("Not a serverpplayerentity!");
            globalInventory = null;
        }
    }

    @ModifyVariable(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/EnderChestInventory;setActiveBlockEntity(Lnet/minecraft/block/entity/EnderChestBlockEntity;)V",
            shift = At.Shift.AFTER
        )
    )
    private EnderChestInventory useGlobalInventory(EnderChestInventory original) {
        return globalInventory;
    }


}

package retr0.globalenderchest.mixin;

import net.minecraft.block.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin(EnderChestBlockEntity.class)
public abstract class MixinEnderChestBlockEntity {
    /*@Unique EnderChestBlockEntity instance = (EnderChestBlockEntity) (Object) this;

    @Shadow @Final @Mutable
    private ViewerCountManager stateManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(BlockPos pos, BlockState state, CallbackInfo ci) {
        var originalManager = (MixinViewerCountManager) stateManager;
        stateManager = new ViewerCountManager() {
            @Override
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                originalManager.invokeOnContainerOpen(world, pos, state);
            }

            @Override
            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                originalManager.invokeOnContainerClose(world, pos, state);
            }

            @Override
            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                GlobalEnderChest.LOGGER.info("newViewCount: " + newViewerCount);
                originalManager.invokeOnViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
            }

            @Override
            protected boolean isPlayerViewing(PlayerEntity player) {
                // if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                //     var inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                //
                //     GlobalEnderChest.LOGGER.info(String.valueOf(player.getEnderChestInventory().isActiveBlockEntity(instance)));
                //
                //     return inventory instanceof EnderChestInventory && player.getEnderChestInventory().isActiveBlockEntity(instance);
                // }
                // return false;
                return player.getEnderChestInventory().isActiveBlockEntity(instance);
            }
        };
    }*/
}

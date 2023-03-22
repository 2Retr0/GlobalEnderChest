package retr0.globalenderchest.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestInventory.class)
public abstract class MixinEnderChestInventory {
    @Inject(method = "onOpen", at = @At("TAIL"))
    private void onOpen(PlayerEntity player, CallbackInfo ci) {
        var activeBlockEntity = ((AccessorEnderChestInventory) player.getEnderChestInventory()).getActiveBlockEntity();

        if (activeBlockEntity != null) {
            activeBlockEntity.onOpen(player);
        }
        // super.onOpen(player);
    }


    /**
     * Ender chest animations sync to player ender chest inventories' activate block entity. We use this
     */
    @Inject(method = "onClose", at = @At("TAIL"))
    private void closeForPlayer(PlayerEntity player, CallbackInfo ci) {
        var activeBlockEntity = ((AccessorEnderChestInventory) player.getEnderChestInventory()).getActiveBlockEntity();

        if (activeBlockEntity != null) {
            activeBlockEntity.onClose(player);
        }

        // super.onClose(player);
        player.getEnderChestInventory().setActiveBlockEntity(null);
    }
}

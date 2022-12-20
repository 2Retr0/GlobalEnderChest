package retr0.globalenderchest.mixin;

import net.minecraft.block.EnderChestBlock;
import net.minecraft.inventory.EnderChestInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import retr0.globalenderchest.GlobalEnderChest;

@Mixin(EnderChestBlock.class)
public abstract class MixinEnderChestBlock {
    @ModifyVariable(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/EnderChestInventory;setActiveBlockEntity(Lnet/minecraft/block/entity/EnderChestBlockEntity;)V"
        )
    )
    private EnderChestInventory useGlobalInventory(EnderChestInventory original) {
        return GlobalEnderChest.getInventory();
    }


}

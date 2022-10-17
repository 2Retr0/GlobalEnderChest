package retr0.globalenderchest.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface MixinPlayerEntity {
    @Accessor("enderChestInventory")
    void setEnderChestInventory(EnderChestInventory value);
}

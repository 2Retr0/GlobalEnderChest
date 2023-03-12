package retr0.globalenderchest.mixin;

import net.minecraft.client.toast.SystemToast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SystemToast.class)
public class MixinSystemToast {
    @Shadow private long startTime;


}

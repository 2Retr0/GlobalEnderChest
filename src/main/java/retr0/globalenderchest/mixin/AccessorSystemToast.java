package retr0.globalenderchest.mixin;

import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SystemToast.class)
public interface AccessorSystemToast {
    @Accessor("lines")
    List<OrderedText> getLines();

    @Accessor("lines")
    void setLines(List<OrderedText> lines);

    @Accessor("width")
    int getWidth();

    @Mutable @Accessor("width")
    void setWidth(int width);
}

package retr0.globalenderchest.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import retr0.globalenderchest.mixin.AccessorSystemToast;

import static retr0.globalenderchest.GlobalEnderChest.MOD_ID;

public class ContainerMigrationToast extends SystemToast {
    private static final Text HEADER = Text.translatable(MOD_ID + ".toast.header");
    private static final Text DESCRIPTION = Text.translatable(MOD_ID + ".toast");
    private static final SystemToast.Type TYPE = SystemToast.Type.WORLD_ACCESS_FAILURE;

    private final MinecraftClient client;
    private int targetSyncId;

    private ContainerMigrationToast(MinecraftClient client, int syncId) {
        super(TYPE, HEADER, null);

        this.client = client;
        this.targetSyncId = syncId;
        formatContent(client);
    }



    @Override
    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        super.draw(matrices, manager, startTime);

        @SuppressWarnings("DataFlowIssue")
        var currentSyncId = manager.getClient().player.currentScreenHandler.syncId;
        return currentSyncId != targetSyncId ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }



    public void setContent(int syncId) {
        super.setContent(HEADER, null);

        targetSyncId = syncId;
        formatContent(client);
    }



    private void formatContent(MinecraftClient client) {
        // For some reason, only SystemToast.create() has the logic to format a multi-line toast description.
        // This is an awful solution but whatever...
        var tempToast = (AccessorSystemToast) SystemToast.create(client, TYPE, HEADER, DESCRIPTION);
        ((AccessorSystemToast) this).setLines(tempToast.getLines());
        ((AccessorSystemToast) this).setWidth(tempToast.getWidth() - 26);
    }



    public static void show(ToastManager manager, int syncId) {
        var containerMigrationToast = manager.getToast(ContainerMigrationToast.class, TYPE);

        if (containerMigrationToast == null)
            manager.add(new ContainerMigrationToast(manager.getClient(), syncId));
        else
            containerMigrationToast.setContent(syncId);
    }
}

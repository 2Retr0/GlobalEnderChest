package retr0.globalenderchest.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import retr0.globalenderchest.screen.ContainerMigrationToast;

import static retr0.globalenderchest.GlobalEnderChest.MOD_ID;

public class MigrationWarningS2CPacket {
    public static final Identifier MIGRATION_WARNING_ID = new Identifier(MOD_ID, "migration_warning");

    /**
     * Sends a {@link MigrationWarningS2CPacket} to display a {@link ContainerMigrationToast} on the client.
     * @param player The {@link ServerPlayerEntity} representing the target client player.
     * @param syncId The client player's current {@link ScreenHandler} {@code syncId}. This is used by the toast on the
     *               to know when to hide on the client.
     */
    public static void send(ServerPlayerEntity player, int syncId) {
        var buf = PacketByteBufs.create();
        buf.writeInt(syncId);

        ServerPlayNetworking.send(player, MIGRATION_WARNING_ID, buf);
    }



    /**
     * Displays a {@link ContainerMigrationToast}.
     */
    public static void receive(
        MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        var syncId = buf.readInt();

        client.execute(() -> {
            var toastManager = client.getToastManager();

            ContainerMigrationToast.show(toastManager, syncId);
        });
    }
}

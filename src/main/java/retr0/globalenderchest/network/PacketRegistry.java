package retr0.globalenderchest.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static retr0.globalenderchest.network.MigrationWarningS2CPacket.MIGRATION_WARNING_ID;

public class PacketRegistry {
    public static void registerC2SPackets() { }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(MIGRATION_WARNING_ID, MigrationWarningS2CPacket::receive);
    }
}

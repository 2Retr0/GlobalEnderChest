package retr0.globalenderchesttest;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.item.Items.SNIFFER_EGG;

public class GlobalEnderChestTest implements ClientModInitializer {
	public static final String MOD_ID = "globalenderchesttest";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello GlobalEnderChest Test!");

		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
			sender.sendMessage(Text.of("Added item!"));
			sender.getEnderChestInventory().setStack(0, SNIFFER_EGG.getDefaultStack());
		});
	}
}

package net.fabricmc.custom_ambiance;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Client implements ClientModInitializer {
	public static String MODID = "custom_ambiance";
	public static MinecraftClient CLIENT = MinecraftClient.getInstance();
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final Random RANDOM = Random.create(System.currentTimeMillis());

	public static final Identifier SPARROW_CHIRP_IDENTIFIER = new Identifier(MODID,"sparrow_chirp");
	public static final SoundEvent SPARROW_CHIRP = new SoundEvent(SPARROW_CHIRP_IDENTIFIER);

	@Override
	public void onInitializeClient() {

		Registry.register(Registry.SOUND_EVENT, SPARROW_CHIRP_IDENTIFIER, SPARROW_CHIRP);

		CASoundEventManager.start();

	}

	public static Optional<ClientPlayerEntity> getPlayer(){
		return Optional.ofNullable(CLIENT.player);
	}
	public static Optional<ClientWorld> getWorld(){
		return Optional.ofNullable(CLIENT.world);
	}
}

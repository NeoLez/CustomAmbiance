package net.fabricmc.custom_ambiance.soundevent.consumers;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public record PlaySound(SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) implements SoundEventConsumer{
    @Override
    public void accept(CASoundEventData soundEventData) {
        Client.getWorld().ifPresent((world)-> world.playSound(soundEventData.position(), sound, category, volume, pitch, useDistance));
    }

    public static SoundEventConsumer fromConfig(ConfigSection config){
        return new PlaySound(   Registry.SOUND_EVENT.get(new Identifier(config.getString("soundIdentifier"))),
                                SoundCategory.valueOf(config.getString("soundCategory")),
                                (config.getDouble("volume")).floatValue(),
                                (config.getDouble("pitch")).floatValue(),
                                config.getBoolean("distanceAttenuation"));
    }
}

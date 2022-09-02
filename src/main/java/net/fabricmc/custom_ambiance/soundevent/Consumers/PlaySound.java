package net.fabricmc.custom_ambiance.soundevent.Consumers;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public record PlaySound(SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) implements SoundEventConsumer{
    @Override
    public void accept(CASoundEventData soundEventData) {
        Client.LOGGER.info("*Chirp*");
        Client.getWorld().ifPresent((world)-> world.playSound(soundEventData.position(), sound, category, volume, pitch, useDistance));
    }

    public static PlaySound fromMapData(Map<String,Object> data){
        return new PlaySound(   Registry.SOUND_EVENT.get(new Identifier((String)data.get("soundIdentifier"))),
                                SoundCategory.valueOf((String) data.get("soundCategory")),
                                ((Double)data.get("volume")).floatValue(),
                                ((Double)data.get("pitch")).floatValue(),
                                (Boolean) data.get("distanceAttenuation"));
    }
}

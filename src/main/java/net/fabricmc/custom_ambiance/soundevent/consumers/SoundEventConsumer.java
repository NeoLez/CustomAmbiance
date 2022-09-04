package net.fabricmc.custom_ambiance.soundevent.consumers;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public interface SoundEventConsumer extends Consumer<CASoundEventData> {
    static List<SoundEventConsumer> getConsumersFromConfig(ConfigSection config){
        LinkedList<SoundEventConsumer> consumers = new LinkedList<>();
        for(String type : config.getKeys()){
            try {
                Class<?> predicate = Class.forName("net.fabricmc.custom_ambiance.soundevent.consumers." + type);
                Method method = predicate.getMethod("fromConfig", ConfigSection.class);

                try {
                    ConfigSection value = config.getConfigSection(type);
                    Object instance = method.invoke(null, value);

                    if (instance instanceof SoundEventConsumer)
                        consumers.add((SoundEventConsumer) instance);
                }catch (NullPointerException e){
                    Client.LOGGER.error("Couldn't load config for action of type "+type);
                }

            }catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e){
                Client.LOGGER.error("No actions of name "+type+" found. Check your configuration file for typos.");
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e){
                Client.LOGGER.error("Error during creation of action.");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return consumers;
    }
}

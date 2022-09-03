package net.fabricmc.custom_ambiance.soundevent.Consumers;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.fabricmc.custom_ambiance.soundevent.predicates.SoundEventPredicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface SoundEventConsumer extends Consumer<CASoundEventData> {
    static List<SoundEventConsumer> getConsumersFromMap(Map<String, Object> kv){
        LinkedList<SoundEventConsumer> consumers = new LinkedList<>();
        for(String type : kv.keySet()){
            try {
                Class<?> predicate = Class.forName("net.fabricmc.custom_ambiance.soundevent.consumers." + type);
                Method method = predicate.getMethod("fromMapData", Map.class);
                Object value = kv.get(type);
                if(value instanceof Map<?,?>) {
                    //noinspection JavaReflectionInvocation
                    Object instance = method.invoke(null, value);

                    if (instance instanceof SoundEventPredicate)
                        consumers.add((SoundEventConsumer) instance);
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

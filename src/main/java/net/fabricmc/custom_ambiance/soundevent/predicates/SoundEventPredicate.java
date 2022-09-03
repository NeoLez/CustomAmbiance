package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface SoundEventPredicate extends Predicate<CASoundEventData> {
    static List<SoundEventPredicate> getPredicatesFromMap(Map<String, Object> kv){
        LinkedList<SoundEventPredicate> predicates = new LinkedList<>();
        for(String type : kv.keySet()){
            try {
                Class<?> predicate = Class.forName("net.fabricmc.custom_ambiance.soundevent.predicates." + type);
                Method method = predicate.getMethod("fromMapData", Map.class);
                Object value = kv.get(type);
                if(value instanceof Map<?,?>) {
                    //noinspection JavaReflectionInvocation
                    Object instance = method.invoke(null, value);

                    if (instance instanceof SoundEventPredicate)
                        predicates.add((SoundEventPredicate) instance);
                }
            }catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e){
                Client.LOGGER.error("No conditions of name "+type+" found. Check your configuration file for typos.");
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e){
                Client.LOGGER.error("Error during creation of condition.");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return predicates;
    }
}

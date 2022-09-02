package net.fabricmc.custom_ambiance.soundevent;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.Consumers.PlaySound;
import net.fabricmc.custom_ambiance.soundevent.Consumers.SoundEventConsumer;
import net.fabricmc.custom_ambiance.soundevent.Predicates.IsBlockOfTag;
import net.fabricmc.custom_ambiance.soundevent.Predicates.SoundEventPredicate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CASoundEvent {
    private final List<SoundEventPredicate> predicates;
    private final List<SoundEventConsumer> consumers;

    public CASoundEvent(List<SoundEventPredicate> predicates, List<SoundEventConsumer> consumers){
        this.predicates = predicates;
        this.consumers = consumers;
    }

    public void run(CASoundEventData data){
        if (meetsConditions(data))
            runConsumers(data);
    }

    private boolean meetsConditions(CASoundEventData data){
        boolean result=true;

        Iterator<SoundEventPredicate> it = predicates.iterator();
        while (it.hasNext() && result){
            result = it.next().test(data);
        }

        return result;
    }

    private void runConsumers(CASoundEventData data){
        for (SoundEventConsumer c: consumers) {
            c.accept(data);
        }
    }

    public static CASoundEvent getEventFromMap(Map<String, Object> kv){
        return new CASoundEvent(getPredicates((Map<String, Object>) kv.get("Conditions")), getConsumers((Map<String, Object>)kv.get("Consequences")));
    }

    public static List<SoundEventPredicate> getPredicates(Map<String, Object> kv){
        LinkedList<SoundEventPredicate> predicates = new LinkedList<>();
        for(String type : kv.keySet()){
            switch (type){
                case "IsBlockOfTag":
                    predicates.add(IsBlockOfTag.fromMapData((Map<String,Object>)kv.get("IsBlockOfTag")));
            }
        }
        return predicates;
    }

    public static List<SoundEventConsumer> getConsumers(Map<String, Object> kv){
        LinkedList<SoundEventConsumer> consumers = new LinkedList<>();
        for(String type : kv.keySet()){
            switch (type){
                case "PlaySound":
                    consumers.add(PlaySound.fromMapData((Map<String,Object>)kv.get("PlaySound")));
            }
        }
        return consumers;
    }

}

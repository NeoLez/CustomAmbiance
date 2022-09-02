package net.fabricmc.custom_ambiance.soundevent;

import net.fabricmc.custom_ambiance.soundevent.Consumers.PlaySound;
import net.fabricmc.custom_ambiance.soundevent.Consumers.SoundEventConsumer;
import net.fabricmc.custom_ambiance.soundevent.Predicates.*;

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

    @SuppressWarnings("unchecked")
    public static CASoundEvent getEventFromMap(Map<String, Object> kv){
        return new CASoundEvent(getPredicates((Map<String, Object>) kv.get("Conditions")), getConsumers((Map<String, Object>)kv.get("Consequences")));
    }
    @SuppressWarnings("unchecked")
    public static List<SoundEventPredicate> getPredicates(Map<String, Object> kv){
        LinkedList<SoundEventPredicate> predicates = new LinkedList<>();
        for(String type : kv.keySet()){
            switch (type) {
                case "IsBlock" -> predicates.add(IsBlock.fromMapData((Map<String, Object>) kv.get(type)));
                case "IsBlockOfTag" -> predicates.add(IsBlockOfTag.fromMapData((Map<String, Object>) kv.get(type)));
                case "IsInRangeCube" -> predicates.add(IsInRangeCube.fromMapData((Map<String, Object>) kv.get(type)));
                case "IsInRangeSphere" ->
                        predicates.add(IsInRangeSphere.fromMapData((Map<String, Object>) kv.get(type)));
                case "RandomChance" -> predicates.add(RandomChance.fromMapData((Map<String, Object>) kv.get(type)));
                case "Or" -> predicates.add(Or.fromMapData((Map<String, Object>) kv.get(type)));
            }
        }
        return predicates;
    }
    @SuppressWarnings("unchecked")
    public static List<SoundEventConsumer> getConsumers(Map<String, Object> kv){
        LinkedList<SoundEventConsumer> consumers = new LinkedList<>();
        for(String type : kv.keySet()){
            switch (type) {
                case "PlaySound" -> consumers.add(PlaySound.fromMapData((Map<String, Object>) kv.get(type)));
            }
        }
        return consumers;
    }

}

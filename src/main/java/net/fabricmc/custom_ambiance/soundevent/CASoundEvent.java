package net.fabricmc.custom_ambiance.soundevent;

import net.fabricmc.custom_ambiance.soundevent.Consumers.SoundEventConsumer;
import net.fabricmc.custom_ambiance.soundevent.predicates.*;

import java.util.Iterator;
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
        return new CASoundEvent(SoundEventPredicate.getPredicatesFromMap((Map<String, Object>) kv.get("Conditions")), SoundEventConsumer.getConsumersFromMap((Map<String, Object>)kv.get("Consequences")));
    }

}

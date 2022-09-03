package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Or implements SoundEventPredicate{
    private final List<SoundEventPredicate> conditions1;
    private final List<SoundEventPredicate> conditions2;

    public Or(List<SoundEventPredicate> conditions1, List<SoundEventPredicate> conditions2){
        this.conditions1 = conditions1;
        this.conditions2 = conditions2;
    }

    @Override
    public boolean test(CASoundEventData soundEventData) {
        return evaluate(conditions1, soundEventData) | evaluate(conditions2, soundEventData);
    }

    private static boolean evaluate(List<SoundEventPredicate> perdicates, CASoundEventData data){
        boolean result=true;

        Iterator<SoundEventPredicate> it = perdicates.iterator();
        while (it.hasNext() && result){
            result = it.next().test(data);
        }

        return result;
    }
    @SuppressWarnings({"unchecked"})
    public static SoundEventPredicate fromMapData(Map<String, Object> data){
        return new Or(SoundEventPredicate.getPredicatesFromMap((Map<String,Object>)data.get("conditions1")), SoundEventPredicate.getPredicatesFromMap((Map<String,Object>)data.get("conditions2")));
    }
}

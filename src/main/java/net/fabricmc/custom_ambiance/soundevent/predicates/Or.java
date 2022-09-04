package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Iterator;
import java.util.List;

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

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new Or(SoundEventPredicate.getPredicatesFromConfig(config.getConfigSection("conditions1")), SoundEventPredicate.getPredicatesFromConfig(config.getConfigSection("conditions2")));
    }
}

package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Iterator;
import java.util.LinkedList;

public class Or implements SoundEventPredicate{
    LinkedList<SoundEventPredicate> term1;
    LinkedList<SoundEventPredicate> term2;
    @Override
    public boolean test(CASoundEventData soundEventData) {

        return evaluate(term1, soundEventData) | evaluate(term2, soundEventData);
    }
    private static boolean evaluate(LinkedList<SoundEventPredicate> perdicates, CASoundEventData data){
        boolean result=true;

        Iterator<SoundEventPredicate> it = perdicates.iterator();
        while (it.hasNext() && result){
            result = it.next().test(data);
        }

        return result;
    }
}

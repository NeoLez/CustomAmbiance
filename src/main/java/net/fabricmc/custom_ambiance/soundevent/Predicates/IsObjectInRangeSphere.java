package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

public record IsObjectInRangeSphere(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        double distance = caSoundEventData.offsetFromOriginal().getSquaredDistance(0,0,0);
        return distance>=min && distance<=max;
    }
}

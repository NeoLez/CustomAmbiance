package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

public record IsOffsetInRangeCube(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return isInRange(caSoundEventData.offsetFromOriginal().getX(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getY(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getZ(), min, max);
    }
    public static boolean isInRange(int val, int min, int max){
        return val >= min && val <= max;
    }
}

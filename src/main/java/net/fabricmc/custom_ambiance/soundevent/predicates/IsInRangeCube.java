package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Map;

@SuppressWarnings("unused")
public record IsInRangeCube(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return isInRange(caSoundEventData.offsetFromOriginal().getX(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getY(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getZ(), min, max);
    }
    public static boolean isInRange(int val, int min, int max){
        return val >= min && val <= max;
    }

    public static SoundEventPredicate fromMapData(Map<String, Object> data){
        return new IsInRangeCube((Integer) data.get("minRadius"), (Integer) data.get("maxRadius"));
    }
}

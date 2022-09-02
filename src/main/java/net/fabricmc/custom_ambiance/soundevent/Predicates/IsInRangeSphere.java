package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Map;

public record IsInRangeSphere(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        double distance = caSoundEventData.offsetFromOriginal().getSquaredDistance(0,0,0);
        return distance>=min && distance<=max;
    }

    public static SoundEventPredicate fromMapData(Map<String, Object> data){
        return new IsInRangeSphere((Integer) data.get("minRadius"), (Integer) data.get("maxRadius"));
    }
}

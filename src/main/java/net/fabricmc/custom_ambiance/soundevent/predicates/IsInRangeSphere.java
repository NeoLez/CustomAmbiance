package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

import java.util.Map;

@SuppressWarnings("unused")
public record IsInRangeSphere(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        double distance = caSoundEventData.offsetFromOriginal().getSquaredDistance(0,0,0);
        return distance>=min && distance<=max;
    }

    @SuppressWarnings("unused")
    public static SoundEventPredicate fromMapData(Map<String, Object> data){
        return new IsInRangeSphere((Integer) data.get("minRadius"), (Integer) data.get("maxRadius"));
    }
}

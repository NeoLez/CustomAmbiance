package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

@SuppressWarnings("unused")
public record IsInRangeSphere(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        double distance = caSoundEventData.offsetFromOriginal().getSquaredDistance(0,0,0);
        return distance>=min && distance<=max;
    }

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new IsInRangeSphere(config.getInteger("minRadius"), config.getInteger("maxRadius"));
    }
}

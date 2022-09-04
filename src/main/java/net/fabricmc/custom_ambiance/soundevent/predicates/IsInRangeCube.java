package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;

@SuppressWarnings("unused")
public record IsInRangeCube(int min, int max) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return isInRange(caSoundEventData.offsetFromOriginal().getX(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getY(), min, max)&&isInRange(caSoundEventData.offsetFromOriginal().getZ(), min, max);
    }
    public static boolean isInRange(int val, int min, int max){
        return val >= min && val <= max;
    }

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new IsInRangeCube(config.getInteger("minRadius"), config.getInteger("maxRadius"));
    }
}

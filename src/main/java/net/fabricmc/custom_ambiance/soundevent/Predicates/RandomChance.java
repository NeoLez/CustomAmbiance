package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.util.math.MathHelper;

public class RandomChance implements SoundEventPredicate{
    float baseChance;
    float chanceIncrement;
    int failedConsecutiveAttempts=0;

    public RandomChance(float baseChance, float chanceIncrement){
        this.baseChance=baseChance;
        this.chanceIncrement=chanceIncrement;
    }

    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        float chance=baseChance+chanceIncrement*failedConsecutiveAttempts;
        chance = MathHelper.clamp(chance, 1, 100);

        if (MathHelper.nextFloat(Client.RANDOM,1.0f,100.0f)<chance) {
            failedConsecutiveAttempts=0;
            return true;
        }else {
            failedConsecutiveAttempts++;
            return false;
        }
    }
}
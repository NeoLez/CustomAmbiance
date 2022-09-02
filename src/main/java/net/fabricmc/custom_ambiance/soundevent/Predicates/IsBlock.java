package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.block.Block;

public record IsBlock(Block block) implements SoundEventPredicate {
    @Override
    public boolean test(CASoundEventData soundEventData) {
        return soundEventData.block() == block;
    }
}

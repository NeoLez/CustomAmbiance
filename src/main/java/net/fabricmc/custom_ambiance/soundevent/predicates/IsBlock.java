package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public record IsBlock(Block block) implements SoundEventPredicate {
    @Override
    public boolean test(CASoundEventData soundEventData) {
        return soundEventData.block() == block;
    }

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new IsBlock(Registry.BLOCK.get(new Identifier(config.getString("block"))));
    }
}

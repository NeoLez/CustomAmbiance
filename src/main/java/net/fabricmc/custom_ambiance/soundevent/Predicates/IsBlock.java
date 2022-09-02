package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public record IsBlock(Block block) implements SoundEventPredicate {
    @Override
    public boolean test(CASoundEventData soundEventData) {
        return soundEventData.block() == block;
    }

    public static SoundEventPredicate fromMapData(Map<String,Object> data){
        return new IsBlock(Registry.BLOCK.get(new Identifier((String)data.get("block"))));
    }
}

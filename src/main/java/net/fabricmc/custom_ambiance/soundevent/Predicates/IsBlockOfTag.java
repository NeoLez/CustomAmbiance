package net.fabricmc.custom_ambiance.soundevent.Predicates;

import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public record IsBlockOfTag(TagKey<Block> tag) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return TagUtil.isIn(tag, caSoundEventData.block());
    }


    public static SoundEventPredicate fromMapData(Map<String,Object> data){

        return new IsBlockOfTag(TagKey.of(Registry.BLOCK_KEY, new Identifier((String) data.get("tagIdentifier"))));
    }
}

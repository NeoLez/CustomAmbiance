package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public record IsBlockOfTag(TagKey<Block> tag) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return TagUtil.isIn(tag, caSoundEventData.block());
    }

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new IsBlockOfTag(TagKey.of(Registry.BLOCK_KEY, new Identifier(config.getString("tagIdentifier"))));
    }
}

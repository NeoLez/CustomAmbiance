package net.fabricmc.custom_ambiance.soundevent;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public record CASoundEventData(Block block, BlockPos position, BlockPos offsetFromOriginal) {
}

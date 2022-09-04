package net.fabricmc.custom_ambiance.soundevent;

import net.minecraft.block.Block;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public record CASoundEventData(ClientWorld world, Block block, BlockPos position, BlockPos offsetFromOriginal) {
}

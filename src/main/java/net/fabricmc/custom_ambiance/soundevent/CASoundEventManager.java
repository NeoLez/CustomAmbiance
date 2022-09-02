package net.fabricmc.custom_ambiance.soundevent;

import net.fabricmc.custom_ambiance.Config;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.custom_ambiance.Client;
import net.fabricmc.custom_ambiance.soundevent.Consumers.PlaySound;
import net.fabricmc.custom_ambiance.soundevent.Consumers.SoundEventConsumer;
import net.fabricmc.custom_ambiance.soundevent.Predicates.IsBlockOfTag;
import net.fabricmc.custom_ambiance.soundevent.Predicates.SoundEventPredicate;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CASoundEventManager {
    private static final int SAMPLES_PER_TICK= 2;
    private static List<CASoundEvent> soundEvents = new LinkedList<>();
    private static final Supplier<Integer> randomVal = ()->{
        int val = Client.RANDOM.nextBetween(8,24);
        if (Client.RANDOM.nextBoolean())
            val*=-1;
        return val;
    };
    private static BlockPos randomBlockPosRange(){
        return new BlockPos(randomVal.get(),
                            randomVal.get(),
                            randomVal.get());
    }

    public static void start(){
        /*LinkedList<SoundEventPredicate> predicates = new LinkedList<>();
        predicates.add(new IsBlockOfTag(BlockTags.LEAVES));
        LinkedList<SoundEventConsumer> consumers = new LinkedList<>();
        consumers.add(new PlaySound(Client.SPARROW_CHIRP, SoundCategory.AMBIENT, 1.75f, 1, true));
        soundEvents.add(new CASoundEvent(predicates, consumers));*/

        soundEvents = Config.getSoundEventList();

        ServerTickEvents.END_SERVER_TICK.register((server -> {
            for(int i=0;i<SAMPLES_PER_TICK;i++) {
                CASoundEventData data = getSoundEventData();
                if (data != null)
                    runSample(data);
            }
        }));
    }

    private static void runSample(CASoundEventData data){
        for (CASoundEvent ev: soundEvents) {
            ev.run(data);
        }
    }

    public static CASoundEventData getSoundEventData(){
        Optional<ClientPlayerEntity> playerOpt = Client.getPlayer();
        if(playerOpt.isPresent()) {
            BlockPos offset = randomBlockPosRange();
            BlockPos samplePos = playerOpt.get().getBlockPos().add(offset);

            Optional<ClientWorld> worldOpt = Client.getWorld();
            if (worldOpt.isPresent()) {
                Optional<RegistryKey<Block>> keyOptional = worldOpt.get().getBlockState(samplePos).getRegistryEntry().getKey();
                if (keyOptional.isPresent())
                    return new CASoundEventData(Registry.BLOCK.get(keyOptional.get()), samplePos, offset);
            }
        }
        return null;
    }
}

package net.fabricmc.custom_ambiance.soundevent;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.custom_ambiance.Client;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CASoundEventManager {
    private static final int SAMPLES_PER_TICK= 10;
    private static final List<CASoundEvent> soundEvents = new LinkedList<>();
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

    private static final ServerTickEvents.EndTick code = server -> {
        for(int i=0;i<SAMPLES_PER_TICK;i++) {
            CASoundEventData data = getSoundEventData();
            if (data != null)
                runSample(data);
        }
    };

    public static void start(){

        loadConfig();

        ServerTickEvents.END_SERVER_TICK.register(code);
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
                    return new CASoundEventData(playerOpt.get().clientWorld, Registry.BLOCK.get(keyOptional.get()), samplePos, offset);
            }
        }
        return null;
    }

    public static void loadConfig(){
        soundEvents.clear();

        String path = Client.CLIENT.runDirectory.getAbsolutePath()+"/config/"+Client.MODID+".yaml";
        ConfigSection config;
        try {
            config = ConfigSection.getConfig(getFile(path, "testConfig.yaml"));
        } catch (FileNotFoundException e) {
            Client.LOGGER.error("Couldn't load config file at path: " + path);
            throw new RuntimeException(e);
        }

        try {
            List<ConfigSection> listOfSoundEventConfigs = config.getListOfConfigSections("BlockChecks");
            for(ConfigSection c : listOfSoundEventConfigs){
                soundEvents.add(CASoundEvent.getEventFromConfig(c));
            }
        }catch (NullPointerException e){
            Client.LOGGER.error("Coudln't find BlockChecks section in config file.");
        }
    }

    public static File getFile(String filePath, String fallbackFilePath){
        File configFile = new File(filePath);
        try {
            if(configFile.createNewFile()){
                InputStream fileStream = CASoundEventManager.class.getClassLoader().getResourceAsStream(fallbackFilePath);
                if(fileStream!=null) {
                    byte[] buffer = fileStream.readAllBytes();
                    try (OutputStream configOutStream = new FileOutputStream(configFile)) {
                        configOutStream.write(buffer);
                    }
                }else{
                    throw new NullPointerException("Fallback file of path "+fallbackFilePath+" couldn't be loaded.");
                }
            }
        } catch (IOException e) {
            Client.LOGGER.error("Couldn't load file at "+filePath);
            throw new RuntimeException(e);
        }
        return configFile;
    }
}

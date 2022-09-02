package net.fabricmc.custom_ambiance;

import net.fabricmc.custom_ambiance.soundevent.CASoundEvent;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Config {

    private static final File configFile;

    public static List<CASoundEvent> getSoundEventList(){
        ArrayList<Map<String, Object>> soundEventsMap = getEventArray();
        LinkedList<CASoundEvent> soundEvents = new LinkedList<>();
        for(Map<String, Object> kv : soundEventsMap){
            soundEvents.add(CASoundEvent.getEventFromMap(kv));
        }
        return soundEvents;
    }

    private static ArrayList<Map<String, Object>> getEventArray(){
        Yaml yaml = new Yaml();
        Map<String, Object> kvPairs;
        try {
            kvPairs = yaml.load(new FileInputStream(configFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ((ArrayList<Map<String, Object>>)kvPairs.get("BlockChecks"));
    }

    static {
        configFile = new File(Client.CLIENT.runDirectory.getAbsolutePath()+"/config/"+Client.MODID+".yaml");
        try {
            if(configFile.createNewFile()){
                byte[] buffer = Config.class.getClassLoader().getResourceAsStream("testConfig.yaml").readAllBytes();
                OutputStream configOutStream = new FileOutputStream(configFile);
                configOutStream.write(buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package net.fabricmc.custom_ambiance;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigSection {
    private final Map<String, Object> mapData;
    private ConfigSection(Map<String, Object> mapData){
        this.mapData = mapData;
    }

    private <T> T getObject(String key, Class<T> clazz) throws NullPointerException{
        Object object = mapData.get(key);
        if(clazz.isInstance(object)) {
            return clazz.cast(object);
        }else
            throw new NullPointerException();
    }

    @SuppressWarnings("unchecked")
    public ConfigSection getConfigSection(String key) throws NullPointerException{
        return new ConfigSection(getObject(key, Map.class));
    }

    public String getString(String key) throws NullPointerException{
        return getObject(key, String.class);
    }

    public Integer getInteger(String key) throws NullPointerException{
        return getObject(key, Integer.class);
    }

    public Double getDouble(String key) throws NullPointerException{
        return getObject(key, Double.class);
    }

    public Boolean getBoolean(String key) throws NullPointerException{
        return getObject(key, Boolean.class);
    }

    @SuppressWarnings("unchecked")
    private  <T> List<T> getListOf(String key, Class<?> clazz) throws NullPointerException{

        List<?> list = getObject(key, List.class);
        if(list.size()>0 && clazz.isInstance(list.get(0))) {

            //noinspection CastCanBeRemovedNarrowingVariableType
            return (List<T>) list;
        }
        else
            throw new NullPointerException();
    }

    public List<ConfigSection> getListOfConfigSections(String key) throws NullPointerException{
        List<Map<String, Object>> a = getListOf(key, Map.class);

        List<ConfigSection> configSections = new ArrayList<>();
        for(Map<String, Object> map: a)
            configSections.add(new ConfigSection(map));
        return configSections;
    }

    public List<String> getListOfStrings(String key) throws NullPointerException{
        return getListOf(key, String.class);
    }

    public List<Integer> getListOfIntegers(String key) throws NullPointerException{
        return getListOf(key, Integer.class);
    }

    public List<Boolean> getListOfBooleans(String key) throws NullPointerException{
        return getListOf(key, Boolean.class);
    }

    public List<Double> getListOfDoubles(String key) throws NullPointerException{
        return getListOf(key, Double.class);
    }

    public Set<String> getKeys(){
        return mapData.keySet();
    }

    public static ConfigSection getConfig(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        return new ConfigSection(yaml.load(new FileInputStream(file)));
    }
}

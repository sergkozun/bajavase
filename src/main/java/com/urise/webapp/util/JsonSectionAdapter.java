package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObj = json.getAsJsonObject();
        JsonPrimitive jPrim = (JsonPrimitive) jObj.get(CLASSNAME);
        String className = jPrim.getAsString();
        try{
            Class clazz = Class.forName(className);
            return context.deserialize(jObj.get(INSTANCE), clazz);
        }catch (ClassNotFoundException e){
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type typeOfSection, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement element = context.serialize(section);
        result.add(INSTANCE, element);
        return result;
    }
}

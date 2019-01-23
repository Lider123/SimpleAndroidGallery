package com.example.babaets.simplegallery.network;

import com.example.babaets.simplegallery.models.Image;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ImageJsonDeserializer implements JsonDeserializer<Image> {
    @Override
    public Image deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject object = json.getAsJsonObject();
        String url = object.get("url").getAsString();
        String title = object.get("title").getAsString();
        return new Image(url, title);
    }
}

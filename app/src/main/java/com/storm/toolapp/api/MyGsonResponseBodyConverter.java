package com.storm.toolapp.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.storm.toolapp.api.bean.BaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by kongqing on 2017/5/22.
 */

public class MyGsonResponseBodyConverter<T extends BaseResponse> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        // 解析code
        JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("code");
        int code = 0;
        if (jsonPrimitive != null) {
            code = jsonPrimitive.getAsInt();
        }
        // 解析message
//        JsonElement jsonElement = jsonObject.get("extra");
//        String extra = null;
//        if (jsonElement != null) {
//            extra = jsonElement.getAsString();
//        }
        T t = null;
        try {
            // 通过反射获取泛型的实例对象
            Class<T> clazz = (Class<T>) type;
            t = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        if (code == 400) {
//            JsonPrimitive j = jsonObject.getAsJsonPrimitive("error_message");
//            String m = "";
//            if (j != null) {
//                m = j.getAsString();
//            }
//            Log.e("erro", m);
//        } else
        if (code == 0) {

        } else {
            // 按标准格式解析
            return gson.fromJson(json, type);
        }

        return t;
    }
}

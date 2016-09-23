package com.jeffrey.demo.retrofitdemo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Li on 2016/9/23.
 */

class GsonRespBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonRespBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        byte[] bytes = new byte[(int) value.contentLength()];
        value.byteStream().read(bytes);
        String strValue = new String(bytes);
        String output = Security.decrypt(strValue,"C31594ba@*2f5dT6");
        Log.d("fei"," ResponseBody  " + output);
        JsonReader jsonReader = gson.newJsonReader(new CharArrayReader(output.toCharArray()));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}


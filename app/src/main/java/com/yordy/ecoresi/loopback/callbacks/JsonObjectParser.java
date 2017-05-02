package com.yordy.ecoresi.loopback.callbacks;

import com.yordy.ecoresi.remoting.JsonUtil;
import com.yordy.ecoresi.remoting.Repository;
import com.yordy.ecoresi.remoting.VirtualObject;
import com.yordy.ecoresi.remoting.adapters.Adapter;

import org.json.JSONObject;

public class JsonObjectParser<T extends VirtualObject>
        extends Adapter.JsonObjectCallback {

    private final Repository<T> repository;
    private final ObjectCallback<T> callback;

    public JsonObjectParser(Repository<T> repository, ObjectCallback<T> callback) {
        this.repository = repository;
        this.callback = callback;
    }

    @Override
    public void onSuccess(JSONObject response) {
        if (response == null) {
            // Not found
            callback.onSuccess(null);
            return;
        }

        callback.onSuccess(
                repository.createObject(JsonUtil.fromJson(response)));
    }

    @Override
    public void onError(Throwable throwable) {
        callback.onError(throwable);
    }
}

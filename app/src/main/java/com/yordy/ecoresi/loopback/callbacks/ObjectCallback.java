package com.yordy.ecoresi.loopback.callbacks;

import com.yordy.ecoresi.remoting.VirtualObject;

public interface ObjectCallback<T extends VirtualObject> {
    public void onSuccess(T object);
    public void onError(Throwable t);
}


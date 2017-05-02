package com.yordy.ecoresi.loopback.callbacks;

import com.yordy.ecoresi.remoting.VirtualObject;

import java.util.List;

public interface ListCallback<T extends VirtualObject> {
    public void onSuccess(List<T> objects);
    public void onError(Throwable t);
}

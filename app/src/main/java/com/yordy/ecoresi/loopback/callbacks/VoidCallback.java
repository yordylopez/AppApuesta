package com.yordy.ecoresi.loopback.callbacks;

public interface VoidCallback {
    public void onSuccess();
    public void onError(Throwable t);
}

package com.yordy.ecoresi.api;

/**
 * Created by yordy on 31/01/2017.
 */
public class RestAdapterUrl {
    private static RestAdapterUrl ourInstance = new RestAdapterUrl();

    public static RestAdapterUrl getInstance() {
        return ourInstance;
    }

    private RestAdapterUrl() {
    }
}

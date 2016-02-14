package me.williamhester.network;

import okhttp3.OkHttpClient;

/**
 * Created by william on 2/13/16.
 */
public class NetworkSingleton {

    private static OkHttpClient client;

    public static OkHttpClient getOkHttpClient() {
        if (client == null) {
            return client = new OkHttpClient();
        }
        return client;
    }

}

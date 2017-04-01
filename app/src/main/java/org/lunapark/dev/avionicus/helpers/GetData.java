package org.lunapark.dev.avionicus.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Get data from URL
 * Created by znak on 31.03.2017.
 */

public class GetData {

    private EventListener eventListener;
    private final Runnable getJSON;
    private ExecutorService executorService;
    private String link;

    public GetData(final EventListener eventListener) {
        this.eventListener = eventListener;
        executorService = Executors.newCachedThreadPool();

        getJSON = new Runnable() {

            @Override
            public void run() {
                HttpURLConnection urlConnection;
                BufferedReader reader;
                String resultJson;

                try {
                    // TODO Check internet connection
                    URL url = new URL(link);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    resultJson = buffer.toString();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    Avionikus avionikus = gson.fromJson(resultJson, Avionikus.class);
                    eventListener.onEvent(avionikus);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public void get(String link) {
        this.link = link;
        executorService.submit(getJSON);
    }

    public void dispose() {
        executorService.shutdown();
    }
}

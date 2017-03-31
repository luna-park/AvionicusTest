package org.lunapark.dev.avionicus.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Get JSON from URL
 * Created by znak on 31.03.2017.
 */

public class GetData {

    private final Callable<String> getJSON;
    private ExecutorService executorService;
    private String link;

    public GetData() {
        executorService = Executors.newCachedThreadPool();

        getJSON = new Callable<String>() {
            @Override
            public String call() throws Exception {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String resultJson = "";

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

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return resultJson;
            }
        };
    }

    public String getData(String link) {
        this.link = link;
        String result;

        try {
            result = executorService.submit(getJSON).get();

        } catch (InterruptedException | ExecutionException e) {
            result = null;
        }
        return result;
    }

    public void dispose() {
        executorService.shutdown();
    }
}

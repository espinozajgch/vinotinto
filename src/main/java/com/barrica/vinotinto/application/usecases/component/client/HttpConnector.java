package com.barrica.vinotinto.application.usecases.component.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.newHttpClient;

public final class HttpConnector implements Connector {

    public static HttpConnector instance;

    private HttpConnector() {
    }

    public static HttpConnector getInstance() {
        if (instance == null) {
            instance = new HttpConnector();
        }
        return instance;
    }

    public String getResponse(String url){

        try {
            HttpResponse<String> response;
            HttpClient client = newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

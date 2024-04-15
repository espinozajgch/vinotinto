package com.barrica.vinotinto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class VinotintoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(VinotintoApplication.class, args);
		System.out.println("Hola Mundo");

		Document doc;

		try {
			// fetching the target website
			/*
			doc = Jsoup
					.connect("https://www.sofascore.com/player/samuel-sosa/836584")
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
					.get();
			System.out.println(doc.html());
			/**/

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.sofascore.com/api/v1/event/11911071/player/35532/statistics"))
					.build();

			HttpResponse<String> response =
					client.send(request, HttpResponse.BodyHandlers.ofString());

			System.out.println(response.body());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}


	}

}

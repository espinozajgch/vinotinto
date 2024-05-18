package com.barrica.vinotinto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@PropertySource("file:${user.home}/.config/external.properties")
public class VinotintoApplication {

	public static void main(String[] args) throws IOException, URISyntaxException {
		SpringApplication.run(VinotintoApplication.class, args);

		/*
		Document doc = Jsoup
				.connect("https://www.google.com/search?q=real+avila&sca_esv=be445f0cc062ab15&rlz=1C5CHFA_enES1096ES1096&sxsrf=ADLYWIKDiSnRvZ-d5DliZKnWTNaNf4v_ag%3A1715025854848&ei=vjc5Zr26M9y5i-gPlZqZSA&ved=0ahUKEwi9ke-W6fmFAxXc3AIHHRVNBgkQ4dUDCBA&uact=5&oq=real+avila&gs_lp=Egxnd3Mtd2l6LXNlcnAiCnJlYWwgYXZpbGEyDxAuGIAEGEYYJxiKBRj9ATILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMgQQABgDMgUQABiABDILEAAYgAQYsQMYgwEyDhAuGIAEGMcBGI4FGK8BMgUQABiABDIEEAAYAzIFEAAYgAQyGRAAGIAEGEYYigUY_QEYlwUYjAUY3QTYAQNIgBhQiQhYkBRwAngBkAEAmAGWAaABpwSqAQMyLjO4AQPIAQD4AQGYAgegAvAEwgIHECMYsAMYJ8ICBxAuGLADGCfCAgoQABiwAxjWBBhHwgINEAAYgAQYsAMYQxiKBcICExAuGIAEGLADGEMYyAMYigXYAQHCAgoQLhiABBgnGIoFwgILEC4YgAQYsQMYgwHCAg4QABiABBixAxiDARiKBcICBxAuGIAEGArCAhcQLhiABBiKBRiXBRjcBBjeBBjgBNgBApgDAIgGAZAGFLoGBggBEAEYCLoGBggCEAEYFLoGBggDEAEYE5IHAzMuNKAH_kI#sie=t;/m/026_phn;2;/m/0d56yj;st;fp;1;;;")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
				.get();
		System.out.println(doc);
		Elements newsHeadlines = doc.select("div.fwXO9b.XAPH9c");

		for (Element headline : newsHeadlines) {
			System.out.println(headline.text());
		}

		/*Map<String, String> worldFlags = generateWorldFlagEmojis();
		for (Map.Entry<String, String> entry : worldFlags.entrySet()) {
			System.out.println("País: " + entry.getKey() + ", Emoji: " + entry.getValue());
		}

		Document doc;

		try {
			// fetching the target website

			doc = Jsoup
					.connect("https://es.besoccer.com/buscar/Júnior-Moreno")
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
					.get();

			System.out.println(doc.title());
			//Element content = doc.getElementById("team_performance");

			Elements body = doc.select("div.row.info");

			int i = 1;
			for (Element element : body) {
				Elements links = element.getElementsByTag("a");

				String linkHref = "";
				for (Element link : links) {
					linkHref = link.attr("href");
					String linkText = link.text();

				}
				System.out.println(i + " - Jugadores: " + linkHref);
				i++;
			}

			Element panel = doc.select("div.panel-body.table-list").first();
			Elements table = panel.select("div.table-row");

			for (Element link : table) {
				Elements img = link.getElementsByTag("img");
				for (Element im : img) {
					System.out.println("Pais:" + im.attr("alt"));
				}
			}


			/*doc = Jsoup
					.connect("https://es.besoccer.com/jugador/partidos/r-romo-28831")
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
					.get();

			System.out.println(doc.title());
			//<div id="player-profile">
			Element content = doc.getElementById("event");
			//String date = doc.getElementsByClass("lmTable");


			Elements body = content.select("tr.row-body");

			for (Element masthead : body) {

				Elements divs = masthead.select("td.green.tiny");
				if (!divs.isEmpty() && divs.size() >= 2) {
					System.out.println("Minutos: " + divs.get(0).text());
					System.out.println("Puntuacion: " + divs.get(1).text());
				}

				// Seleccionar el div con la clase "marker-wrapper" dentro del td
				Element markerWrapperDiv = masthead.select("td.match-cell div.marker-wrapper").first();

				// Verificar si se encontró el div con la clase "marker-wrapper"
				if (markerWrapperDiv != null) {
					// Obtener el texto dentro del div
					String texto = markerWrapperDiv.text();
					System.out.println("Resultado: " + texto);
				} else {
					System.out.println("No se encontró ningún div con la clase 'marker-wrapper'.");
				}

				Element event = masthead.select("div.events").first();

				if (Objects.nonNull(event)) {
					System.out.println("Eventos:");
					// Obtener todos los elementos hijos del div principal
					Elements children = event.getElementsByClass("img-ico");

					Element img = event.getElementsByTag("img").first();
					if (img != null){
						System.out.println("Evento:" + img.attr("alt"));
					}
					for (Element child : children) {
						String clase = child.className().replace("img-ico event-","");
						if(clase.equals("1")){
							if(child.getElementsByClass("ball-num").first() != null){
								System.out.println("Goal: " + child.getElementsByClass("ball-num").text());
							}
							else {
								System.out.println("Goal: " + clase);
							}

						}
						else
						if(clase.equals("15")) {
							System.out.println("Penalty Fallado: ");
						}
						else
						if(clase.equals("18")) {
							System.out.println("Salio: ");
						}
						else
						if(clase.equals("20")) {
							System.out.println("Lesion: ");
						}
						else
						if(clase.equals("22")) {
							System.out.println("Asistencia: ");
						}
						else
						if(clase.equals("5")) {
							System.out.println("Amarilla: ");
						}
						else
						if(clase.equals("19")) {
							System.out.println("Entro: ");
						}
						else
						if(clase.equals("25")) {
							System.out.println("Gol Anulado por el Var: ");
						}
						else
						if(clase.equals("3")) {
							System.out.println("Roja Directa: ");
						}
						else
						if(clase.equals("4")) {
							System.out.println("Roja por doble Amarilla: ");
						}
					}
				}

				Elements links = masthead.getElementsByTag("a");

				String linkHref = "";
				for (Element link : links) {
					linkHref = link.attr("href");
					//String linkText = link.text();
					System.out.println(link.attr("href"));

				}/**/
				//System.out.println("----");

				/*doc = Jsoup
						.connect(linkHref)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
						.get();

				System.out.println(doc.title());

				 */


				//
				//System.out.println(content);

				/*
				Element content = doc.getElementById("player-profile");
				//String date = doc.getElementsByClass("lmTable");


				//Element masthead = doc.select("div.events").first();
				//System.out.println(masthead);

				Elements links = content.getElementsByClass("lmTable__teamName");
				for (Element link : links) {
					//String linkHref = link.attr("href");
					//String linkText = link.text();
					System.out.println(link.attributes());
				}
			//}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}/**/
		//int playerId = 878938;
		//getPlayers();
		//int eventId = getLastEventId(playerId);

		//getEventData(eventId);
		//getStatistics(eventId, playerId);

		//SofaScoreCaller sofaScoreCaller = new SofaScoreCaller();
		//sofaScoreCaller.getPlayersList();

	}

}

package com.barrica.vinotinto.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmojiGenerator {

    private Map<String, String> worldFlags = new HashMap<>(){{
        put("AF", "\uD83C\uDDE6\uD83C\uDDEB"); // Afganistán
        put("AL", "\uD83C\uDDE6\uD83C\uDDF1"); // Albania
        put("DZ", "\uD83C\uDDE9\uD83C\uDDFF"); // Algeria
        put("AD", "\uD83C\uDDE6\uD83C\uDDE9"); // Andorra
        put("AO", "\uD83C\uDDE6\uD83C\uDDF4"); // Angola
        put("AG", "\uD83C\uDDE6\uD83C\uDDEC"); // Antigua y Barbuda
        put("AR", "\uD83C\uDDE6\uD83C\uDDF7"); // Argentina
        put("AM", "\uD83C\uDDE6\uD83C\uDDF2"); // Armenia
        put("AU", "\uD83C\uDDE6\uD83C\uDDFA"); // Australia
        put("AT", "\uD83C\uDDE6\uD83C\uDDF9"); // Austria
        put("AZ", "\uD83C\uDDE6\uD83C\uDDFF"); // Azerbaiyán
        put("BS", "\uD83C\uDDE7\uD83C\uDDF8"); // Bahamas
        put("BH", "\uD83C\uDDE7\uD83C\uDDED"); // Bahréin
        put("BD", "\uD83C\uDDE7\uD83C\uDDE9"); // Bangladesh
        put("BB", "\uD83C\uDDE7\uD83C\uDDE7"); // Barbados
        put("BY", "\uD83C\uDDE7\uD83C\uDDFE"); // Bielorrusia
        put("BE", "\uD83C\uDDE7\uD83C\uDDEA"); // Bélgica
        put("BZ", "\uD83C\uDDE7\uD83C\uDDFF"); // Belice
        put("BJ", "\uD83C\uDDE7\uD83C\uDDEF"); // Benin
        put("BT", "\uD83C\uDDE7\uD83C\uDDF9"); // Bután
        put("BO", "\uD83C\uDDE7\uD83C\uDDF4"); // Bolivia
        put("BA", "\uD83C\uDDE7\uD83C\uDDE6"); // Bosnia y Herzegovina
        put("BW", "\uD83C\uDDE7\uD83C\uDDFC"); // Botswana
        put("BR", "\uD83C\uDDE7\uD83C\uDDF7"); // Brasil
        put("BN", "\uD83C\uDDE7\uD83C\uDDF3"); // Brunéi
        put("BG", "\uD83C\uDDE7\uD83C\uDDEC"); // Bulgaria
        put("BF", "\uD83C\uDDE7\uD83C\uDDEB"); // Burkina Faso
        put("BI", "\uD83C\uDDE7\uD83C\uDDEE"); // Burundi
        put("CV", "\uD83C\uDDE8\uD83C\uDDFB"); // Cabo Verde
        put("KH", "\uD83C\uDDF0\uD83C\uDDED"); // Camboya
        put("CM", "\uD83C\uDDE8\uD83C\uDDF2"); // Camerún
        put("CA", "\uD83C\uDDE8\uD83C\uDDE6"); // Canadá
        put("CF", "\uD83C\uDDE8\uD83C\uDDEB"); // República Centroafricana
        put("TD", "\uD83C\uDDE8\uD83C\uDDE9"); // Chad
        put("CL", "\uD83C\uDDE8\uD83C\uDDF1"); // Chile
        put("CN", "\uD83C\uDDE8\uD83C\uDDF3"); // China
        put("CO", "\uD83C\uDDE8\uD83C\uDDF4"); // Colombia
        put("KM", "\uD83C\uDDF0\uD83C\uDDF2"); // Comoras
        put("CG", "\uD83C\uDDE8\uD83C\uDDEC"); // Congo
        put("CD", "\uD83C\uDDE8\uD83C\uDDE9"); // República Democrática del Congo
        put("CR", "\uD83C\uDDE8\uD83C\uDDF7"); // Costa Rica
        put("HR", "\uD83C\uDDED\uD83C\uDDF7"); // Croacia
        put("CU", "\uD83C\uDDE8\uD83C\uDDFA"); // Cuba
        put("CY", "\uD83C\uDDE8\uD83C\uDDFE"); // Chipre
        put("CZ", "\uD83C\uDDE8\uD83C\uDDFF"); // República Checa
        put("DK", "\uD83C\uDDE9\uD83C\uDDF0"); // Dinamarca
        put("DJ", "\uD83C\uDDE9\uD83C\uDDEF"); // Djibouti
        put("DM", "\uD83C\uDDE9\uD83C\uDDF2"); // Dominica
        put("DO", "\uD83C\uDDE9\uD83C\uDDF4"); // República Dominicana
        put("EC", "\uD83C\uDDEA\uD83C\uDDE8"); // Ecuador
        put("EG", "\uD83C\uDDEA\uD83C\uDDEC"); // Egipto
        put("SV", "\uD83C\uDDF8\uD83C\uDDFB"); // El Salvador
        put("GQ", "\uD83C\uDDEC\uD83C\uDDF6"); // Guinea Ecuatorial
        put("ER", "\uD83C\uDDEA\uD83C\uDDF7"); // Eritrea
        put("EE", "\uD83C\uDDEA\uD83C\uDDEA"); // Estonia
        put("ET", "\uD83C\uDDEA\uD83C\uDDF9"); // Etiopía
        put("FJ", "\uD83C\uDDEB\uD83C\uDDEF"); // Fiyi
        put("FI", "\uD83C\uDDEB\uD83C\uDDEE"); // Finlandia
        put("FR", "\uD83C\uDDEB\uD83C\uDDF7"); // Francia
        put("GA", "\uD83C\uDDEC\uD83C\uDDE6"); // Gabón
        put("GM", "\uD83C\uDDEC\uD83C\uDDF2"); // Gambia
        put("GE", "\uD83C\uDDEC\uD83C\uDDEA"); // Georgia
        put("DE", "\uD83C\uDDE9\uD83C\uDDEA"); // Alemania
        put("GH", "\uD83C\uDDEC\uD83C\uDDED"); // Ghana
        put("GR", "\uD83C\uDDEC\uD83C\uDDF7"); // Grecia
        put("GD", "\uD83C\uDDEC\uD83C\uDDE9"); // Granada
        put("GT", "\uD83C\uDDEC\uD83C\uDDF9"); // Guatemala
        put("GN", "\uD83C\uDDEC\uD83C\uDDF3"); // Guinea
        put("GW", "\uD83C\uDDEC\uD83C\uDDFC"); // Guinea-Bissau
        put("GY", "\uD83C\uDDEC\uD83C\uDDFE"); // Guyana
        put("HT", "\uD83C\uDDED\uD83C\uDDF9"); // Haití
        put("HN", "\uD83C\uDDED\uD83C\uDDF3"); // Honduras
        put("HU", "\uD83C\uDDED\uD83C\uDDFA"); // Hungría
        put("IS", "\uD83C\uDDEE\uD83C\uDDF8"); // Islandia
        put("IN", "\uD83C\uDDEE\uD83C\uDDF3"); // India
        put("ID", "\uD83C\uDDEE\uD83C\uDDE9"); // Indonesia
        put("IR", "\uD83C\uDDEE\uD83C\uDDF7"); // Irán
        put("IQ", "\uD83C\uDDEE\uD83C\uDDF6"); // Iraq
        put("IE", "\uD83C\uDDEE\uD83C\uDDEA"); // Irlanda
        put("IL", "\uD83C\uDDEE\uD83C\uDDF1"); // Israel
        put("IT", "\uD83C\uDDEE\uD83C\uDDF9"); // Italia
        put("JM", "\uD83C\uDDEF\uD83C\uDDF2"); // Jamaica
        put("JP", "\uD83C\uDDEF\uD83C\uDDF5"); // Japón
        put("JO", "\uD83C\uDDEF\uD83C\uDDF4"); // Jordania
        put("KZ", "\uD83C\uDDF0\uD83C\uDDFF"); // Kazajistán
        put("KE", "\uD83C\uDDF0\uD83C\uDDEA"); // Kenia
        put("KI", "\uD83C\uDDF0\uD83C\uDDEE"); // Kiribati
        put("KW", "\uD83C\uDDF0\uD83C\uDDFC"); // Kuwait
        put("KG", "\uD83C\uDDF0\uD83C\uDDEC"); // Kirguistán
        put("LV", "\uD83C\uDDF1\uD83C\uDDFB"); // Letonia
        put("LB", "\uD83C\uDDF1\uD83C\uDDE7"); // Líbano
        put("LS", "\uD83C\uDDF1\uD83C\uDDF8"); // Lesoto
        put("LR", "\uD83C\uDDF1\uD83C\uDDF7"); // Liberia
        put("LY", "\uD83C\uDDF1\uD83C\uDDFE"); // Libia
        put("LI", "\uD83C\uDDF1\uD83C\uDDEE"); // Liechtenstein
        put("LT", "\uD83C\uDDF1\uD83C\uDDF9"); // Lituania
        put("LU", "\uD83C\uDDF1\uD83C\uDDFA"); // Luxemburgo
        put("MK", "\uD83C\uDDF2\uD83C\uDDF0"); // Macedonia del Norte
        put("MG", "\uD83C\uDDF2\uD83C\uDDEC"); // Madagascar
        put("MW", "\uD83C\uDDF2\uD83C\uDDFC"); // Malaui
        put("MY", "\uD83C\uDDF2\uD83C\uDDFE"); // Malasia
        put("MV", "\uD83C\uDDF2\uD83C\uDDFB"); // Maldivas
        put("ML", "\uD83C\uDDF2\uD83C\uDDF1"); // Malí
        put("MT", "\uD83C\uDDF2\uD83C\uDDF9"); // Malta
        put("MH", "\uD83C\uDDF2\uD83C\uDDED"); // Islas Marshall
        put("MR", "\uD83C\uDDF2\uD83C\uDDF7"); // Mauritania
        put("MU", "\uD83C\uDDF2\uD83C\uDDFA"); // Mauricio
        put("MX", "\uD83C\uDDF2\uD83C\uDDFD"); // México
        put("FM", "\uD83C\uDDEB\uD83C\uDDEA"); // Micronesia
        put("MC", "\uD83C\uDDF2\uD83C\uDDE8"); // Mónaco
        put("MN", "\uD83C\uDDF2\uD83C\uDDF3"); // Mongolia
        put("ME", "\uD83C\uDDF2\uD83C\uDDEA"); // Montenegro
        put("MA", "\uD83C\uDDF2\uD83C\uDDE6"); // Marruecos
        put("MZ", "\uD83C\uDDF2\uD83C\uDDFF"); // Mozambique
        put("MM", "\uD83C\uDDF2\uD83C\uDDF2"); // Myanmar
        put("NA", "\uD83C\uDDF3\uD83C\uDDE6"); // Namibia
        put("NR", "\uD83C\uDDF3\uD83C\uDDF7"); // Nauru
        put("NP", "\uD83C\uDDF3\uD83C\uDDF5"); // Nepal
        put("NL", "\uD83C\uDDF3\uD83C\uDDF1"); // Países Bajos
        put("NZ", "\uD83C\uDDF3\uD83C\uDDFF"); // Nueva Zelanda
        put("NI", "\uD83C\uDDF3\uD83C\uDDEE"); // Nicaragua
        put("NE", "\uD83C\uDDF3\uD83C\uDDEA"); // Níger
        put("NG", "\uD83C\uDDF3\uD83C\uDDEC"); // Nigeria
        put("NO", "\uD83C\uDDF3\uD83C\uDDF4"); // Noruega
        put("OM", "\uD83C\uDDF4\uD83C\uDDF2"); // Omán
        put("PK", "\uD83C\uDDF5\uD83C\uDDF0"); // Pakistán
        put("PW", "\uD83C\uDDF5\uD83C\uDDFC"); // Palaos
        put("PA", "\uD83C\uDDF5\uD83C\uDDE6"); // Panamá
        put("PG", "\uD83C\uDDF5\uD83C\uDDEC"); // Papúa Nueva Guinea
        put("PY", "\uD83C\uDDF5\uD83C\uDDFE"); // Paraguay
        put("PE", "\uD83C\uDDF5\uD83C\uDDEA"); // Perú
        put("PH", "\uD83C\uDDF5\uD83C\uDDED"); // Filipinas
        put("PL", "\uD83C\uDDF5\uD83C\uDDF1"); // Polonia
        put("PT", "\uD83C\uDDF5\uD83C\uDDF9"); // Portugal
        put("QA", "\uD83C\uDDF6\uD83C\uDDE6"); // Catar
        put("RO", "\uD83C\uDDF7\uD83C\uDDF4"); // Rumania
        put("RU", "\uD83C\uDDF7\uD83C\uDDFA"); // Rusia
        put("RW", "\uD83C\uDDF7\uD83C\uDDFC"); // Ruanda
        put("KN", "\uD83C\uDDF0\uD83C\uDDF3"); // San Cristóbal y Nieves
        put("LC", "\uD83C\uDDF1\uD83C\uDDE8"); // Santa Lucía
        put("VC", "\uD83C\uDDFB\uD83C\uDDE8"); // San Vicente y las Granadinas
        put("WS", "\uD83C\uDDFC\uD83C\uDDF8"); // Samoa
        put("SM", "\uD83C\uDDF8\uD83C\uDDF2"); // San Marino
        put("ST", "\uD83C\uDDF8\uD83C\uDDF9"); // Santo Tomé y Príncipe
        put("SA", "\uD83C\uDDF8\uD83C\uDDE6"); // Arabia Saudita
        put("SN", "\uD83C\uDDF8\uD83C\uDDF3"); // Senegal
        put("RS", "\uD83C\uDDF7\uD83C\uDDF8"); // Serbia
        put("SC", "\uD83C\uDDF8\uD83C\uDDE8"); // Seychelles
        put("SL", "\uD83C\uDDF8\uD83C\uDDF1"); // Sierra Leona
        put("SG", "\uD83C\uDDF8\uD83C\uDDEC"); // Singapur
        put("SK", "\uD83C\uDDF8\uD83C\uDDF0"); // Eslovaquia
        put("SI", "\uD83C\uDDF8\uD83C\uDDEE"); // Eslovenia
        put("SB", "\uD83C\uDDF8\uD83C\uDDE7"); // Islas Salomón
        put("SO", "\uD83C\uDDF8\uD83C\uDDF4"); // Somalia
        put("ZA", "\uD83C\uDDFF\uD83C\uDDE6"); // Sudáfrica
        put("KR", "\uD83C\uDDF0\uD83C\uDDF7"); // Corea del Sur
        put("SS", "\uD83C\uDDF8\uD83C\uDDF8"); // Sudán del Sur
        put("ES", "\uD83C\uDDEA\uD83C\uDDF8"); // España
        put("LK", "\uD83C\uDDF1\uD83C\uDDF0"); // Sri Lanka
        put("SD", "\uD83C\uDDF8\uD83C\uDDE9"); // Sudán
        put("SR", "\uD83C\uDDF8\uD83C\uDDF7"); // Surinam
        put("SZ", "\uD83C\uDDF8\uD83C\uDDFF"); // Esuatini
        put("SE", "\uD83C\uDDF8\uD83C\uDDEA"); // Suecia
        put("CH", "\uD83C\uDDE8\uD83C\uDDED"); // Suiza
        put("SY", "\uD83C\uDDF8\uD83C\uDDFE"); // Siria
        put("TJ", "\uD83C\uDDF9\uD83C\uDDEF"); // Tayikistán
        put("TZ", "\uD83C\uDDF9\uD83C\uDDFF"); // Tanzania
        put("TH", "\uD83C\uDDF9\uD83C\uDDED"); // Tailandia
        put("TL", "\uD83C\uDDF9\uD83C\uDDF1"); // Timor Oriental
        put("TG", "\uD83C\uDDF9\uD83C\uDDEC"); // Togo
        put("TO", "\uD83C\uDDF9\uD83C\uDDF4"); // Tonga
        put("TT", "\uD83C\uDDF9\uD83C\uDDF9"); // Trinidad y Tobago
        put("TN", "\uD83C\uDDF9\uD83C\uDDF3"); // Túnez
        put("TR", "\uD83C\uDDF9\uD83C\uDDF7"); // Turquía
        put("TM", "\uD83C\uDDF9\uD83C\uDDF2"); // Turkmenistán
        put("TV", "\uD83C\uDDF9\uD83C\uDDFB"); // Tuvalu
        put("UG", "\uD83C\uDDFA\uD83C\uDDEC"); // Uganda
        put("UA", "\uD83C\uDDFA\uD83C\uDDE6"); // Ucrania
        put("AE", "\uD83C\uDDE6\uD83C\uDDEA"); // Emiratos Árabes Unidos
        put("GB", "\uD83C\uDDEC\uD83C\uDDE7"); // Reino Unido
        put("US", "\uD83C\uDDFA\uD83C\uDDF8"); // Estados Unidos
        put("UY", "\uD83C\uDDFA\uD83C\uDDFE"); // Uruguay
        put("UZ", "\uD83C\uDDFA\uD83C\uDDFF"); // Uzbekistán
        put("VU", "\uD83C\uDDFB\uD83C\uDDFA"); // Vanuatu
        put("VA", "\uD83C\uDDFB\uD83C\uDDE6"); // Ciudad del Vaticano
        put("VE", "\uD83C\uDDFB\uD83C\uDDEA"); // Venezuela
        put("VN", "\uD83C\uDDFB\uD83C\uDDF3"); // Vietnam
        put("YE", "\uD83C\uDDFE\uD83C\uDDEA"); // Yemen
        put("ZM", "\uD83C\uDDFF\uD83C\uDDF2"); // Zambia
        put("ZW", "\uD83C\uDDFF\uD83C\uDDFC"); // Zimbabue
    }};

    @Getter
    private final String checkIcon = "✅";

    @Getter
    private final String failIcon = "❌";

    @Getter
    private final String starIcon = "⭐";

    @Getter
    private final String savesIcon = "\uD83E\uDDE4";

    @Getter
    private final String keyPassIcon = "\uD83D\uDD11";

    @Getter
    private final String onTargetIcon = "\uD83C\uDFAF";

    @Getter
    private final String assistIcon = "\uD83C\uDD70\uFE0F";

    @Getter
    private final String timeIcon = "⏱";

    @Getter
    private final String goalIcon = "⚽";

    @Getter
    private final String statsIcon = "\uD83D\uDCCA";

    @Getter
    private final String calendarIcon = "\uD83D\uDDD3\uFE0F";

    @Getter
    private final String veIcon = "🇻🇪";

    @Getter
    private final String botIcon = "\uD83E\uDD16\uD83C\uDF77";

    @Getter
    private final String stadiumIcon = "\uD83C\uDFDF\uFE0F";

    @Getter
    private final String metaIcon = "\uD83E\uDD45";

    public String getFlagEmoji(String countryCode) {
        return worldFlags.get(countryCode.toUpperCase());
    }

}


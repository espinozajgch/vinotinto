package com.barrica.vinotinto.util;

import org.springframework.util.StringUtils;

public class Utils {

    public static String getTournament(String tournament) {
        if(tournament.contains("Club Friendly Games")){
            return tournament.replace("Club Friendly Games", "Amistoso").trim();
        }
        if(tournament.contains("Pre-Olympic")){
            return tournament.replace("Pre-Olympic", "Pre-Olimpico").trim();
        }
        return tournament.trim();
    }

    public static String getStage(String stage) {
        if(StringUtils.hasLength(stage)){

            if(isNumber(stage)) {
                return "J" + stage + " ";
            }
            else {
                if(stage.contains("Round of 32")){
                    return stage.replace("Round of 32", "Ronda de 32") + " ";
                }
                if(stage.contains("Round of 16")){
                    return stage.replace("Round of 16", "Octavos de Final") + " ";
                }
                else
                if(stage.contains("Round")){
                    return stage.replace("Round", "Ronda") + " ";
                }
                else
                if(stage.contains("Quarterfinals")){
                    return stage.replace("Quarterfinals", "Cuartos de Final") + " ";
                }
                else
                if(stage.contains("Semifinals")){
                    return stage.replace("Semifinals", "Semifinal") + " ";
                }
            }
        }
        return stage;
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Expresión regular para verificar si es un número
    }

    public static String getShortedUrl(String url) {
        String [] url_parts =  url.split("v=");

        if(url_parts.length > 1){
            url = "https://youtu.be/" + url_parts[1];
        }
        return url;
    }


    public static String validateLong(String stat, int size){
        return StringUtils.hasLength(stat) && (size + stat.length()) < 267 ? stat : "";
    }

}

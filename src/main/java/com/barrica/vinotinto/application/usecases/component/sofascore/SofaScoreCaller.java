package com.barrica.vinotinto.application.usecases.component.sofascore;

import com.barrica.vinotinto.application.usecases.component.client.HttpConnector;
import com.barrica.vinotinto.domain.model.dto.MatchDto;
import com.barrica.vinotinto.domain.model.dto.MatchHighlightsDto;
import com.barrica.vinotinto.domain.model.dto.MatchStatisticsDto;
import com.barrica.vinotinto.domain.model.dto.PlayerDto;
import com.barrica.vinotinto.infrastructure.adapter.entity.Rfef;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SofaScoreCaller implements HttpCaller {

    HttpConnector sofaScore = HttpConnector.getInstance();

    private final String TERCERA_RFEF = "54347";

    private Map<Integer, String> groups = new HashMap<>(){{
        put(54314, "11346"); // Grupo 1
        put(54315, "11347"); // Grupo 2
        put(54352, "11348"); // Grupo 3
        put(54346, "11349"); // Grupo 4
        put(54345, "11350"); // Grupo 5
        put(54343, "11351"); // Grupo 6
        put(54350, "11356"); // Grupo 7
        put(54347, "11357"); // Grupo 8
        put(54344, "11358"); // Grupo 9
        put(54348, "11359"); // Grupo 11
        put(54349, "11360"); // Grupo 11
        put(54342, "11361"); // Grupo 12
        put(54351, "11362"); // Grupo 13
        put(54353, "11363"); // Grupo 14
        put(54354, "11364"); // Grupo 15
        put(54357, "11365"); // Grupo 16
        put(54355, "11366"); // Grupo 17
        put(54356, "11367"); // Grupo 18
    }};

    @Override
    public PlayerDto getPlayersByPlayerId(int playerId) {
        JSONObject jsonObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/player/" + playerId));

        JSONObject player =  jsonObject.getJSONObject("player");

        return PlayerDto.builder()
                .playerName(player.getString("name"))
                .playerIdSofaScore(player.getInt("id"))
                .playerPosition(player.getString("position"))
                .playerHeight(player.has("height") ? player.getInt("height") : 0)
                .playerNumber(Integer.parseInt(player.has("jerseyNumber") ? player.getString("jerseyNumber") : "0"))
                .playerPreferredFoot(player.has("preferredFoot") ?  player.getString("preferredFoot") : "")
                .playerDateOfBirth(Integer.toString(player.getInt("dateOfBirthTimestamp")))
                .playerGender("M")
                .team(player.getJSONObject("team").getString("shortName"))
                .teamIdSofaScore(player.getJSONObject("team").getInt("id"))
                .teamCountry(player.getJSONObject("team").getJSONObject("country").getString("name"))
                .hasMatch(true)
                .build();
    }

    @Override
    public List<PlayerDto> getPlayersListByTeamId(int teamId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/team/"+ teamId +"/players"));

        List<PlayerDto> playerDtoList = new ArrayList<>();

        myObject.getJSONArray("players").forEach( item -> {
            JSONObject jsonObject = (JSONObject) item;
            JSONObject player =  jsonObject.getJSONObject("player");

            playerDtoList.add(PlayerDto.builder()
                    .playerName(player.getString("name"))
                    .playerIdSofaScore(player.getInt("id"))
                    .playerPosition(player.getString("position"))
                    .playerHeight(player.getInt("height"))
                    .playerNumber(Integer.parseInt(player.getString("jerseyNumber")))
                    .playerPreferredFoot(player.getString("preferredFoot"))
                    .playerDateOfBirth(Integer.toString(player.getInt("dateOfBirthTimestamp")))
                    .playerGender("M")
                    .team(player.getJSONObject("team").getString("shortName"))
                    .teamIdSofaScore(player.getJSONObject("team").getInt("id"))
                    .teamCountry(player.getJSONObject("team").getJSONObject("country").getString("name"))
                    .hasMatch(true)
                    .build()
            );

        });

        return playerDtoList;

    }

    public MatchDto getNextMatchIdByPlayer(int teamId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/team/" + teamId + "/events/next/0"));

        int lastEvent = 0;
        if(!myObject.has("events"))
            return null;

        JSONObject jsonObjectLastEvent = (JSONObject) myObject.getJSONArray("events").get(lastEvent);

        int eventId = jsonObjectLastEvent.getInt("id");

        String round = "";

        if(jsonObjectLastEvent.has("roundInfo")) {
            if (jsonObjectLastEvent.getJSONObject("roundInfo").has("name")) {
                round = jsonObjectLastEvent.getJSONObject("roundInfo").getString("name");
            } else {
                round = Integer.toString(jsonObjectLastEvent.getJSONObject("roundInfo").getInt("round"));
            }
        }

        String countryName = "";
        String countryAlfa2 = "";
        String countryAlfa3 = "";

        if(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").has("country")) {

            JSONObject country = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").getJSONObject("country");
            if (country.has("name")) {
                countryName = country.getString("name");
            }

            if (country.has("alpha2")) {
                countryAlfa2 = country.getString("alpha2");
            }

            if (country.has("alpha3")) {
                countryAlfa3 = country.getString("alpha3");
            }
        }

        //
        // if(jsonObjectLastEvent.getJSONObject("status").getString("type").equals("finished")) {
            return MatchDto.builder()
                    .matchIdSofaScore(eventId)
                    .matchTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp")))
                    .matchDate(formatTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp"))))
                    .homeTeam(jsonObjectLastEvent.getJSONObject("homeTeam").getString("name"))
                    //.homeScore(jsonObjectLastEvent.getJSONObject("homeScore").getInt("normaltime"))
                    .awayTeam(jsonObjectLastEvent.getJSONObject("awayTeam").getString("name"))
                    //.awayScore(jsonObjectLastEvent.getJSONObject("awayScore").getInt("normaltime"))
                    .tournamentIdSofaScore(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getInt("id"))
                    .tournamentName(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getString("name"))
                    .tournamentStage(round)
                    .seasonIdSofaScore(jsonObjectLastEvent.getJSONObject("season").getInt("id"))
                    .seasonName(jsonObjectLastEvent.getJSONObject("season").getString("name"))
                    .seasonYear(jsonObjectLastEvent.getJSONObject("season").getString("year"))
                    .hasGlobalHighlights(jsonObjectLastEvent.getBoolean("hasGlobalHighlights"))
                    .tournamentCountry(countryName)
                    .alfa2(countryAlfa2)
                    .alfa3(countryAlfa3)
                    .homeTeamShortName(jsonObjectLastEvent.getJSONObject("homeTeam").getString("shortName"))
                    .awayTeamShortName(jsonObjectLastEvent.getJSONObject("awayTeam").getString("shortName"))
                    .hometeamIdSofaScore(jsonObjectLastEvent.getJSONObject("homeTeam").getInt("id"))
                    .awayteamIdSofaScore(jsonObjectLastEvent.getJSONObject("awayTeam").getInt("id"))
                    //.matchCity(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("city").getString("name") : "")
                    //.matchCountry(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("stadium").getString("name") : "")
                    //.matchReferee(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getString("name") : "")
                    //.matchRefereeCountry(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getJSONObject("country").getString("name") : "")
                    .status(jsonObjectLastEvent.getJSONObject("status").getInt("code"))
                    .published(false)
                    .build();

    }

    @Override
    public MatchDto getLastMatchIdByPlayer(int playerId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/player/" + playerId + "/events/last/0"));

        int lastEvent = 0;
        if(myObject.has("events"))
            lastEvent = (myObject.getJSONArray("events").length()) - 1;
        else
            return null;

        JSONObject jsonObjectLastEvent = (JSONObject) myObject.getJSONArray("events").get(lastEvent);

        int eventId = jsonObjectLastEvent.getInt("id");

        String round = "";

        if(jsonObjectLastEvent.has("roundInfo")) {
            if (jsonObjectLastEvent.getJSONObject("roundInfo").has("name")) {
                round = jsonObjectLastEvent.getJSONObject("roundInfo").getString("name");
            } else {
                round = Integer.toString(jsonObjectLastEvent.getJSONObject("roundInfo").getInt("round"));
            }
        }

        String countryName = "";
        String countryAlfa2 = "";
        String countryAlfa3 = "";

        if(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").has("country")) {

            JSONObject country = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").getJSONObject("country");
            if (country.has("name")) {
                countryName = country.getString("name");
            }

            if (country.has("alpha2")) {
                countryAlfa2 = country.getString("alpha2");
            }

            if (country.has("alpha3")) {
                countryAlfa3 = country.getString("alpha3");
            }
        }

        if(jsonObjectLastEvent.getJSONObject("status").getString("type").equals("finished")) {
            return MatchDto.builder()
                    .matchIdSofaScore(eventId)
                    .matchTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp")))
                    .matchDate(formatTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp"))))
                    .homeTeam(jsonObjectLastEvent.getJSONObject("homeTeam").getString("name"))
                    .homeScore(jsonObjectLastEvent.getJSONObject("homeScore").getInt("normaltime"))
                    .awayTeam(jsonObjectLastEvent.getJSONObject("awayTeam").getString("name"))
                    .awayScore(jsonObjectLastEvent.getJSONObject("awayScore").getInt("normaltime"))
                    .tournamentIdSofaScore(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getInt("id"))
                    .tournamentName(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getString("name"))
                    .tournamentStage(round)
                    .seasonIdSofaScore(jsonObjectLastEvent.getJSONObject("season").getInt("id"))
                    .seasonName(jsonObjectLastEvent.getJSONObject("season").getString("name"))
                    .seasonYear(jsonObjectLastEvent.getJSONObject("season").getString("year"))
                    .hasGlobalHighlights(jsonObjectLastEvent.getBoolean("hasGlobalHighlights"))
                    .tournamentCountry(countryName)
                    .alfa2(countryAlfa2)
                    .alfa3(countryAlfa3)
                    .homeTeamShortName(jsonObjectLastEvent.getJSONObject("homeTeam").getString("shortName"))
                    .awayTeamShortName(jsonObjectLastEvent.getJSONObject("awayTeam").getString("shortName"))
                    .hometeamIdSofaScore(jsonObjectLastEvent.getJSONObject("homeTeam").getInt("id"))
                    .awayteamIdSofaScore(jsonObjectLastEvent.getJSONObject("awayTeam").getInt("id"))
                    .matchCity(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("city").getString("name") : "")
                    .matchCountry(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("country").getString("name") : "")
                    .matchStadium(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("stadium").getString("name") : "")
                    .matchReferee(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getString("name") : "")
                    .matchRefereeCountry(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getJSONObject("country").getString("name") : "")
                    .published(false)
                    .status(jsonObjectLastEvent.getJSONObject("status").getInt("code"))
                    .build();
        }
        else {
            return null;
        }

    }

    public List<MatchDto> getMatchByPlayer(int playerId, int page){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/player/" + playerId + "/events/last/" + page));

        List<MatchDto> matches = new ArrayList<>();
        //int lastEvent = 0;
        if(myObject.has("events")){

            myObject.getJSONArray("events").forEach(event ->{

                JSONObject jsonObjectLastEvent = (JSONObject) event;

                int eventId = jsonObjectLastEvent.getInt("id");

                String round = "";

                if(jsonObjectLastEvent.has("roundInfo")) {
                    if (jsonObjectLastEvent.getJSONObject("roundInfo").has("name")) {
                        round = jsonObjectLastEvent.getJSONObject("roundInfo").getString("name");
                    } else {
                        round = Integer.toString(jsonObjectLastEvent.getJSONObject("roundInfo").getInt("round"));
                    }
                }

                String countryName = "";
                String countryAlfa2 = "";
                String countryAlfa3 = "";

                int turnamentID = 0;
                String turnamentName = "";

                if(jsonObjectLastEvent.getJSONObject("tournament").has("uniqueTournament")) {
                    if (jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").has("country")) {

                        turnamentID = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getInt("id");
                        turnamentName = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getString("name");

                        JSONObject country = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").getJSONObject("country");
                        if (country.has("name")) {
                            countryName = country.getString("name");
                        }

                        if (country.has("alpha2")) {
                            countryAlfa2 = country.getString("alpha2");
                        }

                        if (country.has("alpha3")) {
                            countryAlfa3 = country.getString("alpha3");
                        }
                    }
                }

                if(jsonObjectLastEvent.getJSONObject("status").getString("type").equals("finished")) {

                    int homeScore = -1;
                    int awayScore = -1;

                    if (jsonObjectLastEvent.getJSONObject("homeScore").has("normaltime")){
                        homeScore = jsonObjectLastEvent.getJSONObject("homeScore").getInt("normaltime");
                    } else if (jsonObjectLastEvent.getJSONObject("homeScore").has("display")) {
                        homeScore = jsonObjectLastEvent.getJSONObject("homeScore").getInt("display");
                    }

                    if (jsonObjectLastEvent.getJSONObject("awayScore").has("normaltime")){
                        awayScore = jsonObjectLastEvent.getJSONObject("awayScore").getInt("normaltime");
                    } else if (jsonObjectLastEvent.getJSONObject("awayScore").has("display")) {
                        awayScore = jsonObjectLastEvent.getJSONObject("awayScore").getInt("display");
                    }

                    matches.add(MatchDto.builder()
                            .matchIdSofaScore(eventId)
                            .matchTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp")))
                            .matchDate(formatTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp"))))
                            .homeTeam(jsonObjectLastEvent.getJSONObject("homeTeam").getString("name"))
                            .homeScore(homeScore)
                            .awayTeam(jsonObjectLastEvent.getJSONObject("awayTeam").getString("name"))
                            .awayScore(awayScore)
                            .tournamentIdSofaScore(turnamentID)
                            .tournamentName(turnamentName)
                            .tournamentStage(round)
                            .seasonIdSofaScore(jsonObjectLastEvent.has("season") ? jsonObjectLastEvent.getJSONObject("season").getInt("id") : 0)
                            .seasonName(jsonObjectLastEvent.has("season") ? jsonObjectLastEvent.getJSONObject("season").getString("name"): null)
                            .seasonYear(jsonObjectLastEvent.has("season") ? jsonObjectLastEvent.getJSONObject("season").getString("year"): null)
                            .hasGlobalHighlights(jsonObjectLastEvent.getBoolean("hasGlobalHighlights"))
                            .tournamentCountry(countryName)
                            .alfa2(countryAlfa2)
                            .alfa3(countryAlfa3)
                            .homeTeamShortName(jsonObjectLastEvent.getJSONObject("homeTeam").getString("shortName"))
                            .awayTeamShortName(jsonObjectLastEvent.getJSONObject("awayTeam").getString("shortName"))
                            .hometeamIdSofaScore(jsonObjectLastEvent.getJSONObject("homeTeam").getInt("id"))
                            .awayteamIdSofaScore(jsonObjectLastEvent.getJSONObject("awayTeam").getInt("id"))
                            .matchCity(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("city").getString("name") : "")
                            .matchCountry(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("country").getString("name") : "")
                            .matchStadium(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("stadium").getString("name") : "")
                            .matchReferee(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getString("name") : "")
                            .matchRefereeCountry(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getJSONObject("country").getString("name") : "")
                            .published(false)
                            .status(jsonObjectLastEvent.getJSONObject("status").getInt("code"))
                            .build());
                }
            });
        }
        return matches;
    }

    public static String formatTimestamp(String timestampString) {
        long timestamp = Long.parseLong(timestampString);

        // Convertir el timestamp a Instant
        Instant instant = Instant.ofEpochSecond(timestamp);

        // Convertir Instant a LocalDateTime
        LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return datetime.format(formatter);
    }

    @Override
    public void getMatchDataById(int eventId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/event/"+eventId));

        System.out.println("------------------------------");
        System.out.println(myObject.getJSONObject("event").getJSONObject("season").getString("name"));
        //System.out.println(myObject.getJSONObject("event").getJSONObject("season").getString("year"));

        int homeScore = myObject.getJSONObject("event").getJSONObject("homeScore").getInt("normaltime");
        int awayScore = myObject.getJSONObject("event").getJSONObject("awayScore").getInt("normaltime");

        System.out.println("------------------------------");
        System.out.println("homeTeam :" + myObject.getJSONObject("event").getJSONObject("homeTeam").getString("name") + ": " + homeScore);
        System.out.println("awayTeam :" + myObject.getJSONObject("event").getJSONObject("awayTeam").getString("name") + ": " + awayScore);
        System.out.println("------------------------------");

    }

    @Override
    public List<MatchStatisticsDto> getMatchStatisticsByPlayer(int eventId, int playerId){

        String url = "https://api.sofascore.com/api/v1/event/"+ eventId +"/player/"+playerId+"/statistics";
        JSONObject objectResponse = new JSONObject(sofaScore.getResponse(url));

        List<MatchStatisticsDto> playerMatchStatisticsDtoList = new ArrayList<>();

        if(!objectResponse.has("error")) {
            JSONObject jsonObjectStatistics = objectResponse.getJSONObject("statistics");

            Iterator<String> keys = jsonObjectStatistics.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObjectStatistics.get(key);

                playerMatchStatisticsDtoList.add(
                        MatchStatisticsDto.builder()
                        .playerId(playerId)
                        .matchId(eventId)
                        .measure(key)
                        .value(value.toString())
                        .build());
            }
        }

        return playerMatchStatisticsDtoList;
    }

    @Override
    public List<MatchHighlightsDto> getMatchHighlights(int eventId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/event/"+eventId+"/highlights"));

        if(myObject.has("highlights")){
            List<MatchHighlightsDto> matchHighlightsDtoList = new ArrayList<>();

            myObject.getJSONArray("highlights").forEach( item -> {
                JSONObject jsonObjectHighlights = (JSONObject) item;

                String url = jsonObjectHighlights.has("url") ? jsonObjectHighlights.getString("url") : "";
                String thumbnailUrl = jsonObjectHighlights.has("thumbnailUrl") ? jsonObjectHighlights.getString("thumbnailUrl") : "";

                String title = jsonObjectHighlights.has("title") ? jsonObjectHighlights.getString("title") : "";
                String subtitle = jsonObjectHighlights.has("subtitle") ? jsonObjectHighlights.getString("subtitle") : "";

                matchHighlightsDtoList.add(MatchHighlightsDto
                        .builder()
                        .matchId(eventId)
                        .url(url)
                        .thumbnailUrl(thumbnailUrl)
                        .title(title)
                        .subtitle(subtitle)
                        .build());
            });

            return matchHighlightsDtoList;
        }

        return null;
    }

    public List<Rfef> getTeamData(){

        List<Rfef> rfefList = new ArrayList<>();

        groups.entrySet().stream().forEach(entry -> {

            JSONObject myObject = new JSONObject(sofaScore.getResponse("https://www.sofascore.com/api/v1/unique-tournament/" + entry.getValue() + "/season/" + entry.getKey() + "/standings/home"));
            JSONObject jsonObjectLastEvent = (JSONObject) myObject.getJSONArray("standings").get(0);
            JSONArray jsonObject = jsonObjectLastEvent.getJSONArray("rows");

            String group = jsonObjectLastEvent.getString("name");

            jsonObject.forEach(item -> {
                JSONObject jsonObjectTeam = (JSONObject) item;

                String team = jsonObjectTeam.getJSONObject("team").getString("name");

                int id = jsonObjectTeam.getJSONObject("team").getInt("id");
                Integer position = jsonObjectTeam.getInt("position");
                Integer matches = jsonObjectTeam.getInt("matches");
                Integer wins = jsonObjectTeam.getInt("wins");
                Integer draws = jsonObjectTeam.getInt("draws");
                Integer losses = jsonObjectTeam.getInt("losses");
                Integer scoresFor = jsonObjectTeam.getInt("scoresFor");
                Integer scoresAgainst = jsonObjectTeam.getInt("scoresAgainst");
                Integer points = jsonObjectTeam.getInt("points");

                Rfef rfef = new Rfef();
                rfef.setTeam_id_sofascore(id);
                rfef.setTeam_name(team);
                rfef.setPosition(position);
                rfef.setMatches(matches);
                rfef.setWins(wins);
                rfef.setDraws(draws);
                rfef.setLosses(losses);
                rfef.setScoresfor(scoresFor);
                rfef.setScoresagainst(scoresAgainst);
                rfef.setPoints(points);
                rfef.setGrupo(group);

                rfefList.add(rfef);

            });

        });

        return rfefList;
    }


    public MatchDto getLastMatchByMatchId(int matchId){

        JSONObject myObject = new JSONObject(sofaScore.getResponse("https://api.sofascore.com/api/v1/event/" + matchId));

        if(myObject.has("event")){

            JSONObject jsonObjectLastEvent = myObject.getJSONObject("event");
            int eventId = jsonObjectLastEvent.getInt("id");

            String round = "";

            if(jsonObjectLastEvent.has("roundInfo")) {
                if (jsonObjectLastEvent.getJSONObject("roundInfo").has("name")) {
                    round = jsonObjectLastEvent.getJSONObject("roundInfo").getString("name");
                } else {
                    round = Integer.toString(jsonObjectLastEvent.getJSONObject("roundInfo").getInt("round"));
                }
            }

            String countryName = "";
            String countryAlfa2 = "";
            String countryAlfa3 = "";

            if(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").has("country")) {

                JSONObject country = jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getJSONObject("category").getJSONObject("country");
                if (country.has("name")) {
                    countryName = country.getString("name");
                }

                if (country.has("alpha2")) {
                    countryAlfa2 = country.getString("alpha2");
                }

                if (country.has("alpha3")) {
                    countryAlfa3 = country.getString("alpha3");
                }
            }

            String status = jsonObjectLastEvent.getJSONObject("status").getString("type");
            if("finished".equalsIgnoreCase(status)) {
                return MatchDto.builder()
                        .matchIdSofaScore(eventId)
                        .matchTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp")))
                        .matchDate(formatTimestamp(Integer.toString(jsonObjectLastEvent.getInt("startTimestamp"))))
                        .homeTeam(jsonObjectLastEvent.getJSONObject("homeTeam").getString("name"))
                        .homeScore(jsonObjectLastEvent.getJSONObject("homeScore").getInt("normaltime"))
                        .awayTeam(jsonObjectLastEvent.getJSONObject("awayTeam").getString("name"))
                        .awayScore(jsonObjectLastEvent.getJSONObject("awayScore").getInt("normaltime"))
                        .tournamentIdSofaScore(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getInt("id"))
                        .tournamentName(jsonObjectLastEvent.getJSONObject("tournament").getJSONObject("uniqueTournament").getString("name"))
                        .tournamentStage(round)
                        .seasonIdSofaScore(jsonObjectLastEvent.getJSONObject("season").getInt("id"))
                        .seasonName(jsonObjectLastEvent.getJSONObject("season").getString("name"))
                        .seasonYear(jsonObjectLastEvent.getJSONObject("season").getString("year"))
                        .hasGlobalHighlights(jsonObjectLastEvent.getBoolean("hasGlobalHighlights"))
                        .tournamentCountry(countryName)
                        .alfa2(countryAlfa2)
                        .alfa3(countryAlfa3)
                        .homeTeamShortName(jsonObjectLastEvent.getJSONObject("homeTeam").getString("shortName"))
                        .awayTeamShortName(jsonObjectLastEvent.getJSONObject("awayTeam").getString("shortName"))
                        .hometeamIdSofaScore(jsonObjectLastEvent.getJSONObject("homeTeam").getInt("id"))
                        .awayteamIdSofaScore(jsonObjectLastEvent.getJSONObject("awayTeam").getInt("id"))
                        .matchCity(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("city").getString("name") : "")
                        .matchCountry(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("country").getString("name") : "")
                        .matchStadium(jsonObjectLastEvent.has("venue") ? jsonObjectLastEvent.getJSONObject("venue").getJSONObject("stadium").getString("name") : "")
                        .matchReferee(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getString("name") : "")
                        .matchRefereeCountry(jsonObjectLastEvent.has("referee") ? jsonObjectLastEvent.getJSONObject("referee").getJSONObject("country").has("name") ? jsonObjectLastEvent.getJSONObject("referee").getJSONObject("country").getString("name"): "" : "")
                        .published(false)
                        .status(jsonObjectLastEvent.getJSONObject("status").getInt("code"))
                        .build();
            }
            else if("postponed".equalsIgnoreCase(status)) {
                return MatchDto.builder()
                        .status(jsonObjectLastEvent.getJSONObject("status").getInt("code"))
                        .build();
            }
            else
            {
                return null;
            }
        }
        else {
            return null;
        }

    }

}

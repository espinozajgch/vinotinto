package com.barrica.vinotinto.application.usecases;

import com.barrica.vinotinto.application.mapper.*;
import com.barrica.vinotinto.application.usecases.component.TweetsDemo;
import com.barrica.vinotinto.application.usecases.component.sofascore.SofaScoreCaller;
import com.barrica.vinotinto.domain.model.dto.*;
import com.barrica.vinotinto.infrastructure.adapter.entity.*;
import com.barrica.vinotinto.infrastructure.adapter.repository.*;
import com.barrica.vinotinto.util.EmojiGenerator;
import com.twitter.clientlib.TwitterCredentialsOAuth1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.barrica.vinotinto.util.Utils.*;

@Component
public class SofaScoreUseCaseImpl implements SofaScoreUseCase {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchStatisticsRepository matchStatisticsRepository;

    @Autowired
    PlayerMatchRepository playerMatchRepository;

    @Autowired
    MatchHighlightsRepository matchHighlightsRepository;

    @Autowired
    MatchRrssRepository matchRrssRepository;

    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    MatchStatisticsMapper matchStatisticsMapper;

    @Autowired
    MatchHighlightsMapper matchHighlightsMapper;

    @Autowired
    MatchRrssMapper matchRrssMapper;

    @Autowired
    SofaScoreCaller sofaScoreCaller;

    @Autowired
    EmojiGenerator emojiGenerator;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TweetsDemo tweetsDemoComponent;

    String NO_DATA = "Sin Datos Estadisticos del Partido";


    @Override
    public List<PlayerDto> getPlayersList() {
        List<PlayerDbo> playerDboList = playerRepository.findAllByOrderById();
        return playerDboList.stream().map(item -> playerMapper.mapPlayerDboToDto(item)).toList();
    }

    public List<PlayerDto> getPlayersHasMatchList() {
        List<PlayerDbo> playerDboList = playerRepository.findAllByHasMatchTrueOrderById();
        return playerDboList.stream().map(item -> playerMapper.mapPlayerDboToDto(item)).toList();
    }

    public List<PlayerDto> getPlayersHasMatchToPublishList() {
        List<PlayerDbo> playerDboList = playerRepository.findAllByHasMatchTrueOrderById();
        return playerDboList.stream().map(item -> playerMapper.mapPlayerDboToDto(item)).toList();
    }

    @Override
    public List<PlayerDto> syncPlayers() {
        int teamId = 4722;
        return sofaScoreCaller.getPlayersListByTeamId(teamId);
    }

    @Override
    public PlayerDto syncPlayer(Integer playerId) {
        PlayerDto playerDto = sofaScoreCaller.getPlayersByPlayerId(playerId);
        playerRepository.save(playerMapper.mapPlayerDtoToDbo(playerDto));
        return playerDto;
    }

    @Override
    public List<MatchDto> getMatchList() {
        List<MatchDbo> matchDboList = matchRepository.findAll();
        return matchDboList.stream().map(item -> matchMapper.mapMatchDboToDto(item)).toList();
    }

    @Override
    public List<MatchDto> getMatchListByPlayer(int playerId) {
        List<MatchDbo> matchDboList = matchRepository.findMatchesByPlayerId(playerId);
        return matchDboList.stream().map(matchDbo -> matchMapper.mapMatchDboToDto(matchDbo)).toList();
    }

    public List<MatchDto> getMatchListNotPublishedByPlayer(int playerId) {
        List<MatchDbo> matchDboList = matchRepository.findMatchesByPlayerIdAndByPublishedFalse(playerId);
        return matchDboList.stream().map(matchDbo -> matchMapper.mapMatchDboToDto(matchDbo)).toList();
    }

    public List<MatchDto> getMatchListNotPublishedByPlayerAndByStatus(int playerId, int status) {

        //System.out.println("Twitter: " + twitterConsumerKey);

        List<MatchDbo> matchDboList = matchRepository.findMatchesByPlayerIdAndByPublishedFalseAndStatus(playerId, status);
        return matchDboList.stream().map(matchDbo -> matchMapper.mapMatchDboToDto(matchDbo)).toList();
    }

    @Override
    public void syncPlayersMatches(String date) {

        List<PlayerDbo> playerDboList = playerRepository.findAllByHasMatchTrueOrderById();

        String formattedDate;
        if (!StringUtils.hasLength(date)){
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedDate = localDate.format(formatter);
        } else {
            formattedDate = date;
        }

        playerDboList.forEach( player ->{
            System.out.println("#####################");
            System.out.println("Player:" + player.getPlayerName());
            System.out.println("Team:" + player.getTeam());
            System.out.println("Fecha del Partido:" + formattedDate);

            MatchDbo matchDbo = matchRepository.findPlayerMatchesByDateAndByPublishedFalse(player.getPlayerIdSofaScore(), formattedDate);

            if(Objects.nonNull(matchDbo)) {
                System.out.println("Match:" + matchDbo.getMatchIdSofaScore());

                if(matchDbo.getStatus() == 0) {
                    MatchDto matchDto = sofaScoreCaller.getLastMatchByMatchId(matchDbo.getMatchIdSofaScore());
                    //MatchDto matchDto = sofaScoreCaller.getLastMatchIdByPlayer(match.getMatchIdSofaScore());

                    if(Objects.nonNull(matchDto)) {
                        if(matchDto.getStatus() == 100) {
                            System.out.println("Partido Finalizado");
                            matchDto.setId(matchDbo.getId());
                            syncMatch(matchDto, player.getPlayerIdSofaScore());
                        }
                        else
                        if(matchDto.getStatus() == 60){
                            System.out.println("Partido Postergado");
                            matchDbo.setStatus(matchDto.getStatus());
                            syncMatchDBO(matchDbo);
                        }
                    }
                    else{
                        System.out.println("Partido no iniciado");
                    }
                }
                else{
                    System.out.println("Partido Actualizado - Añadiendo Estadisticas del Jugador");
                    syncHighlights(matchDbo.getMatchIdSofaScore(), matchDbo.getHasGlobalHighlights());
                    syncStatistics(matchDbo.getMatchIdSofaScore(), player.getPlayerIdSofaScore());
                }
            }
            else{
                System.out.println("Partido a realizar en otra fecha");
            }

            //syncPlayerMatch(player.getPlayerIdSofaScore());
            //syncPlayerMatches(player.getPlayerIdSofaScore());

            System.out.println("");
        });
    }

    public void syncPlayersNextMatches(){

        List<PlayerDbo> playerDboList = playerRepository.findAllByHasMatchFalseOrderById();

        playerDboList.forEach( player ->{
            System.out.println("#####################");
            System.out.println("Player:" + player.getPlayerName());
            System.out.println("Team:" + player.getTeam());

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = localDate.format(formatter);
            System.out.println("Fecha Actual:" + formattedDate);

            syncPlayerNextMatch(player.getPlayerIdSofaScore(), player.getTeamIdSofaScore());

        });
    }

    public void syncPlayerNextMatch(Integer playerId, int teamId) {

        MatchDto matchDto = sofaScoreCaller.getNextMatchIdByPlayer(teamId);

        if(Objects.nonNull(matchDto)) {
            System.out.println("Nuevo Partido Agregado");
            try {
                playerMatchRepository.save(
                        PlayerMatchDbo
                                .builder()
                                .playerId(playerId)
                                .matchId(matchDto.getMatchIdSofaScore())
                                .build()
                );

                syncNextMatch(matchDto, playerId);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Error: Clave primaria duplicada al insertar la tupla playerId/MatchId: " + playerId + "/" + matchDto.getMatchIdSofaScore());
                syncNextMatch(matchDto, playerId);
            }
        }
    }

    private void syncNextMatch(MatchDto matchDto, int playerId){
        try {
            MatchDbo matchDbo = matchMapper.mapMatchDtoToDbo(matchDto);
            matchRepository.save(matchDbo);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: Clave primaria duplicada al insertar el Match " + matchDto.getMatchIdSofaScore());
        }
    }

    @Override
    public void syncPlayerMatch(Integer playerId) {

        MatchDto matchDto = sofaScoreCaller.getLastMatchIdByPlayer(playerId);

        if(Objects.nonNull(matchDto)) {
            try {
                playerMatchRepository.save(
                        PlayerMatchDbo
                                .builder()
                                .playerId(playerId)
                                .matchId(matchDto.getMatchIdSofaScore())
                                .build()
                );

                syncMatch(matchDto, playerId);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Error: Clave primaria duplicada al insertar la tupla playerId/MatchId: " + playerId + "/" + matchDto.getMatchIdSofaScore());
                syncMatch(matchDto, playerId);
            }
        }
    }

    public void syncPlayerMatches(Integer playerId) {

        int i = 0;
        boolean next = true;
        while(next) {
            List<MatchDto> matchDto = sofaScoreCaller.getMatchByPlayer(playerId, i);

            if (Objects.nonNull(matchDto) && !matchDto.isEmpty()) {

                matchDto.forEach(match -> {
                    try {
                        playerMatchRepository.save(
                                PlayerMatchDbo
                                        .builder()
                                        .playerId(playerId)
                                        .matchId(match.getMatchIdSofaScore())
                                        .build()
                        );

                        syncMatch(match, playerId);
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("Error: Clave primaria duplicada al insertar la tupla playerId/MatchId: " + playerId + "/" + match.getMatchIdSofaScore());
                        syncMatch(match, playerId);
                    }
                });

                if(i>2)
                    next = false;

                i++;

            }else{
                next = false;
            }
        }
    }

    private void syncMatch(MatchDto matchDto, int playerId){
        try {
            MatchDbo matchDbo = matchMapper.mapMatchDtoToDbo(matchDto);
            matchRepository.save(matchDbo);

            System.out.println("Partido Actualizado - Añadiendo Estadisticas del Jugador");
            syncHighlights(matchDto.getMatchIdSofaScore(), matchDto.getHasGlobalHighlights());
            syncStatistics(matchDto.getMatchIdSofaScore(), playerId);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: Clave primaria duplicada al insertar el Match " + matchDto.getMatchIdSofaScore());

            System.out.println("Partido Actualizado - Añadiendo Estadisticas del Jugador");
            syncHighlights(matchDto.getMatchIdSofaScore(), matchDto.getHasGlobalHighlights());
            syncStatistics(matchDto.getMatchIdSofaScore(), playerId);
        }
    }

    private void syncMatchDBO(MatchDbo matchDbo){
        try {
            matchRepository.save(matchDbo);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: Clave primaria duplicada al insertar el Match " + matchDbo.getMatchIdSofaScore());
        }
    }

    @Override
    public List<PMatchStatisticsDto> getMatchStatisticsListByPlayer(int playerId) {
        List<MatchDbo> matchDboList = matchRepository.findMatchesByPlayerId(playerId);
        return matchDboList
                .stream()
                .map(matchDbo ->
                        PMatchStatisticsDto
                        .builder()
                        .matchDto(matchMapper.mapMatchDboToDto(matchDbo))
                        .statisticsDtoList(getMatchStatisticsListByMatchIdAndPlayerId(matchDbo.getMatchIdSofaScore(), playerId))
                        .build())
                .toList().stream()
                .sorted(Comparator.comparing(item -> item.getMatchDto().getMatchDate()))
                .toList();
    }

    @Override
        public List<StatisticsDto> getMatchStatisticsListByMatchIdAndPlayerId(Integer matchId, Integer playerId) {
        List<MatchStatisticsDbo> matchStatisticsDboList = matchStatisticsRepository.findStatisticsByMatchIdAndPlayerId(matchId, playerId);
        return matchStatisticsDboList.stream().map(item -> matchStatisticsMapper.mapMatchStatisticsDboToStatisticsDto(item)).toList();
    }

    @Override
    public void syncPlayerMatchStatistics() {
        List<PlayerMatchDbo> playerMatchDboList = playerMatchRepository.findAll();
        playerMatchDboList.forEach(item -> syncStatistics(item.getMatchId(), item.getPlayerId()) );
    }

    private void syncStatistics(int matchId, int playerId) {

        try {
            List<MatchStatisticsDto> playerMatchStatisticsDtoList = sofaScoreCaller.getMatchStatisticsByPlayer(matchId, playerId);

            List<MatchStatisticsDbo> matchStatisticsDboList = playerMatchStatisticsDtoList
                    .stream()
                    .map(item -> matchStatisticsMapper.mapMatchStatisticsDtoToDbo(item))
                    .toList();

            matchStatisticsRepository.saveAll(matchStatisticsDboList);

        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: Clave primaria duplicada al insertar las estadisticas MatchId/playerId: " + matchId + "/" + playerId);
        }
    }

    @Override
    public List<PMatchHighlightsDto> getMatchHighlightsByPlayerId(Integer playerId) {

        List<PMatchHighlightsDto> pMatchHighlightsDtoList = new ArrayList<>();

        List<MatchDbo> matchDboList = matchRepository.findMatchesByPlayerId(playerId);

        matchDboList.forEach(matchDbo -> {

            List<MatchHighlightsDbo> matchHighlightsDboList = matchHighlightsRepository.findByMatchId(matchDbo.getMatchIdSofaScore());

            pMatchHighlightsDtoList.add(
                    PMatchHighlightsDto
                            .builder()
                            .matchDto(matchMapper.mapMatchDboToDto(matchDbo))
                            .highlightsDtoList(matchHighlightsDboList.stream().map(item -> matchHighlightsMapper.mapMatchStatisticsDboToHighlightsDto(item)).toList())
                            .build());
        });

        return pMatchHighlightsDtoList;

    }

    @Override
    public void syncMatchesHighlights() {
        List<MatchDbo> matchDboList = matchRepository.findAll();
        matchDboList.forEach(matchDbo -> syncHighlights(matchDbo.getMatchIdSofaScore(), matchDbo.getHasGlobalHighlights()));
    }

    private void syncHighlights(Integer matchId, Boolean hasGlobalHighlights) {
        if(Boolean.TRUE.equals(hasGlobalHighlights)) {
            List<MatchHighlightsDto> matchHighlightsDtoList = sofaScoreCaller.getMatchHighlights(matchId);

            try {
                if(Objects.nonNull(matchHighlightsDtoList)){
                    matchHighlightsRepository.saveAll(matchHighlightsDtoList.stream().map(item -> matchHighlightsMapper.mapMatchHighlightsDtoToDbo(item)).toList());
                }
            } catch (DataIntegrityViolationException e) {
                System.out.println("Error: Clave primaria duplicada al insertar el Highlights " + matchId);
            }
        }
    }

    @Override
    public void syncMatchHighlights(Integer matchId) {
        MatchDbo matchDbo = matchRepository.findByMatchIdSofaScore(matchId);
        syncHighlights(matchDbo.getMatchIdSofaScore(), matchDbo.getHasGlobalHighlights());
    }

    @Override
    public List<PMatchOverallDto> getMatchOverallDtoList(int playerId, List<MatchDto> matchDtoList) {
        return matchDtoList.stream().map(matchDto -> {

            List<MatchHighlightsDbo> matchHighlightsDboList = matchHighlightsRepository.findByMatchId(matchDto.getMatchIdSofaScore());
            List<MatchStatisticsDbo> matchStatisticsDboList = matchStatisticsRepository.findStatisticsByMatchIdAndPlayerId(matchDto.getMatchIdSofaScore(), playerId);

            return PMatchOverallDto

                    .builder()
                    .matchDto(matchDto)
                    .highlightsDtoList(matchHighlightsDboList.stream().map(item -> matchHighlightsMapper.mapMatchStatisticsDboToHighlightsDto(item)).toList())
                    .statisticsDtoList(matchStatisticsDboList.stream().map(item -> matchStatisticsMapper.mapMatchStatisticsDboToStatisticsDto(item)).toList())
                    .build();
        }).toList();
    }

    public List<PMatchOverallRRSSDto> getMatchOverallRRSSDtoList(PlayerDto playerDto, List<MatchDto> matchDtoList, boolean tweet) {

        return matchDtoList.stream().map(matchDto -> {

            String header = getHeader(playerDto, matchDto);
            String summary = getSummary(matchDto.getMatchIdSofaScore(), playerDto.getPlayerIdSofaScore(), header.length());

            StringBuilder post = new StringBuilder();
            post.append(header)
                    .append("\n\n")
                    .append(summary);

            String url = "";

            List<String> urls = getHighlightsUrls(playerDto, matchDto.getMatchIdSofaScore());

            String tweetId = null;
            if(!summary.isEmpty() && !NO_DATA.equalsIgnoreCase(summary)){
                if(!urls.isEmpty()){
                    url = urls.get(0);
                    if(post.length() < 256){
                        post.append(url);
                    }
                    else{

                        if (url.contains("https://twitter.com") || url.contains("https://x.com")) {

                            String [] url_parts =  url.split("status/");
                            if(url_parts.length > 1){
                                String urlPart = url_parts[1];
                                tweetId =  urlPart.replace("/video/1","");
                                System.out.println("Tweet id: " + tweetId);
                            }

                        }
                    }

                    //header +=   "\n" + (validate(url, (header.length())));
                }

                if(tweet) {
                    createTweet(matchDto.getMatchIdSofaScore(), post.toString().trim(), tweetId);
                }
            }

            System.out.println(" ################");
            System.out.println(post);
            System.out.println("Url: " + url);
            System.out.println("Urls: " + urls);
            System.out.println("Fecha: " + matchDto.getMatchDate());
            System.out.println("Size: " + post.length());
            System.out.println("################");
            System.out.println("\n");

            return PMatchOverallRRSSDto
                    .builder()
                    .matchDto(matchDto)
                    .rrss(post.toString())
                    .build();

        }).toList();
    }

    private String getHeader(PlayerDto playerDto, MatchDto matchDto){
        String title = getTitle(playerDto);
        String subtitle = getSubtitle(matchDto);
        String result = getResult(matchDto);

        return new StringBuilder()
                .append(emojiGenerator.getBotIcon().trim())
                .append("\n")
                .append(title.trim())
                .append("\n")
                .append(subtitle.trim())
                .append("\n")
                .append(result.trim())
                .toString();
    }

    private String getResult(MatchDto matchDto) {
        return new StringBuilder()
                .append(emojiGenerator.getMetaIcon().trim())
                .append(matchDto.getHomeTeamShortName().trim())
                .append(" ")
                .append(matchDto.getHomeScore())
                .append("-")
                .append(matchDto.getAwayScore())
                .append(" ")
                .append(matchDto.getAwayTeamShortName().trim())
                .toString();

    }

    private List<String> getHighlightsUrls(PlayerDto playerDto, Integer matchId) {
        List<String> urls = new ArrayList<>();

        List<MatchHighlightsDbo> matchHighlightsDboList = matchHighlightsRepository.findByMatchId(matchId);

        matchHighlightsDboList.forEach(highlights -> {
            String subtitle = highlights.getSubtitle();
            String url = "";
            if((subtitle.equalsIgnoreCase("Full Highlights")
                    || subtitle.equalsIgnoreCase("All Goals")) || subtitle.contains(playerDto.getPlayerName())){

                url = highlights.getUrl();
                String [] url_parts =  url.split("&");
                if(url.contains("youtu.be")) {
                    urls.add(url_parts[0]);
                }
                else
                if(url.contains("www.youtube.com")){
                    urls.add(getShortedUrl(url_parts[0]));
                }

                else
                if (url.contains("https://twitter.com") || url.contains("https://x.com")) {
                    String replaceDomain = url_parts[0].replace("https://x.com","https://twitter.com");

                    urls.add(replaceDomain + "/video/1");
                    /*String [] url_parts =  url.split("status/");
                    if(url_parts.length > 1){
                        url = "Twitter id: " + url_parts[1];
                    }/**/

                }
            }
        });
        return urls;
    }

    private void createTweet(Integer matchId, String post, String tweetIdQuote) {
        try {
            System.out.println("Publicando Tweet \uD83E\uDD16⚽\uD83C\uDF77");

            String tweetId = tweetsDemoComponent.createTweets(post, tweetIdQuote);

            matchRrssRepository.save(matchRrssMapper.mapMatchRrssDtoToDbo(MatchRrssDto.builder()
                    .matchIdSofaScore(matchId)
                    .tweetId(tweetId)
                    .message(post)
                    .build()));/**/

            // Pausa la ejecución del programa durante 5 segundos
            Thread.sleep(5000); // 5000 milisegundos = 5 segundos
        } catch (InterruptedException | DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            System.out.println("Error: Clave primaria duplicada al insertar el Tweet " + matchId);
        }

    }

    private String getTitle(PlayerDto playerDto) {
        String name = StringUtils.hasLength(playerDto.getTwitter()) ? playerDto.getTwitter() : playerDto.getPlayerName();
        String team = StringUtils.hasLength(playerDto.getTeamTwitter()) ? playerDto.getTeamTwitter() : playerDto.getTeam();
        return emojiGenerator.getVeIcon().trim() + name.trim() + " - " + team.trim();
    }

    private String getSubtitle(MatchDto matchDto) {
        String countryIcon = StringUtils.hasLength(matchDto.getAlfa2()) ? emojiGenerator.getFlagEmoji(matchDto.getAlfa2()) : "";
        String iconFinal = StringUtils.hasLength(countryIcon) ? countryIcon : emojiGenerator.getCalendarIcon();
        String tournament = getTournament(matchDto.getTournamentName());
        String stage = getStage(matchDto.getTournamentStage());
        return iconFinal.trim() + stage + tournament;
    }

    private String getSummary(Integer matchId, Integer playerId, int size){

        List<MatchStatisticsDbo> matchStatisticsDboList = matchStatisticsRepository.findStatisticsByMatchIdAndPlayerId(matchId, playerId);

        StringBuilder sb = new StringBuilder();
        StringBuilder min = new StringBuilder();
        StringBuilder goal = new StringBuilder();
        StringBuilder asistencias = new StringBuilder();
        StringBuilder resumen = new StringBuilder();
        StringBuilder valoracion = new StringBuilder();
        StringBuilder pasesPrecisos = new StringBuilder();
        StringBuilder duelosGanados = new StringBuilder();
        StringBuilder atajadas = new StringBuilder();
        StringBuilder penaltisCometidos = new StringBuilder();
        StringBuilder penaltisRecibidos = new StringBuilder();
        StringBuilder penaltisFallado = new StringBuilder();
        StringBuilder autogol = new StringBuilder();
        StringBuilder intercepciones = new StringBuilder();
        StringBuilder entradas = new StringBuilder();

        StringBuilder totalPases = new StringBuilder();
        StringBuilder regatesGanados = new StringBuilder();
        StringBuilder regatesTotales = new StringBuilder();

        StringBuilder pasesClaves = new StringBuilder();
        StringBuilder disparosPuerta = new StringBuilder();

        StringBuilder despejes = new StringBuilder();
        StringBuilder balonesLargosTotales = new StringBuilder();
        StringBuilder balonesLargosPrecisos = new StringBuilder();

        matchStatisticsDboList.forEach(item -> {
            if(item.getMeasure().equalsIgnoreCase("totalLongBalls")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    balonesLargosTotales.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("accurateLongBalls")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    balonesLargosPrecisos.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("totalClearance")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    despejes.append(emojiGenerator.getCheckIcon()).append("Despejes: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("onTargetScoringAttempt")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    disparosPuerta.append(emojiGenerator.getOnTargetIcon()).append("Disparos a Puerta: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("keyPass")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    pasesClaves.append(emojiGenerator.getKeyPassIcon()).append("Pases Claves: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("minutesPlayed")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    min.append(emojiGenerator.getTimeIcon()).append("Minutos: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("goals")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    goal.append(emojiGenerator.getGoalIcon()).append("Goles: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("goalAssist")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    asistencias.append(emojiGenerator.getAssistIcon()).append("Asistencias: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("accuratePass")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    pasesPrecisos.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("duelWon")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    duelosGanados.append(emojiGenerator.getCheckIcon()).append("Duelos Ganados: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("interceptionWon")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    intercepciones.append(emojiGenerator.getCheckIcon()).append("Intercepciones: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("totalTackle")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    entradas.append(emojiGenerator.getCheckIcon()).append("Entradas: ").append(item.getValue()).append("\n");
            }


            if(item.getMeasure().equalsIgnoreCase("totalPass")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    totalPases.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("wonContest")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    regatesGanados.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("totalContest")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    regatesTotales.append(item.getValue());
            }

            if(item.getMeasure().equalsIgnoreCase("saves")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    atajadas.append(emojiGenerator.getSavesIcon()).append("Atajadas: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("penaltyWon")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    penaltisRecibidos.append(emojiGenerator.getCheckIcon()).append("Penaltis Recibidos: ").append(item.getValue()).append("\n");
            }

            /*
            if(item.getMeasure().equalsIgnoreCase("penaltyConceded")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    penaltisCometidos.append(emojiGenerator.getFailIcon()).append("Penaltis Cometidos: ").append(item.getValue()).append("\n");
            }*/

            if(item.getMeasure().equalsIgnoreCase("penaltyMiss")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    penaltisFallado.append(emojiGenerator.getFailIcon()).append("Penaltis Fallados: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("ownGoals")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    autogol.append(emojiGenerator.getFailIcon()).append("Goles en Propia Puerta: ").append(item.getValue()).append("\n");
            }

            if(item.getMeasure().equalsIgnoreCase("rating")){
                if(!item.getValue().equalsIgnoreCase("0"))
                    valoracion.append(emojiGenerator.getStarIcon()).append("Rating: ").append(item.getValue()).append("\n");
            }

            sb.append(item.getMeasure()).append(": ").append(item.getValue()).append("\n");

        });

        resumen.append(StringUtils.hasLength(min.toString())? emojiGenerator.getStatsIcon() + "@SofascoreLA\n": "");
        resumen.append(StringUtils.hasLength(min.toString())? min.toString():"");
        resumen.append(StringUtils.hasLength(atajadas.toString())? atajadas.toString():"");
        resumen.append(StringUtils.hasLength(goal.toString())?goal.toString():"");
        resumen.append(StringUtils.hasLength(asistencias.toString())?asistencias.toString():"");
        resumen.append(StringUtils.hasLength(disparosPuerta.toString())?disparosPuerta.toString():"");
        resumen.append(StringUtils.hasLength(pasesClaves.toString())?pasesClaves.toString():"");
        resumen.append(validateLong(getPases(totalPases, pasesPrecisos), (size + resumen.length())));
        resumen.append(validateLong(getRegates(regatesGanados, regatesTotales), (size + resumen.length())));
        resumen.append(validateLong(getBalonesLargos(balonesLargosTotales, balonesLargosPrecisos), (size + resumen.length())));
        resumen.append(validateLong(duelosGanados.toString(), (size + resumen.length())));
        resumen.append(validateLong(intercepciones.toString(), (size + resumen.length())));
        resumen.append(validateLong(entradas.toString(), (size + resumen.length())));
        resumen.append(validateLong(despejes.toString(), (size + resumen.length())));
        resumen.append(validateLong(penaltisRecibidos.toString(), (size + resumen.length())));
        resumen.append(validateLong(penaltisFallado.toString(), (size + resumen.length())));
        resumen.append(validateLong(penaltisCometidos.toString(), (size + resumen.length())));
        resumen.append(validateLong(autogol.toString(), (size + resumen.length())));
        resumen.append(StringUtils.hasLength(valoracion.toString())?valoracion.toString():"");

        return StringUtils.hasLength(resumen.toString()) ? resumen.toString(): NO_DATA;
    }

    private String getBalonesLargos(StringBuilder balonesLargosTotales, StringBuilder balonesLargosPrecisos) {
        StringBuilder balonesLargos = new StringBuilder();
        if(StringUtils.hasLength(balonesLargosTotales.toString()) && StringUtils.hasLength(balonesLargosPrecisos.toString())){
            balonesLargos.append(emojiGenerator.getCheckIcon()).append("Balones Largos: ").append(balonesLargosPrecisos).append("/").append(balonesLargosTotales).append(" ");

            int precisos = Integer.parseInt(balonesLargosPrecisos.toString());
            int totales = Integer.parseInt(balonesLargosTotales.toString());
            double precision = (double) precisos / totales;
            int porcentaje = (int) (precision * 100);

            balonesLargos.append("(").append(porcentaje).append("%").append(")").append("\n");

        }
        else{
            if(StringUtils.hasLength(balonesLargosPrecisos.toString()))
                balonesLargos.append(emojiGenerator.getCheckIcon()).append("Balones Largos Precisos: ").append(balonesLargosPrecisos).append("\n");
        }
        return balonesLargos.toString();
    }

    private String getRegates(StringBuilder regatesGanados, StringBuilder regatesTotales) {
        StringBuilder regates = new StringBuilder();
        if(StringUtils.hasLength(regatesGanados.toString()) && StringUtils.hasLength(regatesTotales.toString())){
            regates.append(emojiGenerator.getCheckIcon()).append("Regates: ").append(regatesGanados).append("/").append(regatesTotales).append(" ");

            int precisos = Integer.parseInt(regatesGanados.toString());
            int totales = Integer.parseInt(regatesTotales.toString());
            double precision = (double) precisos / totales;
            int porcentaje = (int) (precision * 100);

            regates.append("(").append(porcentaje).append("%").append(")").append("\n");
        }
        else{
            if(StringUtils.hasLength(regatesGanados.toString()))
                regates.append(emojiGenerator.getCheckIcon()).append("Regates Ganados: ").append(regatesGanados).append("\n");
        }
        return regates.toString();
    }

    private String getPases(StringBuilder totalPases, StringBuilder pasesPrecisos) {
        StringBuilder pases = new StringBuilder();
        if(StringUtils.hasLength(totalPases.toString()) && StringUtils.hasLength(pasesPrecisos.toString())){
            pases.append(emojiGenerator.getCheckIcon()).append("Pases: ").append(pasesPrecisos).append("/").append(totalPases).append(" ");

            int precisos = Integer.parseInt(pasesPrecisos.toString());
            int totales = Integer.parseInt(totalPases.toString());
            double precision = (double) precisos / totales;
            int porcentaje = (int) (precision * 100);

            pases.append("(").append(porcentaje).append("%").append(")").append("\n");

        }
        else{
            if(StringUtils.hasLength(pasesPrecisos.toString()))
                pases.append(emojiGenerator.getCheckIcon()).append("Pases Precisos: ").append(pasesPrecisos).append("\n");
        }
        return pases.toString();
    }

    public void syncTeam() {
        List<Rfef> rfefList = sofaScoreCaller.getTeamData();
        teamRepository.saveAll(rfefList);
    }

}

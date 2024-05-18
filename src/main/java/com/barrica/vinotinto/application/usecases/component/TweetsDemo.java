package com.barrica.vinotinto.application.usecases.component;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.TwitterCredentialsOAuth1;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.auth.HttpBasicAuth;
import com.twitter.clientlib.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/*
 * Sample code to demonstrate the use of the v2 Tweets endpoint
 * */
public class TweetsDemo {

    static String bearer = "AAAAAAAAAAAAAAAAAAAAAJ%2FxtQEAAAAAPGoLfNNkBlkJ1E98v%2F1X45lXcy0%3DVn4wIeLzvG9Y1qoQaPlLY1qi3qWzoHaRsX9A2h7d3LuRbWXCoH";

    // To set your enviornment variables in your terminal run the following line:
    // export 'BEARER_TOKEN'='<your_bearer_token>'

   /* public static void main(String args[]) throws IOException, URISyntaxException {
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAJ%2FxtQEAAAAAPGoLfNNkBlkJ1E98v%2F1X45lXcy0%3DVn4wIeLzvG9Y1qoQaPlLY1qi3qWzoHaRsX9A2h7d3LuRbWXCoH";

        if (null != bearerToken) {
            //Replace comma separated ids with Tweets Ids of your choice
            String response = getTweets("1228393702244134912", bearerToken);
            System.out.println(response);
        } else {
            System.out.println("There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable");
        }
    }*/

    /*
     * This method calls the v2 Tweets endpoint with ids as query parameter
     * */
    public static String getTweets(String ids, String bearerToken) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("ids", ids));
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    public static void getTweets(){
        //The ID of the Tweet
        String ids = "1778507109425983728";

        TwitterApi apiInstance = new TwitterApi();

        Set<String> expansions = new HashSet<>(List.of("author_id")); // Set<String> | A comma separated list of fields to expand.
        Set<String> tweetFields = new HashSet<>(Arrays.asList("created_at", "lang", "context_annotations")); // Set<String> | A comma separated list of Tweet fields to display.
        Set<String> userFields = new HashSet<>(Arrays.asList("created_at", "description", "name")); // Set<String> | A comma separated list of User fields to display.


        // Instantiate auth credentials (App-only example)
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

        // Pass credentials to library client
        apiInstance.setTwitterCredentials(credentials);


        try {
            SingleTweetLookupResponse result = apiInstance.tweets().findTweetById(ids, null, null, null, null, null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TweetsApi#findTweetById");
            System.err.println("Status code: " + e.toString());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            //e.printStackTrace();
        }

    }

    public static String createTweets(TwitterCredentialsOAuth1 twitterCredentialsOAuth, String text, String tweetIdQuote){
        String tweetId = "0";

        // Instantiate library client
        TwitterApi apiInstance = new TwitterApi();

        // Instantiate auth credentials (App-only example)
        //TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

        // Pass credentials to library client
        apiInstance.setTwitterCredentials(twitterCredentialsOAuth);

        CreateTweetRequest createTweetRequest = new CreateTweetRequest();
        // The text of the Tweet
        createTweetRequest.text(text);

        if(StringUtils.hasLength(tweetIdQuote)){
            createTweetRequest.quoteTweetId(tweetIdQuote);
        }


        try {
            TweetCreateResponse result = apiInstance.tweets().createTweet(createTweetRequest);
            System.out.println("Create Tweet :" + result);
        } catch (IllegalArgumentException e ) {
            String error = e.getMessage();
            System.err.println("Exception when calling TweetsApi#createTweet");
            System.err.println("Status code: " + error);

            String[] errors = error.split("JSON:");

            if(errors.length > 1){
                JSONObject myObject = new JSONObject(errors[1]);
                tweetId = myObject.getString("id");
                System.out.println("tweetId: " + tweetId);
            }

        } catch (ApiException e) {
            System.err.println("Status code: " + e.getMessage());
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
        }

        return tweetId;
    }

    public static String getUsers(String usernames) throws IOException, URISyntaxException {
        String userResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", usernames));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearer));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return userResponse;
    }

    public static void getUserInfo(TwitterCredentialsOAuth1 twitterCredentialsOAuth){
        try {
            /*TwitterCredentialsOAuth1 twitterCredentialsOAuth1 = new TwitterCredentialsOAuth1(
                    "QVU74erE6Pp7wgcqCHHem6KBj",
                    "v8XJWmVIdxnAHCPS8WzihZkBNyVF5sDl1TkB0RvtmcVjcBj2T1",
                    "76861750-dBdW2mSQ9QScthEg5ri68qTXz3lkR8gYu1zi6KswZ",
                    "IxgdpoFn20kA3urmKk0drksnozNZp09dgwz7ULKEaPQd5");

            /*TwitterCredentialsOAuth2 twitterCredentialsOAuth2 = new TwitterCredentialsOAuth2(
                    "TEN2V0s4YWFaVXYzZ0U5a081em46MTpjaQ",
                    "SDeqyXML22PPRp90Avg8Mue3NR16fnT5ThuZwTbL9nCcO080li",
                    "76861750-MqLlKmPAQ5Tu9nBWJdQNgI6cJicCoq7sUstldHZc4",
                    "false");*/

            // Instantiate library client
            TwitterApi apiInstance = new TwitterApi();

            // Instantiate auth credentials (App-only example)
            TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

            // Pass credentials to library client
            apiInstance.setTwitterCredentials(twitterCredentialsOAuth);
            SingleUserLookupResponse result = apiInstance.users().findMyUser(null, null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling UsersApi#findMyUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    public static void createPoll(TwitterCredentialsOAuth1 twitterCredentialsOAuth){
        // Instantiate library client
        TwitterApi apiInstance = new TwitterApi();

        // Instantiate auth credentials (App-only example)
        //TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

        // Pass credentials to library client
        apiInstance.setTwitterCredentials(twitterCredentialsOAuth);

        // Set the params values

        CreateTweetRequest createTweetRequest = new CreateTweetRequest();
        CreateTweetRequestPoll createTweetRequestPoll = new CreateTweetRequestPoll();

        // The text of the Tweet
        createTweetRequest.text("Are you excited for the weekend?");

        // Options for a Tweet with a poll
        List<String> pollOptions = Arrays.asList("Yes", "Maybe", "No");
        createTweetRequestPoll.options(pollOptions);

        // Duration of the poll in minutes
        createTweetRequestPoll.durationMinutes(120);

        createTweetRequest.poll(createTweetRequestPoll);

        try {
            TweetCreateResponse result = apiInstance.tweets().createTweet(createTweetRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TweetsApi#createTweet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    public static void replayTweets(TwitterCredentialsOAuth1 twitterCredentialsOAuth, String text, String id){
        // Set the params values


        /*TwitterCredentialsOAuth2 twitterCredentialsOAuth2 = new TwitterCredentialsOAuth2(
                "TEN2V0s4YWFaVXYzZ0U5a081em46MTpjaQ",
                "SDeqyXML22PPRp90Avg8Mue3NR16fnT5ThuZwTbL9nCcO080li",
                "76861750-MqLlKmPAQ5Tu9nBWJdQNgI6cJicCoq7sUstldHZc4",
                "false");*/

        // Instantiate library client
        TwitterApi apiInstance = new TwitterApi();

        // Instantiate auth credentials (App-only example)
        //TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

        // Pass credentials to library client
        apiInstance.setTwitterCredentials(twitterCredentialsOAuth);

        CreateTweetRequest createTweetRequest = new CreateTweetRequest();
        // The text of the Tweet
        createTweetRequest.text(text);
        createTweetRequest.quoteTweetId(id);

        //TweetCreateResponseData tweetCreateResponseData = new TweetCreateResponseData()
        try {

            TweetCreateResponse result = apiInstance.tweets().createTweet(createTweetRequest);
            System.out.println("Create Tweet ");
        } catch (IllegalArgumentException e ) {

            System.err.println("Exception when calling TweetsApi#createTweet");
            System.err.println("Status code: " + e.getMessage());
            //System.err.println("Status code: " + e.getCode());
            //System.err.println("Reason: " + e.getResponseBody());
            //System.err.println("Response headers: " + e.getResponseHeaders());
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getMessage());
            //System.err.println("Status code: " + e.getCode());
            //System.err.println("Reason: " + e.getResponseBody());
            //System.err.println("Response headers: " + e.getResponseHeaders());
        }
    }
}

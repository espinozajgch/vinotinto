package com.barrica.vinotinto.application.usecases.component;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsOAuth1;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.CreateTweetRequest;
import com.twitter.clientlib.model.CreateTweetRequestPoll;
import com.twitter.clientlib.model.SingleUserLookupResponse;
import com.twitter.clientlib.model.TweetCreateResponse;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class TweetsDemo {

    @Value("${twitter.consumer.key}")
    private String twitterConsumerKey;

    @Value("${twitter.consumer.secret}")
    private String twitterConsumerSecret;

    @Value("${twitter.token}")
    private String twitterToken;

    @Value("${twitter.token.secret}")
    private String twitterTokenSecret;

    TwitterCredentialsOAuth1 barrica;

    @PostConstruct
    public void init() {
        barrica = new TwitterCredentialsOAuth1(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterToken,
                twitterTokenSecret);
    }


    public String createTweets(String text, String tweetIdQuote){
        String tweetId = "0";

        // Instantiate library client
        TwitterApi apiInstance = new TwitterApi();

        // Instantiate auth credentials (App-only example)
        //TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearer);

        // Pass credentials to library client
        apiInstance.setTwitterCredentials(barrica);

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

    private void getUserInfo(TwitterCredentialsOAuth1 twitterCredentialsOAuth){
        try {

            // Instantiate library client
            TwitterApi apiInstance = new TwitterApi();

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

    private void createPoll(TwitterCredentialsOAuth1 twitterCredentialsOAuth){
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

    private void replayTweets(TwitterCredentialsOAuth1 twitterCredentialsOAuth, String text, String id){
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

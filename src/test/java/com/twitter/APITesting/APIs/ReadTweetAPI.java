package com.twitter.APITesting.APIs;

import com.twitter.APITesting.setUp.BaseTest;
import com.twitter.APITesting.testcases.TwitterAPITest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReadTweetAPI extends BaseTest {

    public static Response sendGetRequestToReadTweetAPIWithValidAuth() {
        String tweetId = "";
        tweetId = TwitterAPITest.getTweetId();
        System.out.println(tweetId);

        Response response = given().auth().oauth(config.getProperty("consumerKey"), config.getProperty("consumerSecret"),
                config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .queryParam("id", tweetId)
                .get(config.getProperty("readTweetEndpoint"));
        return response;
    }
}

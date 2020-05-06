package com.twitter.APITesting.APIs;

import com.twitter.APITesting.setUp.BaseTest;
import com.twitter.APITesting.testcases.TwitterAPITest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteTweetAPI extends BaseTest {
    static String tweetId = "";
    public static Response sendPostRequestToDeleteTweetAPIWithValidAuth() {

        tweetId =TwitterAPITest.getTweetId();

        Response response = given().auth().oauth(config.getProperty("consumerKey"), config.getProperty("consumerSecret"),
                config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .pathParam("id", tweetId)
                .post(config.getProperty("deleteTweetEndPoint"));
        System.out.println(response.prettyPrint());
        return response;
    }

    public static Response sendPostRequestToDeleteTweetAPIWithInvalidTweetId() {

        tweetId = TwitterAPITest.getDeletedTweetId();

        Response response = given().auth().oauth(config.getProperty("consumerKey"), config.getProperty("consumerSecret"),
                config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .pathParam("id", tweetId)
                .post(config.getProperty("deleteTweetEndPoint"));
        System.out.println(response.prettyPrint());
        return response;
    }

}

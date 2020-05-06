package com.twitter.APITesting.APIs;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.twitter.APITesting.setUp.BaseTest;

import io.restassured.response.Response;

public class PostTweetAPI  extends BaseTest {

    public static Response sendPostRequestToPostTweetAPIWithValidAuth(Hashtable<String, String> data) {

        Response response = given().auth().oauth(config.getProperty("consumerKey"), config.getProperty("consumerSecret"),
                config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .queryParam("status", data.get("status"))
                .post(config.getProperty("postTweetEndPoint"));
        //System.out.println(response.prettyPrint());
        return response;
    }

    public static Response sendPostRequestToPostTweetAPIWithInValidAuth(Hashtable<String, String> data) {


        Response response = given().auth().oauth(config.getProperty("inValidConsumerKey"), config.getProperty("consumerSecret"),
                config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .queryParam("status", data.get("status"))
                .post(config.getProperty("postTweetEndPoint"));
        System.out.println(response.prettyPrint());
        return response;
    }

}

package com.twitter.APITesting.APIs;

import com.twitter.APITesting.setUp.BaseTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DirectMessagesAPI extends BaseTest {

    public static Response sendGetRequestToDirectMessagesAPI() {
        Response response = given().basePath(config.getProperty("directMessagesPath"))
                .auth().oauth(config.getProperty("consumerKey"), config.getProperty("consumerSecret"),
                        config.getProperty("accessToken"), config.getProperty("accessSecret"))
                .get(config.getProperty("welcomeMessagesEndPoint"));
        return response;
    }
}

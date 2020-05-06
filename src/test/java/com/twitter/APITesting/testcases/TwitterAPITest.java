package com.twitter.APITesting.testcases;


import java.util.Collection;
import java.util.Hashtable;

import com.twitter.APITesting.APIs.DeleteTweetAPI;
import com.twitter.APITesting.APIs.DirectMessagesAPI;
import com.twitter.APITesting.APIs.PostTweetAPI;
import com.twitter.APITesting.APIs.ReadTweetAPI;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.twitter.APITesting.listeners.ExtentListeners;
import com.twitter.APITesting.setUp.BaseTest;
import com.twitter.APITesting.utilities.DataUtil;

import io.restassured.response.Response;

public class TwitterAPITest extends BaseTest {
	static private String tweetId = "";
	static private String deletedTweetId = "";

	//Tests to add, read and delete data from external source aka EXCEl

	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void validatePostTweetWithValidAuth(Hashtable<String, String> data) {

		Response response = PostTweetAPI.sendPostRequestToPostTweetAPIWithValidAuth(data);
		ExtentListeners.testReport.get().info(response.toString());
		tweetId = response.getBody().path("id_str");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(tweetId.length()>0);
	}

	@Test(dependsOnMethods={"validatePostTweetWithValidAuth"})
	public void validateGetTweetWithValidAuth() {

		Response response = ReadTweetAPI.sendGetRequestToReadTweetAPIWithValidAuth();
		ExtentListeners.testReport.get().info(response.toString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getBody().path("id_str"), tweetId);
	}

	@Test(dependsOnMethods={"validateGetTweetWithValidAuth"})
	public void validateDeleteTweetWithValidAuth() {

		Response response = DeleteTweetAPI.sendPostRequestToDeleteTweetAPIWithValidAuth();
		ExtentListeners.testReport.get().info(response.toString());
		deletedTweetId = tweetId;
		Assert.assertEquals(response.statusCode(), 200);
	}


	//Test to verify the HTTP Code : 404 - Resource not found
	@Test(dependsOnMethods={"validateDeleteTweetWithValidAuth"})
	public void validateDeleteTweetWithInValidTweet() {

		Response response = DeleteTweetAPI.sendPostRequestToDeleteTweetAPIWithInvalidTweetId();
		ExtentListeners.testReport.get().info(response.toString());
		JSONArray JSONResponseBody =
				new JSONArray((Collection<?>) response.getBody().path("errors"));
		String message = JSONResponseBody.getJSONObject(0).getString("message");
		Assert.assertEquals(response.statusCode(), 404);
		Assert.assertEquals(message, "No status found with that ID.");
	}


	//Test to verify the HTTP Code : 403 - Forbidden
	@Test(dependsOnMethods={"validateDeleteTweetWithValidAuth"})
	public void validateGetDiretMessages() {

		Response response = DirectMessagesAPI.sendGetRequestToDirectMessagesAPI();
		ExtentListeners.testReport.get().info(response.toString());
		JSONArray JSONResponseBody =
				new JSONArray((Collection<?>) response.getBody().path("errors"));
		String message = JSONResponseBody.getJSONObject(0).getString("message");
		Assert.assertEquals(response.statusCode(), 403);
		Assert.assertEquals(message, "This application is not allowed to access or delete your direct messages.");

	}


	//Test to verify the HTTP Code : 401 - Forbidden
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void validatePostTweetWithInValidAuth(Hashtable<String, String> data) {

		Response response = PostTweetAPI.sendPostRequestToPostTweetAPIWithInValidAuth(data);
		ExtentListeners.testReport.get().info(response.toString());
		JSONArray JSONResponseBody =
				new JSONArray((Collection<?>) response.getBody().path("errors"));
		String message = JSONResponseBody.getJSONObject(0).getString("message");
		Assert.assertEquals(response.statusCode(), 401);
		Assert.assertEquals(message, "Could not authenticate you.");

	}


	public static String getTweetId(){
		return tweetId;
	}

	public static String getDeletedTweetId(){
		return deletedTweetId;
	}

}

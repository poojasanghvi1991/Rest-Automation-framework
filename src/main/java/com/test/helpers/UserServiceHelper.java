package com.test.helpers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.constants.Endpoints;
import com.test.models.AddUser_RequestPOJO;
import com.test.models.CUD_responsePOJO;
import com.test.models.DeleteUser_RequestPOJO;
import com.test.models.GetUser_ResponsePOJO;
import com.test.models.Login_RequestPOJO;
import com.test.models.UpdateUser_RequestPOJO;
import com.test.utils.Utils;


public class UserServiceHelper {

	protected static Response response;
	protected static String access_token = null;
	
	
	public static String getBaseUri() throws IOException {
		
		String baseUri = Utils.getConfigProperty("baseUri");
		return baseUri;
	}

	public static List<Map<String,String>> loginToTekarch() throws IOException{
		
		String username = Utils.getConfigProperty("username");
		String password = Utils.getConfigProperty("password");
		
		Login_RequestPOJO pojoObj = new Login_RequestPOJO(username, password);
		response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(pojoObj)
				.when()
				.post(Endpoints.LOGIN);
		
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String,String>> tokenList = mapper.readValue(response.asString(), new TypeReference<List<Map<String,String>>>(){});
		return tokenList;
//		return response;
	}
	
	public static String getToken() throws IOException {
		
		List<Map<String,String>> tokenList = loginToTekarch();
//		access_token = response.jsonPath().getString("[0].token");
		access_token = tokenList.get(0).get("token");
		return access_token;
	}
	
	public static List<GetUser_ResponsePOJO> getUserData() throws JsonMappingException, JsonProcessingException {
		
		Header head = new Header("token", access_token);
		response = RestAssured.given()
				.header(head)
				.when()
				.get(Endpoints.GET_DATA);
		
		ObjectMapper mapper = new ObjectMapper();
		List<GetUser_ResponsePOJO> allUsersList = mapper.readValue(response.asString(), new TypeReference<List<GetUser_ResponsePOJO>>(){});
//		GetUser_ResponsePOJO[] allUsersArray = response.as(GetUser_ResponsePOJO[].class);
//		List<GetUser_ResponsePOJO> allUsersList = new ArrayList<GetUser_ResponsePOJO>(Arrays.asList(allUsersArray));
		return allUsersList;		
	}
	

	public static Response addUserData(String acctno, String deptno, String salary, String pincode) {
		
		Header head = new Header("token", access_token);
		AddUser_RequestPOJO pojoObj = ReusableMethods.loadAddUserObject(acctno, deptno, salary, pincode);
		response = RestAssured.given()
				.header(head)
				.contentType(ContentType.JSON)
				.body(pojoObj)
				.when()
				.post(Endpoints.ADD_DATA);	
		return response;
	}
	
	public static CUD_responsePOJO updateUserData(String acctno, String deptno, String salary, String pincode, String userid, String id) throws JsonMappingException, JsonProcessingException {
		
		Header head = new Header("token", access_token);
		UpdateUser_RequestPOJO pojoObj = ReusableMethods.loadUpdateUserObject(acctno, deptno, salary, pincode, userid, id);
		response = RestAssured.given()
				.header(head)
				.contentType(ContentType.JSON)
				.body(pojoObj)
				.when()
				.put(Endpoints.UPDATE_DATA);
		
		CUD_responsePOJO updateObj = response.as(CUD_responsePOJO.class);
		return updateObj;
	}
	
	public static Response deleteUserData(String userid, String id) throws JsonMappingException, JsonProcessingException {
		
		Header head = new Header("token", access_token);
		DeleteUser_RequestPOJO pojoObj = ReusableMethods.loadDeleteUserObject(userid, id);
		response = RestAssured.given()
				.header(head)
				.contentType(ContentType.JSON)
				.body(pojoObj)
				.when()
				.delete(Endpoints.DELETE_DATA);
		return response;
	}
	
}

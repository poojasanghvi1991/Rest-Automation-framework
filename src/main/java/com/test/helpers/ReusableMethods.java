package com.test.helpers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import io.restassured.module.jsv.JsonSchemaValidator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.models.AddUser_RequestPOJO;
import com.test.models.DeleteUser_RequestPOJO;
import com.test.models.GetUser_ResponsePOJO;
import com.test.models.UpdateUser_RequestPOJO;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;


public class ReusableMethods {
	

	public static AddUser_RequestPOJO loadAddUserObject(String acctno, String deptno, String salary, String pincode) {
		AddUser_RequestPOJO ob = new AddUser_RequestPOJO();
		ob.setAccountno(acctno);
		ob.setDepartmentno(deptno);
		ob.setSalary(salary);
		ob.setPincode(pincode);
		return ob;
	}
	
	public static UpdateUser_RequestPOJO loadUpdateUserObject(String acctno, String deptno, String salary, String pincode, String userid, String id) throws JsonMappingException, JsonProcessingException {
		
//		List<GetUser_ResponsePOJO> list = UserServiceHelper.getUserData();
//		String userid = list.get(0).getUserid();
//		String id = list.get(0).getId();
		
		UpdateUser_RequestPOJO ob = new UpdateUser_RequestPOJO();
		ob.setAccountno(acctno);
		ob.setDepartmentno(deptno);
		ob.setSalary(salary);
		ob.setPincode(pincode);
		ob.setUserid(userid);
		ob.setId(id);
		return ob;
	}
	
	
	public static DeleteUser_RequestPOJO loadDeleteUserObject(String userid, String id) throws JsonMappingException, JsonProcessingException {
		
//		List<GetUser_ResponsePOJO> list = UserServiceHelper.getUserData();
//		String userid = list.get(0).getUserid();
//		String id = list.get(0).getId();
		
		DeleteUser_RequestPOJO ob = new DeleteUser_RequestPOJO();
		ob.setUserid(userid);
		ob.setId(id);
		return ob;
	}
	
	public static String getJsonPathStringData(Response response, String path) {
		return response.jsonPath().getString(path);
	}
	
	public static int getStatusCode(Response response) {
		return response.statusCode();
	}
	
	public static String getContentType(Response response) {
		return response.getContentType();
	}
	
	public static Long getResponseTimeIn(Response response, TimeUnit unit) {	
		return response.getTimeIn(unit);
	}
	
	public static Headers getHeaders(Response response) {
		return response.getHeaders();
	}
	
	
	public static String getStatusLine(Response response) {
		return response.getStatusLine();
	}
	
	public static void getPrettyPrint(Response response) {
		response.body().prettyPrint();
	}
	
	public static void validateStatusCode(Response response, int code) {
		response.then().statusCode(code);
	}
	
	public static void validateContentType(Response response, ContentType contentType) {
		response.then().contentType(contentType);
	}
	
	public static void validateTimeLessThan(Response response, Long time) {
		response.then().time(Matchers.lessThan(time));
	}
	
	public static void validateResponseSchema(Response response, String classpath) {
		response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(classpath));
	}
	
	public static void ValidateResponseDataEqualTo(Response response, String jsonpath, String value) {
		response.then().body(jsonpath, Matchers.equalTo(value));
	}
	
	public static void logCompleteResponse(Response response) {
		response.then().log().all();
	}
	
}

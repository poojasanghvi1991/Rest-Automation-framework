package com.test.allTests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.test.helpers.ReusableMethods;
import com.test.helpers.UserServiceHelper;
import com.test.models.CUD_responsePOJO;
import com.test.models.GetUser_ResponsePOJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TekarchApiTestScriptE2E extends UserServiceHelper{

	static String userId;
	static String recordId;
	
	
	@BeforeMethod
	public void setBaseUri() throws IOException {	
		RestAssured.baseURI = getBaseUri();
	}
	
	@Test(priority=1)
	public void TC_001_validLogin() throws IOException {
		
		access_token = getToken();			//getToken calls the loginToTekarch() method implicitly
		
		ReusableMethods.getPrettyPrint(response);
		
		ReusableMethods.validateResponseSchema(response, "schemas/Login_ResponseSchema.json");
		ReusableMethods.validateContentType(response, ContentType.JSON);
		ReusableMethods.validateStatusCode(response, 201);
		ReusableMethods.validateTimeLessThan(response, 3000L);
		
		String statusLine = ReusableMethods.getStatusLine(response);
		System.out.println("/nresponse status line==========" + statusLine);

		System.out.println("/nToken generated========" + access_token);
	}
	
	
	@Parameters({"accNumber", "deptno", "salary", "pincode"})
	@Test(priority=2)
	public void TC_002_addUserData(String accNumber, String deptno, String salary, String pincode) throws IOException {
		
		if(access_token == null) {
			access_token = getToken();
		}
		response = addUserData(accNumber, deptno, salary, pincode);
		ReusableMethods.getPrettyPrint(response);
		
		ReusableMethods.validateResponseSchema(response, "schemas/CUD_responseSchema.json");
		ReusableMethods.validateContentType(response, ContentType.JSON);
		ReusableMethods.validateStatusCode(response, 201);
		ReusableMethods.validateTimeLessThan(response, 5000L);
		ReusableMethods.ValidateResponseDataEqualTo(response, "status", "success");
		
		ReusableMethods.logCompleteResponse(response);
		String statusLine = ReusableMethods.getStatusLine(response);
		System.out.println("\nResponse status line===========" +statusLine);
		
		String message = ReusableMethods.getJsonPathStringData(response, "status");
		System.out.println("response message==========" + message);
	}
	
	@Parameters("accNumber")
	@Test(priority=3)
	public void TC_003_getUserData(String accNumber) throws IOException {
		
		if(access_token == null) {
			access_token = getToken();
		}
		List<GetUser_ResponsePOJO> allUsersList = getUserData();
		
		ReusableMethods.getPrettyPrint(response);
		ReusableMethods.validateResponseSchema(response, "schemas/GetUsers_ResponseSchema.json");
		ReusableMethods.validateContentType(response, ContentType.JSON);
		ReusableMethods.validateStatusCode(response, 200);
		ReusableMethods.validateTimeLessThan(response, 30000L);
		
		int totalRecords = allUsersList.size();
		System.out.println("Total number of user records============" + totalRecords);
		
//		Object fullFirstRecord = allUsersList.get(0);
		Object fullFirstRecord  = response.jsonPath().param("accNumAlias", accNumber).get("find{it-> it.accountno == accNumAlias}");
		System.out.println("Full record of most recently created user===========" + fullFirstRecord);
		
		String acctno = allUsersList.get(0).getAccountno();
		System.out.println("\nAccount number of most recently created user===========" + acctno);
		
//		userId = allUsersList.get(0).getUserid();
		userId = response.jsonPath().param("accNumAlias", accNumber).get("find{it-> it.accountno == accNumAlias}.userid");
		System.out.println("USERID of most recently created user===========" + userId);
		
//		recordId = allUsersList.get(0).getId();
		recordId = response.jsonPath().param("accNumAlias", accNumber).get("find{it-> it.accountno == accNumAlias}.id");
		System.out.println("ID of most recently created user===========" + recordId);
		
		System.out.println("\nResponse status line===========" + ReusableMethods.getStatusLine(response));
	
		String deptno = ReusableMethods.getJsonPathStringData(response, "[0].departmentno");
		System.out.println("Department number of most recently created user========" + deptno);
	}

	
	@Parameters({"accNumber", "updated_deptno", "updated_salary", "pincode"})
	@Test(priority=4)
	public void TC_004_updateUserData(String accNumber, String updated_deptno, String updated_salary, String pincode) throws IOException {
		
		if(access_token == null) {
			access_token = getToken();				//"TA-233mnp3"; "t7klgJQVzg4APHTnBj4V"; "DjDIsedTgoh7ZJASHPM2"
		}
		CUD_responsePOJO updateObj = updateUserData(accNumber, updated_deptno, updated_salary, pincode, userId , recordId);
		ReusableMethods.getPrettyPrint(response);
		
		ReusableMethods.validateResponseSchema(response, "schemas/CUD_responseSchema.json");
		ReusableMethods.validateContentType(response, ContentType.JSON);
		ReusableMethods.validateStatusCode(response, 200);
		ReusableMethods.validateTimeLessThan(response, 5000L);
		ReusableMethods.ValidateResponseDataEqualTo(response, "status", "success");
		
		ReusableMethods.logCompleteResponse(response);
		
		String message = updateObj.getStatus();
		System.out.println("response message========" + message);
	}
	
	
	@Test(priority=5)
	public void TC_005_deleteUserData() throws IOException {
		
		if(access_token == null) {
			access_token = getToken();
		}
		response = deleteUserData(userId, recordId);
	ReusableMethods.getPrettyPrint(response);
		
		ReusableMethods.validateResponseSchema(response, "schemas/CUD_responseSchema.json");
		ReusableMethods.validateContentType(response, ContentType.JSON);
		ReusableMethods.validateStatusCode(response, 200);
		ReusableMethods.validateTimeLessThan(response, 5000L);
		ReusableMethods.ValidateResponseDataEqualTo(response, "status", "success");
		
		ReusableMethods.logCompleteResponse(response);
		
		String message = ReusableMethods.getJsonPathStringData(response, "status");
		System.out.println("response message========" + message);
		
	}
	
}

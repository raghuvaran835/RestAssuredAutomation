package tests;

import org.testng.annotations.Test;

//import io.restassured.RestAssured;  //Normal import
import static io.restassured.RestAssured.*; //static import
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestsExample {
	
	
	@Test
	public void test_1()
	{
		Response response =get("https://reqres.in/api/users?page=2");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		System.out.println(response.getTime());
		System.out.println(response.getHeader("content-type"));
		System.out.println(response.getStatusLine());
	}
	
	@Test
	public void test2()
	{
		baseURI="https://reqres.in/api";
		given().when().
			get("/users?page=2").
		then().
			statusCode(200).
			body("data[1].id",equalTo(8));
		
		
	}
	
	@Test
	public void adLocationAPI()
	{
		String new_address="No 1/1,pillaiyar Kovil Street,Thiruvalam";
		baseURI="http://rahulshettyacademy.com";
		String response=given()
				.queryParam("key", "qaclick123")
				.body("{\r\n"
						+ "  \"location\": {\r\n"
						+ "    \"lat\": -39.383495,\r\n"
						+ "    \"lng\": 39.427367\r\n"
						+ "  },\r\n"
						+ "  \"accuracy\": 50,\r\n"
						+ "  \"name\":\"BHEL1\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
						+ "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n"
						+ "    \"shop\"\r\n"
						+ "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n"
						+ "  \"language\": \"French-IN\"\r\n"
						+ "}\r\n"
						+ "")
				.log().all()
		.when()
			.post("/maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat().statusCode(200)
			.body("scope", equalTo("APP"))
			.header("Content-Type", "application/json;charset=UTF-8").extract().response().asString();
		
		JsonPath jp=new JsonPath(response);
		String place_id=jp.getString("place_id");
		System.out.println(response);
		System.out.println("Place Id is :"+place_id);
		
		// Update API
		
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+new_address+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when()
			.put("/maps/api/place/update/json")
		.then()
			.log().all()
			.assertThat().statusCode(200)
			.body("msg", equalTo("Address successfully updated"));
		
		
		// Get API
		
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id",place_id).
		when()
			.get("/maps/api/place/get/json").
		then()
			.log().all().assertThat().statusCode(200)
			.body("address", equalTo(new_address));
			
	}

}

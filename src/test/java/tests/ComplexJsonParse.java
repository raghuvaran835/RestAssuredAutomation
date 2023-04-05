package tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	@Test
	public void complexJsonValidation()
	{
		JsonPath jp= new JsonPath(Payload.getCoursePayload());
		
//		Print No of Courses Returned by API
		
		int courseCount=jp.getInt("courses.size()");
		System.out.println("No of Courses:"+courseCount);
		
//		Print total Purchase Amount
		
		int totalAmount=jp.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
//		Print the title of first Course
		
		String titleFirstCourse=jp.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
//		Print All Courses title and Respective prices
		
		for(int i=0;i<courseCount;i++)
		{
			System.out.println(jp.get("courses["+i+"].title"));
			System.out.println(jp.getInt("courses["+i+"].price"));
		}
		
//		Print No of copies of title 'RPA'
		for(int i=0;i<courseCount;i++)
		{
			String courseTitle="RPA";
			System.out.println("Print No of copies of title :"+courseTitle);
			if(jp.get("courses["+i+"].title").equals(courseTitle))
			{
				System.out.println(jp.getInt("courses["+i+"].copies"));
				break;
			}
			
		}
		
//		Verify if sum of all course prices* copies matches total purchase amount
		
		System.out.println("Verify if sum of all course prices* copies matches total purchase amount");
		
		int sum=0;
		for(int i=0;i<courseCount;i++)
		{
			sum +=jp.getInt("courses["+i+"].price") * jp.getInt("courses["+i+"].copies");
		}
		System.out.println("Sum :"+sum);
		assertEquals(totalAmount, sum);
		
		
	}
}

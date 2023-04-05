package tests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicAddBookApi {
	
	@Test(dataProvider = "BooksData")
	public void addBookDynamicAPI(String name,String isbn,int aisle,String author)
	{
		baseURI="https://rahulshettyacademy.com";
		
		given()
			.header("Content-Type","application/json")
			.body(Payload.getAddBookPayload(name,isbn,aisle,author))
			.log().all()
		.when()
		.post("Library/Addbook.php")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200);
		
	}
	
	@Test
	public void addBookStaticApi() throws IOException
	{
		baseURI="https://rahulshettyacademy.com";
		
		byte[] b=Files.readAllBytes(Paths.get("src\\test\\java\\JsonFiles\\AddBook.json"));
		String addBookPayload=new String(b);
		
		given()
			.header("Content-Type","application/json")
			.body(addBookPayload)
			.log().all()
		.when()
		.post("Library/Addbook.php")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200);
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getBookData()
	{
		return new Object[][] {
			{"C++","BCA",4010,"Raghu"},
			{"Python","BCA",4011,"Raghu"}
		};
	}
	
	

}

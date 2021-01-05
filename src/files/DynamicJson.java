package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import files.ReusableMethods;

public class DynamicJson {
	
	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").
		body(payload.AddBook(isbn, aisle))
		.when()
		.post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
		
		//deleteBook
		String delete_response = given().log().all().header("Content-Type", "application/json").
				body("{\n"
						+ " \n"
						+ "\"ID\" : \""+id+"\"\n"
						+ " \n"
						+ "} ")
				.when()
				.post("/Library/DeleteBook.php ")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js2 = ReusableMethods.rawToJson(delete_response);
				String msg = js2.get("msg");
				System.out.println(msg);
		
	}
	
	@DataProvider(name = "BookData")
	public Object[][] getData() {
		
		//array=collection of elements
		//Multidimensional array=collection of arrays
		return new Object[][] {{"asdfd","360"},{"asdffggdss","3530"},{"aafdsd","310"}};
	}

}

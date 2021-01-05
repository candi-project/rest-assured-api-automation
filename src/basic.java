import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;



import files.payload;
import files.ReusableMethods;


public class basic  {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
    //Validate if Add Place API is working as expected.
		
		//given - All input details
		//when - submit the API - resource name, http method
		//Then - validate the response
		
		//body(payload.AddPlace())
		//content of the file to String -> content of file convert into bytes -> Byte data to String
		
		RestAssured.baseURI = "https://rahulshettyacademy.com" ;
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String (Files.readAllBytes(Paths.get("/Users/candichiu/Downloads/AddPlace.json")))).when().post("/maps/api/place/add/json")
		 .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		 .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.print(response);
		
		JsonPath js = new JsonPath(response); //for pasing json
		String place_id = js.getString("place_id");
		
		//System.out.print(place_id);
		
		//Update Place
		
		String newAddr = "Baierbrunner,street,28";
				
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\n"
				+ "\"place_id\":\""+place_id+"\",\n"
				+ "\"address\":\""+newAddr+"\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String get_response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1= ReusableMethods.rawToJson(get_response);
		String actual_Addr = js1.getString("address");
		
		//System.out.print(actual_Addr);
		Assert.assertEquals(actual_Addr, newAddr);
		
			
	}
}





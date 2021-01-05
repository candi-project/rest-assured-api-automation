import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void SumOfCourse() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		int courseAmount = js.getInt("courses.size()");
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		
		int sum =0;
		for(int i=0;i<courseAmount;i++) {
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = price*copies;
			System.out.println(amount);
			sum = sum+ amount;
		}
		System.out.println(sum);
		Assert.assertEquals(sum, purchaseAmount);
		
	}

}

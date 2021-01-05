import io.restassured.path.json.JsonPath;
import files.payload;

public class ComplexJsonParse {
	
	public static void main (String[] args) {
      
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		// Print No of courses returned by API
		int courseAmount = js.getInt("courses.size()");
		System.out.print(courseAmount);
		
		System.out.println();
		
		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.print(purchaseAmount);
		
		System.out.println();
		
		//Print Title of the first course
		String TitleOfFirstCourse = js.getString("courses[0].title");
		System.out.println(TitleOfFirstCourse);
		
		//Print All course titles and their respective Prices
		for(int i=0;i<courseAmount;i++) {
			String courseTitle = js.getString("courses["+i+"].title");
			System.out.println(courseTitle);
			System.out.println(js.get("courses["+i+"].price").toString());
			
		}
		
		//Print no of copies sold by RPA Course
		System.out.print("Print no of copies sold by RPA Course: ");
		for(int i=0;i<courseAmount;i++) {
			String courseTitle = js.getString("courses["+i+"].title");
			if (courseTitle.equalsIgnoreCase("RPA")){
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		    int sum =0;
		for(int i=0;i<courseAmount;i++) {
			
			int copies = js.getInt("courses["+i+"].copies");
			int prices = js.get("courses["+i+"].price");
			int amount = copies*prices;
			System.out.println(amount);
			sum= sum+amount;
			
			
		}
		System.out.println(sum);
	}

}

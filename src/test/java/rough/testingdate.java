package rough;

import java.util.Date;

public class testingdate {

	

	public static void main(String[] args)
	{	String ssName ;
		Date d = new Date();
		String s =d.toString().replace(" ", "_").replace(":","_");
		ssName = "error"+"_"+s+".jgp";
		System.out.println(ssName);
		
	}

}

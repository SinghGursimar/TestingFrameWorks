package rough;

import java.io.FileInputStream;

import java.util.Properties;

public class testProperties {

	public static void main(String[] args) throws Exception
	{
		System.out.println(System.getProperty("user.dir"));
		Properties OR = new Properties();
		Properties config = new Properties();
		//this is how we generilise a path 
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		
		 fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("bmlbtn"));
		//C:\Users\sps\Eclipse-2020-Workspace\ZFramework_DataDriven\src\test\resources\properties\OR.properties
		//C:\Users\sps\Eclipse-2020-Workspace\ZFramework_DataDriven\src\test\resources\properties\Config.properties
		
	}

}

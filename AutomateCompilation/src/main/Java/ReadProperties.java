package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	private static Properties properties = new Properties();

//	static String configFile = System.getenv("CONFIG_FILE");

/*	static {
		InputStream input;
		try {
//			input = new FileInputStream(configFile);
			input = new FileInputStream("FullPRJ Config.properties");
			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	public static void loadProperties(String configFile)
	{
		InputStream input = null;
		try 
		{
            input = new FileInputStream(configFile);
            properties.load(input);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
//	public static String getChromepath() {
//		return properties.getProperty("chromepath");
//	}

	public static String getUrl() {
		return properties.getProperty("url");
	}

	public static String getUsername() {
		return properties.getProperty("username");
	}

	public static String getPassword() {
		return properties.getProperty("password");
	}
}

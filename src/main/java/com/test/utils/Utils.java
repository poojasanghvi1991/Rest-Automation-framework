package com.test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.test.constants.SourcePath;

public class Utils {

	public static String getConfigProperty(String key) throws IOException {
		
		String prop_value="";	
		String filepath = SourcePath.CONFIG_PROPERTIES_PATH;
		FileInputStream fin = new FileInputStream(filepath); 
		Properties ob = new Properties();
		ob.load(fin);
		prop_value = ob.getProperty(key);
		fin.close();		
		return prop_value;		
	}	
}

//	public class ReadPropertiesFileTest {
//	   public static void main(String args[]) throws IOException {
//	      Properties prop = readPropertiesFile("credentials.properties");
//	      System.out.println("username: "+ prop.getProperty("username"));
//	      System.out.println("password: "+ prop.getProperty("password"));
//	   }
//	   public static Properties readPropertiesFile(String fileName) throws IOException {
//	      FileInputStream fis = null;
//	      Properties prop = null;
//	      try {
//	         fis = new FileInputStream(fileName);
//	         prop = new Properties();
//	         prop.load(fis);
//	      } catch(FileNotFoundException fnfe) {
//	         fnfe.printStackTrace();
//	      } catch(IOException ioe) {
//	         ioe.printStackTrace();
//	      } finally {
//	         fis.close();
//	      }
//	      return prop;
//	   }
//	}
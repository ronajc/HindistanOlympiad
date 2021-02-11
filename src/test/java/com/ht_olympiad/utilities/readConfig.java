package com.ht_olympiad.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class readConfig {
	Properties prop;

	public readConfig() {
		File sourceFile = new File("./configuration/UAT-config.properties");

		FileInputStream fisFileInputStream;
		try {
			fisFileInputStream = new FileInputStream(sourceFile);
			prop = new Properties();
			prop.load(fisFileInputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getURL()
	{
		String URL = prop.getProperty("baseURL");
		return URL;
	}
}

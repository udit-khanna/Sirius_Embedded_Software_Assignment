package com.conns.assignment.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used to Read the property from the config property
 * 
 * @author Udit Khanna
 */
public class PropertiesReader {
	private static final String CONFIG_FILE_PATH = "\\src\\main\\java\\com\\conns\\assignment\\config\\config.properties";
	private static final String CONFIG_PATH = System.getProperty("user.dir") + CONFIG_FILE_PATH;

	/**
	 * 
	 * @param propertyKey
	 *            it store the variable whose value needs to be fetch
	 * @return it return the value that is fetch from the key
	 */
	public static String readProperty(String propertyKey) {
		String value = null;
		Properties config = new Properties();
		try (FileInputStream inputFile = new FileInputStream(CONFIG_PATH);) {
			config.load(inputFile);
			value = config.getProperty(propertyKey);
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return value;

	}

}

package com.tnt.commonutilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

public class Config_Reader {
	public Properties properties;
	String config_file_path = System.getProperty("user.dir") + "\\Configuration\\configuration.properties";

	public Properties loadProperties() {
		File file = new File(FilenameUtils.getName(config_file_path));
		FileInputStream fileInputStream = null;
		properties = new Properties();
		try {
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();

		// load properties file
		try {
			prop.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

}

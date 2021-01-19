package com.longtop.Util.LoadMotor;

import java.io.IOException;
import java.util.Properties;

public class LoadDBSource {
    static Properties prop = new Properties();

    public static boolean loadFile(String fileName) {
        try {
            prop.load(LoadDBSource.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }
}

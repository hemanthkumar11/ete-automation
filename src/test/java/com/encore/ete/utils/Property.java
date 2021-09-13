package com.encore.ete.utils;


import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;

/**
 * utility class based on Apache Commons extension to java properties file.
 * additional capabilities provided by the Apache util include nested property files
 * and multi occurrences of keys (ie. array lists)
 */
public class Property {

    /**
     * gets properties file
     */
    public static PropertiesConfiguration getProperties(String propsPath) {
        PropertiesConfiguration props = new PropertiesConfiguration();
        try {
            Reader reader = new FileReader(propsPath);
            props.read(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * gets string data from any properties file on given path
     */
    public static String getProperty(String propsPath, String key) {
        return getProperties(propsPath).getString(key);
    }

    /**
     * gets string array data from any properties file on given path
     */
    public static String[] getPropertyArray(String propsPath, String key) {
        return getProperties(propsPath).getStringArray(key);
    }

    /**
     * gets value for variable based on preference of system property first then environment variable
     */
    public static String getVariable(String propname){
        String val = System.getProperty(propname, null);
        val = (val==null?System.getenv(propname):val);
        return val;
    }
}

package org.primefaces.diamond;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Property {

    static boolean first = true;
    static Properties p = new Properties();


    public static String getProperty(String property) {

        try {
            File f = new File("./application.properties");
            FileInputStream fis = new FileInputStream(f);
            p.load(fis);
            fis.close();
            String prop = p.getProperty(property);

            return prop;
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return "<NA>";
    }

    public Property() {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.primefaces.diamond.shared;



import org.primefaces.diamond.util.Utility;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class CostCalculator {
    public double estimate(double models, double attributes)
    {
        String props = Utility.getData("https://www.genpen.ai/globals.properties");;
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(props));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double coef = 0.45;
        try {
            coef = Double.parseDouble(properties.getProperty("costcalculator.coef"));
        } catch(Exception ex) {}
        double estimate = models * attributes * coef;
        System.out.println("Internal Ink Estimate:" + estimate);
        return estimate;
    }

    public static void main(String[] args)
    {
        CostCalculator costCalculator = new CostCalculator();
        double e = costCalculator.estimate(10,10);
        System.out.println((int)e);
    }
}

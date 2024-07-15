/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.primefaces.diamond.service;

import org.joda.time.DateTime;

/**
 *
 * @author Sean Beecrot <sean@genpen.ai> <seanbeecroft@gmail.com> 1-416-878-5282 
 */
public class TimeService {
    public String getFormattedTimeArray() {
        String arr = "";
        DateTime dt = new DateTime();  // current time
        //int month = dt.getMonth();     // gets the current month
        int hours = dt.getHourOfDay(); // gets hour of day
        int c = 0;
        for(int i = hours; c < 7; i--){
            c++;
            int t = i;
            if( i < 1 ) {
                t = 24 - Math.abs(i);
            }
            else {
                t = i;
            }
            String hourTime = "'" + t + ":00'";
            if( c == 1  ){
                arr = hourTime + " " + arr;
            } else {
                arr = hourTime + ", " + arr;
            }
        }
        return arr;
    }
        
        public static void main(String[] args)
        {
            
            TimeService ts = new TimeService();
            System.out.println(ts.getFormattedTimeArray());
        }
    }


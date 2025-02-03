package org.mulamu.Module4.web_log;
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        String file = "src/main/java/org/mulamu/Module4/web_log/weblog1_log";
        LogAnalyzer logAnalyzer = new LogAnalyzer();


        logAnalyzer.readFile(file);
//        logAnalyzer.printAll();
        System.out.println("There are a total of " + logAnalyzer.countUniqueIPs() + " unique IP addresses.");

        //Higher than this number
        int num = 400;
        logAnalyzer.printAllHigherThanNum(num);

        int low = 200;
        int high = 450;

        System.out.println("Number of unique IP address between "+ low+ " and " + high + " is " + logAnalyzer.countUniqueIPsInRange(low,high));

        String date = "Sep 14";
        System.out.println("Number of unique IP address visits on " + date + " is " + logAnalyzer.uniqueIPVisitsOnDay(date).size());

    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.testLogAnalyzer();
    }
}

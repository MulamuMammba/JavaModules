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

        int low = 300;
        int high = 399;

        System.out.println("Number of unique IP address between "+ low+ " and " + high + " is " + logAnalyzer.countUniqueIPsInRange(low,high));

        String date = "Mar 24";
        System.out.println("Number of unique IP address visits on " + date + " is " + logAnalyzer.uniqueIPVisitsOnDay(date).size());

        System.out.println("Number of visits per IP Address: - ");
        HashMap<String, Integer> map = logAnalyzer.countVisitsPerIP();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("Most number of visits : ");
        System.out.println(logAnalyzer.mostNumberVisitsByIP(map));

        System.out.println("IP Addresses with most visits - \n" + logAnalyzer.iPsMostVisits(map));

        HashMap<String, ArrayList<String>> hm = logAnalyzer.iPsForDays();
        System.out.println("Day with the most IP Visit: "+logAnalyzer.dayWithMostIPVisits(hm));

        String day = "Mar 17";
        System.out.println("IP with the most visits on " + day + " is "+ logAnalyzer.iPsWithMostVisitsOnDay(hm,day));

    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.testLogAnalyzer();
    }
}

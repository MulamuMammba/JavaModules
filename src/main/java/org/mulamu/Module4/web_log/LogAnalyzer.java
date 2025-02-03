package org.mulamu.Module4.web_log;
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         // complete constructor
         records = new ArrayList<LogEntry>(){};
     }
        
     public void readFile(String filename) {
         // complete method

             FileResource resource = new FileResource(filename);
             for(String line : resource.lines()){
                 records.add(WebLogParser.parseEntry(line));
             }

     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }

     public int countUniqueIPs(){
         ArrayList<String> uniqueIPs = new ArrayList<String>();

         for (LogEntry le: records){
             String ipAddr = le.getIpAddress();

             if (!uniqueIPs.contains(ipAddr)){
                 uniqueIPs.add(ipAddr);
             }
         }

         return uniqueIPs.size();
     }

     public void printAllHigherThanNum(int num){

         int count = 0;
         ArrayList<Integer> al = new ArrayList<>();
         System.out.println("\n\n" +
                 "All logs with code higher than " + num);
         for (LogEntry le: records){
             if (le.getStatusCode() > num){
                 System.out.println("- " + le);
                 count++;
                 al.add(le.getStatusCode());
             }
         }
         System.out.println("There are " + count + " of them. \n\n");
         System.out.println("List of the unique Codes.");
         for( int i :uniqueCodes(al)){
             System.out.println("- " + i);
         };
     }

    public ArrayList<String> uniqueIPVisitsOnDay(String someday){
        ArrayList<String> uniqueIPVisitsOnDay = new ArrayList<String>();
        for (LogEntry le : records){
            Date d = le.getAccessTime();
            String dateStr = d.toString();

            // Extracting the desired format "MMM DD" from the date string
            String formattedDate = dateStr.substring(4, 10);

            if (formattedDate.equals(someday)){
                if (!uniqueIPVisitsOnDay.contains(le.getIpAddress())){
                    uniqueIPVisitsOnDay.add(le.getIpAddress());
                }
            }
        }
        return uniqueIPVisitsOnDay;
    }



    public int countUniqueIPsInRange(int low, int high){
         ArrayList<LogEntry> withinRange = new ArrayList<LogEntry>();
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records){
            if (le.getStatusCode()>=low && le.getStatusCode()<=high){
                withinRange.add(le);
            }
         }
         for (LogEntry le :withinRange){

             for (LogEntry rec : records){
                 String ipAddr = rec.getIpAddress();

                 if (!uniqueIPs.contains(ipAddr)){
                     uniqueIPs.add(ipAddr);
                 }
             }
         }

         return uniqueIPs.size();

     }

     private List<Integer> uniqueCodes(ArrayList<Integer> list){
         ArrayList<Integer> uc = new ArrayList<>();

         for(int i: list){
             if (!uc.contains(i)){
                 uc.add(i);
             }
         }

         return uc;

     }

}

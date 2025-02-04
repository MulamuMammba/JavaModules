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

            String formattedDate = dateStr.substring(4, 10);

            if (formattedDate.equals(someday)){
                if (!uniqueIPVisitsOnDay.contains(le.getIpAddress())){
                    uniqueIPVisitsOnDay.add(le.getIpAddress());
                }
            }
        }
        return uniqueIPVisitsOnDay;
    }



    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<String> uniqueIPs = new ArrayList<String>();

        for (LogEntry le : records) {
            if (le.getStatusCode() >= low && le.getStatusCode() <= high) {
                String ipAddr = le.getIpAddress();
                if (!uniqueIPs.contains(ipAddr)) {
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

     public HashMap<String,Integer> countVisitsPerIP(){

         HashMap<String,Integer> counts = new HashMap<String,Integer>();
         for (LogEntry le: records){
             String ip = le.getIpAddress();

             if (!counts.containsKey(ip)){
                 counts.put(ip,1);
             }else {
                 counts.put(ip,counts.get(ip)+1);
             }
         }

         return counts;
     }

     public int mostNumberVisitsByIP(HashMap<String,Integer> map){

         int count = 0;
         String visiter = "";
         for (Map.Entry<String, Integer> entry : map.entrySet()) {
             if (entry.getValue()> count){
                 count = entry.getValue();
                 visiter = entry.getKey();
             }
         }
         return count;
     }

     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> map){
         int mostVisits = mostNumberVisitsByIP(map);
         ArrayList<String> iPsList = new ArrayList<>();
         for (Map.Entry<String, Integer> entry : map.entrySet()) {
             if (entry.getValue() == mostVisits){
                 iPsList.add(entry.getKey());
             }
         }

         return iPsList;
     }


        public HashMap<String, ArrayList<String>> iPsForDays() {
            HashMap<String, ArrayList<String>> iPsForDaysMap = new HashMap<>();

            for (LogEntry le : records) {
                Date d = le.getAccessTime();
                String dateStr = d.toString();
                String formattedDate = dateStr.substring(4, 10);

                if (!iPsForDaysMap.containsKey(formattedDate)) {
                    iPsForDaysMap.put(formattedDate, new ArrayList<>());
                }

                iPsForDaysMap.get(formattedDate).add(le.getIpAddress());
            }

            return iPsForDaysMap;
        }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        String dayWithMostVisits = "";
        int maxVisits = 0;

        for (String day : map.keySet()) {
            int numVisits = map.get(day).size();
            if (numVisits > maxVisits) {
                maxVisits = numVisits;
                dayWithMostVisits = day;
            }
        }

        return dayWithMostVisits;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day) {
        ArrayList<String> iPsOnDay = map.get(day);
        if (iPsOnDay == null) {
            return new ArrayList<>();
        }

        HashMap<String, Integer> ipCountMap = new HashMap<>();
        for (String ip : iPsOnDay) {
            ipCountMap.put(ip, ipCountMap.getOrDefault(ip, 0) + 1);
        }

        int maxCount = Collections.max(ipCountMap.values());
        ArrayList<String> iPsWithMostVisits = new ArrayList<>();
        for (String ip : ipCountMap.keySet()) {
            if (ipCountMap.get(ip) == maxCount) {
                iPsWithMostVisits.add(ip);
            }
        }

        return iPsWithMostVisits;
    }
}

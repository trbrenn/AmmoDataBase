/*
 * CalendarTest.java
 *
 * Created on July 1, 2007, 11:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClasses;

import java.util.Calendar;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

/**
 *
 * @author Todd Brenneman
 */
public class CalendarTest {
    
    private Calendar cal;

    public CalendarTest() {
    }

    public CalendarTest(Calendar d) {
        this.cal = d;
    }

    public CalendarTest(String d)    {
        this.cal = this.convertDate(d);
    }

    public Calendar getCurrentDate()    {
        cal = Calendar.getInstance();
        return cal;
    }

    public Calendar getCal() {
        return this.cal;
    }

    // Creates a calendar object out of a PROPERLY FORMATED string.
    public Calendar convertDate(String date)    {
        cal = Calendar.getInstance();
        if(isNumber(date.substring(0,1)))
        {
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            int mnth = 0;
            if(month.equals("01") || month.equals("1"))
                    mnth = Calendar.JANUARY;
            else if(month.equals("02") || month.equals("2"))
                    mnth = Calendar.FEBRUARY;
            else if(month.equals("03") || month.equals("3"))
                    mnth = Calendar.MARCH;
            else if(month.equals("04") || month.equals("4"))
                    mnth = Calendar.APRIL;
            else if(month.equals("05") || month.equals("5"))
                    mnth = Calendar.MAY;
            else if(month.equals("06") || month.equals("6"))
                    mnth = Calendar.JUNE;
            else if(month.equals("07") || month.equals("7"))
                    mnth = Calendar.JULY;
            else if(month.equals("08") || month.equals("8"))
                    mnth = Calendar.AUGUST;
            else if(month.equals("09") || month.equals("9"))
                    mnth = Calendar.SEPTEMBER;
            else if(month.equals("10") || month.equals("10"))
                    mnth = Calendar.OCTOBER;
            else if(month.equals("11") || month.equals("11"))
                    mnth = Calendar.NOVEMBER;
            else if(month.equals("12") || month.equals("12"))
                    mnth = Calendar.DECEMBER;
            String day = date.substring(8,10);

            cal.set(Integer.parseInt(year),mnth,Integer.parseInt(day));
        }
        else
        {
            int in = date.indexOf(" ");
            int coma = date.indexOf(",");
            int len = date.length();
            int mnth = 0;

            String month = date.substring(0,in);
            String day = date.substring(in, coma);
            String year = date.substring(coma+1,len);
            month = month.trim();
            day = day.trim();
            year = year.trim();

            if(month.equals("January"))
                mnth = Calendar.JANUARY;
            else if(month.equals("February"))
                mnth = Calendar.FEBRUARY;
            else if(month.equals("March"))
                mnth = Calendar.MARCH;
            else if(month.equals("April"))
                mnth = Calendar.APRIL;
            else if(month.equals("May"))
                mnth = Calendar.MAY;
            else if(month.equals("June"))
                mnth = Calendar.JUNE;
            else if(month.equals("July"))
                mnth = Calendar.JULY;
            else if(month.equals("August"))
                mnth = Calendar.AUGUST;
            else if(month.equals("September"))
                mnth = Calendar.SEPTEMBER;
            else if(month.equals("October"))
                mnth = Calendar.OCTOBER;
            else if(month.equals("November"))
                mnth = Calendar.NOVEMBER;
            else if(month.equals("December"))
                mnth = Calendar.DECEMBER;

            cal.set(Integer.parseInt(year),mnth,Integer.parseInt(day));                      
        }
        return cal;
    }

    // Converts a Calendar object into a string used in the query. The calendar object handles all data 		// checking.
    public String convertDate(Calendar date)    {
        String syear = new String();
        String smonth = new String();
        String sday = new String();

        if(date.isSet(Calendar.YEAR))
        {
            syear = ""+date.get(Calendar.YEAR);
        }
        if(date.isSet(Calendar.MONTH))
        {
            if(date.get(Calendar.MONTH)== Calendar.JANUARY)
                smonth = "01";
            else if(date.get(Calendar.MONTH) == Calendar.FEBRUARY)
                smonth = "02";
            else if(date.get(Calendar.MONTH) == Calendar.MARCH)
                smonth = "03";
            else if(date.get(Calendar.MONTH) == Calendar.APRIL)
                smonth = "04";
            else if(date.get(Calendar.MONTH) == Calendar.MAY)
                smonth = "05";
            else if(date.get(Calendar.MONTH) == Calendar.JUNE)
                smonth = "06";
            else if(date.get(Calendar.MONTH) == Calendar.JULY)
                smonth = "07";
            else if(date.get(Calendar.MONTH) == Calendar.AUGUST)
                smonth = "08";
            else if(date.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                smonth = "09";
            else if(date.get(Calendar.MONTH) == Calendar.OCTOBER)
                smonth = "10";
            else if(date.get(Calendar.MONTH) == Calendar.NOVEMBER)
                smonth = "11";
            else if(date.get(Calendar.MONTH) == Calendar.DECEMBER)
                smonth = "12";
        }
        if(date.isSet(Calendar.DAY_OF_MONTH))
        {
            int iday = date.get(Calendar.DAY_OF_MONTH);
            if(iday < 10)
                sday = "0" + iday;
            else
                sday = Integer.toString(iday);
        }
        return syear.toString()+"-"+smonth.toString()+"-"+sday.toString();
    }

    //Output date in standard date format "Month Date, Year"
    public String formatedString(Calendar date)    {
        int year = 0;
        String month = new String("");
        int day = 0;

        if(date.isSet(Calendar.YEAR))
        {
            year = date.get(Calendar.YEAR);
        }
        if(date.isSet(Calendar.MONTH))
        {
            if(date.get(Calendar.MONTH)== Calendar.JANUARY)
                month = "January";
            else if(date.get(Calendar.MONTH) == Calendar.FEBRUARY)
                month = "February";
            else if(date.get(Calendar.MONTH) == Calendar.MARCH)
                month = "March";
            else if(date.get(Calendar.MONTH) == Calendar.APRIL)
                month = "April";
            else if(date.get(Calendar.MONTH) == Calendar.MAY)
                month = "May";
            else if(date.get(Calendar.MONTH) == Calendar.JUNE)
                month = "June";
            else if(date.get(Calendar.MONTH) == Calendar.JULY)
                month = "July";
            else if(date.get(Calendar.MONTH) == Calendar.AUGUST)
                month = "August";
            else if(date.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                month = "September";
            else if(date.get(Calendar.MONTH) == Calendar.OCTOBER)
                month = "October";
            else if(date.get(Calendar.MONTH) == Calendar.NOVEMBER)
                month = "November";
            else if(date.get(Calendar.MONTH) == Calendar.DECEMBER)
                month = "December";
        }
        if(date.isSet(Calendar.DAY_OF_MONTH))
        {
            day = date.get(Calendar.DAY_OF_MONTH);
        }
        return month + " " + Integer.toString(day) + ", " + Integer.toString(year);
    }	

    //Output Date in Military standard "Date Month Year"
    public String milFormatedString(Calendar date)    {
        int year = 0;
        String month = new String("");
        int day = 0;

        if(date.isSet(Calendar.YEAR))
        {
            year = date.get(Calendar.YEAR);
        }
        if(date.isSet(Calendar.MONTH))
        {
            if(date.get(Calendar.MONTH)== Calendar.JANUARY)
                month = "January";
            else if(date.get(Calendar.MONTH) == Calendar.FEBRUARY)
                month = "February";
            else if(date.get(Calendar.MONTH) == Calendar.MARCH)
                month = "March";
            else if(date.get(Calendar.MONTH) == Calendar.APRIL)
                month = "April";
            else if(date.get(Calendar.MONTH) == Calendar.MAY)
                month = "May";
            else if(date.get(Calendar.MONTH) == Calendar.JUNE)
                month = "June";
            else if(date.get(Calendar.MONTH) == Calendar.JULY)
                month = "July";
            else if(date.get(Calendar.MONTH) == Calendar.AUGUST)
                month = "August";
            else if(date.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                month = "September";
            else if(date.get(Calendar.MONTH) == Calendar.OCTOBER)
                month = "October";
            else if(date.get(Calendar.MONTH) == Calendar.NOVEMBER)
                month = "November";
            else if(date.get(Calendar.MONTH) == Calendar.DECEMBER)
                month = "December";
        }
        if(date.isSet(Calendar.DAY_OF_MONTH))
        {
            day = date.get(Calendar.DAY_OF_MONTH);
        }
        return Integer.toString(day) + " " + month + " " + Integer.toString(year);
    }

    public String milFormatedString()    {
        Calendar date = cal;
        int year = 0;
        String month = new String("");
        int day = 0;

        if(date.isSet(Calendar.YEAR))
        {
            year = date.get(Calendar.YEAR);
        }
        if(date.isSet(Calendar.MONTH))
        {
            if(date.get(Calendar.MONTH)== Calendar.JANUARY)
                    month = "January";
            else if(date.get(Calendar.MONTH) == Calendar.FEBRUARY)
                    month = "February";
            else if(date.get(Calendar.MONTH) == Calendar.MARCH)
                    month = "March";
            else if(date.get(Calendar.MONTH) == Calendar.APRIL)
                    month = "April";
            else if(date.get(Calendar.MONTH) == Calendar.MAY)
                    month = "May";
            else if(date.get(Calendar.MONTH) == Calendar.JUNE)
                    month = "June";
            else if(date.get(Calendar.MONTH) == Calendar.JULY)
                    month = "July";
            else if(date.get(Calendar.MONTH) == Calendar.AUGUST)
                    month = "August";
            else if(date.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                    month = "September";
            else if(date.get(Calendar.MONTH) == Calendar.OCTOBER)
                    month = "October";
            else if(date.get(Calendar.MONTH) == Calendar.NOVEMBER)
                    month = "November";
            else if(date.get(Calendar.MONTH) == Calendar.DECEMBER)
                    month = "December";
        }
        if(date.isSet(Calendar.DAY_OF_MONTH))
        {
            day = date.get(Calendar.DAY_OF_MONTH);
        }
        return Integer.toString(day) + " " + month + " " + Integer.toString(year);
    }

    public boolean isNumber(String n)    {
        if(n.equalsIgnoreCase("0"))
            return true;
        else if(n.equalsIgnoreCase("1"))
            return true;
        else if(n.equalsIgnoreCase("2"))
            return true;
        else if(n.equalsIgnoreCase("3"))
            return true;
        else if(n.equalsIgnoreCase("4"))
            return true;
        else if(n.equalsIgnoreCase("5"))
            return true;
        else if(n.equalsIgnoreCase("6"))
            return true;
        else if(n.equalsIgnoreCase("7"))
            return true;
        else if(n.equalsIgnoreCase("8"))
            return true;
        else if(n.equalsIgnoreCase("9"))
            return true;   
        else 
            return false;
    }

    public String toString() {
        int year = 0;
        String month = "0";
        String day = "0";


        if(cal.isSet(Calendar.YEAR))
        {
            year = cal.get(Calendar.YEAR);
        }
        if(cal.isSet(Calendar.MONTH))
        {
            if(cal.get(Calendar.MONTH)== Calendar.JANUARY)
                    month = "01";
            else if(cal.get(Calendar.MONTH) == Calendar.FEBRUARY)
                    month = "02";
            else if(cal.get(Calendar.MONTH) == Calendar.MARCH)
                    month = "03";
            else if(cal.get(Calendar.MONTH) == Calendar.APRIL)
                    month = "04";
            else if(cal.get(Calendar.MONTH) == Calendar.MAY)
                    month = "05";
            else if(cal.get(Calendar.MONTH) == Calendar.JUNE)
                    month = "06";
            else if(cal.get(Calendar.MONTH) == Calendar.JULY)
                    month = "07";
            else if(cal.get(Calendar.MONTH) == Calendar.AUGUST)
                    month = "08";
            else if(cal.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                    month = "09";
            else if(cal.get(Calendar.MONTH) == Calendar.OCTOBER)
                    month = "10";
            else if(cal.get(Calendar.MONTH) == Calendar.NOVEMBER)
                    month = "11";
            else if(cal.get(Calendar.MONTH) == Calendar.DECEMBER)
                    month = "12";
        }
        if(cal.isSet(Calendar.DAY_OF_MONTH))
        {
            int d = cal.get(Calendar.DAY_OF_MONTH);
            if (d < 10)
                day = "0" + Integer.toString(d);
            else
                day = Integer.toString(d);
        }

        return Integer.toString(year) + "-" + month + "-" + day;
    }

    public String timeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMDDHHmmss");  
        LocalDateTime now = LocalDateTime.now();  
        String ts = new String(dtf.format(now));    
        return ts;
    }

    public String generateLotNumber(Calendar date)    {
        String syear = new String();
        String smonth = new String();
        String sday = new String();

        if(date.isSet(Calendar.YEAR))
        {
                syear = ""+date.get(Calendar.YEAR);
        }
        if(date.isSet(Calendar.MONTH))
        {
            if(date.get(Calendar.MONTH)== Calendar.JANUARY)
                    smonth = "01";
            else if(date.get(Calendar.MONTH) == Calendar.FEBRUARY)
                    smonth = "02";
            else if(date.get(Calendar.MONTH) == Calendar.MARCH)
                    smonth = "03";
            else if(date.get(Calendar.MONTH) == Calendar.APRIL)
                    smonth = "04";
            else if(date.get(Calendar.MONTH) == Calendar.MAY)
                    smonth = "05";
            else if(date.get(Calendar.MONTH) == Calendar.JUNE)
                    smonth = "06";
            else if(date.get(Calendar.MONTH) == Calendar.JULY)
                    smonth = "07";
            else if(date.get(Calendar.MONTH) == Calendar.AUGUST)
                    smonth = "08";
            else if(date.get(Calendar.MONTH) == Calendar.SEPTEMBER)
                    smonth = "09";
            else if(date.get(Calendar.MONTH) == Calendar.OCTOBER)
                    smonth = "10";
            else if(date.get(Calendar.MONTH) == Calendar.NOVEMBER)
                    smonth = "11";
            else if(date.get(Calendar.MONTH) == Calendar.DECEMBER)
                    smonth = "12";
        }
        if(date.isSet(Calendar.DAY_OF_MONTH))
        {
            int iday = date.get(Calendar.DAY_OF_MONTH);
            if(iday < 10)
                sday = "0" + iday;
            else
                sday = Integer.toString(iday);
        }
        return syear.toString()+smonth.toString()+sday.toString()+"001";
    }
}

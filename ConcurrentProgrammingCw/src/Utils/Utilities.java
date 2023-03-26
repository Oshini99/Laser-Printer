/**
 * File:      Utils.java  (Class)
 * Author:    I.A. Oshini Ilukkumbura (w1790248/2019757)
 * This class contains commonly using methods and variables.
 **/


package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    // class wise customized log messages
    public static final String PRINTER = "[" + generatedDateTime() + "] [PRINTER]: ";
    public static final String STUDENT = "[" + generatedDateTime() + "] [STUDENT]: ";
    public static final String PRINTING_SYSTEM = "[" + generatedDateTime() + "] [PRINTING SYSTEM]: ";
    public static final String PAPER_TECHNICIAN = "[" + generatedDateTime() + "] [PAPER TECHNICIAN]: ";
    public static final String TONER_TECHNICIAN = "[" + generatedDateTime() + "] [TONER TECHNICIAN]: ";


    // Generate current date time to prompt on terminal
    public static String generatedDateTime() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        String logTime = formatter.format(date);
        return logTime;
    }

    // random duration time for thread sleeping
    public static long generateDuration() {
        return (long) (Math.random() * 1000);
    }


    // log messages according to their status
    public static void logMessage(StatusEnum status, String message) {
        if (status == StatusEnum.SUCCESS) {
            System.out.println(ColoredTerminal.GREEN + message+ ColoredTerminal.RESET);
        } else if (status == StatusEnum.FAILED) {
            System.out.println(ColoredTerminal.RED + message+ ColoredTerminal.RESET);
        } else {
            System.out.println(message);
        }

    }
}

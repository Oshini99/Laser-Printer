/**
 * File:      WriteStatusFile.java  (Class)
 * Author:    I.A. Oshini Ilukkumbura (w1790248/2019757)
 * This class contains required methods and variables to print final status report.
 **/

package Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWriter {

    // Headers to print the report
    public static final String STUDENT_DETAILS = "STUDENTS\n" + "Student \t List of Printed Documents\n";
    public static final String INIT_TECHNICIAN_STATUS = "TECHNICIANS\nTechnician \t\t  Total Attempts \t Successful Attempts\n";
    public static final String INIT_PRINTER_STATUS = "Attribute \t Current Level \t Full Capacity\n";

    //Writes final status after printing in to a file with appended date and time to its name.
    public static void writeToFile(String status){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\src\\docs\\FinalStatusReport_"
                    + dtf.format(now)+".txt", "UTF-8");
            writer.println(status);
            writer.close();
            System.out.println(" Writing to file successfully !");
        } catch (IOException e) {
            System.out.println("File writing unsuccessful. Error Occurred!");
            e.printStackTrace();
        }
    }
}

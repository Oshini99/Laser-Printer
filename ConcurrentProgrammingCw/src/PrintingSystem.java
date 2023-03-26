/**
 * File : PrintingSystem.java (Class)
 * Author: I.A. Oshini Ilukkumbura (w1790248/2019757)
 * **/

import Utils.ColoredTerminal;
import Utils.StatusEnum;
import Utils.Utilities;
import Utils.FileWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PrintingSystem {

    public static void main(String[] args) {


        // Create the Thread Group for students
        ThreadGroup students = new ThreadGroup("Students");
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Created the ThreadGroup :"
                + students.getName());

        // Create the Thread Group for technicians
        ThreadGroup technicians = new ThreadGroup("Technicians");
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Created the ThreadGroup :"
                + technicians.getName());


        // Create LaserPrinter
        LaserPrinter printer = new LaserPrinter("Cannon LX-11", students);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Printer Initialised");


        // initialize the student threads
        Student student1 = new Student("Aunka", students, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Initialized Student : "
                + student1.getName());
        Student student2 = new Student("Mithila", students, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Initialized Student : "
                + student2.getName());
        Student student3 = new Student("Aanya", students, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Initialized Student : "
                + student3.getName());
        Student student4 = new Student("Dinuki", students, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Initialized Student : "
                + student4.getName());

        //*** required to print the final status report
        //List to store student objects.
        List<Student> listOfStudents = new ArrayList<Student>();
        listOfStudents.add(student1);
        listOfStudents.add(student2);
        listOfStudents.add(student3);
        listOfStudents.add(student4);


        // Create the technicians thread
        // Create the thread for Paper technician
        Technician paperTechnician = new PaperTechnician("Paper Technician", technicians, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Paper Technician Initialised");

        // Create the thread for Toner technician
        Technician tonerTechnician = new TonerTechnician("Toner Technician", technicians, printer);
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Toner Technician Initialised");

        // Start the thread in process
        // Start the student threads
        student1.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Started Student : "
                + student1.getName());

        student2.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Started Student : "
                + student2.getName());

        student3.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Started Student : "
                + student3.getName());

        student4.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Started Student : "
                + student4.getName());

        // Start the threads of technicians
        paperTechnician.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Paper technician Started..");

        tonerTechnician.start();
        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTING_SYSTEM + "Toner technician Started..");

        // wait for all the threads to complete
        try {
            student1.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM + student1.getName()
                    + " completed the process");

            student2.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM + student2.getName()
                    + " completed the process");

            student3.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM + student3.getName()
                    + " completed the process");

            student4.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM + student4.getName()
                    + " completed the process");

            paperTechnician.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM
                    + " Paper technician has completed the process");

            tonerTechnician.join();
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTING_SYSTEM
                    + "Toner technician has completed the process");

            Utilities.logMessage(StatusEnum.SUCCESS, Utilities.PRINTING_SYSTEM + "Task completed. "
                    + printer.toString());

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


        //***************************PRINT FINAL STATUS REPORT*********************************

        //Printing the final status of the system.
        Utilities.logMessage(StatusEnum.DEFAULT,ColoredTerminal.YELLOW +"\n\n--------------FINAL STATUS REPORT--------------\n"
                + ColoredTerminal.RESET);

        //Variables that are used to print student details.
        String studentDetails = FileWriter.STUDENT_DETAILS;
        for (int i = 0; i < listOfStudents.size(); i++) {
            studentDetails = (studentDetails.concat(listOfStudents.get(i).getName() + "\t\t" +
                    listOfStudents.get(i).getPrintedDocumentList() + "\n"));
        }

        //Variables that are used to print technician details.
        String initTechnicianStatus = FileWriter.INIT_TECHNICIAN_STATUS;
        String paperTechnicianDetails = (paperTechnician.getName() + "\t\t\t\t" +
                ((PaperTechnician) paperTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((PaperTechnician) paperTechnician).getSuccessfulRefills() + "\n");

        String tonerTechnicianDetails = (tonerTechnician.getName() + "\t\t\t\t" +
                ((TonerTechnician) tonerTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((TonerTechnician) tonerTechnician).getSuccessfulRefills() + "\n");

        //Variables that are used to print printer details.
        String initPrinterStatus = "PRINTER - " + printer.getPrinterId() + "\n" + FileWriter.INIT_PRINTER_STATUS;

        String printerDetails =
                "Paper Level \t  " + printer.getCurrentPaperLevel() + "\t\t\t  " + ServicePrinter.Full_Paper_Tray
                        + "\n" + "Toner Level \t  " + printer.getCurrentTonerLevel() + "\t\t\t  "
                        + ServicePrinter.Full_Toner_Level + "\n" +ColoredTerminal.PURPLE+ "A total of "
                        + printer.getPrintedDocumentAmount() + " documents were printed."+ColoredTerminal.RESET;

        String finalStatus = studentDetails + initTechnicianStatus + paperTechnicianDetails + tonerTechnicianDetails
                + initPrinterStatus + printerDetails;

        //Print details of Student.
        Utilities.logMessage(StatusEnum.DEFAULT,studentDetails);

        //Print details of Technicians.
        Utilities.logMessage(StatusEnum.DEFAULT, initTechnicianStatus + paperTechnicianDetails
                + tonerTechnicianDetails);

        //Print the details on laser printer
        Utilities.logMessage(StatusEnum.DEFAULT,initPrinterStatus +printerDetails );

        Scanner scanner = new Scanner(System.in);
        Utilities.logMessage(StatusEnum.DEFAULT,ColoredTerminal.BLUE
                +"\nDo you want to save the final status report to a file?(Please input a valid answer. )"
                +ColoredTerminal.RESET+ "\n1. Yes\n2. No");
        if (scanner.nextInt() == 1) {
            FileWriter.writeToFile(finalStatus);
        } else {
            Utilities.logMessage(StatusEnum.DEFAULT,"No logs saved!");
        }
        //****************************************************************
    }


}




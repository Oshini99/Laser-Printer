/**
 * File : Student.java (Class)
 * Author:    I.A. Oshini Ilukkumbura (w1790248/2019757)
 * **/

import Utils.StatusEnum;
import Utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends Thread {

    private Printer printer;
    private List<String> printedDocumentList =new ArrayList<String>();

    // A constructor to initialize student information including the student thread group
    public Student(String name,ThreadGroup threadGroup , Printer printer) {
        super(threadGroup, name);
        this.printer = printer;
    }


    @Override
    public void run(){
        int totalNumOfPages = 0 ;               // total number of pages that printed
        int allowedAmountOfDocsPerStudent = 5;  // allowed document amount to print per student

        // Creating 5 instances to represent the documents which have requested the printer to print them.
        for (int i = 0;i<allowedAmountOfDocsPerStudent;i++){
            String documentName = "Document_"+(i+1);
            int docPageCount = generateRandomPageCount();
            Document document = new Document(generateDocID(this.getName(),documentName),documentName,docPageCount);

            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.STUDENT + "["+this.getName()
                    +"] Requesting to Print : "+ document);
            printer.printDocument(document);
            totalNumOfPages += docPageCount;


            //Add document to student's printed document list.
            this.printedDocumentList.add(generateDocID(this.getName(),documentName));
           //------------

            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.STUDENT+"Printer Status. " + printer.toString());

            try {
                sleep(Utilities.generateDuration()); // used the random timer for sleep method

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            }
        }
        Utilities.logMessage(StatusEnum.SUCCESS,Utilities.STUDENT+this.getName()
                +" Finished Printing:5 documents "+totalNumOfPages+" pages");
    }


    // Generate document ID according to Student name and the document name
    private String generateDocID(String studentName, String documentName){
        return studentName + "-" + documentName;
    }

    // Generate random page count for the documents
    // Page count should be 1 to 20 ( inclusive 1 & 20)
    private int generateRandomPageCount(){
        int pageCount = new Random().nextInt(19)+1;
        return pageCount;
    }

    public List<String> getPrintedDocumentList(){
        return printedDocumentList;
    }


}

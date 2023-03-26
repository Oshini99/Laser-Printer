/**
 * File : LaserPrinter.java (Class)
 * Author:    Oshini Ilukkumbura (w1790248/2019757)
 * **/

import Utils.StatusEnum;
import Utils.Utilities;


public class LaserPrinter implements ServicePrinter {

    // Initialized the variables and threadGroup which holds the information related to the printer
    private String printerId;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int printedDocumentAmount;
    private ThreadGroup usersGroup;

    // A constructor that initializes the laser printer.
    public LaserPrinter(String printerId, ThreadGroup usersGroup) {
        this.printerId = printerId;
        this.currentPaperLevel = Full_Paper_Tray;
        this.currentTonerLevel = Full_Toner_Level;
        this.printedDocumentAmount = 0;             // assigned the zero for the initial no of documents printed
        this.usersGroup = usersGroup;
    }

    @Override
    public synchronized void printDocument(Document document) {

        Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: before printing - "+toString());

        // Check whether there's enough resources for both papers and toner
        // If either the paper or toner or both are not sufficient to proceed have to wait until there is enough of
        // both to print.
        while (currentPaperLevel < document.getNumberOfPages() || currentTonerLevel < document.getNumberOfPages()) {

            if(currentPaperLevel < document.getNumberOfPages()){
                Utilities.logMessage(StatusEnum.FAILED,Utilities.PRINTER+"Insufficient paper level. " +
                        "Waiting until refilled...");
            }
            else if(currentTonerLevel < document.getNumberOfPages()) {
                Utilities.logMessage(StatusEnum.FAILED,Utilities.PRINTER+"Insufficient toner level. " +
                        "Waiting until replaced the cartridge...");
            }
            else {
                Utilities.logMessage(StatusEnum.FAILED,Utilities.PRINTER+" Insufficient level of both Paper " +
                        "and Toner , Waiting until refilled both..");

            }

            try {

                Utilities.logMessage(StatusEnum.FAILED,Utilities.PRINTER+"Waiting to print"
                        + document.getDocumentName()+"student"+document.getUserID());
                wait();         // Insufficient amount of resources. Wait until it's refilled.

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            }
        }

        // Print the document if there are sufficient amount of resources to proceed.
        if (currentPaperLevel >= document.getNumberOfPages() && currentTonerLevel >= document.getNumberOfPages()) {
            currentPaperLevel -= document.getNumberOfPages();
            currentTonerLevel -= document.getNumberOfPages();
            printedDocumentAmount++;

            // Prompt successfully printed message
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTER+"Printed Document: "+document);
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: "+toString());
        }

        // notifying all the threads
        notifyAll();


    }


    @Override
    public synchronized void replaceTonerCartridge(TonerTechnician technician, int replaceAttempt) {

        // When the toner level is increased than the minimum toner level,it cannot be replaced.
        // Therefore, checking the need of replace toner in every 5 seconds of time.
        while (currentTonerLevel > Minimum_Toner_Level) {

            try {
                // Check whether the printer status to ensure that it has finished serving for all users.
                if(isPrinterUsageFinish()) {    // No student is waiting. Can stop concurrent loop
                    Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTER + "Usage of printer has finished." +
                            " No need to replace the toner cartridge.");
                    return;
                }else {
                    Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: "+toString());
                    Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Minimum toner level has not " +
                            "reached to refill yet. Please wait to check again.");
                    wait(5000);
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            }
        }

        //check and replacing the toner cartridge
        if (currentTonerLevel < Minimum_Toner_Level) {
            currentTonerLevel = Full_Toner_Level;
            // required details to print final status report
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            //-----------------------------------------------
            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTER+"Replaced the toner cartridge as attempt : "
                    +replaceAttempt);
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: "+toString());

        }
        //Notify all other threads that this thread has finished its execution.
        notifyAll();


    }

    @Override
    public synchronized void refillPaper(PaperTechnician technician, int refillAttempt) {

        //Checking the need of refilling papers in every 5 seconds of time
        while ((currentPaperLevel + SheetsPerPack) > Full_Paper_Tray) {

            try {
                if (isPrinterUsageFinish()) {// If there's no any active user to use the printer

                    Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTER+"Usage of printer has finished. " +
                            "No need to refill papers.");
                    return;
                } else {
                    Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: "+toString());
                    Utilities.logMessage(StatusEnum.FAILED,Utilities.PRINTER+"Refilling paper has overloaded. " +
                            "Waiting to check again.");

                    wait(5000);

                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // Refill papers according to the need
        if (currentPaperLevel + SheetsPerPack <= Full_Paper_Tray) {
            currentPaperLevel= currentPaperLevel + SheetsPerPack;

            // related to print final status report
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            //---------------------------------------------------------------

            Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PRINTER
                    +"Refilled the Papers successfully in attempt - "+refillAttempt);
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PRINTER+"Printer Status: "+toString());
        }
        //Notify all other threads that this thread has finished its execution.
        notifyAll();

    }

    // toString method to represent the current state of the printer
    @Override
    public String toString() {
        return "[" +
                "printerId : '" + printerId + '\'' +
                ", Paper Level : " + currentPaperLevel +
                ", Toner Level : " + currentTonerLevel +
                ", Documents Printed : " + printedDocumentAmount +
                ']';
    }


    // check whether that there's any active user to use the printer or not
    private boolean isPrinterUsageFinish() {
        return usersGroup.activeCount() == 0;
    }

    //-- getter and setter methods to use in printing the final status report
    public String getPrinterId() {
        return printerId;
    }

    public int getCurrentPaperLevel() {
        return currentPaperLevel;
    }

    public int getCurrentTonerLevel() {
        return currentTonerLevel;
    }

    public int getPrintedDocumentAmount() {
        return printedDocumentAmount;
    }
    //----------------------------------------------

}

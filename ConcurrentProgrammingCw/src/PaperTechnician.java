/**
 * File : PaperTechnician.java (Class)
 * Author:    Oshini Ilukkumbura (w1790248/2019757)
 * This class represents a technician that replaces the printer's toner cartridge.
 * **/

import Utils.StatusEnum;
import Utils.Utilities;

public class PaperTechnician extends Technician {

    // A constructor to hold information related to Paper Technician
    public PaperTechnician(String name, ThreadGroup group, LaserPrinter printer) {
        // calling the parent constructor
        super(name, group, printer);
    }

    public void setSuccessfulRefills(int successfulAttempt){
        this.successfulRefills = successfulAttempt;
    }

    public int getSuccessfulRefills(){
        return this.successfulRefills;
    }

    public int getTotalAttempts(){
        return this.totalAttempts;
    }


    // Attempt to refill the paper tray of printer three times
    @Override
    public void run(){

        int allowedNumOfRefills = 3;        // allowed number of refills
        int numOfPaperPacks = 0 ;           // number of paper packs currently refilled

        for(int i = 0; i < allowedNumOfRefills; i++){
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PAPER_TECHNICIAN + " Requesting to refill the papers");

            this.printer.refillPaper(this,i+1);

            numOfPaperPacks++;
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.PAPER_TECHNICIAN + "Printer Status: "
                    +printer.toString());
            try{
                // sleep for a random amount of time between each attempt to refill the paper.
                sleep(Utilities.generateDuration());

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        this.totalAttempts = numOfPaperPacks;

        Utilities.logMessage(StatusEnum.SUCCESS,Utilities.PAPER_TECHNICIAN +"Paper Technician Finished, " +
                "packs of paper used: "+this.getSuccessfulRefills());
    }







}

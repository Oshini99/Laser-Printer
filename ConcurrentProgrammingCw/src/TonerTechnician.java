/**
 * File : TonerTechnician.java (Class)
 * Author:    Oshini Ilukkumbura (w1790248/2019757)
 * This class represents a technician that replaces the printer's toner cartridge
 * **/
import Utils.StatusEnum;
import Utils.Utilities;

public class TonerTechnician extends Technician {

    public TonerTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer) {
        // calling the parent constructor
        super(name, threadGroup, printer);

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

    @Override
    public void run(){
        int numOfReplacedCartridges = 0;    // number of cartridges currently replaced
        int allowedNumOfRefills = 3;        // allowed number of refills

        for(int i = 0; i < allowedNumOfRefills ; i++){
            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.TONER_TECHNICIAN
                    + "Requesting to replace the toner cartridge.");

            this.printer.replaceTonerCartridge(this,i+1);
            numOfReplacedCartridges++;

            Utilities.logMessage(StatusEnum.DEFAULT,Utilities.TONER_TECHNICIAN + "Printer Status: "
                    + printer.toString() );
            try{
                sleep(Utilities.generateDuration());

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        this.totalAttempts = numOfReplacedCartridges;


        Utilities.logMessage(StatusEnum.SUCCESS,Utilities.TONER_TECHNICIAN +"Toner Technician Finished, " +
                "cartridges replaced : "+this.getSuccessfulRefills());
    }




}

/**
 * File : Technician.java (Class)
 * Author:    I.A.Oshini Ilukkumbura (w1790248/2019757)
 * A class to represent a technician that replaces the printer's toner cartridge
 * This class is a Thread.
 * **/

public class Technician extends Thread {

    protected LaserPrinter printer;
    protected int totalAttempts = 0;
    protected int successfulRefills = 0;

    public Technician(String name, ThreadGroup group, LaserPrinter printer) {
        super(group, name);
        this.printer = printer;

    }
}

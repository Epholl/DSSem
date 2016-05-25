package sk.epholl.dissim.sem3.util;

import OSPABA.Simulation;
import sk.epholl.dissim.sem3.simulation.MySimulation;

/**
 * Created by Tomáš on 19.05.2016.
 */
public class Log {

    public static final boolean LOG = true;

    public static Simulation SIM;

    public static void setSimulation(Simulation sim) {
        SIM = sim;
    }

    public static void println(String message) {
        Exception e = new Exception();
        double time = SIM.currentTime();
        String callerClass = e.getStackTrace()[1].getClassName();
        int lineNumber = e.getStackTrace()[1].getLineNumber();
        System.out.println(((MySimulation)SIM).getSimDateTime() + ", " + callerClass + ", " + lineNumber + ": " + message);
    }
}

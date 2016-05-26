package sk.epholl.dissim.sem3.util;

import OSPABA.Simulation;
import sk.epholl.dissim.sem3.simulation.MySimulation;

import java.time.LocalDateTime;

/**
 * Created by Tomáš on 19.05.2016.
 */
public class Log {

    public static final boolean LOG = false;
    public static final boolean USE_LINE_NUMBERS = false;

    public static Simulation SIM;

    public static void setSimulation(Simulation sim) {
        SIM = sim;
    }

    public static void println(String message) {
        if (LOG) {
            if (USE_LINE_NUMBERS) {
                Exception e = new Exception();
                String callerClass = e.getStackTrace()[1].getClassName();
                int lineNumber = e.getStackTrace()[1].getLineNumber();
                if (SIM != null) {
                    System.out.println(((MySimulation)SIM).getSimDateTime() + ", " + callerClass + ", " + lineNumber + ": " + message);
                } else {
                    System.out.println("No Sim." + LocalDateTime.now() + ", " + callerClass + ", " + lineNumber + ": " + message);
                }
            } else {
                if (SIM != null) {
                    System.out.println(((MySimulation)SIM).getSimDateTime() + ": " + message);
                } else {
                    System.out.println("No Sim." + LocalDateTime.now() + ": " + message);
                }
            }
        }
    }
}

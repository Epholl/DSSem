package sk.epholl.dissim.sem3.util;

import java.time.LocalTime;

/**
 * Created by Tomáš on 11.05.2016.
 */
public class ConsoleTests {

    public static void main(String[] args) {
        /*MySimulation sim = new MySimulation();
        sim.simulate(1, 3600L);
        System.out.println(sim.getSimTimeNiceFormat());*/

        LocalTime startT = LocalTime.of(7,0);
        LocalTime endT = LocalTime.of(6,0);

        double seconds = Utils.secondsUntilNextTime(startT, endT);

        System.out.println(seconds); // output: 345
        System.out.println(seconds/60); // output: 345
        System.out.println(seconds/(60*60)); // output: 345
    }
}

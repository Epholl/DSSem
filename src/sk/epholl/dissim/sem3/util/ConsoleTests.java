package sk.epholl.dissim.sem3.util;

import sk.epholl.dissim.sem3.simulation.MySimulation;

/**
 * Created by Tomáš on 11.05.2016.
 */
public class ConsoleTests {

    public static void main(String[] args) {
        MySimulation sim = new MySimulation();
        sim.simulate(1, 72000L);
        System.out.println(sim.getSimTimeNiceFormat());

        /*LocalTime startT = LocalTime.of(6, 0);
        LocalTime endT = LocalTime.of(7, 0);
        LocalTime cmp = LocalTime.of(7, 0);

        System.out.println(Utils.timeInInterval(startT, endT, cmp));*/
    }
}

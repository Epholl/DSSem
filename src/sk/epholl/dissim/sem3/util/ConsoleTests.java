package sk.epholl.dissim.sem3.util;

import sk.epholl.dissim.sem3.simulation.MySimulation;

/**
 * Created by Tomáš on 11.05.2016.
 */
public class ConsoleTests {

    public static void main(String[] args) {
        MySimulation sim = new MySimulation();
        sim.simulate(1);
        System.out.println(sim.getSimTimeNiceFormat());
    }
}

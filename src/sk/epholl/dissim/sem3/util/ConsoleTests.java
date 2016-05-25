package sk.epholl.dissim.sem3.util;

import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.simulation.SimulationParameters;

/**
 * Created by Tomáš on 11.05.2016.
 */
public class ConsoleTests {

    public static void main(String[] args) {
        SimulationParameters params = SimulationParameters.getDefaultParameters();
        MySimulation sim = new MySimulation(params);
        sim.simulate(1, 232000D);
        System.out.println("Sim ended: " + sim.getSimDateTime());

        /*LocalTime startT = LocalTime.of(6, 0);
        LocalTime endT = LocalTime.of(7, 0);
        LocalTime cmp = LocalTime.of(7, 0);

        System.out.println(Utils.isTimeInInterval(startT, endT, cmp));*/
    }
}

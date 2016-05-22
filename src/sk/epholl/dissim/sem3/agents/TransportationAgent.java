package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.entities.BumpyRoad;
import sk.epholl.dissim.sem3.entities.NarrowRoad;
import sk.epholl.dissim.sem3.managers.TransportationManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.simulation.SimulationParameters;

//meta! id="4"
public class TransportationAgent extends Agent {

    private NarrowRoad roadAb;
    private BumpyRoad roadBc;
    private NarrowRoad roadCa;

    public TransportationAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        SimulationParameters params = ((MySimulation)mySim()).getSimParams();
        roadAb = new NarrowRoad(mySim, params.roadAb.length, "AB");
        roadBc = new BumpyRoad(mySim, params.roadBc.length, "BC");
        roadCa = new NarrowRoad(mySim, params.roadCa.length, "CA");
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new TransportationManager(Id.transportationManager, mySim(), this);
		addOwnMessage(Mc.transferVehicle);
        addOwnMessage(Mc.vehicleTransferred);
	}
	//meta! tag="end"


    public NarrowRoad getRoadAb() {
        return roadAb;
    }

    public BumpyRoad getRoadBc() {
        return roadBc;
    }

    public NarrowRoad getRoadCa() {
        return roadCa;
    }
}
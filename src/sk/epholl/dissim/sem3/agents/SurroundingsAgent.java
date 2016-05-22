package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.continualAssistants.MaterialConsumedScheduler;
import sk.epholl.dissim.sem3.continualAssistants.SupplierScheduler;
import sk.epholl.dissim.sem3.managers.SurroundingsManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.simulation.SimulationParameters;

import java.util.List;

//meta! id="2"
public class SurroundingsAgent extends Agent {

    private boolean supplierAActive;

    public SurroundingsAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    public boolean isSupplierAActive() {
        return supplierAActive;
    }

    public void setSupplierAActive(boolean supplierAActive) {
        this.supplierAActive = supplierAActive;
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new SurroundingsManager(Id.surroundingsManager, mySim(), this);
		new MaterialConsumedScheduler(Id.materialConsumedScheduler, mySim(), this);

        SimulationParameters params = ((MySimulation)mySim()).getSimParams();
        List<SimulationParameters.Supplier> suppliers = params.suppliers;
        for (int i = 0; i < suppliers.size(); i++) {
            SimulationParameters.Supplier supplier = suppliers.get(i);
            new SupplierScheduler(Id.supplierAScheduler + i, mySim(), this, supplier.amountGenerator, supplier.timeBetweenGenerator, supplier.name);
        }
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.requestMaterialConsumption);
	}
	//meta! tag="end"
}
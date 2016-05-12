package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.continualAssistants.*;
import sk.epholl.dissim.sem3.managers.SurroundingsManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

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
		new SupplierAActiveProcess(Id.supplierAActiveProcess, mySim(), this);
		new MaterialConsumedScheduler(Id.materialConsumedScheduler, mySim(), this);
		new SupplierBScheduler(Id.supplierBScheduler, mySim(), this);
		new SupplierCScheduler(Id.supplierCScheduler, mySim(), this);
		new SupplierAScheduler(Id.supplierAScheduler, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.requestMaterialConsumption);
	}
	//meta! tag="end"
}
package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.continualAssistants.SupplierAScheduler;
import sk.epholl.dissim.sem3.continualAssistants.SupplierBScheduler;
import sk.epholl.dissim.sem3.continualAssistants.SupplierCScheduler;
import sk.epholl.dissim.sem3.managers.SurroundingsManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="2"
public class SurroundingsAgent extends Agent {
    public SurroundingsAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new SurroundingsManager(Id.surroundingsManager, mySim(), this);
        new SupplierBScheduler(Id.supplierBScheduler, mySim(), this);
        new SupplierCScheduler(Id.supplierCScheduler, mySim(), this);
        new SupplierAScheduler(Id.supplierAScheduler, mySim(), this);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.requestMaterialConsumption);
    }
    //meta! tag="end"
}
package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="2"
public class SurroundingsManager extends Manager {
    public SurroundingsManager(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="QuarryTransportationModelAgent", id="14", type="Notice"
    public void startSchedulingSupplies(MessageForm message) {
    }

    //meta! sender="QuarryTransportationModelAgent", id="12", type="Response"
    public void processRequestMaterialConsumption(MessageForm message) {
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="SupplierAScheduler", id="22", type="Finish"
    public void processFinishSupplierAScheduler(MessageForm message) {
    }

    //meta! sender="SupplierCScheduler", id="26", type="Finish"
    public void processFinishSupplierCScheduler(MessageForm message) {
    }

    //meta! sender="SupplierBScheduler", id="24", type="Finish"
    public void processFinishSupplierBScheduler(MessageForm message) {
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.init:
                startSchedulingSupplies(message);
                break;

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.supplierAScheduler:
                        processFinishSupplierAScheduler(message);
                        break;

                    case Id.supplierCScheduler:
                        processFinishSupplierCScheduler(message);
                        break;

                    case Id.supplierBScheduler:
                        processFinishSupplierBScheduler(message);
                        break;
                }
                break;

            case Mc.requestMaterialConsumption:
                processRequestMaterialConsumption(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public SurroundingsAgent myAgent() {
        return (SurroundingsAgent) super.myAgent();
    }

}
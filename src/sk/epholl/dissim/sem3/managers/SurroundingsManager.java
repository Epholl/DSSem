package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.MySimulation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    // TODO messageForm instance wasted here
	//meta! sender="QuarryTransportationModelAgent", id="14", type="Notice"
	public void processInit(MessageForm message) {
        startContinual(Id.supplierAScheduler, Mc.start);
        startContinual(Id.supplierBScheduler, Mc.start);
        startContinual(Id.supplierCScheduler, Mc.start);
        startContinual(Id.materialConsumedScheduler, Mc.start);
    }

    private void startContinual(int id, int code) {
        MyMessage message = new MyMessage(mySim());
        message.setCode(code);
        message.setAddressee(myAgent().findAssistant(id));
        startContinualAssistant(message);
    }

    private void endSupplier(MessageForm message, int monthsAfterSimStart) {
        LocalDateTime simStartTime = ((MySimulation) mySim()).getSimStartTime();
        LocalDateTime supplyEndTime = simStartTime.plusMonths(monthsAfterSimStart);
        long seconds = supplyEndTime.until(supplyEndTime, ChronoUnit.SECONDS);
        ((MyMessage) message).setAmount(seconds);
        message.setCode(Mc.supplierEnded);
        message.setAddressee(myAgent().findAssistant(Id.supplierAActiveProcess));
        startContinualAssistant(message);
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
		supplyDelivered((MyMessage) message);
    }

	//meta! sender="SupplierCScheduler", id="26", type="Finish"
	public void processFinishSupplierCScheduler(MessageForm message) {
		supplyDelivered((MyMessage) message);
    }

	//meta! sender="SupplierBScheduler", id="24", type="Finish"
	public void processFinishSupplierBScheduler(MessageForm message) {
		supplyDelivered((MyMessage) message);
    }

	private void supplyDelivered(MyMessage message) {
		message.setCode(Mc.materialDelivered);
		message.setAddressee(Id.quarryTransportationModelAgent);
		notice(message);
	}

	//meta! sender="MaterialConsumedScheduler", id="33", type="Finish"
	public void processFinishMaterialConsumedScheduler(MessageForm message) {
    }

    //meta! userInfo="Removed from model"
    public void processFinishSupplierAEndProcess(MessageForm message) {
    }

	//meta! sender="SupplierAActiveProcess", id="43", type="Finish"
	public void processFinishSupplierAActiveProcess(MessageForm message) {
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.init:
			processInit(message);
		break;

		case Mc.finish:
			switch (message.sender().id()) {
			case Id.supplierAScheduler:
				processFinishSupplierAScheduler(message);
			break;

			case Id.materialConsumedScheduler:
				processFinishMaterialConsumedScheduler(message);
			break;

			case Id.supplierCScheduler:
				processFinishSupplierCScheduler(message);
			break;

			case Id.supplierAActiveProcess:
				processFinishSupplierAActiveProcess(message);
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
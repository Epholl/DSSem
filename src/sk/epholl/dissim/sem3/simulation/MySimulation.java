package sk.epholl.dissim.sem3.simulation;

import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.*;
import sk.epholl.dissim.sem3.util.Log;
import sk.epholl.dissim.sem3.util.Utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class MySimulation extends Simulation {

    private LocalDateTime startDateTime;

	private SimulationParameters params;

    public MySimulation(SimulationParameters params) {
        this.params = params;
		init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis

		startDateTime = params.startDateTime;
		Log.println("Preparing simulation " + currentReplication() + " of " + replicationCount());
	}

	public void simulateAutoFigureEndDate(int replicationCount) {
		super.simulate(replicationCount, params.getEndingSimulationTime());
	}

	@Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
    }

    @Override
    public void replicationFinished() {
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
    }

    @Override
    public void simulationFinished() {
        // Dysplay SIM results
        super.simulationFinished();
    }

	public SimulationParameters getSimParams() {
		return params;
	}

    public LocalDateTime getSimStartTime() {
        return startDateTime;
    }

    public LocalDateTime getSimDateTime() {
        return startDateTime.plusSeconds((long) currentTime());
    }

	public double durationTillTime(LocalTime localTime) {
		LocalTime simTime = getSimDateTime().toLocalTime();
		return Utils.secondsUntilNextTime(simTime, localTime);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		setQuarryTransportationModelAgent(new QuarryTransportationModelAgent(Id.quarryTransportationModelAgent, this, null));
		setTransportationAgent(new TransportationAgent(Id.transportationAgent, this, quarryTransportationModelAgent()));
		setLoaderAgent(new LoaderAgent(Id.loaderAgent, this, quarryTransportationModelAgent()));
		setUnloaderAgent(new UnloaderAgent(Id.unloaderAgent, this, quarryTransportationModelAgent(), 1));
		setSurroundingsAgent(new SurroundingsAgent(Id.surroundingsAgent, this, quarryTransportationModelAgent()));
	}

	private QuarryTransportationModelAgent _quarryTransportationModelAgent;

public QuarryTransportationModelAgent quarryTransportationModelAgent()
	{ return _quarryTransportationModelAgent; }

	public void setQuarryTransportationModelAgent(QuarryTransportationModelAgent quarryTransportationModelAgent)
	{_quarryTransportationModelAgent = quarryTransportationModelAgent; }

	private TransportationAgent _transportationAgent;

public TransportationAgent transportationAgent()
	{ return _transportationAgent; }

	public void setTransportationAgent(TransportationAgent transportationAgent)
	{_transportationAgent = transportationAgent; }

	private LoaderAgent _loaderAgent;

public LoaderAgent loaderAgent()
	{ return _loaderAgent; }

	public void setLoaderAgent(LoaderAgent loaderAgent)
	{_loaderAgent = loaderAgent; }

	private UnloaderAgent _unloaderAgent;

public UnloaderAgent unloaderAgent()
	{ return _unloaderAgent; }

	public void setUnloaderAgent(UnloaderAgent unloaderAgent)
	{_unloaderAgent = unloaderAgent; }

	private SurroundingsAgent _surroundingsAgent;

public SurroundingsAgent surroundingsAgent()
	{ return _surroundingsAgent; }

	public void setSurroundingsAgent(SurroundingsAgent surroundingsAgent)
	{_surroundingsAgent = surroundingsAgent; }
	//meta! tag="end"
}
package sk.epholl.dissim.sem3.simulation;

import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.*;

import java.time.LocalDateTime;

public class MySimulation extends Simulation {

    private LocalDateTime startDateTime;

    public MySimulation() {
        init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis

        // needs to be initialized from UI
        startDateTime = LocalDateTime.of(2016, 5, 1, 7, 30);
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
        // Dysplay simulation results
        super.simulationFinished();
    }

    public LocalDateTime getSimStartTime() {
        return startDateTime;
    }

    public LocalDateTime getSimTimeNiceFormat() {
        return startDateTime.plusSeconds((long) currentTime());
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		setQuarryTransportationModelAgent(new QuarryTransportationModelAgent(Id.quarryTransportationModelAgent, this, null));
		setTransportationAgent(new TransportationAgent(Id.transportationAgent, this, quarryTransportationModelAgent()));
		setLoaderAgent(new LoaderAgent(Id.loaderAgent, this, quarryTransportationModelAgent()));
		setUnloaderAgent(new UnloaderAgent(Id.unloaderAgent, this, quarryTransportationModelAgent()));
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
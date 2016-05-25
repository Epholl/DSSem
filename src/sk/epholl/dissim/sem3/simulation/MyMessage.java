package sk.epholl.dissim.sem3.simulation;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.entities.Unloader;
import sk.epholl.dissim.sem3.entities.Vehicle;

public class MyMessage extends MessageForm {

    private double amount;
    private Vehicle vehicle;
    private String from;
    private String target;
    private Loader loader;
    private Unloader unloader;

    private boolean loadersOpen;
    private boolean unloadersOpen;

    private double travelArriveTime;

    public MyMessage(Simulation sim) {
        super(sim);
    }

    public MyMessage(MyMessage original) {
        super(original);
        // copy() is called in superclass
    }

    @Override
    public MessageForm createCopy() {
        return new MyMessage(this);
    }

    @Override
    protected void copy(MessageForm message) {
        super.copy(message);
        MyMessage original = (MyMessage) message;
        vehicle = original.vehicle;
        amount = original.amount;
        target = original.target;
        loader = original.loader;
        unloader = original.unloader;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Unloader getUnloader() {
        return unloader;
    }

    public void setUnloader(Unloader unloader) {
        this.unloader = unloader;
    }

    public boolean areLoadersOpen() {
        return loadersOpen;
    }

    public void setLoadersOpen(boolean loadersOpen) {
        this.loadersOpen = loadersOpen;
    }

    public boolean areUnloadersOpen() {
        return unloadersOpen;
    }

    public void setUnloadersOpen(boolean unloadersOpen) {
        this.unloadersOpen = unloadersOpen;
    }

    public double getTravelArriveTime() {
        return travelArriveTime;
    }

    public void setTravelArriveTime(double travelArriveTime) {
        this.travelArriveTime = travelArriveTime;
    }
}
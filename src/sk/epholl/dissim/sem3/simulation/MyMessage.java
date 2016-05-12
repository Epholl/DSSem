package sk.epholl.dissim.sem3.simulation;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.entities.Vehicle;

public class MyMessage extends MessageForm {

    private double amount;
    private Vehicle vehicle;

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
}
package sk.epholl.dissim.sem3.simulation;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.entities.Vehicle;

import java.util.List;

public class MyMessage extends MessageForm {

    private double amount;
    private Vehicle vehicle;
    private String target;
    private List<Vehicle> allVehicles;

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
        allVehicles = original.allVehicles;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public void setAllVehicles(List<Vehicle> allVehicles) {
        this.allVehicles = allVehicles;
    }
}
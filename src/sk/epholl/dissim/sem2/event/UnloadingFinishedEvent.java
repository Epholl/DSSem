package sk.epholl.dissim.sem2.event;

import sk.epholl.dissim.sem2.core.Event;
import sk.epholl.dissim.sem2.entity.Unloader;
import sk.epholl.dissim.sem2.entity.Vehicle;

/**
 * Created by Tomáš on 13.04.2016.
 */
public class UnloadingFinishedEvent extends Event {

    private Vehicle vehicle;
    private Unloader unloader;

    public UnloadingFinishedEvent(double occurTime, Vehicle vehicle, Unloader unloader) {
        super(occurTime);
        this.vehicle = vehicle;
        this.unloader = unloader;
    }

    @Override
    public void onOccur() {
        double cargo = vehicle.unload();
        unloader.acceptCargo(cargo);
        unloader.finished(vehicle);
    }
}

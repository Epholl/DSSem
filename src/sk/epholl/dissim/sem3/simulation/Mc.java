package sk.epholl.dissim.sem3.simulation;

import OSPABA.IdList;

public class Mc extends IdList {

    public static final int supplierEnded = 1;
	public static final int vehicleLoaded = 2;
	public static final int loaderOpen = 3;
	public static final int loaderClose = 4;
	public static final int vehicleTransferred = 5;
	public static final int vehicleUnloaded = 6;
	public static final int unloaderClose = 7;
	public static final int unloaderOpen = 8;
	public static final int loadersStateChanged = 9;
	public static final int unloadersStateChanged = 10;
	public static final int morningWakeup = 11;

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public static final int unloadVehicle = 1010;
	public static final int materialDelivered = 1001;
	public static final int requestMaterialConsumption = 1002;
	public static final int init = 1004;
	public static final int transferVehicle = 1006;
	public static final int loadVehicle = 1007;
	//meta! tag="end"

    // 1..1000 range reserved for user
}
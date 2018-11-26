package com.oocl.cultivation;

import java.util.ArrayList;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ArrayList<ParkingLot> parkingLots){
        super(parkingLots);
    }

    @Override
    protected ParkingLot pickParkingLot() {
        ParkingLot parkingLotWithMoreSpace = null;
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getCurrentParkingSpace() > 0) {
                parkingLotWithMoreSpace = compareParkingLotSpace(parkingLotWithMoreSpace, parkingLot);
            }
        }
        if (parkingLotWithMoreSpace == null) {
            return null;
        } else {
            return parkingLotWithMoreSpace;
        }
    }

    private ParkingLot compareParkingLotSpace(ParkingLot parkingLotWithMoreSpace, ParkingLot currentParkingLot){
        if (parkingLotWithMoreSpace == null) {
            return currentParkingLot;
        } else {
            if (currentParkingLot.getCurrentParkingSpace() > parkingLotWithMoreSpace.getCurrentParkingSpace()) {
                return currentParkingLot;
            }else{
                return parkingLotWithMoreSpace;
            }
        }
    }
}

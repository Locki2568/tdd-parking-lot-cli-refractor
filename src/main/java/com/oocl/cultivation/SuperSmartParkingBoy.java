package com.oocl.cultivation;

import java.util.ArrayList;

public class SuperSmartParkingBoy extends ParkingBoy{
    public SuperSmartParkingBoy(ArrayList<ParkingLot> parkingLots){
        super(parkingLots);
    }

    @Override
    public ParkingLot pickParkingLot() {
        ParkingLot parkingLotWithMoreSpaceRate = null;
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getCurrentParkingSpaceRate() > 0) {
                parkingLotWithMoreSpaceRate = compareParkingLotSpaceRate(parkingLotWithMoreSpaceRate, parkingLot);
            }
        }
        if (parkingLotWithMoreSpaceRate == null) {
            return null;
        } else {
            return parkingLotWithMoreSpaceRate;
        }
    }

    private ParkingLot compareParkingLotSpaceRate(ParkingLot parkingLotWithMoreSpace, ParkingLot currentParkingLot){
        if (parkingLotWithMoreSpace == null) {
            return currentParkingLot;
        } else {
            if (currentParkingLot.getCurrentParkingSpaceRate() > parkingLotWithMoreSpace.getCurrentParkingSpaceRate()) {
                return currentParkingLot;
            }else{
                return parkingLotWithMoreSpace;
            }
        }
    }
}

package com.oocl.cultivation;

import java.util.ArrayList;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ArrayList<ParkingLot> parkingLots){
        super(parkingLots);
    }

    @Override
    public ParkingLot pickParkingLot(){
        if (parkingLots.size() == 0){return null;}
        if(parkingLots.size() == 1){
            if (parkingLots.get(0).getCurrentParkingSpace() > 0){
                return parkingLots.get(0);
            }else{
                return null;
            }
        }else{
            ParkingLot parkingLotWithMoreSpace = null;
            for(ParkingLot parkingLot : parkingLots){
                if (parkingLot.getCurrentParkingSpace() > 0){
                    if (parkingLotWithMoreSpace == null){
                        parkingLotWithMoreSpace = parkingLot;
                    }else{
                        if (parkingLot.getCurrentParkingSpace() > parkingLotWithMoreSpace.getCurrentParkingSpace()){
                            parkingLotWithMoreSpace = parkingLot;
                        }
                    }
                }
            }
            if (parkingLotWithMoreSpace == null) {
                return null;
            }else{
                return parkingLotWithMoreSpace;
            }
        }
    }
}

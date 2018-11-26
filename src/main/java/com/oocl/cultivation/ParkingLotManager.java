package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingLotManager extends ParkingBoy{
    ArrayList<ParkingBoy> parkingBoys;

    public ParkingLotManager(ArrayList<ParkingLot> parkingLots, ArrayList<ParkingBoy> parkingBoys){
        super(parkingLots);
        this.parkingBoys = parkingBoys;
    }

    public ParkingTicket toldParkingBoyToPark(ParkingBoy parkingBoy, Car car){
        if(checkIfParkingBoyUnderCommand(parkingBoy)) {
            return parkingBoy.park(car);
        }else{
            return null;
        }
    }

    public Car toldParkingBoyToFetchCar(ParkingBoy parkingBoy, ParkingTicket parkingTicket){
        return parkingBoy.fetch(parkingTicket);
    }

    private boolean checkIfParkingBoyUnderCommand(ParkingBoy parkingBoy){
        return this.parkingBoys.contains(parkingBoy);
    }
}

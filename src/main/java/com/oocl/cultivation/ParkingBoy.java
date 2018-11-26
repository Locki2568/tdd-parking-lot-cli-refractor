package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingBoy {

    protected final ArrayList<ParkingLot> parkingLots;
    protected String lastErrorMessage;

    public ParkingBoy(ArrayList<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    private ParkingLot pickParkingLot(){
        for(ParkingLot parkingLot : parkingLots){
            if (parkingLot.getCurrentParkingSpace() > 0){
                return parkingLot;
            }
        }
        return null;
    }

    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        ParkingLot availableParkingLot = pickParkingLot();
        if (availableParkingLot != null) {
            lastErrorMessage = null;
            return availableParkingLot.storeCar(car);
        }else{
            lastErrorMessage = "The parking lot is full.";
            return null;
        }
    }

    public Car fetch(ParkingTicket ticket) {
        // TODO: Please implement the method
        if(ticket != null) {
            if (ticket.getParkingLot() != null && ticket.getParkingLot().isParkingLotContainsCar(ticket)) {
                ParkingLot parkingLot = ticket.getParkingLot();
                lastErrorMessage = null;
                return parkingLot.pickCar(ticket);
            } else {
                lastErrorMessage = "Unrecognized parking ticket.";
                return null;
            }
        }else{
            lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}

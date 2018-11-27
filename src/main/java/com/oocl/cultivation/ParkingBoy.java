package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingBoy {

    protected final ArrayList<ParkingLot> parkingLots;
    protected String lastErrorMessage;

    public ParkingBoy(ArrayList<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    protected ParkingLot pickParkingLot(){
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
        if(!checkTicketIsNull(ticket)) {
            if (ifTicketIsValid(ticket)) {
                ParkingLot parkingLot = ticket.getParkingLot();
                lastErrorMessage = null;
                return parkingLot.pickCar(ticket);
            } else {
                lastErrorMessage = "Unrecognized parking ticket.";
                return null;
            }
        }else{
            return null;
        }
    }

    private boolean checkTicketIsNull(ParkingTicket ticket){
        if(ticket != null) {
            return false;
        }else{
            lastErrorMessage = "Please provide your parking ticket.";
            return true;
        }
    }

    private boolean ifTicketIsValid(ParkingTicket ticket){
        if (ticket.getParkingLot() == null){
            return false;
        }else{
            return ticket.getParkingLot().isParkingLotContainsCar(ticket);
        }
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}

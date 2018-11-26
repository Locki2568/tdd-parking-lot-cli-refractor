package com.oocl.cultivation;

import java.util.concurrent.atomic.AtomicLong;

public class ParkingTicket {

    private String ticketID;
    private ParkingLot parkingLot;

    public ParkingTicket(){}

    public ParkingTicket(String ticketID, ParkingLot parkingLot){
        this.ticketID = ticketID;
        this.parkingLot = parkingLot;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}

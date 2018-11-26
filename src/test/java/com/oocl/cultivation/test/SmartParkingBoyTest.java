package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SmartParkingBoyTest {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();
        //When
        ParkingTicket ticket = smartParkingBoy.park(car);
        Car fetched = smartParkingBoy.fetch(ticket);
        //Then
        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car firstCar = new Car();
        Car secondCar = new Car();
        //When
        ParkingTicket firstTicket = smartParkingBoy.park(firstCar);
        ParkingTicket secondTicket = smartParkingBoy.park(secondCar);

        Car fetchedByFirstTicket = smartParkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = smartParkingBoy.fetch(secondTicket);
        //Then
        assertSame(firstCar, fetchedByFirstTicket);
        assertSame(secondCar, fetchedBySecondTicket);
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        ParkingTicket ticket = smartParkingBoy.park(car);
        //Then
        assertNull(smartParkingBoy.fetch(wrongTicket));
        assertSame(car, smartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        smartParkingBoy.fetch(wrongTicket);
        String message = smartParkingBoy.getLastErrorMessage();
        //Then
        assertEquals("Unrecognized parking ticket.", message);
    }

    //Given
    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket();

        smartParkingBoy.fetch(wrongTicket);
        assertNotNull(smartParkingBoy.getLastErrorMessage());

        ParkingTicket ticket = smartParkingBoy.park(new Car());
        assertNotNull(ticket);
        assertNull(smartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);

        assertNull(smartParkingBoy.fetch(null));
        assertSame(car, smartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

        smartParkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                smartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(ticket);

        assertNull(smartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(ticket);
        smartParkingBoy.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                smartParkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

        smartParkingBoy.park(new Car());

        assertNull(smartParkingBoy.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

        smartParkingBoy.park(new Car());
        smartParkingBoy.park(new Car());

        assertEquals("The parking lot is full.", smartParkingBoy.getLastErrorMessage());
    }
    //Story 4
    //Given: smart parking boy, 2 parking lot with different amount of empty positions
    //When: smart parking boy park car, he should park the car to the parking lot with more space
    //Then: the car fetched should be fetched from parking lot with more space.
    @Test
    void should_smart_parking_boy_always_park_car_to_parking_slot_with_more_space(){
        final int capacity = 5;
        ParkingLot parkingLotA = new ParkingLot(capacity);
        ParkingLot parkingLotB = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        parkingLots.add(parkingLotB);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

        Car expectedCar = new Car();
        ParkingTicket parkingTicket = smartParkingBoy.park(expectedCar);
        Car atualCar = smartParkingBoy.fetch(parkingTicket);

        assertSame(expectedCar, atualCar);
        assertSame(parkingLotB, parkingTicket.getParkingLot());
    }
}

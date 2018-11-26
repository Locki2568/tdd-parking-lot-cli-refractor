package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SuperSmartParkingBoyTest {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car car = new Car();
        //When
        ParkingTicket ticket = superSmartParkingBoy.park(car);
        Car fetched = superSmartParkingBoy.fetch(ticket);
        //Then
        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car firstCar = new Car();
        Car secondCar = new Car();
        //When
        ParkingTicket firstTicket = superSmartParkingBoy.park(firstCar);
        ParkingTicket secondTicket = superSmartParkingBoy.park(secondCar);

        Car fetchedByFirstTicket = superSmartParkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = superSmartParkingBoy.fetch(secondTicket);
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
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        ParkingTicket ticket = superSmartParkingBoy.park(car);
        //Then
        assertNull(superSmartParkingBoy.fetch(wrongTicket));
        assertSame(car, superSmartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        superSmartParkingBoy.fetch(wrongTicket);
        String message = superSmartParkingBoy.getLastErrorMessage();
        //Then
        assertEquals("Unrecognized parking ticket.", message);
    }

    //Given
    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket();

        superSmartParkingBoy.fetch(wrongTicket);
        assertNotNull(superSmartParkingBoy.getLastErrorMessage());

        ParkingTicket ticket = superSmartParkingBoy.park(new Car());
        assertNotNull(ticket);
        assertNull(superSmartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);

        assertNull(superSmartParkingBoy.fetch(null));
        assertSame(car, superSmartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);

        superSmartParkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                superSmartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(ticket);

        assertNull(superSmartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(ticket);
        superSmartParkingBoy.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                superSmartParkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);

        superSmartParkingBoy.park(new Car());

        assertNull(superSmartParkingBoy.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);

        superSmartParkingBoy.park(new Car());
        superSmartParkingBoy.park(new Car());

        assertEquals("The parking lot is full.", superSmartParkingBoy.getLastErrorMessage());
    }
    //Story 5
    //Given: Super smart parking boy, 2 parking lot with different amount of empty positions rate
    //When: Super smart parking boy park car, he should park the car to the parking lot with more space rate
    //Then: the car fetched should be fetched from parking lot with more space rate.
    @Test
    void should_super_smart_parking_boy_always_park_car_to_parking_slot_with_more_space_rate(){
        final int capacity = 5;
        ParkingLot parkingLotA = new ParkingLot();
        ParkingLot parkingLotB = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        parkingLots.add(parkingLotB);
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);

        Car randomCar = new Car();
        // What if two car part have the same space rate?
        // Current logic should be the first one.
        superSmartParkingBoy.park(randomCar);
        Car expectedCar = new Car();
        ParkingTicket parkingTicket = superSmartParkingBoy.park(expectedCar);
        Car actualCar = superSmartParkingBoy.fetch(parkingTicket);

        assertSame(expectedCar, actualCar);
        assertSame(parkingLotB, parkingTicket.getParkingLot());
    }
}

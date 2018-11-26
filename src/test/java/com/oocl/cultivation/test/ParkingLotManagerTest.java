package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotManagerTest {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car car = new Car();
        //When
        ParkingTicket ticket = parkingLotManager.park(car);
        Car fetched = parkingLotManager.fetch(ticket);
        //Then
        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car firstCar = new Car();
        Car secondCar = new Car();
        //When
        ParkingTicket firstTicket = parkingLotManager.park(firstCar);
        ParkingTicket secondTicket = parkingLotManager.park(secondCar);

        Car fetchedByFirstTicket = parkingLotManager.fetch(firstTicket);
        Car fetchedBySecondTicket = parkingLotManager.fetch(secondTicket);
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
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        ParkingTicket ticket = parkingLotManager.park(car);
        //Then
        assertNull(parkingLotManager.fetch(wrongTicket));
        assertSame(car, parkingLotManager.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        ParkingTicket wrongTicket = new ParkingTicket();
        //When
        parkingLotManager.fetch(wrongTicket);
        String message = parkingLotManager.getLastErrorMessage();
        //Then
        assertEquals("Unrecognized parking ticket.", message);
    }

    //Given
    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        ParkingTicket wrongTicket = new ParkingTicket();

        parkingLotManager.fetch(wrongTicket);
        assertNotNull(parkingLotManager.getLastErrorMessage());

        ParkingTicket ticket = parkingLotManager.park(new Car());
        assertNotNull(ticket);
        assertNull(parkingLotManager.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car car = new Car();

        ParkingTicket ticket = parkingLotManager.park(car);

        assertNull(parkingLotManager.fetch(null));
        assertSame(car, parkingLotManager.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        parkingLotManager.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                parkingLotManager.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car car = new Car();

        ParkingTicket ticket = parkingLotManager.park(car);
        parkingLotManager.fetch(ticket);

        assertNull(parkingLotManager.fetch(ticket));
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);
        Car car = new Car();

        ParkingTicket ticket = parkingLotManager.park(car);
        parkingLotManager.fetch(ticket);
        parkingLotManager.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                parkingLotManager.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        parkingLotManager.park(new Car());

        assertNull(parkingLotManager.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        parkingBoys.add(parkingBoy);
        ParkingBoy parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        parkingLotManager.park(new Car());
        parkingLotManager.park(new Car());

        assertEquals("The parking lot is full.", parkingLotManager.getLastErrorMessage());
    }
    //Story 6
    //Given: ParkingLotManager,Super smart parking boy, 2 parking lot with different amount of empty positions rate
    //When: ParkingLotManager told super smart parking boy park car, he should park the car to the parking lot with more space rate
    //Then: the car fetched should be fetched from parking lot with more space rate.
    @Test
    void should_Manger_told_super_smart_parking_boy_always_park_car_to_parking_slot_with_more_space_rate(){
        final int capacity = 5;
        ParkingLot parkingLotA = new ParkingLot();
        ParkingLot parkingLotB = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        parkingLots.add(parkingLotB);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        parkingBoys.add(superSmartParkingBoy);
        ParkingLotManager parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        Car randomCar = new Car();
        // What if two car part have the same space rate?
        // Current logic should be the first one.
        parkingLotManager.park(randomCar);
        Car expectedCar = new Car();
        ParkingTicket parkingTicket = parkingLotManager.toldParkingBoyToPark(superSmartParkingBoy, expectedCar);
        Car actualCar = superSmartParkingBoy.fetch(parkingTicket);

        assertSame(expectedCar, actualCar);
        assertSame(parkingLotB, parkingTicket.getParkingLot());
    }
    //Story 6
    //Given: ParkingLotManager,Super smart parking boy, a parking lot with limited space
    //When: ParkingLotManager told super smart parking boy park car, he should return error message
    //Then: Manager should get the same message.
    @Test
    void should_manger_tell_error_msg_when_telling_parking_boy_to_park_car_to_full_parking_lot(){
        final int capacity = 1;
        ParkingLot parkingLotA = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        parkingBoys.add(superSmartParkingBoy);
        ParkingLotManager parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        Car randomCar = new Car();
        // What if two car part have the same space rate?
        // Current logic should be the first one.
        parkingLotManager.park(randomCar);
        Car expectedCar = new Car();
        parkingLotManager.toldParkingBoyToPark(superSmartParkingBoy, expectedCar);

        assertEquals("The parking lot is full.", parkingLotManager.getErrorMessageFromParkingBoy(superSmartParkingBoy));
    }
    //Story 6
    //Given: ParkingLotManager,Super smart parking boy, a parking lot, a wrong ticket
    //When: ParkingLotManager told super smart parking boy to fetch car, he should return error message
    //Then: Manager should get the same message.
    @Test
    void should_manger_tell_error_msg_when_telling_parking_boy_to_fetch_car_with_wrong_ticket(){
        final int capacity = 1;
        ParkingLot parkingLotA = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        parkingBoys.add(superSmartParkingBoy);
        ParkingLotManager parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        Car randomCar = new Car();
        // What if two car part have the same space rate?
        // Current logic should be the first one.
        parkingLotManager.park(randomCar);
        Car expectedCar = new Car();
        parkingLotManager.toldParkingBoyToPark(superSmartParkingBoy, expectedCar);
        ParkingTicket wrongTicket = new ParkingTicket();
        parkingLotManager.toldParkingBoyToFetchCar(superSmartParkingBoy, wrongTicket);

        assertEquals("Unrecognized parking ticket.", parkingLotManager.getErrorMessageFromParkingBoy(superSmartParkingBoy));
    }

    //Story 6
    //Given: ParkingLotManager,Super smart parking boy, a parking lot, a empty ticket
    //When: ParkingLotManager told super smart parking boy to fetch car, he should return error message
    //Then: Manager should get the same message.
    @Test
    void should_manger_tell_error_msg_when_telling_parking_boy_to_fetch_car_with_no_ticket(){
        final int capacity = 1;
        ParkingLot parkingLotA = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLotA);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        parkingBoys.add(superSmartParkingBoy);
        ParkingLotManager parkingLotManager = new ParkingLotManager(parkingLots, parkingBoys);

        Car randomCar = new Car();
        // What if two car part have the same space rate?
        // Current logic should be the first one.
        parkingLotManager.park(randomCar);
        Car expectedCar = new Car();
        parkingLotManager.toldParkingBoyToPark(superSmartParkingBoy, expectedCar);
        parkingLotManager.toldParkingBoyToFetchCar(superSmartParkingBoy, null);

        assertEquals("Please provide your parking ticket.", parkingLotManager.getErrorMessageFromParkingBoy(superSmartParkingBoy));
    }
}

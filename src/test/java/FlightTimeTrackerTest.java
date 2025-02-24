import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTimeTrackerTest {
    @Test
    void testLogFlightTime() {
        Specialist johnDoe = new Specialist("John Doe");
        johnDoe.addFlight(new Flight("Airbus A320", "1234", LocalDateTime.of(2025, 12, 15, 8, 0),
                LocalDateTime.of(2025, 12, 15, 10, 0), Arrays.asList("John Doe")));
        johnDoe.addFlight(new Flight("Boeing 737", "5678", LocalDateTime.of(2025, 12, 16, 11, 0),
                LocalDateTime.of(2025, 12, 16, 13, 0), Arrays.asList("John Doe")));

        Specialist janeSmith = new Specialist("Jane Smith");
        janeSmith.addFlight(new Flight("Airbus A320", "1235", LocalDateTime.of(2025, 12, 17, 9, 0),
                LocalDateTime.of(2025, 12, 17, 11, 0), Arrays.asList("Jane Smith")));

        List<Specialist> specialists = Arrays.asList(johnDoe, janeSmith);

        FlightTimeLog flightTimeLog = new FlightTimeLog();
        flightTimeLog.logFlightTime(specialists);

        assertEquals(4.0, johnDoe.getFlightHoursForMonth("DECEMBER 2025"));
        assertEquals(2.0, janeSmith.getFlightHoursForMonth("DECEMBER 2025"));
    }

    @Test
    void testOver80HoursFlagForMultipleSpecialists() {
        Specialist johnDoe = new Specialist("John Doe");
        johnDoe.addFlight(new Flight("Airbus A320", "1234", LocalDateTime.of(2025, 12, 1, 8, 0),
                LocalDateTime.of(2025, 12, 1, 20, 0), Arrays.asList("John Doe")));
        johnDoe.calculateFlightHours();

        Specialist janeSmith = new Specialist("Jane Smith");
        janeSmith.addFlight(new Flight("Airbus A320", "1235", LocalDateTime.of(2025, 12, 1, 9, 0),
                LocalDateTime.of(2025, 12, 30, 18, 0), Arrays.asList("Jane Smith")));
        janeSmith.calculateFlightHours();

        List<Specialist> specialists = Arrays.asList(johnDoe, janeSmith);

        FlightTimeLog flightTimeLog = new FlightTimeLog();
        flightTimeLog.logFlightTime(specialists);

        assertFalse(johnDoe.getOver80HoursFlag("DECEMBER 2025"));
        assertTrue(janeSmith.getOver80HoursFlag("DECEMBER 2025"));
    }
}
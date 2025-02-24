import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpecialistTest {
    @Test
    void testCalculateFlightHours() {
        Specialist specialist = new Specialist("John Doe");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        specialist.addFlight(new Flight("Airbus A320", "1234", LocalDateTime.parse("2025-12-15T08:00:00", formatter),
                LocalDateTime.parse("2025-12-15T10:00:00", formatter), Arrays.asList("John Doe")));
        specialist.addFlight(new Flight("Boeing 737", "5678", LocalDateTime.parse("2025-12-16T11:00:00", formatter),
                LocalDateTime.parse("2025-12-16T13:00:00", formatter), Arrays.asList("John Doe")));

        specialist.calculateFlightHours();

        assertEquals(4.0, specialist.getFlightHoursForMonth("DECEMBER 2025"), 0.001);
    }

    @Test
    void testOver80HoursFlag() {
        Specialist specialist = new Specialist("John Doe");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        specialist.addFlight(new Flight("Airbus A320", "1234", LocalDateTime.parse("2025-12-01T08:00:00", formatter),
                LocalDateTime.parse("2025-12-25T20:00:00", formatter), Arrays.asList("John Doe")));
        specialist.calculateFlightHours();

        assertTrue(specialist.getOver80HoursFlag("DECEMBER 2025"));
    }
}

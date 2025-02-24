import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlightTimeLog {
    @JsonProperty
    public void logFlightTime(List<Specialist> specialists) {
        for (Specialist specialist : specialists) {
            specialist.calculateFlightHours();
        }
    }
    @JsonProperty
    public void printFlightTimeSummary(List<Specialist> specialists) {
        for (Specialist specialist : specialists) {
            System.out.println("Летчик: " + specialist.getName());
            for (String month : specialist.getFlightHoursPerMonth().keySet()) {
                System.out.println("Месяц: " + month + " - Часов полета: " + specialist.getFlightHoursForMonth(month));
                if (specialist.getOver80HoursFlag(month)) {
                    System.out.println("Больше 80 часов в месяце: YES");
                } else {
                    System.out.println("Больше 80 часов в месяце: NO");
                }
            }
        }
    }
}

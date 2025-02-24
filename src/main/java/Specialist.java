import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Specialist {
    private String name;
    private List<Flight> flights;
    private Map<String, Double> flightHoursPerMonth = new HashMap<>();
    private Map<String, Boolean> over80HoursFlag = new HashMap<>();

    public Specialist(String name) {
        this.name = name;
        this.flights = new ArrayList<>();
    }

    public void calculateFlightHours() {
        for (Flight flight : flights) {
            String month = flight.getFlightMonth();
            double currentHours = flightHoursPerMonth.getOrDefault(month, 0.0);
            flightHoursPerMonth.put(month, currentHours + flight.getFlightDurationInHours());

            setOver80HoursFlag(month);
        }
    }

    public void setOver80HoursFlag(String month) {
        double totalHours = flightHoursPerMonth.getOrDefault(month, 0.0);
        if (totalHours > 80) {
            over80HoursFlag.put(month, true);
        } else {
            over80HoursFlag.put(month, false);
        }
    }
    @JsonProperty
    public Double getFlightHoursForMonth(String month) {
        return flightHoursPerMonth.getOrDefault(month, 0.0);
    }
    @JsonProperty
    public Boolean getOver80HoursFlag(String month) {
        return over80HoursFlag.getOrDefault(month, false);
    }
    @JsonProperty
    public Map<String, Double> getFlightHoursPerMonth() {
        return flightHoursPerMonth;
    }
    @JsonProperty
    public Map<String, Boolean> getOver80HoursFlag() {
        return over80HoursFlag;
    }
    public void addFlight(Flight flight) {
        flights.add(flight);
    }
    public List<Flight> getFlights() {
        return flights;
    }
    @JsonProperty
    public String getName() {
        return name;
    }
}

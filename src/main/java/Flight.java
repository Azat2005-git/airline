import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Flight {

    private String aircraftType;
    private String aircraftNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<String> crew;

    public Flight(String aircraftType, String aircraftNumber, LocalDateTime departureTime, LocalDateTime arrivalTime, List<String> crew) {
        this.aircraftType = aircraftType;
        this.aircraftNumber = aircraftNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.crew = crew;
    }

    public long getFlightDurationInHours() {
        Duration duration = Duration.between(departureTime, arrivalTime);
        return duration.toHours();
    }
    @JsonProperty
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    @JsonProperty
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    @JsonProperty
    public String getAircraftType() {
        return aircraftType;
    }
    @JsonProperty
    public String getAircraftNumber() {
        return aircraftNumber;
    }
    @JsonProperty
    public List<String> getCrew() {
        return crew;
    }
    @JsonProperty
    public String getFlightMonth() {
        return departureTime.getMonth().toString() + " " + departureTime.getYear();
    }
}

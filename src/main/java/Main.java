import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<Specialist> specialists = loadSpecialists("src/main/resources/specialists.csv");
            List<Flight> flights = loadFlights("src/main/resources/flights.csv");

            assignFlightsToSpecialists(specialists, flights);

            FlightTimeLog flightTimeLog = new FlightTimeLog();
            flightTimeLog.logFlightTime(specialists);

            flightTimeLog.printFlightTimeSummary(specialists);

            String jsonOutput = convertToJson(specialists);
            System.out.println(jsonOutput);

            writeToJsonFile(specialists, "flight_time_log.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static void writeToJsonFile(Object object, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), object);
    }

    public static List<Specialist> loadSpecialists(String filename) throws IOException {
        List<Specialist> specialists = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String name = line.trim();
                specialists.add(new Specialist(name));
            }
        }
        return specialists;
    }

    public static List<Flight> loadFlights(String filename) throws IOException {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String aircraftType = columns[0].trim();
                String aircraftNumber = columns[1].trim();
                String departureTimeStr = columns[2].trim();
                String arrivalTimeStr = columns[3].trim();
                String crewStr = columns[4].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);
                LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);

                List<String> crew = Arrays.asList(crewStr.split(","));

                Flight flight = new Flight(aircraftType, aircraftNumber, departureTime, arrivalTime, crew);
                flights.add(flight);
            }
        }
        return flights;
    }

    public static void assignFlightsToSpecialists(List<Specialist> specialists, List<Flight> flights) {
        for (Flight flight : flights) {
            for (String crewMember : flight.getCrew()) {
                for (Specialist specialist : specialists) {
                    if (specialist.getName().equals(crewMember)) {
                        specialist.addFlight(flight);
                    }
                }
            }
        }
    }

}

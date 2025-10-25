import java.io.*;
import java.util.*;

public class RealEstateAgent {

    private static TreeSet<RealEstate> realEstates = new TreeSet<>(Comparator.comparing(RealEstate::getTotalPrice));

    // --- Load data from file ---
    public static void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                String className = parts[0].trim().toUpperCase();
                String city = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int sqm = Integer.parseInt(parts[3].trim());
                double numberOfRooms = Double.parseDouble(parts[4].trim());
                Genre genre = Genre.valueOf(parts[5].trim().toUpperCase());

                RealEstate estate;

                if (className.equals("PANEL")) {
                    int floor = Integer.parseInt(parts[6].trim());
                    boolean isInsulated = parts[7].trim().equalsIgnoreCase("yes");
                    estate = new Panel(city, price, sqm, numberOfRooms, genre, floor, isInsulated);
                } else {
                    estate = new RealEstate(city, price, sqm, numberOfRooms, genre);
                }

                realEstates.add(estate);
            }
            System.out.println("✅ Data successfully loaded from file.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Data format error: " + e.getMessage());
        }
    }

    // --- Display results and save to file ---
    public static void displayResults(String outputFile) {
        if (realEstates.isEmpty()) {
            System.out.println("⚠️ No data loaded!");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            double avgPricePerSqm = realEstates.stream()
                    .mapToDouble(RealEstate::getPrice)
                    .average()
                    .orElse(0);

            RealEstate cheapest = realEstates.stream()
                    .min(Comparator.comparing(RealEstate::getTotalPrice))
                    .orElse(null);

            RealEstate mostExpensiveBudapest = realEstates.stream()
                    .filter(r -> r.getCity().equalsIgnoreCase("Budapest"))
                    .max(Comparator.comparing(RealEstate::getTotalPrice))
                    .orElse(null);

            double totalValue = realEstates.stream()
                    .mapToDouble(RealEstate::getTotalPrice)
                    .sum();

            writer.println("Average square meter price: " + avgPricePerSqm);
            writer.println("Cheapest property price: " + (cheapest != null ? cheapest.getTotalPrice() : "N/A"));
            writer.println("Average sqm per room of most expensive in Budapest: " +
                    (mostExpensiveBudapest != null ? mostExpensiveBudapest.averageSqmPerRoom() : "N/A"));
            writer.println("Total price of all properties: " + totalValue);

            double avgTotalPrice = totalValue / realEstates.size();

            writer.println("\n--- Condominiums below average total price ---");
            realEstates.stream()
                    .filter(r -> r.getGenre() == Genre.CONDOMINIUM)
                    .filter(r -> r.getTotalPrice() <= avgTotalPrice)
                    .forEach(r -> writer.println(r.toString()));

            writer.flush();
            System.out.println(" Results written to " + outputFile);

        } catch (IOException e) {
            System.out.println(" Error writing results: " + e.getMessage());
        }
    }

    // --- Main method ---
    public static void main(String[] args) {
        String inputFile = "data.txt";   // <-- name of your input file
        String outputFile = "outputRealEstate.txt";

        loadFromFile(inputFile);
        displayResults(outputFile);
    }
}

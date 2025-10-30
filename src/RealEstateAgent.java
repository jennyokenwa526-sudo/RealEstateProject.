import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * The {@code RealEstateAgent} class is responsible for managing a collection of {@link RealEstate} objects.
 * It provides methods to load property data from a file, display results, and write them to an output file.
 * <p>
 * Logging is implemented using {@link java.util.logging.Logger} to record important events and errors.
 * Logs are written to both the console and a file named {@code realEstateApp.log}.
 * </p>
 */
public class RealEstateAgent {

    /** A collection of real estate properties sorted by total price. */
    private static TreeSet<RealEstate> realEstates =
            new TreeSet<>(Comparator.comparing(RealEstate::getTotalPrice));

    /** Logger instance for recording application events. */
    private static final Logger logger = Logger.getLogger(RealEstateAgent.class.getName());

    static {
        try {
            // Set up logging to file
            FileHandler fileHandler = new FileHandler("realEstateApp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println(" Failed to initialize logging: " + e.getMessage());
        }
    }

    /**
     * Loads property data from a specified input file and populates the collection.
     *
     * @param filename the name of the input file containing property data
     */
    public static void loadFromFile(String filename) {
        logger.info("Loading properties from file: " + filename);

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
                logger.info("Loaded property: " + estate.getCity() + " (" + estate.getGenre() + ")");
            }

            logger.info(" Data successfully loaded from file: " + filename);

        } catch (IOException e) {
            logger.log(Level.SEVERE, " Error reading file: " + filename, e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, " Data format error while reading file: " + filename, e);
        }
    }

    /**
     * Displays the computed results and writes them to an output file.
     * If no data is loaded, an appropriate warning message is logged and printed.
     *
     * @param outputFile the name of the output file to write results to
     */
    public static void displayResults(String outputFile) {
        logger.info("Preparing to display and save results to file: " + outputFile);

        if (realEstates.isEmpty()) {
            logger.warning(" No data loaded — cannot display results.");
            System.out.println(" No data loaded!");
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
            logger.info(" Results successfully written to " + outputFile);

        } catch (IOException e) {
            logger.log(Level.SEVERE, " Error writing results to file: " + outputFile, e);
        }
    }

    /**
     * The main method used for standalone execution.
     * It loads property data, processes it, and generates a summary file.
     *
     * @param args optional command-line arguments (not used)
     */
    public static void main(String[] args) {
        logger.info("Starting RealEstateAgent application...");

        String inputFile = "src/data.txt";
        String outputFile = "outputRealEstate.txt";

        loadFromFile(inputFile);
        displayResults(outputFile);

        logger.info("RealEstateAgent program finished successfully.");
    }
}

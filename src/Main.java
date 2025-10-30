import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * The {@code Main} class serves as the entry point for the RealEstate application.
 * <p>
 * It reads property data from the {@code data.txt} file, creates instances of
 * {@link RealEstate} and {@link Panel}, and prints their details to the console.
 * </p>
 * <p>
 * Logging is used to track the application's progress and errors.
 * Logs are written to the file {@code realEstateApp.log}.
 * </p>
 */
public class Main {

    /** Logger used to record informational and error messages for the application. */
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * The main method starts the RealEstate application.
     * It reads property data from a file, creates property objects, and displays them.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Configure logging to file
        try {
            FileHandler fileHandler = new FileHandler("realEstateApp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }

        logger.info("Starting RealEstate application...");

        List<RealEstate> properties = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");

                if (parts[0].equals("REALESTATE")) {
                    RealEstate r = new RealEstate(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Genre.valueOf(parts[5])
                    );
                    properties.add(r);
                    logger.info("Loaded RealEstate: " + r.getCity());
                } else if (parts[0].equals("PANEL")) {
                    Panel p = new Panel(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Genre.valueOf(parts[5]),
                            Integer.parseInt(parts[6]),
                            parts[7].equalsIgnoreCase("yes")
                    );
                    properties.add(p);
                    logger.info("Loaded Panel: " + p.getCity());
                }
            }
            logger.info("Successfully loaded " + properties.size() + " properties.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading data file", e);
        }

        // Print all properties
        for (RealEstate r : properties) {
            System.out.println(r);
        }

        logger.info("Application finished successfully.");
    }
}
